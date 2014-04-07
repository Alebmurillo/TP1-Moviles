<!DOCTYPE html>
<html>
	<head><title>MySQL Table Viewer</title></head>
	<body>
	<a href="index.php">Volver</a>
<?php
	include_once("config.php");
	$table = "appointment";
        $name = "Editar Cita";
        
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
        if(isset($_POST["enviar"]))
	{     
            mysql_query("UPDATE appointment SET date='".$_POST['date']."', startTime ='".$_POST['stime']."', endTime='".$_POST['etime']."',doctor=".$_POST['doctor'].",user=".$_POST['user'].",place=".$_POST['consultorio']." WHERE idAppointment=".$_POST['id']);
      
        }          
        else if (isset($_GET["eliminar"])){             
            $indexDelete =$_GET["id"];
            $querry = mysql_query("DELETE FROM {$table} WHERE idAppointment = ".$indexDelete);          
        }
        
    $result = mysql_query("SELECT idAppointment, date, startTime, endTime, nameDoctor, nameUser,name,numConsultorio FROM appointment,doctor,user, clinica, consultorio WHERE idClinic = idclinica and user =idUser  and doctor =idDoctor and place = idConsultorio");
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
    echo "<td>Fecha</td><td>Hora de inicio</td><td>Hora de final</td><td>Doctor</td><td>Usuario</td><td>Clinica</td><td>Consultorio</td>";
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
            <a href='updateCita.php?id=".$cell."&eliminar=t'>Update</a>

            <a href='editarCita.php?id=".$cell."&eliminar=t'>Delete</a>
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