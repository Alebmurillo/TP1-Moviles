 <?php
include '../login/db_connect.php';
include '../login/functions.php';
sec_session_start(); //Nuestra manera personalizada segura de iniciar sesión php.

if(isset($_POST['email'], $_POST['p'])) {
   $email = $_POST['email'];
   $password = $_POST['p']; //La contraseña con hash
   if(login($email, $password, $mysqli) == true) {
        //Inicio de sesión exitosa
        echo '¡Has iniciado sesion!';

   } else {
        //Inicio de sesión fallida
        header('Location: ./login.php?error=1');
   }
} 
else {
   //Las variaciones publicadas correctas no se enviaron a esta página
echo 'Solicitud no válida';
}
?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Cloud Calendars</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="http://fonts.googleapis.com/css?family=Varela" rel="stylesheet" />
<link href="../Style/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="../Style/fonts.css" rel="stylesheet" type="text/css" media="all" />

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
                            <li class="active"><a href="../index.html" accesskey="1" title="">Homepage</a></li>
                            <li><a href="../Login/logout.php" accesskey="3" title="">Log out</a></li>
                            
			</ul>
		</div>
	</div>
	</div>
	<div id="banner">
		<div class="container">                    
        <img src="../Style/images/landscape.png" alt="" width="1000" height="300" />
			<div class="title">
				<h2>Clinic Cloud Calendar</h2>
				<span class="byline">Lema bonito que luis debe inventar</span> </div>
			
		</div>
	</div>
<div id="page" class="container">
<div id="extra" class="container">			
        <div id="two-column">
            <?php
            if (login_check($mysqli)){ ?>
                <div class="tbox1">
                        <div class="box">
                           <h3>Create</h3> 
                                <ul class="actions">
                                    <li><a href="crear.php?table=clinic" class="button">Clinic</a></li>
                                    <li><a href="crear.php?table=date" class="button">Date</a></li>
                                    <li><a href="crear.php?table=room" class="button">Room</a></li>
                                    <li><a href="crear.php?table=user" class="button">Mobile User</a></li>
                                    <li><a href="crear.php?table=doctor" class="button">Doctor</a></li>
                                </ul>
                           </div>
                        </div>
                
                <div class="tbox2">
                        <div class="box">
                                <h3>Edit</h3> 
                                <ul class="actions">
                                    <li><a href="#" class="button">Clinic</a></li>
                                    <li><a href="#" class="button">Date</a></li>
                                    <li><a href="#" class="button">Room</a></li>
                                    <li><a href="#" class="button">Mobile User</a></li>
                                    <li><a href="#" class="button">Doctor</a></li>
                                </ul>
                                </div>
                        </div>
         <?php   } 
         else {echo "Please Log in";}?>
                </div>
    </div>
        </div>

</div>

<div id="copyright" class="container">
	<p>Copyright (c) 2014 CloudCalendars.com. All rights reserved. | Photos by <a href="http://fotogrph.com/">Fotogrph</a> | Design by <a href="http://www.freecsstemplates.org/" rel="nofollow">FreeCSSTemplates.org</a>.</p>
</div>
</body>
</html>
