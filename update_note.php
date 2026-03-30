<?php
require_once "includes/dbconnection.php";

$data = json_decode(file_get_contents("php://input"), true);

$note_id = $data['note_id'];
$title = $data['title'];
$content = $data['content'];

$sql = "UPDATE notes SET title=?, content=? WHERE id=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ssi", $title, $content, $note_id);

if ($stmt->execute()) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error"]);
}
?>