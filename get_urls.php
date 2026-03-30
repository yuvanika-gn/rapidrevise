<?php

require_once "includes/dbconnection.php";

$user_id = $_GET['user_id'];
$subject_name = $_GET['subject_name'];

$sql = "SELECT id,url_name,url_link 
        FROM subject_urls 
        WHERE user_id='$user_id' 
        AND subject_name='$subject_name'";

$result = mysqli_query($conn,$sql);

$data = [];

while($row = mysqli_fetch_assoc($result)){
    $data[] = $row;
}

echo json_encode([
    "status"=>"success",
    "data"=>$data
]);

?>