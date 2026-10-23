<?php
$host = "localhost";     // Database host
$username = "root";      // Database username
$password = "";          // Database password
$dbname = "rapidrevise"; // Database name
$port = 3307;            // Change if using a different port (default 3306)

$conn = new mysqli($host, $username, $password, $dbname, $port);

// Check connection
if ($conn->connect_error) {
    die(json_encode(array("status" => "error", "message" => "Database connection failed: " . $conn->connect_error)));
}
?>