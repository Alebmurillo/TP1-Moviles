<?php
   include_once("config.php");
   $usr = $_POST['user'];
   $doc = $_POST['doctor'];
   $place = $_POST['place'];
   $initDate = $_POST['initDate'];
   $endDate = $_POST['endDate'];         
    
   $conn = mysql_connect($server,  $db_user, $db_pass);
   if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}	
    mysql_select_db($db);
    mysql_query("INSERT INTO appointment (date, duration, doctor, user, place) VALUES('".$initDate."','".$endDate."',".$doc.",".$usr.",".$place.")");
    $sql = "SELECT * FROM appointment";
    $result = mysql_query($sql);
    $json = array();
    if(mysql_num_rows($result))
    {
        while($row=mysql_fetch_row($result))
	{
            $json['emp_info'][]=$row;
	}
    }
    mysql_close();
    echo json_encode($json); 
?> 