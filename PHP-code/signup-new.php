<?php
    require "conn.php";

    $fname = $_POST["first_name"];
    $lname = $_POST["last_name"];
    $uname = $_POST["user_name"];
    $upass = $_POST["password"];
    $uemail = $_POST["user_email"];
    $uphone = $_POST["user_phone"];

    if($preparedQuery = $conn->prepare('INSERT into nimbusiouser (username, firstname, lastname, password, email, phone) VALUES (?, ?, ?, ?, ?, ?)')){
            $preparedQuery->bind_param('ssssss', $uname, $fname, $lname, $upass, $uemail, $uphone); 
            $preparedQuery->execute();

            echo 'Row inserted';
    }
    else{
        echo "Error: ".$conn->error;
    }
    $conn.close();
?>