<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$user_id = $_GET['user_id'] ?? null;
$keyword = $_GET['keyword'] ?? "";

if(!$user_id){
    echo json_encode([
        "status"=>"error",
        "message"=>"Missing user_id"
    ]);
    exit();
}

$keyword = "%".$keyword."%";

$data = [];

/* ---------------- SEARCH NOTES ---------------- */

$stmt = $conn->prepare(
"SELECT id, title AS name, 'note' AS type
 FROM notes
 WHERE user_id = ? AND title LIKE ?"
);

$stmt->bind_param("is",$user_id,$keyword);
$stmt->execute();
$result = $stmt->get_result();

while($row = $result->fetch_assoc()){
    $data[] = $row;
}


/* ---------------- SEARCH DOCUMENTS ---------------- */

$stmt = $conn->prepare(
"SELECT id, document_path AS name, document_path AS file, 'document' AS type
 FROM subject_documents
 WHERE user_id = ? AND document_path LIKE ?"
);

$stmt->bind_param("is",$user_id,$keyword);
$stmt->execute();
$result = $stmt->get_result();

while($row = $result->fetch_assoc()){
    $data[] = $row;
}


/* ---------------- SEARCH URLS ---------------- */

$stmt = $conn->prepare(
"SELECT id, url_name AS name, url_link AS url, 'url' AS type
FROM subject_urls
WHERE user_id = ? AND url_name LIKE ?"
);

$stmt->bind_param("is",$user_id,$keyword);
$stmt->execute();
$result = $stmt->get_result();

while($row = $result->fetch_assoc()){
    $data[] = $row;
}


echo json_encode([
    "status"=>"success",
    "data"=>$data
]);

$conn->close();
?>