<?php
    require "conn.php";

    $ccuid = $_POST["cc_uid"]; //REMOVE THIS!!! uid in db table MUST be set to auto-increment
    $ccn= $_POST["cc_number"];
    $ccun= $_POST["cc_name"];
    $cced= $_POST["cc_exp"];
    $cci = $_POST["cc_issuer"];
    $cccvv = $_POST["cc_security"];
    $ccadd1 = $_POST["cc_add1"];
    $ccadd2 = $_POST["cc_add2"];
    $cczip = $_POST["cc_zip"];

    if($preparedQuery = $conn->prepare('SELECT * FROM nimbusiouserpayment WHERE nameoncard=? AND cardnumber=?')){
        $preparedQuery->bind_param('si', $ccun, $ccn);
        $preparedQuery->execute();

        if($preparedQuery->fetch()){
            //match found, refuse
            echo 'Error: This card already exists!';
        }
        else{
             if($preparedQuery = $conn->prepare('INSERT into nimbusiouserpayment (cardnumber, nameoncard, expdate, ccissuer, cvv, addressl1, addressl2, zipcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)')){
                $preparedQuery->bind_param('isssissi', $ccn, $ccun, $cced, $cci, $cccvv, $ccadd1, $ccadd2, $cczip); 
                if($preparedQuery->execute()){
                    echo 'Card Added!';
                }
                else{
                    echo "Error: ".$conn->error;
                }
            }
            else{
                echo "Error: ".$conn->error;
            }
        }
        $preparedQuery->close();
    }

    $conn.close();
?>