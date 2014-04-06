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
            mysql_query("INSERT INTO consultorio (IdClinica, numConsultorio) VALUES(".$_POST['tipo'].",".$_POST['num_consultorio'].") ");
            echo "Insertado";
            echo $_POST['tipo'];
            mysql_close($conn);
            }
        
?><!DOCTYPE html>
<html>
	<head></head>
	<body>
            <a href="index.php">Cancelar</a>
		<form method="post" action="crearconsultorio.php">
                        <br>Clinica
                        <select name='tipo'>
                        <?php
                        $conn = mysql_connect($server,  $db_user, $db_pass);
                        if  (!$conn) 
                        {
                        die('No pudo conectarse: ' . mysql_error());
                        }
                        mysql_select_db($db);
                        $rows = mysql_query( "SELECT idClinic, name FROM clinica", $conn );
                                                
                        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
					{
                        
                        echo "<option value=".$row['idClinic'].">";
			echo $row['name'];
                        echo "</option>";
                                        
                        }                              
                        mysql_close($conn);
                       ?>
                        </select>
			<br>Numero de consultorio
			<input type="text" name="num_consultorio" />
                        <br>
			<input type="submit" name="enviar" value="Guardar">
		</form>
	</body>
</html>