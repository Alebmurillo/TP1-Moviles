<?php
include_once("config.php");
	if(isset($_POST["enviar"]))
	{
            $conn = mysql_connect($server,  $db_user, $db_pass);
            if  (!$conn) 
            {
            die('No pudo conectarse: ' . mysql_error());
            }
            mysql_select_db($db);
            mysql_query("INSERT INTO appointment (date, startTime, endTime, doctor, user, place) VALUES('".$_POST['date']."','".$_POST['startTime']."','".$_POST['endTime']."',".$_POST['doctor'].",".$_POST['user'].",".$_POST['consultorio'].") ");
            echo "Insertado";
            mysql_close($conn);
            }
        
?><!DOCTYPE html>
<html>
	<head></head>
	<body>
            <a href="index.php">Cancelar</a>
		<form method="post" action="crearCita.php">
                    
                        <br>Doctor
                        <select name='doctor'>
                        <?php
                        $conn = mysql_connect($server,  $db_user, $db_pass);
                        if  (!$conn) 
                        {
                        die('No pudo conectarse: ' . mysql_error());
                        }
                        mysql_select_db($db);
                        $rows = mysql_query( "SELECT idDoctor, nameDoctor FROM doctor", $conn );
                                                
                        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
					{
                        
                        echo "<option value=".$row['idDoctor'].">";
					echo $row['nameDoctor'];
                                        echo "</option>";
                                        
                                        }
                                        echo $row['id'];
                                        
                       ?>
                        </select>
                         <br>Usuario
                        <select name='user'>
                        <?php                        
                        $rows = mysql_query( "SELECT idUser, nameUser FROM user", $conn );
                                                
                        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
					{
                        
                        echo "<option value=".$row['idUser'].">";
					echo $row['nameUser'];
                                        echo "</option>";
                                        
                                        }
                                        echo $row['id'];
                       ?>
                        </select>
                          <br>Consultorio
                        <select name='consultorio'>
                        <?php
                        $rows = mysql_query( "SELECT idConsultorio, idClinica, numConsultorio, name FROM consultorio, clinica WHERE idClinica=idClinic GROUP BY idConsultorio", $conn );
                                                
                        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
					{
                        
                        echo "<option value=".$row['idConsultorio'].">";
					echo $row['numConsultorio']."     ".$row['name'];
                                        
                                        echo "</option>";
                                        
                                        }
                                        echo $row['id'];
                                        mysql_close($conn);
                       ?>
                        </select>
			
                        <br>
                        
			
                Fecha:
                <input type="date" name='date' size='9' value="" /> 
                <br>
                Hora inicial:
                <input type="time" name='startTime' size='9' value="" /> 

                Hora Final:
                 <input type="time" name='endTime' size='9' value="" /> 
                <time datetime="YYYY-MM-DDThh:mm:ssTZD"> 
                <br>

                <input type="submit" name="enviar" value="Guardar">
                        
		</form>
	</body>
</html>