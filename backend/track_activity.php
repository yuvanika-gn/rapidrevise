<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$data = json_decode(file_get_contents("php://input"), true);

$user_id = $data['user_id'] ?? '';
$subject_id = $data['subject_id'] ?? '';
$feature_type = $data['feature_type'] ?? '';
$action_type = $data['action_type'] ?? '';
$time_spent = $data['time_spent'] ?? 0;

if (!$user_id || !$subject_id || !$feature_type || !$action_type) {
    echo json_encode(["status" => "error"]);
    exit();
}

$sql = "INSERT INTO student_activity 
(user_id, subject_id, feature_type, action_type, time_spent) 
VALUES (?, ?, ?, ?, ?)";

$stmt = $conn->prepare($sql);
$stmt->bind_param("iissi", $user_id, $subject_id, $feature_type, $action_type, $time_spent);

if ($stmt->execute()) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error"]);
}
?>