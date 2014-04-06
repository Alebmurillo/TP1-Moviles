<!DOCTYPE html>
<html>
	<head>
            <title>Clinic Calendar</title></head>
	<body>
            <form method="post" action="showTable.php">
            <h1 align="center">Clinic Calendar</h1>
            <h2 align="center">Ventana de mantenimiento</h2>
            
            <h3 align="center">Seleccione la tabla a modificar</h3>
            <center> 
            <?php
                include_once("config.php");
                $con=mysql_connect($server, $db_user, $db_pass)or die("cannot connect"); 
                mysql_select_db($db)or die("cannot select DB");
                $sql = "SHOW TABLES FROM $db";

                $result = mysql_query($sql);

                if (!$result) {
                    echo "DB Error, could not list tables\n";
                    echo 'MySQL Error: ' . mysql_error();
                    exit;
            }
            echo "<select name='tabla'>";
            while ($row = mysql_fetch_row($result)) {
                echo "<option value='".$row[0]."' > {$row[0]} </option>";
            }
            echo "</select>";
            mysql_free_result($result);
        ?>                
                <br>
			<input type="submit" name="enviar" value="Consultar">              
                </form>
                </center> 
	</body>
</html>
