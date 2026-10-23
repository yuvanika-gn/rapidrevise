<?php

require_once "includes/dbconnection.php";

$user_id = $_POST['user_id'];
$subject = $_POST['subject_name'];
$url_name = $_POST['url_name'];
$url_link = $_POST['url_link'];

$sql="INSERT INTO subject_urls(user_id,subject_name,url_name,url_link)
VALUES('$user_id','$subject','$url_name','$url_link')";

if(mysqli_query($conn,$sql)){

 echo json_encode([
   "status"=>"success",
   "message"=>"URL stored"
 ]);

}else{

 echo json_encode([
   "status"=>"error",
   "message"=>"Database error"
 ]);

}
?>