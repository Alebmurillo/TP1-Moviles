<?php
//imprime en la pagina el menu de actualizacion de un consultorio
function showUpdateRoom(){   
    include("../config.php");
	$id = $_GET['id'];
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	$rows = mysql_query( "SELECT idConsultorio, numConsultorio, name, idClinic FROM consultorio, clinica WHERE idClinic = idclinica and idConsultorio = ".$id, $conn );
	$row = mysql_fetch_array($rows, MYSQL_ASSOC);
    ?>
<h2>Edit Room</h2>
<input type="hidden" name="id" value="<?=$row['idConsultorio']?>" />
<br> Room:
<input type="text" name="numConsultorio" value="<?=$row['numConsultorio']?>" />
<br>Current Clinic:
<input type="text" name="clinicName" value="<?=$row['name']?>" />
<br>New Clinic:
 <select name='clinic'>

<?php 
$clinic=$row['idClinic'];
$rows = mysql_query( "SELECT idClinic, name FROM clinica, consultorio WHERE idClinica=".$clinic, $conn );
//muestras las clinicas que coinciden con el nombre buscado
while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
                {

echo "<option value=".$row['idClinic'].">";
echo $row['name'];
echo "</option>";

}      
?>
</select>
<br> <br>
<input class='button' type="submit" name="enviarConsultorio" value="Save">
<input class= 'button' type="submit" name="cancelar" value="Cancel">
<br>
<input class= 'button' type="submit" name="DeleteRoom" value="Delete">
<?php  
}
//imprime en la pagina el menu de actualizacion de una cita
function showUpdateAppointment(){
    include("../config.php");
	$id = $_GET['id'];
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	$rows = mysql_query( "SELECT idAppointment, date, startTime, endTime, doctor, user FROM appointment WHERE idAppointment=".$id, $conn );
	$row = mysql_fetch_array($rows, MYSQL_ASSOC);
        ?>
        <h2> Edit Appointment</h2>
                <input type="hidden" name="id" value="<?=$row['idAppointment']?>" />
                <br>Date:
                <input type="date" name="date" value="<?=$row['date']?>" />
                <br>Start:
                <input type="time" name="stime" value="<?=$row['startTime']?>" />
                <br>
                <br>Doctor:
                <select name='doctor'>
                <?php 
                $iddoctor = $row['doctor'];
                $iduser = $row['user'];
                $rows = mysql_query( "SELECT idDoctor, nameDoctor FROM doctor", $conn );

                while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
                                {

                echo "<option value=".$row['idDoctor'];
                if($iddoctor == $row['idDoctor']){
                    echo " selected";
                }
                echo ">";
                echo $row['nameDoctor'];
                echo "</option>";

                }      
                            ?>
                </select>
                <br> User:
                <select name='user'>
                <?php 

                $rows = mysql_query( "SELECT idUser, nameUser FROM user", $conn );

                while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
                                {

                echo "<option value=".$row['idUser'];
                if($iduser == $row['idUser']){
                    echo " selected";
                }
                echo ">";
                echo $row['nameUser'];
                echo "</option>";

                }      
                            ?>
                </select>
                <br>
                <input class='button' type="submit" name="enviarCita" value="Save">
                <input class= 'button' type="submit" name="cancelar" value="Cancel">
                <br>
                <input class= 'button' type="submit" name="DeleteDate" value="Delete">
                        <?php
}
//imprime en la pagina el menu de actualizacion de doc
function showUpdateDoctor(){
include("../config.php");
	$id = $_GET['id'];
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	$rows = mysql_query( "SELECT iddoctor, nameDoctor, tel, cel, especialidad, idConsultorio FROM doctor WHERE iddoctor =".$id, $conn );
	$row = mysql_fetch_array($rows, MYSQL_ASSOC);
        $idconsultorio=$row['idConsultorio']; 
    ?>
<h2>Edit Doctor</h2>
<input type="hidden" name="id" value="<?=$row['iddoctor']?>" />
<br> Name:
<input type="text" name="nameDoctor" value="<?=$row['nameDoctor']?>" />
<br> Tel:
<input type="text" name="tel" value="<?=$row['tel']?>" />
<br> Cel:
<input type="text" name="cel" value="<?=$row['cel']?>" />
<br> Function:
<input type="text" name="especialidad" value="<?=$row['especialidad']?>" />
Room:
                 <select name='consultorio'>
<?php
$rows = mysql_query( "SELECT idConsultorio, name, numConsultorio FROM consultorio,clinica WHERE idClinic=idClinica GROUP BY idConsultorio", $conn );

                while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
                                {

                echo "<option value=".$row['idConsultorio'];
                if($idconsultorio == $row['idConsultorio']){
                    echo " selected";
                }
                echo ">";
                echo $row['name']."- Room: ".$row['numConsultorio'];
                echo "</option>";

                }      
                            ?>
                 </select>

<br> <br>
<input class='button' type="submit" name="enviarDoctor" value="Save">
<input class= 'button' type="submit" name="cancelar" value="Cancel">
<br>
<input class= 'button' type="submit" name="DeleteDoctor" value="Delete">
<?php  
}
//imprime en la pagina el menu de actualizacion de clinica
function showUpdateClinic(){
include("../config.php");
	$id = $_GET['id'];
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	$rows = mysql_query( "SELECT idClinic, name, latitud, longitud FROM Clinica WHERE idClinic = ".$id, $conn );
	$row = mysql_fetch_array($rows, MYSQL_ASSOC);
    ?>
<h2>Edit Clinic</h2>
<input type="hidden" name="id" value="<?=$row['idClinic']?>" />
<br> Name:
<input type="text" name="name" value="<?=$row['name']?>" />
<br>Latitude:
<input type="text" name="latitud" value="<?=$row['latitud']?>" />
<br>Longitude:
 <input type="text" name="longitud" value="<?=$row['longitud']?>" />
<br> <br>
<input class='button' type="submit" name="enviarClinica" value="Save">
<input class= 'button' type="submit" name="cancelar" value="Cancel">
<br>
<input class= 'button' type="submit" name="DeleteClinic" value="Delete">
<?php  
}
//imprime en la pagina el menu de actualizacion de usuario
function showUpdateUser(){
    
    
    include("../config.php");
	$id = $_GET['id'];
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	$rows = mysql_query( "SELECT idUser, nameUser, tel, facebook, sex FROM user WHERE idUser = ".$id, $conn );
	$row = mysql_fetch_array($rows, MYSQL_ASSOC);
    ?>
<h2>Edit User</h2>
<input type="hidden" name="id" value="<?=$row['idUser']?>" />
<br> Name:
<input type="text" name="nameUser" value="<?=$row['nameUser']?>" />
<br> Tel:
<input type="text" name="tel" value="<?=$row['tel']?>" />
<br> Facebook:
<input type="text" name="facebook" value="<?=$row['facebook']?>" />
<br> Sex:
<input type="text" name="sex" value="<?=$row['sex']?>" />

<br> <br>
<input class='button' type="submit" name="enviarUsuario" value="Save">
<input class= 'button' type="submit" name="cancelar" value="Cancel">
<br>
<input class= 'button' type="submit" name="DeleteUser" value="Delete">
<?php  
}
?>