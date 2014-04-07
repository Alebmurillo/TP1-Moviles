<?php

	include_once("config.php");

	 $usuario = $_POST['usuario'];

	$con=mysql_connect($server, $db_user, $db_pass)or die("cannot connect");
	mysql_select_db($db)or die("cannot select DB");
	if($usuario!=""){
			$sql = "SELECT * FROM appointment ";
	}else{
			$sql = "SELECT appointment.date,appointment.startTime,doctor.nameDoctor,clinica.name,appointment.idAppointment FROM appointment INNER JOIN doctor ON appointment.doctor=doctor.iddoctor INNER JOIN clinica ON appointment.place=clinica.idClinic";
	}

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
