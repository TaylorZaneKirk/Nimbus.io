<?php
    require "conn.php";

    $ccuid = $_POST["cc_uid"];
    $ccn= $_POST["cc_number"];
    $ccun= $_POST["cc_name"];
    $cced= $_POST["cc_exp"];
    $cci = $_POST["cc_issuer"];
    $cccvv = $_POST["cc_security"];
    $ccadd1 = $_POST["cc_add1"];
    $ccadd2 = $_POST["cc_add2"];
    $cczip = $_POST["cc_zip"];

    if($preparedQuery = $conn->prepare('SELECT * FROM nimbusiouserpayment WHERE uid=?')){
        $preparedQuery->bind_param('i', $ccuid);
        $preparedQuery->execute();

        if($preparedQuery->fetch()){
            $preparedQuery->close();
            if($preparedQuery2 = $conn->prepare('UPDATE nimbusiouserpayment SET cardnumber=?, nameoncard=?, expdate=?, ccissuer=?, cvv=?, addressl1=?, addressl2=?, zipcode=? WHERE uid=?')){
                $preparedQuery2->bind_param('isssissii', $ccn, $ccun, $cced, $cci, $cccvv, $ccadd1, $ccadd2, $cczip, $ccuid);
                $preparedQuery2->execute();
                $preparedQuery2->close();
                echo "Card Updated!";
            }
            else{
                echo "Error: ".$conn->error;
            }
        }
        else{
            if($preparedQuery3 = $conn->prepare('INSERT into nimbusiouserpayment (uid, cardnumber, nameoncard, expdate, ccissuer, cvv, addressl1, addressl2, zipcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)')){
                $preparedQuery3->bind_param('iisssissi', $ccuid, $ccn, $ccun, $cced, $cci, $cccvv, $ccadd1, $ccadd2, $cczip); 
                if($preparedQuery3->execute()){
                    echo 'Card Added!';
                }
                else{
                    echo "Error: ".$conn->error;
                }
            }
            else{
                echo "Error: ".$conn->error;
            }
            $preparedQuery3->close();
        }
    }
?>