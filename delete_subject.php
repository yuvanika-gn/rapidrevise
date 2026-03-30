<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$data = json_decode(file_get_contents("php://input"), true);

$subject_id = $data['subject_id'] ?? '';
$user_id = $data['user_id'] ?? '';

if (empty($subject_id) || empty($user_id)) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing data"
    ]);
    exit();
}

// Delete subject ONLY for that user (safety)
$query = "DELETE FROM subjects WHERE id = ? AND user_id = ?";
$stmt = $conn->prepare($query);
$stmt->bind_param("ii", $subject_id, $user_id);

if ($stmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Subject deleted successfully"
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Delete failed"
    ]);
}

$stmt->close();
$conn->close();
?>
