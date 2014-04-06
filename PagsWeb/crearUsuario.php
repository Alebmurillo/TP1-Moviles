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
            mysql_query("INSERT INTO user (nameUser, tel, sex,facebook) VALUES('".$_POST['nombre']."',".$_POST['telefono'].",'".$_POST['sexo']."','".$_POST['facebook']."') ");
            echo "Insertado";
            mysql_close($conn);
            }
        
?><!DOCTYPE html>
<html>
	<head></head>
	<body>
            <a href="index.php">Cancelar</a>
		<form method="post" action="crearUsuario.php">
                        Nombre
        		<input type="text" name="nombre" />
			<br>Celular
			<input type="text" name="celular" />
			<br>Telefono
			<input type="text" name="telefono" />
			<br>Sexo
                        <input type="text" name="sexo" />
                        <br>facebook
                        <input type="text" name="facebook" />
                        <br>
			<input type="submit" name="enviar" value="Guardar">
		</form>
	</body>
</html>