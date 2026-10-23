<?php

require_once "includes/dbconnection.php";

$user_id = $_GET['user_id'];

$sql = "SELECT COUNT(*) AS total FROM subject_images WHERE user_id='$user_id'";

$result = mysqli_query($conn,$sql);

if($result){

    $row = mysqli_fetch_assoc($result);

    echo json_encode([
        "status" => "success",
        "total" => $row['total']
    ]);

}else{

    echo json_encode([
        "status" => "error",
        "message" => "Query failed"
    ]);

}

?>