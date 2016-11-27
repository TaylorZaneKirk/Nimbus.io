<?php
    require "conn.php";

    $uid = $_GET['uid'];

    if($preparedQuery = $conn->prepare('SELECT cardnumber, nameoncard, expdate, ccissuer, cvv, addressl1, addressl2, zipcode FROM nimbusiouserpayment WHERE uid=?')){
        
        $preparedQuery->bind_param('i', $uid);
        $preparedQuery->execute();
        $preparedQuery->bind_result($r_cardnumber, $r_nameoncard, $r_expdate, $r_ccissuer, $r_cvv, $r_addressl1, $r_addressl2, $r_zipcode);
        if($preparedQuery->fetch()){
            $result = array();
            array_push($result, array("cardnumber"=>$r_cardnumber, "nameoncard"=>$r_nameoncard, "expdate"=>$r_expdate, "ccissuer"=>$r_ccissuer, "cvv"=>$r_cvv, "addressl1"=>$r_addressl1, "addressl2"=>$r_addressl2, "zipcode"=>$r_zipcode));
            echo json_encode(array("result"=>$result));
        }
        else{
            echo "error: Record does not exist.";
        }
    }

    $preparedQuery->close();
?>