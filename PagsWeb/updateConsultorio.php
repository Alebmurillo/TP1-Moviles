<?php
	include_once("config.php");
	$id = $_GET['id'];
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	$rows = mysql_query( "SELECT idConsultorio, numConsultorio, name, idClinic FROM consultorio, clinica WHERE idClinic = idclinica and idConsultorio = ".$id, $conn );
	$row = mysql_fetch_array($rows, MYSQL_ASSOC);
?>
<!DOCTYPE html>
<html>
	<head></head>
	<body>
		<form method="post" action="editarConsultorio.php">
			<input type="hidden" name="id" value="<?=$row['idConsultorio']?>" />
			<br>
			<input type="text" name="numConsultorio" value="<?=$row['numConsultorio']?>" />
			<br>
			<input type="text" name="clinicName" value="<?=$row['name']?>" />
                         <select name='clinic'>
                        <?php 
                            $clinic=$row['idClinic'];
                        $rows = mysql_query( "SELECT idClinic, name FROM clinica, consultorio WHERE idClinica=".$clinic, $conn );
                                                
                        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
					{
                        
                        echo "<option value=".$row['idClinic'].">";
			echo $row['name'];
                        echo "</option>";
                                        
                        }      
                                    ?>
			</select>
			<input type="submit" name="enviar" value="Guardar">
			<input type="submit" name="cancelar" value="Cancelar">
		</form>
	</body>
</html>
