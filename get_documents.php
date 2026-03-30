<?php

header("Content-Type: application/json");

require_once "includes/dbconnection.php";

$user_id = $_GET['user_id'];
$subject_name = $_GET['subject_name'];

$result = $conn->query(
"SELECT id, document_path FROM subject_documents
 WHERE user_id='$user_id' AND subject_name='$subject_name'"
);

$data = [];

while($row = $result->fetch_assoc()){
    $data[] = $row;
}

echo json_encode([
    "status"=>"success",
    "data"=>$data
]);

$conn->close();