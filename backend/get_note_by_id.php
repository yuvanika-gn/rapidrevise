<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$note_id = $_GET['note_id'] ?? null;

if (!$note_id) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing note_id"
    ]);
    exit();
}

$stmt = $conn->prepare(
    "SELECT id, title, content, created_at 
     FROM notes 
     WHERE id = ?"
);

$stmt->bind_param("i", $note_id);
$stmt->execute();

$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    echo json_encode([
        "status" => "success",
        "data" => $row
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Note not found"
    ]);
}

$stmt->close();
$conn->close();
?>