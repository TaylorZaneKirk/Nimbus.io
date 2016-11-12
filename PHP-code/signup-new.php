<?php
    require "conn.php";

    $fname = $_POST["first_name"];
    $lname = $_POST["last_name"];
    $uname = $_POST["user_name"];
    $upass = $_POST["password"];
    $uemail = $_POST["user_email"];
    $uphone = $_POST["user_phone"];

    //check if there is already an account associated with specified username or email
    if($preparedQuery = $conn->prepare('SELECT * FROM nimbusiouser WHERE username=? OR email=?')){
        $preparedQuery->bind_param('ss', $uname, $uemail);
        $preparedQuery->execute();

        if($preparedQuery->fetch()){
            //match found, refuse
            echo 'Error: Account with your chosen username or email already exists!';
        }
        else{
            //No record exists, add one...
            if($preparedQuery = $conn->prepare('INSERT into nimbusiouser (username, firstname, lastname, password, email, phone) VALUES (?, ?, ?, ?, ?, ?)')){
                $preparedQuery->bind_param('ssssss', $uname, $fname, $lname, $upass, $uemail, $uphone); 
                $preparedQuery->execute();

                echo 'Account Created!';
            }
            else{
                echo "Error: ".$conn->error;
            }
        }
        $preparedQuery->close();
    }
    else{
        echo "Error: ".$conn->error;
    }
?>