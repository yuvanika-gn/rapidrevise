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
$apiKey = "AIzaSyDLrIDQuxVO_khuwxmE2Qc-85GWNd053yM"; // replace
$model = "gemma-3-4b-it";

$endpoint = "https://generativelanguage.googleapis.com/v1beta/models/{$model}:generateContent?key={$apiKey}";

// 🔥 PROMPT (STRUCTURED LIKE FLASHCARDS)
$instruction = "
Extract formulas from the notes.

Rules:
- Include formulas present in notes
- Also include important formulas related to topic
- Maximum 5 formulas
- Keep name short
- Keep description simple

Format exactly:

Name: Newton's Second Law
Formula: F = ma
Description: Force equals mass times acceleration

Name: Momentum
Formula: p = mv
Description: Momentum is mass times velocity
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

// 🔥 PARSE FORMULAS (LIKE FLASHCARDS)
$lines = explode("\n", $text);

$formulas = [];

$name = "";
$formula = "";
$desc = "";

foreach ($lines as $line) {

    $line = trim($line);

    if (stripos($line, "Name:") !== false) {
        $name = trim(str_replace("Name:", "", $line));
    }

    if (stripos($line, "Formula:") !== false) {
        $formula = trim(str_replace("Formula:", "", $line));
    }

    if (stripos($line, "Description:") !== false) {
        $desc = trim(str_replace("Description:", "", $line));

        if (!empty($name) && !empty($formula)) {
            $formulas[] = [
                "name" => $name,
                "equation" => $formula,
                "description" => $desc
            ];
        }
    }
}

// LIMIT TO 5
$formulas = array_slice($formulas, 0, 5);

// FINAL OUTPUT
echo json_encode([
    "status" => "success",
    "formulas" => $formulas
]);