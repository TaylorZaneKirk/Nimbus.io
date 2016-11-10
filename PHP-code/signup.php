<?php 

require "conn.php";
$fname = $_POST["first_name"];
$lname = $_POST["last_name"];
$uname = $_POST["user_name"];
$upass = $_POST["password"];
$uemail = $_POST["user_email"];
$uphone = $_POST["user_phone"];

$mysql_qry = "INSERT into nimbusiouser (username, firstname, lastname, password, email, phone) values ('$uname', '$fname', '$lname', '$upass', '$uemail', '$uphone')";

if($conn -> query($mysql_qry) == TRUE) {
echo "Row inserted";
}
else {
echo "Error: ".$conn->error;
}
$conn.close();
?>
