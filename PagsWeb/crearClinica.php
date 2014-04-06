<?php
	if(isset($_POST["enviar"]))
	{
              include_once("config.php");
            $conn = mysql_connect($server,  $db_user, $db_pass);
            if  (!$conn) 
            {
            die('No pudo conectarse: ' . mysql_error());
            }
            mysql_select_db($db);
            mysql_query("INSERT INTO clinica (name, latitud, longitud) VALUES('".$_POST['nombre']."',".$_POST['latitud'].",".$_POST['longitud'].") ");
            echo "Insertado";
            mysql_close($conn);
            }
        
?><!DOCTYPE html>
<html>
	<head></head>
	<body>
            <a href="index.php">Cancelar</a>
		<form method="post" action="crearClinica.php">
                        Nombre
        		<input type="text" name="nombre" />
			<br>Latitud
			<input type="text" name="latitud" />
			<br>Longitud
			<input type="text" name="longitud" />
                        <br>
			<input type="submit" name="enviar" value="Guardar">
		</form>
	</body>
</html>