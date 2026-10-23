<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

// GET INPUT
$data = json_decode(file_get_contents("php://input"), true);
$subject = $data['subject_name'] ?? '';

if (empty($subject)) {
    echo json_encode([
        "status" => "error",
        "message" => "Subject missing"
    ]);
    exit();
}

// FETCH NOTES
$sql = "SELECT content FROM notes WHERE subject_name=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $subject);
$stmt->execute();
$result = $stmt->get_result();

$allNotes = "";
while ($row = $result->fetch_assoc()) {
    $allNotes .= $row['content'] . " ";
}

if (empty($allNotes)) {
    echo json_encode([
        "status" => "error",
        "message" => "No notes found"
    ]);
    exit();
}

// 🔥 GEMINI CONFIG
$apiKey = "AIzaSyDLrIDQuxVO_khuwxmE2Qc-85GWNd053yM";
$model = "gemma-3-4b-it";

$endpoint = "https://generativelanguage.googleapis.com/v1beta/models/{$model}:generateContent?key={$apiKey}";

// 🔥 PROMPT (QUIZ STRUCTURE)
$instruction = "
Generate a multiple choice quiz from the notes.

Rules:
- Questions must be based on notes
- Number of questions depends on length of notes (5–10 max)
- Each question must have 4 options
- Only one correct answer
- Keep questions simple and clear

Format exactly:

Question: What is Newton's Second Law?
Option1: F = ma
Option2: E = mc2
Option3: V = IR
Option4: p = mv
Answer: F = ma

Question: What is momentum?
Option1: Mass × Velocity
Option2: Force × Time
Option3: Energy × Speed
Option4: Voltage × Current
Answer: Mass × Velocity
";

$finalPrompt = $instruction . "\n\n" . $allNotes;

// REQUEST BODY
$postData = [
    "contents" => [
        [
            "role" => "user",
            "parts" => [
                ["text" => $finalPrompt]
            ]
        ]
    ]
];

// CURL
$ch = curl_init($endpoint);

curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, ["Content-Type: application/json"]);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($postData));

$response = curl_exec($ch);
$httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

if ($response === false) {
    echo json_encode([
        "status" => "error",
        "message" => curl_error($ch)
    ]);
    curl_close($ch);
    exit();
}

curl_close($ch);

$result = json_decode($response, true);

if ($httpCode !== 200) {
    echo json_encode([
        "status" => "error",
        "message" => "AI API failed",
        "response" => $result
    ]);
    exit();
}

// 🔥 EXTRACT AI TEXT
$text = $result['candidates'][0]['content']['parts'][0]['text'] ?? "";

// CLEAN TEXT
$text = strip_tags($text);
$text = preg_replace('/[*_#`>-]/', '', $text);

// 🔥 PARSE QUIZ
$lines = explode("\n", $text);

$quiz = [];

$question = "";
$options = [];
$answer = "";

foreach ($lines as $line) {

    $line = trim($line);

    if (stripos($line, "Question:") !== false) {
        $question = trim(str_replace("Question:", "", $line));
        $options = [];
        $answer = "";
    }

    if (stripos($line, "Option1:") !== false) {
        $options[0] = trim(str_replace("Option1:", "", $line));
    }

    if (stripos($line, "Option2:") !== false) {
        $options[1] = trim(str_replace("Option2:", "", $line));
    }

    if (stripos($line, "Option3:") !== false) {
        $options[2] = trim(str_replace("Option3:", "", $line));
    }

    if (stripos($line, "Option4:") !== false) {
        $options[3] = trim(str_replace("Option4:", "", $line));
    }

    if (stripos($line, "Answer:") !== false) {
        $answer = trim(str_replace("Answer:", "", $line));

        if (!empty($question) && count($options) === 4) {
            $quiz[] = [
                "question" => $question,
                "options" => $options,
                "correctAnswer" => $answer
            ];
        }
    }
}

// LIMIT (safety)
$quiz = array_slice($quiz, 0, 10);

// FINAL OUTPUT
echo json_encode([
    "status" => "success",
    "quiz" => $quiz
]);