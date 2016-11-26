<?php

require "conn.php";

//if($_SERVER['REQUEST_METHOND']=='GET'){
//$user_name = $_GET['username'];
$uid = $_GET['uid'];
$mysql_qry = "select cardnumber, nameoncard, expdate, ccissuer, cvv, addressl1, addressl2, zipcode from nimbusiouserpayment where uid like '$uid';";
$r = mysqli_query($conn ,$mysql_qry);
$res = mysqli_fetch_array($r);
$result = array();

array_push($result, array("cardnumber"=>$res['cardnumber'],"nameoncard"=>$res['nameoncard'],"expdate"=>$res['expdate'],"ccissuer"=>$res['ccissuer'],"cvv"=>$res['cvv'], "addressl1"=>$res['addressl1'], "addressl2"=>$res['addressl2'], "zipcode"=>$res['zipcode']));

echo json_encode(array("result"=>$result));
mysqli_close($conn);

?>
