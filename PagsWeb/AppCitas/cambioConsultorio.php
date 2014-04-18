<?php
	include_once("config.php");
	$id = $_GET['id'];
	$instruccion = $_GET['instruccion'];

	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	$rows = mysql_query( "SELECT * FROM Usuarios WHERE id = ". mysql_real_escape_string($id, $conn ));
	$row = mysql_fetch_array($rows, MYSQL_ASSOC);
?>
<!DOCTYPE html>
<html>
	<head></head>
	<body>
		<form method="post" action="listar_usuarios.php">
			<input type="hidden" name="id" value="<?=$row['id']?>" />
			<input type="hidden" name="instruccion" value="<?=$instruccion?>" />
			<input type="text" name="nombre" value="<?=$row['nombre']?>" />
			<br>
			<select name="tipo">
				<option value="0" <?=($row["tipo"]==0?"selected":"")?>>
					Administrador
				</option>
				<option value="1" <?=($row["tipo"]==1?"selected":"")?>>
					Cliente
				</option>
			</select>
			<br>
			<input type="text" name="fecha_nacimiento" value="<?=$row['fecha_nacimiento']?>" />
			<br>
			<input type="submit" name="enviar" value="Guardar">
			<input type="submit" name="cancelar" value="Cancelar">
		</form>
	</body>
</html>
