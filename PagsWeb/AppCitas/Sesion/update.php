<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Cloud Calendars</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="http://fonts.googleapis.com/css?family=Varela" rel="stylesheet" />
<link href="../Style/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="../Style/fonts.css" rel="stylesheet" type="text/css" media="all" />

<!--[if IE 6]><link href="default_ie6.css" rel="stylesheet" type="text/css" /><![endif]-->

</head>
<body>
<div id="wrapper">
	<div id="header-wrapper">
	<div id="header" class="container">
		<div id="logo">
			<h1><a href="#">Cloud Calendars</a></h1>
		</div>
		<div id="menu">
			<ul>
                            <li class="active"><a href="../Sesion/indexSesion.php" accesskey="1" title="">Homepage</a></li>
				<li><a href="../Login/logout.php" accesskey="3" title="">Log out</a></li>
			</ul>
		</div>
	</div>
	</div>
	<div id="banner">
		<div class="container">                    
			<div class="title">
				<h2>Clinic Cloud Calendar</h2>
				<span class="byline">WEB APP</span> </div>
			
		</div>
	</div>
 <div id="page" class="container">
    <form method="post" action="update.php">
        <ul class="actions">
             </div>
                    <ul class="actions">
                    <form action="update.php?table=room" method="post" name="login_form">
           <?php     
            include("functionsEdit.php");
            include("../Login/functions.php");
            include("../Login/db_connect.php");
            sec_session_start();
           if (login_check($mysqli)){
            if(isset($_POST["searchRoom"]))
            {       
                editRoom($_POST['roomName']);  
            }
            if(isset($_POST["enviarConsultorio"]))
            {       
                updateRoom();
                echo "Room Updated";
            }
            if(isset($_POST["DeleteRoom"]))
            {       
                deleteRoom();
                echo "Deleted";                
            }
            if(isset($_POST["searchDoctor"]))
            {       
                editDoctor($_POST['doctorName']);  
            }
            if(isset($_POST["searchClinic"]))
            {       
                editClinic($_POST['clinicName']);  
            }    
            if(isset($_POST["searchUser"]))
            {       
                editUser($_POST['userName']);  
            }
            if(isset($_POST["searchDate"]))
            {       
                editDate($_POST['userName']);  
            }         
            if(isset($_POST["enviarCita"]))
            {       
                updateDate();
                echo "Date Updated";
            }
            if(isset($_POST["enviarDoctor"]))
            {       
                updateDoctor();
                echo "Doctor Updated";
            }
            if(isset($_POST["enviarClinica"]))
            {       
                updateClinic();
                echo "Clinic Updated";
            }
            if(isset($_POST["enviarUsuario"]))
            {       
                updateUser();
                echo "User Updated";
            }
            if(isset($_POST["DeleteUser"]))
            {       
                deleteUser();
                echo "Deleted";                
            }
            if(isset($_POST["DeleteClinic"]))
            {       
                deleteClinic();
                echo "Deleted";                
            }
            if(isset($_POST["DeleteDoctor"]))
            {       
                deleteDoctor();
                echo "Deleted";                
            }
            if(isset($_POST["DeleteDate"]))
            {       
                deleteDate();
                echo "Deleted";                
            }
           
               if (isset($_GET['table'])){
               if ($_GET['table'] == 'clinic'){?>                  
                <h2>Search Clinic by Name</h2>
                <input type="text" name="clinicName" /><br />
                <li><input class="button" type="submit" name="searchClinic" value="Search"></li>
                <?php
                
               }
               if ($_GET['table'] == 'date'){?>                  
                <h2>Search Date by User name</h2>
                <input type="text" name="userName" /><br />
                <li><input class="button" type="submit" name="searchDate" value="Search"></li>

                <?php
                
               }
               if ($_GET['table'] == 'room'){ 
                ?>                  
                <h2>Search Room by Clinic name</h2>
                <input type="text" name="roomName" /><br />
                <li><input class="button" type="submit" name="searchRoom" value="Search"></li>

                <?php
                                            
               }
               if ($_GET['table'] == 'doctor'){              
               ?>                  
                <h2>Search Doctor by Name</h2>
                <input type="text" name="doctorName" /><br />
                <li><input class="button" type="submit" name="searchDoctor" value="Search"></li>

                <?php
               }
               if ($_GET['table'] == 'user'){
                   ?>                  
                <h2>Search User by Name</h2>
                <input type="text" name="userName" /><br />
                <li><input class="button" type="submit" name="searchUser" value="Search"></li>

           <?php           }}}
         
           else{
               echo "Please Log In";
           }
           ?>
            </form>
           </ul>
		</form>
   	</div>
</div>
<div id="copyright" class="container">
	<p>Copyright (c) 2014 CloudCalendars.com. All rights reserved. | Photos by <a href="http://fotogrph.com/">Fotogrph</a> | Design by <a href="http://www.freecsstemplates.org/" rel="nofollow">FreeCSSTemplates.org</a>.</p>
</div>
</body>
</html>