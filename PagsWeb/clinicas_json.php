<?php

	include_once("config.php");
	$con=mysql_connect($server, $db_user, $db_pass)or die("cannot connect");
	mysql_select_db($db)or die("cannot select DB");
	$sql = "SELECT * FROM clinica";
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
