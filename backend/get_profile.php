<?php

header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$user_id = $_GET['user_id'];

$response = [];

$stmt = $conn->prepare("SELECT name,email FROM users WHERE id=?");
$stmt->bind_param("i",$user_id);
$stmt->execute();
$result = $stmt->get_result();
$user = $result->fetch_assoc();

$subjects = $conn->query("SELECT COUNT(*) as total FROM subjects WHERE user_id=$user_id")->fetch_assoc();
$documents = $conn->query("SELECT COUNT(*) as total FROM subject_documents WHERE user_id=$user_id")->fetch_assoc();
$images = $conn->query("SELECT COUNT(*) as total FROM subject_images WHERE user_id=$user_id")->fetch_assoc();

$response["status"] = "success";
$response["name"] = $user["name"];
$response["email"] = $user["email"];
$response["subjects"] = $subjects["total"];
$response["documents"] = $documents["total"];
$response["images"] = $images["total"];

echo json_encode($response);