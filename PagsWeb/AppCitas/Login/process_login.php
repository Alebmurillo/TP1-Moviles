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
        <img src="../Style/images/landscape.png" alt="" width="1000" height="300" />
			<div class="title">
				<h2>Clinic Cloud Calendar</h2>
				<span class="byline">Lema bonito que luis debe inventar</span> </div>
			
		</div>
	</div>
 <div id="page" class="container">
    
<div class="title">
    <h2> Welcome</h2>
		<form method="post" action="crear.php">
                    <ul class="actions">
                       <?php
                        include 'db_connect.php';
                        include 'functions.php';
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
                        } else {
                           //Las variaciones publicadas correctas no se enviaron a esta página
                        echo 'Solicitud no válida';
}
?>
                       </ul>
		</form>
                    	</div>
	</div>
</div>
<div id="copyright" class="container">
	<p>Copyright (c) 2014 CloudCalendars.com. All rights reserved. | Photos by <a href="http://fotogrph.com/">Fotogrph</a> | Design by <a href="http://www.freecsstemplates.org/" rel="nofollow">FreeCSSTemplates.org</a>.</p>
</div>
</body>
</html>>



