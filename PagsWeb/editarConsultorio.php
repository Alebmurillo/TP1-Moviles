<!DOCTYPE html>
<html>
	<head><title>MySQL Table Viewer</title></head>
	<body>
	<a href="index.php">Volver</a>
<?php
	include_once("config.php");
	$table = "consultorio";
        $name = "Editar Consultorio";
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	
    $result = mysql_query("SELECT idConsultorio, numConsultorio, name FROM {$table}, clinica WHERE idClinic = idclinica");
    if (!$result) {
        die("Query to show fields from table failed");
    }

    $fields_num = mysql_num_fields($result);
    echo "<center>";
    echo "<h1>{$name}</h1>";
    echo "<table border='1.5'><tr>";
    // printing table headers
    $field = mysql_fetch_field($result);
    echo "<td>EDITAR</td>";
    echo "<td>Numero de consultorio</td><td>Clinica</td>";
    echo "</tr>\n";
    // printing table rows
    while($row = mysql_fetch_row($result))
    {
        

        // $row is array... foreach( .. ) puts every element
        // of $row to $cell variable
        $varId = 0;
        foreach($row as $cell)
        {
        if ($varId == 0){
            echo "<tr>";
            echo "
                <td>
            <a href='usuario_detalle.php?id=<?=".$cell."?>&instruccion=U'>Update</a>

            <a href='usuario_detalle.php?id=<?=".$cell."?>&instruccion=D'>Delete</a>
            \n
            </td>";
            
            $varId = 1;
            
        }
            else {echo "<td>$cell</td>";         
        }
        }
    }
    mysql_free_result($result);
    
    ?></form>
    </body></html>