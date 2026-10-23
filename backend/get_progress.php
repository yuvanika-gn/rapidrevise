<?php
header("Content-Type: application/json");
require_once "includes/dbconnection.php";

$user_id = $_GET['user_id'] ?? '';

if (!$user_id) {
    echo json_encode([]);
    exit();
}

$sql = "
SELECT 
    s.id AS subject_id,
    s.subject_name,

    COUNT(CASE WHEN sa.feature_type='key_concept' THEN 1 END) AS concepts,
    COUNT(CASE WHEN sa.feature_type='flashcard' THEN 1 END) AS flashcards,
    COUNT(CASE WHEN sa.feature_type='formula' THEN 1 END) AS formulas,

    COUNT(CASE WHEN sa.action_type='correct' THEN 1 END) AS correct_answers,
    COUNT(CASE WHEN sa.action_type IN ('correct','wrong') THEN 1 END) AS total_answers

FROM subjects s
LEFT JOIN student_activity sa 
ON s.id = sa.subject_id AND sa.user_id = ?

WHERE s.user_id = ?
GROUP BY s.id
ORDER BY s.id DESC
";

$stmt = $conn->prepare($sql);
$stmt->bind_param("ii", $user_id, $user_id);
$stmt->execute();

$result = $stmt->get_result();

$data = [];

while ($row = $result->fetch_assoc()) {

    $accuracy = 0;

    if ($row['total_answers'] > 0) {
        $accuracy = ($row['correct_answers'] / $row['total_answers']) * 100;
    }

    $data[] = [
        "subject_id" => (int)$row['subject_id'],
        "subject_name" => $row['subject_name'],
        "concepts" => (int)$row['concepts'],
        "flashcards" => (int)$row['flashcards'],
        "formulas" => (int)$row['formulas'],
        "total_activity" => (int)(
            $row['concepts'] +
            $row['flashcards'] +
            $row['formulas']
        ),
        "accuracy" => round($accuracy, 2)
    ];
}

echo json_encode($data);
?>