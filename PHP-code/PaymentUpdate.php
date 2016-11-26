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

$mysql_qry = "INSERT into nimbusiouserpayment (uid, cardnumber, nameoncard, expdate, ccissuer, cvv, addressl1, addressl2, zipcode) values ('$ccuid','$ccn', '$ccun', '$cced', '$cci', '$cccvv', '$ccadd1', '$ccadd2', '$cczip')";

if($conn -> query($mysql_qry) == TRUE) {
echo "Row inserted";
}
else {
echo "Error: ".$conn->error;
}
$conn.close();

?>

// Need your help to change the code is a way that it will check and update the existing information in the table (UPDATE statement for payment info) and it should 
create a new field if it does not have a row in the table.
// Please let me know when done.
