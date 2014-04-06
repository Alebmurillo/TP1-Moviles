<!DOCTYPE html>
<html>
	<head><title>MySQL Table Viewer</title></head>
	<body>
	<a href="index.php">Volver</a>
<?php
	include_once("config.php");
	$table = $_GET["table"];
        $name = $_GET["id"];
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	
    $result = mysql_query("SELECT * FROM {$table}");
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
    for($i=1; $i<$fields_num; $i++)
    {
        $field = mysql_fetch_field($result);
        echo "<td>{$field->name}</td>";
    }
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