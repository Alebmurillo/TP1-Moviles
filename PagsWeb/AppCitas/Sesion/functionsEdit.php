<?php
//e: $clinicName: Nombre de la clinica a editar
//s: 
// imprime la ventana de modificacion de consultorio
function editRoom($clinicName) {
   include("../config.php");
   $table = "consultorio";
    $conn = mysql_connect($server,  $db_user, $db_pass);
    mysql_select_db($db);
    if(isset($_POST["enviar"])){
	 mysql_query("UPDATE consultorio SET  numConsultorio=". mysql_real_escape_string($_POST['numConsultorio']).", idClinica =". mysql_real_escape_string($_POST['clinic'])." WHERE idConsultorio=". mysql_real_escape_string($_POST['id']));
         }          
  //  else if (isset($_GET["delete"])){
    //    $indexDelete =$_GET["id"];
    //    $querry = mysql_query("DELETE FROM {$table} WHERE idconsultorio = ". mysql_real_escape_string($indexDelete));  
    //    }
    $result = mysql_query("SELECT idConsultorio, numConsultorio, name FROM {$table}, clinica WHERE idClinic = idclinica AND name LIKE '%{$clinicName}%'");
    if (!$result) {
        die("Query to show fields from table failed");
    }
    $fields_num = mysql_num_fields($result);
    echo "<center>";
    echo "<table border='1'>";
    $field = mysql_fetch_field($result);
    echo "<td>EDIT</td>";
    echo "<td>Room</td><td>Clinic</td>";
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
            <a href='updateTable.php?id=".$cell."&table=room' class='button' value='Delete' name='UpdateRoom'>Edit</a>
            </td>";

            $varId = 1;
            
        }
            else {echo "<td>$cell</td>";         
        }
        }
    }
        mysql_free_result($result);
   
}
//e: 
//s: 
//ejecuta los comandos de eliminacion de consultorio
function deleteRoom(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
    mysql_select_db($db);
    $indexDelete =$_POST['id'];
    $querry = mysql_query("DELETE FROM consultorio WHERE idconsultorio = ". mysql_real_escape_string($indexDelete)); 
}
//e: $clinicName: Nombre de la clinica a editar
//s: 
// elecuta los comandos de actualizacion de la db del consultorio
function updateRoom(){  
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
    mysql_select_db($db);
    mysql_query("UPDATE consultorio SET  numConsultorio=". mysql_real_escape_string($_POST['numConsultorio']).", idClinica =". mysql_real_escape_string($_POST['clinic'])." WHERE idConsultorio=". mysql_real_escape_string($_POST['id']));

}
//e: $clinicName: Nombre de la clinica a editar
//s: 
// imprime la ventana de modificacion de doctor
function editDoctor($doctorName){
	include("../config.php");
	$table = "doctor";
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	
    $result = mysql_query("SELECT doctor.iddoctor, doctor.namedoctor, user.nameUser, doctor.tel, doctor.cel, doctor.especialidad, consultorio.numConsultorio, clinica.name FROM doctor, user, consultorio, clinica WHERE nameDoctor LIKE '%{$doctorName}%' AND doctor.iduser = user.iduser AND doctor.idConsultorio=consultorio.idConsultorio AND clinica.idclinic= consultorio.idclinica GROUP BY doctor.iddoctor");
    if (!$result) {
        die("Query to show fields from table failed");
    }

    $fields_num = mysql_num_fields($result);
    echo "<center>";
    echo "<h1>Doctores</h1>";
    echo "<table border='1.5'><tr>";
    echo "<td>EDITAR</td>";
    echo "<td>Doctor</td>";
    echo "<td>User Alias</td>";
    echo "<td>Cel</td>";
    echo "<td>Type</td>";
    echo "<td>Room</td>";
    echo "<td>Tel</td>";
    echo "<td>Clinic</td>";
    
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
            <a href='updateTable.php?id=".$cell."&table=doctor' class='button' value='Delete' name='UpdateRoom'>Edit</a>
            \n
            </td>";
            
            $varId = 1;
            
        }
            else {echo "<td>$cell</td>";         
        }
        }
        
    }
    mysql_free_result($result);
}
//e: $doctorName: Nombre de la clinica a editar
//s: 
// imprime la ventana de modificacion de clinica
function editClinic($clinicName){
	include("../config.php");
	$table = "clinica";
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	
    $result = mysql_query("SELECT * FROM {$table} WHERE name LIKE '%{$clinicName}%'");
    if (!$result) {
        die("Query to show fields from table failed");
    }

    $fields_num = mysql_num_fields($result);
    echo "<center>";
    echo "<h1>Clinics</h1>";
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
            <a href='updateTable.php?id=".$cell."&table=clinic' class='button' value='Delete' name='UpdateRoom'>Edit</a>
            \n
            </td>";
            
            $varId = 1;
            
        }
            else {echo "<td>$cell</td>";         
        }
        }
        
    }
    mysql_free_result($result);
}
//e: $userName: Nombre de la clinica a editar
//s: 
// imprime la ventana de modificacion de usuario
function editUser($userName){
	include("../config.php");
	$table = "user";
	$conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	
    $result = mysql_query("SELECT * FROM {$table} WHERE nameUser LIKE '%{$userName}%'");
    if (!$result) {
        die("Query to show fields from table failed");
    }

    $fields_num = mysql_num_fields($result);
    echo "<center>";
    echo "<h1>Users</h1>";
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
            <a href='updateTable.php?id=".$cell."&table=room' class='button' value='Delete' name='UpdateRoom'>Edit</a>
            \n
            </td>";
            
            $varId = 1;
            
        }
            else {echo "<td>$cell</td>";         
        }
        }
        
    }
    mysql_free_result($result);
}
//e: userName: Nombre de la clinica a editar
//s: 
// imprime la ventana de modificacion de cita
function editDate($userName){
        include("../config.php");
        
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
            mysql_query("UPDATE appointment SET date='". mysql_real_escape_string($_POST['date'])."', startTime ='". mysql_real_escape_string($_POST['stime'])."', endTime='". mysql_real_escape_string($_POST['etime'])."',doctor=". mysql_real_escape_string($_POST['doctor']).",user=". mysql_real_escape_string($_POST['user']).",place=". mysql_real_escape_string($_POST['consultorio'])." WHERE idAppointment=". mysql_real_escape_string($_POST['id'])." AND nameUser LIKE '%{$doctorName}%'");
      
        }          
        else if (isset($_GET["eliminar"])){             
            $indexDelete =$_GET["id"];
            $querry = mysql_query("DELETE FROM {$table} WHERE idAppointment = ". mysql_real_escape_string($indexDelete));          
        }
   $result = mysql_query("SELECT idAppointment, date, startTime, nameDoctor, nameUser,name,numConsultorio FROM appointment,doctor,user, clinica, consultorio WHERE idClinic = idclinica and user=user.idUser and doctor =idDoctor and doctor.idconsultorio= consultorio.idConsultorio GROUP BY idAppointment" );
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
    echo "<td>Fecha</td><td>Hora de inicio</td><td>Doctor</td><td>Usuario</td><td>Clinica</td><td>Consultorio</td>";
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
            <a href='updateTable.php?id=".$cell."&table=date' class='button' value='Delete' name='UpdateDate'>Edit</a>
            \n
            </td>";
            
            $varId = 1;
            
        }
            else {echo "<td>$cell</td>";         
        }
        }
    }
    mysql_free_result($result);
    
}
// ejecuta los comandos de actualizacion de la db de cita
function updateDate(){  
    include("../config.php");
    $id = $_POST['id'];
    $conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
            echo 'ERROR INSERTANDO';
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	mysql_query("UPDATE appointment SET date='". mysql_real_escape_string($_POST['date'])."', startTime ='". mysql_real_escape_string($_POST['stime'])."', doctor=". mysql_real_escape_string($_POST['doctor']).",user=". mysql_real_escape_string($_POST['user'])." WHERE idAppointment=". mysql_real_escape_string($_POST['id']));
      
}
// ejecuta los comandos de eliminacion de la db del cita
function deleteDate(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
    mysql_select_db($db);
    $indexDelete =$_POST['id'];
    $querry = mysql_query("DELETE FROM appointment WHERE idAppointment = ". mysql_real_escape_string($indexDelete)); 
}
// ejecuta los comandos de actualizacion de la db del doctor
function updateDoctor(){  
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
            echo 'ERROR INSERTANDO';
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	mysql_query("UPDATE doctor SET nameDoctor='". mysql_real_escape_string($_POST['nameDoctor'])."', tel =". mysql_real_escape_string($_POST['tel']).", cel=". mysql_real_escape_string($_POST['cel']).",especialidad=". mysql_real_escape_string($_POST['especialidad']).", idconsultorio=". mysql_real_escape_string($_POST['consultorio'])." WHERE iddoctor=". mysql_real_escape_string($_POST['id']));
      
}
// ejecuta los comandos de eliminacion de la db del doc
function deleteDoctor(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
    mysql_select_db($db);
    $indexDelete =$_POST['id'];
    $querry = mysql_query("DELETE FROM doctor WHERE iddoctor = ". mysql_real_escape_string($indexDelete)); 
}
// ejecuta los comandos de actualizacion de la db de clinica
function updateClinic(){  
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
            echo 'ERROR INSERTANDO';
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	mysql_query("UPDATE clinica SET name='". mysql_real_escape_string($_POST['name'])."', longitud ='". mysql_real_escape_string($_POST['longitud'])."', latitud='". mysql_real_escape_string($_POST['latitud'])."' WHERE idclinic=". mysql_real_escape_string($_POST['id']));
      
}
// ejecuta los comandos de eliminacion de la db de clinica
function deleteClinic(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
    mysql_select_db($db);
    $indexDelete =$_POST['id'];
    $querry = mysql_query("DELETE FROM clinica WHERE idclinic = ". mysql_real_escape_string($indexDelete)); 
}
// ejecuta los comandos de actualizacion de la db del usuario
function updateUser(){  
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
	if  (!$conn) 
	{
            echo 'ERROR INSERTANDO';
    	die('No pudo conectarse: ' . mysql_error());
	}
	mysql_select_db($db);
	mysql_query("UPDATE user SET nameUser='". mysql_real_escape_string($_POST['nameUser'])."', tel =". mysql_real_escape_string($_POST['tel']).", facebook='". mysql_real_escape_string($_POST['facebook'])."', sex='". mysql_real_escape_string($_POST['sex'])."' WHERE idUser=". mysql_real_escape_string($_POST['id']));
    
}
// ejecuta los comandos de eliminacion de la db de usuario
function deleteUser(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
    mysql_select_db($db);
    $indexDelete =$_POST['id'];
    $querry = mysql_query("DELETE FROM user WHERE idUser = ". mysql_real_escape_string($indexDelete)); 
}

?>
