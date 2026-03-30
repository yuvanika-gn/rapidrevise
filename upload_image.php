<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$user_id = $_POST['user_id'] ?? null;
$subject_name = $_POST['subject_name'] ?? null;

if (!$user_id || !$subject_name || !isset($_FILES['image'])) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing parameters"
    ]);
    exit();
}

$targetDir = "uploads/";
if (!is_dir($targetDir)) {
    mkdir($targetDir, 0777, true);
}

$fileName = time() . "_" . basename($_FILES["image"]["name"]);
$targetFilePath = $targetDir . $fileName;

if (move_uploaded_file($_FILES["image"]["tmp_name"], $targetFilePath)) {

    $stmt = $conn->prepare(
        "INSERT INTO subject_images (user_id, subject_name, image_path)
         VALUES (?, ?, ?)"
    );

    $stmt->bind_param("iss", $user_id, $subject_name, $targetFilePath);

    if ($stmt->execute()) {
        echo json_encode([
            "status" => "success",
            "message" => "Image uploaded successfully"
        ]);
    } else {
        echo json_encode([
            "status" => "error",
            "message" => "Database insert failed"
        ]);
    }

    $stmt->close();

} else {
    echo json_encode([
        "status" => "error",
        "message" => "Image upload failed"
    ]);
}

$conn->close();
?>
