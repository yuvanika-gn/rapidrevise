<?php
header("Content-Type: application/json");

// Include DB connection
require_once "includes/dbconnection.php";

// Read JSON input
$data = json_decode(file_get_contents("php://input"), true);

$fullname = trim($data['fullname'] ?? '');
$email    = trim($data['email'] ?? '');
$password = trim($data['password'] ?? '');

// Basic validation
if (empty($fullname) || empty($email) || empty($password)) {
    echo json_encode([
        "status" => "error",
        "message" => "All fields are required"
    ]);
    exit();
}

// Allow only @gmail.com emails
if (!preg_match("/^[a-zA-Z0-9._%+-]+@gmail\.com$/", $email)) {
    echo json_encode([
        "status" => "error",
        "message" => "Only @gmail.com emails are allowed"
    ]);
    exit();
}

// Check if email already exists (multi-user support)
$checkQuery = "SELECT id FROM signup WHERE email = ?";
$checkStmt = $conn->prepare($checkQuery);
$checkStmt->bind_param("s", $email);
$checkStmt->execute();
$checkStmt->store_result();

if ($checkStmt->num_rows > 0) {
    echo json_encode([
        "status" => "error",
        "message" => "Email already registered"
    ]);
    exit();
}
$checkStmt->close();

// Insert user (ID auto-incremented)
$insertQuery = "INSERT INTO signup (fullname, email, password) VALUES (?, ?, ?)";
$insertStmt = $conn->prepare($insertQuery);
$insertStmt->bind_param("sss", $fullname, $email, $password);

if ($insertStmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Signup successful",
        "user_id" => $insertStmt->insert_id
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Signup failed"
    ]);
}

$insertStmt->close();
$conn->close();
?>
