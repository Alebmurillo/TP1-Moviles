<!DOCTYPE html>
<html>
	<head></head>
	<body>
	<a href="index.php">Volver</a>
<?php
	include_once("config.php");
	$instruccion = $_POST["instruccion"];
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	if(isset($_POST["enviar"]))
	{
		if($instruccion=="I")
		{
			mysql_query("INSERT INTO Usuarios (nombre, tipo, fecha_nacimiento) VALUES('".$_POST['nombre']."',".$_POST['tipo'].",'".$_POST['fecha_nacimiento']."') ");
		}
		else if($instruccion=="U")
		{
			mysql_query("UPDATE Usuarios SET nombre = '".$_POST['nombre']."', tipo=".$_POST['tipo'].",fecha_nacimiento='".$_POST['fecha_nacimiento']."' WHERE id=".$_POST['id']);
		}
		else if($instruccion=="D")
		{
			mysql_query( "DELETE FROM Usuarios WHERE id = ".$_POST['id'], $conn );
		}
	}
	$rows = mysql_query( "SELECT id, nombre FROM Usuarios", $conn );
?>

		<a href="usuario_detalle.php?instruccion=I">Agregar nuevo usuario</a>
		<table>
			<thead>
				<th>
					<td>
						Id
					</td>
					<td>
						Nombre
					</td>
					<td>
						Update
					</td>
					<td>
						Delete
					</td>
				</th>
			</thead>
			<tbody>
				<?php
					while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
					{
				?>
						<tr>
							<td>
								<?=$row['id']?>
							</td>
							<td>
								<?=$row['nombre']?>
							<td/>
							<td>
								<a href="usuario_detalle.php?id=<?=$row['id']?>&instruccion=U">Update</a>
							</td>
							<td>
								<a href="usuario_detalle.php?id=<?=$row['id']?>&instruccion=D">Delete</a>
							</td>
						</tr>
				<?php
					}
					mysql_close($conn);
				?>
			</tbody>
		</table>
	</body>
</html>
