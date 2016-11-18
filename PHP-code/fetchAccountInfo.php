<?php

require "conn.php";

//if($_SERVER['REQUEST_METHOND']=='GET'){
//$user_name = $_GET['username'];
$user_name = 'vcmandalapu';
$mysql_qry = "select uid, firstname, lastname, email, phone from nimbusiouser where username like '$user_name';";
$r = mysqli_query($conn ,$mysql_qry);
$res = mysqli_fetch_array($r);
$result = array();

array_push($result, array("uid"=>$res['uid'],"firstname"=>$res['firstname'],"lastname"=>$res['lastname'],"email"=>$res['email'],"phone"=>$res['phone']));

echo json_encode(array("result"=>$result));
mysqli_close($conn);
//}
?>
