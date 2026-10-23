<?php

header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$data = json_decode(file_get_contents("php://input"), true);

$id = $data['id'] ?? null;
$new_name = $data['new_name'] ?? null;

if(!$id || !$new_name){
    echo json_encode([
        "status"=>"error",
        "message"=>"Missing parameters"
    ]);
    exit();
}

$stmt = $conn->prepare("SELECT document_path FROM subject_documents WHERE id=?");
$stmt->bind_param("i",$id);
$stmt->execute();
$result = $stmt->get_result();
$row = $result->fetch_assoc();

if(!$row){
    echo json_encode([
        "status"=>"error",
        "message"=>"Document not found"
    ]);
    exit();
}

$oldPath = $row['document_path'];

$extension = pathinfo($oldPath, PATHINFO_EXTENSION);

$newFileName = $new_name . "." . $extension;

$newPath = dirname($oldPath) . "/" . $newFileName;

if(rename($oldPath,$newPath)){

    $update = $conn->prepare("UPDATE subject_documents SET document_path=? WHERE id=?");
    $update->bind_param("si",$newPath,$id);
    $update->execute();

    echo json_encode([
        "status"=>"success"
    ]);

}else{

    echo json_encode([
        "status"=>"error",
        "message"=>"Rename failed"
    ]);
}

$conn->close();
?>
