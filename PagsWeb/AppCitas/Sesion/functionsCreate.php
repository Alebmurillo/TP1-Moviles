<?php

function crearCita() {
   include("../config.php");
        echo "nect";
        $conn = mysql_connect($server,  $db_user, $db_pass);
        if  (!$conn) 
        {
            echo "ERROORRR";
            die('No pudo conectarse: ' . mysql_error());
        }
        mysql_select_db($db);
        $rows = mysql_query( "SELECT idDoctor, nameDoctor FROM doctor", $conn );
        echo "
     <br>Doctor
        <select name='doctor'>";
        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
        {
            print "<option value=".$row['idDoctor'].">";
            print $row['nameDoctor'];
            print "</option>";
        }
        print $row['id'];
       ?>
        </select>
     
         <br>Usuario
        <select name='user'>
        <?php                        
        $rows = mysql_query( "SELECT idUser, nameUser FROM user", $conn );

        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
                        {

        echo "<option value=".$row['idUser'].">";
                        echo $row['nameUser'];
                        echo "</option>";

                        }
                        echo $row['id'];
       ?>
        </select>
          <br>Consultorio
        <select name='consultorio'>
        <?php
        $rows = mysql_query( "SELECT idConsultorio, idClinica, numConsultorio, name FROM consultorio, clinica WHERE idClinica=idClinic GROUP BY idConsultorio", $conn );

        while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
                        {

        echo "<option value=".$row['idConsultorio'].">";
                        echo $row['numConsultorio']."     ".$row['name'];

                        echo "</option>";

                        }
                        echo $row['id'];
                        mysql_close($conn);
       echo " </select><br>Fecha:<input type='date' name='date' size='9' value='' /> <br> Hora inicial:<input type='time' name='startTime' size='9' value='' /> Hora Final: <input type='time' name='endTime' size='9' value='' /> <time datetime='YYYY-MM-DDThh:mm:ssTZD'> <br><li><input class='button' type='submit' name='enviarCita' value='Guardar'></li>";


}
function crearConsultorio(){
    include("../config.php");
    ?>
    <form method="post" action="crearCita.php">
    <br>Clinica
    <select name='tipo'>
    <?php
    $conn = mysql_connect($server,  $db_user, $db_pass);
    if  (!$conn) 
    {
    die('No pudo conectarse: ' . mysql_error());
    }
    mysql_select_db($db);
    $rows = mysql_query( "SELECT idClinic, name FROM clinica", $conn );

    while($row = mysql_fetch_array($rows, MYSQL_ASSOC))
                {

    echo "<option value=".$row['idClinic'].">";
    echo $row['name'];
    echo "</option>";

    }                              
    mysql_close($conn);
    ?>
    </select>
    <br>Numero de consultorio
    <input type="text" name="num_consultorio" />
    <br>
    <input class='button' type="submit" name="enviarConsultorio" value="Guardar">
    </form>
            <?php
}
function crearDoctor(){
    include("../config.php");
    ?>
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
			<input class='button' type="submit" name="enviarDoctor" value="Guardar">
		</form> <?php
}
function crearClinica(){?>
    <form method="post" action="crearCita.php">
                        Nombre
        		<input type="text" name="nombre" />
			<br>Latitud
			<input type="text" name="latitud" />
			<br>Longitud
			<input type="text" name="longitud" />
                        <br>
			<input class='button' type="submit" name="enviarClinica" value="Guardar">
		</form>
                    <?php
}
function crearUsuario(){
     include("../config.php");
    ?>
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
			<input class='button'  type="submit" name="enviarUsuario" value="Guardar">
		</form><?php
}
function enviarCita(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
    if  (!$conn) 
    {
        die('No pudo conectarse: ' . mysql_error());
    }
    mysql_select_db($db);
    mysql_query("INSERT INTO appointment (date, startTime, endTime, doctor, user, place) VALUES('". mysql_real_escape_string($_POST['date'])."','". mysql_real_escape_string($_POST['startTime'])."','". mysql_real_escape_string($_POST['endTime'])."',". mysql_real_escape_string($_POST['doctor']).",". mysql_real_escape_string($_POST['user']).",". mysql_real_escape_string($_POST['consultorio']).") ");
    echo "Insertado";
    mysql_close($conn);}
function enviarClinica(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
    if  (!$conn) 
    {
        die('No pudo conectarse: ' . mysql_error());
    }
    mysql_select_db($db);
    mysql_query("INSERT INTO clinica (name, latitud, longitud) VALUES('". mysql_real_escape_string($_POST['nombre'])."',". mysql_real_escape_string($_POST['latitud']).",". mysql_real_escape_string($_POST['longitud']).") ");
    echo "Insertado";
mysql_close($conn);}
function enviarUsuario(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
    if  (!$conn) 
    {
    die('No pudo conectarse: ' . mysql_error());
    }
    mysql_select_db($db);
    mysql_query("INSERT INTO user (nameUser, tel, sex,facebook) VALUES('". mysql_real_escape_string($_POST['nombre'])."',". mysql_real_escape_string($_POST['telefono']).",'". mysql_real_escape_string($_POST['sexo'])."','". mysql_real_escape_string($_POST['facebook'])."') ");
    echo "Insertado";
    mysql_close($conn);
    }
function enviarDoctor(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
            if  (!$conn) 
            {
            die('No pudo conectarse: ' . mysql_error());
            }
            mysql_select_db($db);
            mysql_query("INSERT INTO doctor (nameDoctor, cel, tel, especialidad) VALUES('". mysql_real_escape_string($_POST['nombre'])."',". mysql_real_escape_string($_POST['celular']).",". mysql_real_escape_string($_POST['telefono']).",". mysql_real_escape_string($_POST['especialidad']).") ");
            echo "Insertado";
            mysql_close($conn);
    
}
function enviarConsultorio(){
    include("../config.php");
    $conn = mysql_connect($server,  $db_user, $db_pass);
            if  (!$conn) 
            {
            die('No pudo conectarse: ' . mysql_error());
            }
            mysql_select_db($db);
            mysql_query("INSERT INTO consultorio (IdClinica, numConsultorio) VALUES(". mysql_real_escape_string($_POST['tipo']).",". mysql_real_escape_string($_POST['num_consultorio']).") ");
            echo "Insertado";
            echo $_POST['tipo'];
            mysql_close($conn);
            
}
    ?>