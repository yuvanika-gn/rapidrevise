<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$data = json_decode(file_get_contents("php://input"), true);
$subject = $data['subject_name'] ?? '';

if (empty($subject)) {
    echo json_encode([
        "status" => "error",
        "message" => "No subject provided"
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

// 🔐 API KEY
$apiKey = "AIzaSyDLrIDQuxVO_khuwxmE2Qc-85GWNd053yM";
$model = "gemma-3-4b-it";

$endpoint = "https://generativelanguage.googleapis.com/v1beta/models/{$model}:generateContent?key={$apiKey}";

// 🔥 BETTER PROMPT
$instruction = "
Summarize the notes into key points.

Rules:
- 4 to 8 points
- Each point should have:
    Title: (2-3 words)
    Point: (1 line explanation)
- No symbols
- No numbering

Format exactly like:
Title: ...
Point: ...
";

$finalPrompt = $instruction . "\n\n" . $allNotes;

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

curl_close($ch);

$result = json_decode($response, true);

if ($httpCode !== 200) {
    echo json_encode(["status" => "error"]);
    exit();
}

// 🔥 EXTRACT TEXT
$text = $result['candidates'][0]['content']['parts'][0]['text'] ?? "";

$text = preg_replace('/[*_#`>-]/', '', $text);

// SPLIT BLOCKS
$blocks = preg_split('/\n\s*\n/', trim($text));

$points = [];

foreach ($blocks as $block) {

    $lines = explode("\n", trim($block));

    $title = "";
    $point = "";

    foreach ($lines as $line) {
        if (stripos($line, "Title:") !== false) {
            $title = trim(str_replace("Title:", "", $line));
        }
        if (stripos($line, "Point:") !== false) {
            $point = trim(str_replace("Point:", "", $line));
        }
    }

    if (!empty($title) && !empty($point)) {
        $points[] = [
            "title" => $title,
            "point" => $point
        ];
    }
}

echo json_encode([
    "status" => "success",
    "points" => $points
]);
?>