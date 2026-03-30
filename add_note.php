<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$data = json_decode(file_get_contents("php://input"), true);

$user_id      = $data['user_id'] ?? null;
$subject_name = trim($data['subject_name'] ?? '');
$title        = trim($data['title'] ?? '');
$content      = trim($data['content'] ?? '');

if (!$user_id || empty($subject_name) || empty($title) || empty($content)) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing required fields"
    ]);
    exit();
}

$stmt = $conn->prepare(
    "INSERT INTO notes (user_id, subject_name, title, content)
     VALUES (?, ?, ?, ?)"
);

$stmt->bind_param("isss", $user_id, $subject_name, $title, $content);

if ($stmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Note saved successfully"
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Database insert failed"
    ]);
}

$stmt->close();
$conn->close();
?>
