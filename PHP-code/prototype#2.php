<?php
    require "conn.php";

    $uid = $_GET['uid'];
    //$IPaddress = $_GET['IPaddress'];
    //$serviceid = $_GET['serviceid'];
    $servicename = $_GET['servicename'];
    $servicedesc = $_GET['servicedesc'];
    $serviceprice = 4.99;
    $Memory = $_GET['Memory'];
    $Processor = $_GET['Processor'];
    $Storage = $_GET['Storage'];

    $uid = 2;
    $servicename = "blah";
    $servicedesc = "test";
    $Memory = 512;
    $Processor = "Xenon";
    $Storage = 10000;

    //generate a random IPaddress string
    $IPaddress = "".mt_rand(0,255).".".mt_rand(0,255).".".mt_rand(0,255).".".mt_rand(0,255);

    if($preparedQuery3 = $conn->prepare('SELECT MAX(serviceid) FROM nimbusioservices')){
        $preparedQuery3->execute();
        $preparedQuery3->store_result();
        $preparedQuery3->bind_result($maxType);
        $preparedQuery3->fetch();
        $serviceid = $maxType + 1;
        $preparedQuery3->close();
    }

    //Insert new type record into nimbusioservices, we want serviceid to be set 
    //  to AUTO-INCREMENT on the backend-side for new records that are inserted
    //  into the table of the nimbusioservices. Ideally, this incremented record should
    //  match the value of the variable $newserviceid at this point
    if($preparedQuery4 = $conn->prepare('INSERT INTO nimbusioservices (serviceid, servicename, servicedesc, serviceprice, Processor, Memory, Storage) VALUES (?,?,?,?,?,?,?)')){
        $preparedQuery4->bind_param('issdsii', $serviceid, $servicename, $servicedesc, $serviceprice, $Processor, $Memory, $Storage);
        $preparedQuery4->execute();
        $preparedQuery4->store_result();
        $preparedQuery4->close();
        echo "test1";
    }
    
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
        echo "Test2 " . $IPaddress . " " . $serviceid;    
    }
?>