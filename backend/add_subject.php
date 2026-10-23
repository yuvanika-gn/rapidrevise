<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$data = json_decode(file_get_contents("php://input"), true);

$user_id = $data['user_id'] ?? '';
$subject = trim($data['subject_name'] ?? '');

if (empty($user_id) || empty($subject)) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing data"
    ]);
    exit();
}

$query = "INSERT INTO subjects (user_id, subject_name) VALUES (?, ?)";
$stmt = $conn->prepare($query);
$stmt->bind_param("is", $user_id, $subject);

if ($stmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Subject added successfully"
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Failed to add subject"
    ]);
}

$stmt->close();
$conn->close();
?>
