<?php
    require "conn.php";

    $result = array();

    if($preparedQuery = $conn->prepare('SELECT servicename, servicedesc, serviceprice, Memory, Processor, Storage FROM nimbusioservices WHERE serviceid<=4')){
        $preparedQuery->execute();
        $preparedQuery->store_result();
        $preparedQuery->bind_result($r_servicename, $r_servicedesc, $r_serviceprice, $r_Memory, $r_Processor, $r_Storage);
        
        while($preparedQuery->fetch()){   
            array_push($result, array("IPaddress"=>$r_IPaddress, "serviceid"=>$r_serverid, "servicename"=>$r_servicename, "servicedesc"=>$r_servicedesc, "serviceprice"=>$r_serviceprice, "Memory"=>$r_Memory, "Processor"=>$r_Processor, "Storage"=>$r_Storage));      
        }

        $preparedQuery->close();
    }

    echo json_encode(array("result"=>$result));
?>