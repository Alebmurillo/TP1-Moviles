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
            mysql_query("INSERT INTO doctor (nameDoctor, cel, tel, especialidad) VALUES('".$_POST['nombre']."',".$_POST['celular'].",".$_POST['telefono'].",".$_POST['especialidad'].") ");
            echo "Insertado";
            echo "INSERT INTO doctor (nameDoctor, cel, tel, especialidad) VALUES('".$_POST['nombre']."',".$_POST['celular'].",".$_POST['telefono'].",".$_POST['especialidad'].") ";
            mysql_close($conn);
            }
        
?><!DOCTYPE html>
<html>
	<head></head>
	<body>
            <a href="index.php">Cancelar</a>
		<form method="post" action="crearDoctor.php">
                        Nombre
        		<input type="text" name="nombre" />
			<br>Celular
			<input type="text" name="celular" />
			<br>Telefono
			<input type="text" name="telefono" />
                        <br>facebook
                        <input type="text" name="facebook" />
                        <br>Especialidad
                        <input type="text" name="especialidad" />
                        <br>
			<input type="submit" name="enviar" value="Guardar">
		</form>
	</body>
</html>