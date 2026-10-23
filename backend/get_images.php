<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$user_id = $_GET['user_id'] ?? '';
$subject_name = $_GET['subject_name'] ?? '';

if (empty($user_id) || empty($subject_name)) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing parameters"
    ]);
    exit();
}

$stmt = $conn->prepare(
    "SELECT id, image_path FROM subject_images
     WHERE user_id = ? AND subject_name = ?
     ORDER BY id DESC"
);

$stmt->bind_param("is", $user_id, $subject_name);
$stmt->execute();

$result = $stmt->get_result();

$data = [];

while ($row = $result->fetch_assoc()) {
    $data[] = $row;
}

echo json_encode([
    "status" => "success",
    "data" => $data
]);

$stmt->close();
$conn->close();
?>