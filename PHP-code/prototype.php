<?php
    require "conn.php";

    $uid = $_GET['uid'];

    $result = array();

    if($preparedQuery = $conn->prepare('SELECT IPaddress, serverid FROM userservers WHERE uid=?')){
        
        $preparedQuery->bind_param('i', $uid);
        $preparedQuery->execute();
        $preparedQuery->store_result();
        $preparedQuery->bind_result($r_IPaddress, $r_serverid);
        while($preparedQuery->fetch()){

            if($preparedQuery2 = $conn->prepare('SELECT servicename, servicedesc, serviceprice, Memory, Processor, Storage FROM nimbusioservices WHERE serviceid=?')){
                $preparedQuery2->bind_param('i', $r_serverid);
                $preparedQuery2->execute();
                $preparedQuery2->bind_result($r_servicename, $r_servicedesc, $r_serviceprice, $r_Memory, $r_Processor, $r_Storage);

                if($preparedQuery2->fetch()){
                    
                    array_push($result, array("IPaddress"=>$r_IPaddress, "serviceid"=>$r_serverid, "servicename"=>$r_servicename, "servicedesc"=>$r_servicedesc, "serviceprice"=>$r_serviceprice, "Memory"=>$r_Memory, "Processor"=>$r_Processor, "Storage"=>$r_Storage));
                    
                }
                $preparedQuery2->close();
             }
             else{
                 echo "error: services call error";
             }
        }

        $preparedQuery->close();
    }

    echo json_encode(array("result"=>$result));
?>