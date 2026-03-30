<?php

header("Content-Type: application/json");

require_once "includes/dbconnection.php";

$response = [];

// Check parameters
if (!isset($_POST['user_id']) || !isset($_POST['subject_name'])) {

    $response["status"] = "error";
    $response["message"] = "Missing parameters";

    echo json_encode($response);
    exit();
}

// Check document
if (!isset($_FILES['document'])) {

    $response["status"] = "error";
    $response["message"] = "No document uploaded";

    echo json_encode($response);
    exit();
}

$user_id = $_POST['user_id'];
$subject_name = $_POST['subject_name'];

$target_dir = "uploads/documents/";

// Create folder if not exists
if (!file_exists($target_dir)) {
    mkdir($target_dir, 0777, true);
}

// File name
$file_name = time() . "_" . basename($_FILES["document"]["name"]);
$target_file = $target_dir . $file_name;

// Move file
if (move_uploaded_file($_FILES["document"]["tmp_name"], $target_file)) {

    $stmt = $conn->prepare("INSERT INTO subject_documents (user_id, subject_name, document_path) VALUES (?, ?, ?)");
    $stmt->bind_param("iss", $user_id, $subject_name, $target_file);
    $stmt->execute();

    $response["status"] = "success";
    $response["message"] = "Document uploaded";
    $response["path"] = $target_file;

} else {

    $response["status"] = "error";
    $response["message"] = "Upload failed";
}

echo json_encode($response);

$conn->close();
?>