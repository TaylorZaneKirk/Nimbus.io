<?php
    require "conn.php";

    $uid = $_GET['uid'];

    if($preparedQuery = $conn->prepare('SELECT ipAddress, serverType FROM serverTable WHERE uid=?')){
        
        $preparedQuery->bind_param('i', $uid);
        $preparedQuery->execute();
        $preparedQuery->bind_result($r_ipAddress, $r_serverType);
        if($preparedQuery->fetch()){

            if($preparedQuery = $conn->prepare('SELECT serverName, serverDesc, serverRAM, serverProc, serverSpace FROM servicesTable WHERE serverType=?')){
                $preparedQuery->bind_param('i', $r_serverType);
                $preparedQuery->execute();
                $preparedQuery->bind_result($r_serverName, $r_serverDesc, $r_serverRAM, $r_serverProc, $r_serverSpace);

                if($preparedQuery->fetch()){
                    $result = array();
                    array_push($result, array("serverName"=>$r_serverName, "serverDesc"=>$r_serverDesc, "serverRam"=>$r_serverRAM, "serverProc"=>$r_serverProc, "serverSpace"=>$r_serverSpace));
                    echo json_encode(array("result"=>$result));
                }
             }
        }
        else{
            echo "error: Record does not exist.";
        }
    }

    $preparedQuery->close();
?>