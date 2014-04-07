<?php
	include_once("config.php");
	$id = $_GET['id'];
        echo $id;
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	$rows = mysql_query( "SELECT idAppointment, date, startTime, endTime, place, doctor, user FROM appointment,doctor,user, clinica, consultorio WHERE idClinic = idclinica and user =idUser  and doctor =idDoctor and place = idConsultorio and idAppointment=".$id, $conn );
	$row = mysql_fetch_array($rows, MYSQL_ASSOC);
?>
<!DOCTYPE html>
<html>
	<head></head>
	<body>
		<form method="post" action="editarCita.php">
			<input type="hidden" name="id" value="<?=$row['idAppointment']?>" />
			<br>
			<input type="date" name="date" value="<?=$row['date']?>" />
                        <br>
			<input type="time" name="stime" value="<?=$row['startTime']?>" />
                        <br>
			<input type="time" name="etime" value="<?=$row['endTime']?>" />
			<br>
                        
                         <select name='consultorio'>
                        <?php 
                            $iddoctor=$row['doctor'];
                            $iduser=$row['user'];
                            $idconsultorio=$row['place'];
                                   
                        $rows = mysql_query( "SELECT idConsultorio, name, numConsultorio FROM consultorio,clinica WHERE idClinic=idClinica GROUP BY idConsultorio", $conn );
                                                
                        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
					{
                        
                        echo "<option value=".$row['idConsultorio'];
                        if($idconsultorio == $row['idConsultorio']){
                            echo " selected";
                        }
                        echo ">";
			echo $row['name']."- Numero de consultorio ".$row['numConsultorio'];
                        echo "</option>";
                                        
                        }      
                                    ?>
			</select>
                        <br>
                        <select name='doctor'>
                        <?php 
                                                          
                        $rows = mysql_query( "SELECT idDoctor, nameDoctor FROM doctor", $conn );
                                                
                        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
					{
                        
                        echo "<option value=".$row['idDoctor'];
                        if($iddoctor == $row['idDoctor']){
                            echo " selected";
                        }
                        echo ">";
			echo $row['nameDoctor'];
                        echo "</option>";
                                        
                        }      
                                    ?>
			</select>
                        <br>
                        <select name='user'>
                        <?php 
                                                          
                        $rows = mysql_query( "SELECT idUser, nameUser FROM user", $conn );
                                                
                        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
					{
                        
                        echo "<option value=".$row['idUser'];
                        if($iduser == $row['idUser']){
                            echo " selected";
                        }
                        echo ">";
			echo $row['nameUser'];
                        echo "</option>";
                                        
                        }      
                                    ?>
			</select>
                        <br>
			<input type="submit" name="enviar" value="Guardar">
			<input type="submit" name="cancelar" value="Cancelar">
		</form>
	</body>
</html>
