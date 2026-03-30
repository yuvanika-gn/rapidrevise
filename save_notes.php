<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$user_id = $_POST['user_id'] ?? '';
$subject_name = $_POST['subject_name'] ?? '';
$title = $_POST['title'] ?? '';
$content = $_POST['content'] ?? '';

if (empty($user_id) || empty($subject_name) || empty($content)) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing fields"
    ]);
    exit();
}

// Insert into DB
$query = "INSERT INTO notes (user_id, subject_name, title, content, created_at) 
          VALUES (?, ?, ?, ?, NOW())";

$stmt = $conn->prepare($query);
$stmt->bind_param("isss", $user_id, $subject_name, $title, $content);

if ($stmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Saved successfully"
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Failed to save"
    ]);
}

$stmt->close();
$conn->close();
?>