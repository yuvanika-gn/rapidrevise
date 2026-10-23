<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

// Get input
$data = json_decode(file_get_contents("php://input"), true);
$subject_name = $data['subject_name'] ?? '';

if (empty($subject_name)) {
    echo json_encode([
        "status" => "error",
        "message" => "No subject_name provided"
    ]);
    exit();
}

// 1. Fetch all content for this subject
$query = "SELECT content FROM notes WHERE subject_name = ?";
$stmt = $conn->prepare($query);
$stmt->bind_param("s", $subject_name);
$stmt->execute();
$result = $stmt->get_result();

$allContent = "";

while ($row = $result->fetch_assoc()) {
    $allContent .= " " . $row['content'];
}

if (empty($allContent)) {
    echo json_encode([
        "status" => "error",
        "message" => "No notes found for this subject"
    ]);
    exit();
}

// 🔐 SAME API KEY
$apiKey = "AIzaSyDLrIDQuxVO_khuwxmE2Qc-85GWNd053yM";

// ✅ SAME MODEL
$model = "gemma-3-4b-it";

// SAME ENDPOINT
$endpoint = "https://generativelanguage.googleapis.com/v1beta/models/{$model}:generateContent?key={$apiKey}";

// 🔥 NEW PROMPT FOR KEY POINTS
$instruction = "
Convert the content into SHORT KEY POINTS.

Rules:
- Use bullet points
- Keep it very short
- Only important concepts
- No explanations
- Easy for revision before exam
";

$finalPrompt = $instruction . "\n\nContent:\n" . $allContent;

// Request body (same structure)
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

// Curl
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
        "message" => "API failed",
        "response" => $result
    ]);
    exit();
}

// Extract output
$keypoints = $result['candidates'][0]['content']['parts'][0]['text'] 
    ?? "No key points generated";

// Clean
$keypoints = strip_tags($keypoints);
$keypoints = preg_replace('/[*_#`>-]/', '', $keypoints);
$keypoints = trim($keypoints);

// Final response
echo json_encode([
    "status" => "success",
    "summary" => $keypoints
]);

$conn->close();
?>