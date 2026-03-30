<?php

header("Content-Type: application/json");

require_once "includes/dbconnection.php";

$data = json_decode(file_get_contents("php://input"), true);

$id = $data['id'] ?? '';

if(!$id){
    echo json_encode(["status"=>"error","message"=>"Missing ID"]);
    exit();
}

$stmt = $conn->prepare("SELECT document_path FROM subject_documents WHERE id=?");
$stmt->bind_param("i",$id);
$stmt->execute();
$result = $stmt->get_result();
$row = $result->fetch_assoc();

if($row){

    $filePath = $row['document_path'];

    if(file_exists($filePath)){
        unlink($filePath);
    }

    $deleteStmt = $conn->prepare("DELETE FROM subject_documents WHERE id=?");
    $deleteStmt->bind_param("i",$id);
    $deleteStmt->execute();

    echo json_encode(["status"=>"success"]);

}else{

    echo json_encode(["status"=>"error","message"=>"Document not found"]);
}

$conn->close();