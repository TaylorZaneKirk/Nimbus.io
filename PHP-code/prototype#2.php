<?php
    require "conn.php";

    $uid = $_GET['uid'];
    //$ipAddress = $_GET['ipAddress'];
    $serverType = $_GET['serverType'];
    $serverName = $_GET['serverName'];
    $serverDesc = $_GET['serverDesc'];
    $serverRAM = $_GET['serverRAM'];
    $serverProc = $_GET['serverProc'];
    $serverSpace = $_GET['serverSpace'];

    //generate a random ipAddress string
    $ipAddress = "".mt_rand(0,255).".".mt_rand(0,255).".".mt_rand(0,255).".".mt_rand(0,255);

    //User just bought a server, add their server to ServerTable
    if($preparedQuery = $conn->prepare('INSERT INTO serverTable (uid, ipAddress, serverType) VALUES (?,?,?)')){
        $preparedQuery->bind_param('isi', $uid, $ipAddress, $serverType);
        $preparedQuery->execute();
        //If using preset types, job is done. If user is using a custom type, first
        //  check to see if there is already a custom type on record that matches
        //  all configurations of THIS custom type. If there is a match, update
        //  the serverType record on the ServerTable to use that matching type.
        //  If there is no match returned, create a new serverType on the ServicesTable
        //  and then update the serverType record on the ServerTable to reflect
        //  the new serverType
        
        if($serverType == 0){ //Is custom?

            //Check if this custom type already has a record
            if($preparedQuery = $conn->prepare('SELECT serverType FROM servicesTable WHERE serverName=? AND serverDesc=? AND serverProc=? AND serverRAM=? AND serverSpace=?')){
                $preparedQuery->bind_param('sssss', $serverName, $serverDesc, $serverProc, $serverRAM, $serverSpace);
                $preparedQuery->execute();
                $preparedQuery->bind_result($newServerType);

                if($preparedQuery->fetch()){ //Found a match, update ServerTable and use this type instead
                    //Pretty much do nothing
                }
                else{ //No match found, create new type
                    //Get the max value from the serverType column of the ServicesTable
                    if($preparedQuery = $conn->prepare('SELECT MAX(serverType) FROM servicesTable')){
                        $preparedQuery->execute();
                        $preparedQuery->bind_result($maxType);

                        $newServerType = $maxType + 1;

                        //Insert new type record into ServicesTable, we want serverType to be set 
                        //  to AUTO-INCREMENT on the backend-side for new records that are inserted
                        //  into the table of the ServicesTable. Ideally, this incremented record should
                        //  match the value of the variable $newServerType at this point
                        if($preparedQuery = $conn->prepare('INSERT INTO servicesTable (serverName, serverDesc, serverProc, serverRAM, serverSpace) VALUES (?,?,?,?,?)')){
                            $preparedQuery->bind_param('sssss', $serverName, $serverDesc, $serverProc, $serverRAM, $serverSpace);
                            $preparedQuery->execute();
                        }
                    }
                }

                //Now we update ServerTable to reflect changes for custom type
                    //UPDATE serverTable SET serverType=$newServerType WHERE uid=$uid AND serverType=serverType
                if($preparedQuery = $conn->prepare('UPDATE serverTable SET serverType=? WHERE uid=? AND serverType=?')){
                        $preparedQuery->bind_param('iii', $newServerType, $uid, $serverType)
                        $preparedQuery->execute();
                }
            }
        }

        $preparedQuery->close();
    }
?>