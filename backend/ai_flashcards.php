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

// 🔥 FETCH NOTES FROM DB
$sql = "SELECT content FROM notes WHERE subject_name = ?";
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

// 🔥 GEMINI API
$apiKey = "AIzaSyDLrIDQuxVO_khuwxmE2Qc-85GWNd053yM"; // ⚠️ Replace
$model = "gemma-3-4b-it";

$endpoint = "https://generativelanguage.googleapis.com/v1beta/models/{$model}:generateContent?key={$apiKey}";

// 🔥 PROMPT
$instruction = "
Create flashcards from the following notes.

Rules:
- Generate 5 flashcards
- Question must be a natural question
- Start with words like What, Why, How
- Question length: 5 to 8 words
- Keep it simple and clear
- Answer must be short and clear
- No numbering

Format exactly:

Q: What is the main principle of motion?
A: It explains how objects move under force

Q: Why is chlorophyll important in plants?
A: It helps absorb sunlight for photosynthesis
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

// CURL REQUEST
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

// CLEAN
$text = strip_tags($text);
$text = preg_replace('/[*_#`>-]/', '', $text);

// 🔥 PARSE FLASHCARDS
$lines = explode("\n", $text);

$flashcards = [];
$q = "";
$a = "";

foreach ($lines as $line) {

    $line = trim($line);

    if (stripos($line, "Q:") !== false) {
        $q = trim(str_replace("Q:", "", $line));
    }

    if (stripos($line, "A:") !== false) {
        $a = trim(str_replace("A:", "", $line));

        if (!empty($q) && !empty($a)) {
            $flashcards[] = [
                "question" => $q,
                "answer" => $a
            ];
        }
    }
}

// LIMIT TO 5
$flashcards = array_slice($flashcards, 0, 5);

// FINAL RESPONSE
echo json_encode([
    "status" => "success",
    "flashcards" => $flashcards
]);
?>