if($preparedQuery = $dbConnect->prepare('SELECT balance FROM accounts WHERE account_id=?')){
                        $preparedQuery->bind_param('s', $account);  //'?' from above query becomes $account
                        $preparedQuery->execute();  //send the query to db
                        $preparedQuery->bind_result($balance);  //the result of the query can
                                                                //ONLY be placed in $balance
                        //get result of query
                        if($preparedQuery->fetch()){
                            //fetch was good, do the displays
                            //List working-account
                            echo 'Current Account: ' . $account . '<br>';
                            //Display balance of the account to the user
                            echo 'Your Current Balance: $' . $balance;
                        }
                        else{
                            //input was good, but record does not exist in db
                            echo 'Request Refused: Please check your Account Number and try again';
                        }
                        $preparedQuery->close();    //secure close