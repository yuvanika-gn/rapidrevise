<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$user_id = $_GET['user_id'] ?? null;
$subject_name = $_GET['subject_name'] ?? null;

if (!$user_id || !$subject_name) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing parameters"
    ]);
    exit();
}

$stmt = $conn->prepare(
    "SELECT id, title, content, created_at
     FROM notes
     WHERE user_id = ? AND subject_name = ?
     ORDER BY id DESC"
);

$stmt->bind_param("is", $user_id, $subject_name);
$stmt->execute();

$result = $stmt->get_result();
$notes = [];

while ($row = $result->fetch_assoc()) {
    $notes[] = $row;
}

echo json_encode([
    "status" => "success",
    "data" => $notes
]);

$stmt->close();
$conn->close();
?>
