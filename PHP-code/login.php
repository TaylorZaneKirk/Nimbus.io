<?php
	require "conn.php";
    
	if($preparedQuery = $conn->prepare('SELECT password FROM userlogin WHERE username=?')){
		$preparedQuery->bind_param('s', $user_name);  //'?' from above query becomes $account
		$preparedQuery->execute();  //send the query to db
		$preparedQuery->bind_result($user_pass);  //the result of the query can
												//ONLY be placed in $user_data, which needs to be shared between all activities
		
        //get result of query
		if($preparedQuery->fetch()){
			//fetch was good, do the displays
			//Test passwords
            if($user_pass == $password)
			    echo 'login success !!!!! Welcome ' . $user_name;
            else{
                //User exists, but wrong pass
                echo 'Request Refused: Please check username or password and try again';
            }
		}
		else{
			//input was good, but record does not exist in db
			echo 'Request Refused: Please check your username or password and try again';
		}
		$preparedQuery->close();    //secure close
    }
?>