<?php
    require "conn.php";

    $uid = $_GET['uid'];
    $IPaddress = $_GET['IPaddress'];
    $serviceid = $_GET['serviceid'];
    $servicename = $_GET['servicename'];
    $servicedesc = $_GET['servicedesc'];
    $serviceprice = $_GET['serviceprice'];
    $Memory = $_GET['Memory'];
    $Processor = $_GET['Processor'];
    $Storage = $_GET['Storage'];

    //generate a random IPaddress string
    $IPaddress = "".mt_rand(0,255).".".mt_rand(0,255).".".mt_rand(0,255).".".mt_rand(0,255);
    
    //User just bought a server, add their server to userservers
    if($preparedQuery = $conn->prepare('INSERT INTO userservers (uid, serverid, IPaddress) VALUES (?, ?, ?)')){
        $preparedQuery->bind_param('iis', $uid, $serviceid, $IPaddress);
        $preparedQuery->execute();
        //If using preset types, job is done. If user is using a custom type, first
        //  check to see if there is already a custom type on record that matches
        //  all configurations of THIS custom type. If there is a match, update
        //  the serviceid record on the userservers to use that matching type.
        //  If there is no match returned, create a new serviceid on the nimbusioservices
        //  and then update the serviceid record on the userservers to reflect
        //  the new serviceid
        $preparedQuery->store_result();
        $preparedQuery->close();
    }
        
    if($serviceid == 0){ //Is custom?

        //Check if this custom type already has a record
        if($preparedQuery2 = $conn->prepare('SELECT serviceid FROM nimbusioservices WHERE servicename=? AND servicedesc=? AND Processor=? AND Memory=? AND Storage=?')){
            $preparedQuery2->bind_param('sssii', $servicename, $servicedesc, $Processor, $Memory, $Storage);
            $preparedQuery2->execute();
            $preparedQuery2->store_result();
            $preparedQuery2->bind_result($newserviceid);

            if($preparedQuery2->fetch()){ //Found a match, update userservers and use this type instead
                //Pretty much do nothing
            }
            else{ //No match found, create new type
                //Get the max value from the serviceid column of the nimbusioservices

                if($preparedQuery3 = $conn->prepare('SELECT MAX(serviceid) FROM nimbusioservices')){
                    $preparedQuery3->execute();
                    $preparedQuery3->store_result();
                    $preparedQuery3->bind_result($maxType);
                    $preparedQuery3->fetch();
                    $newserviceid = $maxType + 1;
                    $preparedQuery3->close();
                }

                //Insert new type record into nimbusioservices, we want serviceid to be set 
                //  to AUTO-INCREMENT on the backend-side for new records that are inserted
                //  into the table of the nimbusioservices. Ideally, this incremented record should
                //  match the value of the variable $newserviceid at this point
                if($preparedQuery4 = $conn->prepare('INSERT INTO nimbusioservices (serviceid, servicename, servicedesc, serviceprice, Processor, Memory, Storage) VALUES (?,?,?,?,?,?,?)')){
                    $preparedQuery4->bind_param('issdsii', $newserviceid, $servicename, $servicedesc, $serviceprice, $Processor, $Memory, $Storage);
                    $preparedQuery4->execute();
                    $preparedQuery4->store_result();
                    $preparedQuery4->close();
                }
            }
            //Now we update userservers to reflect changes for custom type
            //UPDATE userservers SET serviceid=$newserviceid WHERE uid=$uid AND serviceid=serviceid
            if($preparedQuery5 = $conn->prepare('UPDATE userservers SET serverid=? WHERE IPaddress=?')){
                $preparedQuery5->bind_param('is', $newserviceid, $IPaddress);
                $preparedQuery5->execute();
                $preparedQuery5->store_result();
                $preparedQuery5->close();
            }
            $preparedQuery2->close();
        }
    }
?>