<?php
header("Content-Type: application/json");

require_once "includes/dbconnection.php";

// Get JSON input
$data = json_decode(file_get_contents("php://input"), true);

$user_id = $data['user_id'] ?? 0;

if ($user_id == 0) {
    echo json_encode([
        "status" => "error",
        "message" => "User ID required"
    ]);
    exit();
}

// 1. Get user details
$userQuery = "SELECT fullname, email FROM signup WHERE id = ?";
$stmt = $conn->prepare($userQuery);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows == 0) {
    echo json_encode([
        "status" => "error",
        "message" => "User not found"
    ]);
    exit();
}

$user = $result->fetch_assoc();
$stmt->close();

// 2. Get subject count
$subjectQuery = "SELECT COUNT(*) as total FROM subjects WHERE user_id = ?";
$stmt = $conn->prepare($subjectQuery);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$subjectResult = $stmt->get_result()->fetch_assoc();
$subjects = $subjectResult['total'];
$stmt->close();

// 3. Get document count
$docQuery = "SELECT COUNT(*) as total FROM subject_documents WHERE user_id = ?";
$stmt = $conn->prepare($docQuery);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$docResult = $stmt->get_result()->fetch_assoc();
$documents = $docResult['total'];
$stmt->close();

// 4. Get image count
$imageQuery = "SELECT COUNT(*) as total FROM subject_images WHERE user_id = ?";
$stmt = $conn->prepare($imageQuery);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$imageResult = $stmt->get_result()->fetch_assoc();
$images = $imageResult['total'];
$stmt->close();

// Final response
echo json_encode([
    "status" => "success",
    "name" => $user['fullname'],
    "email" => $user['email'],
    "subjects" => $subjects,
    "documents" => $documents,
    "images" => $images
]);

$conn->close();
?>