<?php
include("functionsModify.php");
include("../Login/functions.php");
include("../Login/db_connect.php");
?>

<!DOCTYPE html>
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
				<span class="byline">Lema bonito que luis debe inventar</span> </div>
			
		</div>
	</div>
 <div id="page" class="container">
    <form method="post" action="update.php">
             </div>
    <center>
                    <?php
                    sec_session_start();
           if (login_check($mysqli)){
               if(isset($_GET['table'])){
                   if($_GET['table']== 'user') {showUpdateUser();}
                   if($_GET['table']== 'clinic') {showUpdateClinic();}
                   if($_GET['table']== 'doctor') {showUpdateDoctor();}
                   if($_GET['table']== 'date') {showUpdateAppointment();}
                   if($_GET['table']== 'room') {showUpdateRoom();}
                    }
                    }
                else{echo "login";}
                    ?>
	</form>
   	</div>
</div>
<div id="copyright" class="container">
	<p>Copyright (c) 2014 CloudCalendars.com. All rights reserved. | Photos by <a href="http://fotogrph.com/">Fotogrph</a> | Design by <a href="http://www.freecsstemplates.org/" rel="nofollow">FreeCSSTemplates.org</a>.</p>
</div>
</body>
</html>>
