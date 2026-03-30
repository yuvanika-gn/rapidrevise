<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$data = json_decode(file_get_contents("php://input"), true);

$note_id = $data['note_id'] ?? null;
$user_id = $data['user_id'] ?? null;

if (!$note_id || !$user_id) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing parameters"
    ]);
    exit();
}

$stmt = $conn->prepare(
    "DELETE FROM notes WHERE id = ? AND user_id = ?"
);

$stmt->bind_param("ii", $note_id, $user_id);

if ($stmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Note deleted successfully"
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
