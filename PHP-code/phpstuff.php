<?php
	require "conn.php";
	if($preparedQuery = $conn->prepare('SELECT * FROM userlogin WHERE username=:uname AND password=:upass')){
		$preparedQuery->bind_param('uname', $user_name);  //'?' from above query becomes $account
		$preparedQuery->bind_param('upass', $user_pass);
		$preparedQuery->execute();  //send the query to db
		$preparedQuery->bind_result($user_data);  //the result of the query can
												//ONLY be placed in $user_data, which needs to be shared between all activities
		//get result of query
		if($preparedQuery->fetch()){
			//fetch was good, do the displays
			//List working-account
			echo 'login success !!!!! Welcome ' . $user_name;
		}
		else{
			//input was good, but record does not exist in db
			echo 'Request Refused: Please check your username or password and try again';
		}
		$preparedQuery->close();    //secure close
?>

<?php
	////////////////////
	//Connect to Server
	///////////////////
	//Server creditials
	$host = 'localhost';
	$db   = 'nimbus_android';
	$user = 'nimbus_android';
	$pass = 'nimbus.io'; //please do not hack me, Passos
	$dbConnect = new mysqli($host, $user, $pass, $db);  //attempt to connect to db
	
	if(mysqli_connect_errno()){
		//Could not establish a connection to the database
		echo 'No Server Response';
		exit;
	}
?>