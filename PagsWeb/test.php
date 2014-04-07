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
    mysql_query("INSERT INTO appointment (startTime, endTime, doctor, user, place) VALUES('".$initDate."','".$endDate."',".$doc.",".$usr.",".$place.")");
	$sql = "SELECT appointment.date,appointment.startTime,doctor.nameDoctor,clinica.name,appointment.idAppointment FROM appointment INNER JOIN doctor ON appointment.doctor=doctor.iddoctor INNER JOIN clinica ON appointment.place=clinica.idClinic";
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