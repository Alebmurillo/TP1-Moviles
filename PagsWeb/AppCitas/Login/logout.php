<script type="text/javascript" src="sha512.js"></script>
<script type="text/javascript" src="forms.js"></script>
<?php
if(isset($_GET['error'])) {
   echo 'Error Logging In!';
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
				<li class="active"><a href="../index.html" accesskey="1" title="">Homepage</a></li>
			</ul>
		</div>
	</div>
	</div>
	<div id="banner">
		<div class="container">                    
			<div class="title">
				<h2>Clinic Cloud Calendar Web Platform</h2>
				<span class="byline">Lema bonito que luis debe inventar</span> </div>
			
		</div>
	</div>
<div id="page" class="container">
    
<div class="title">
	    <h2>Login</h2>
	</div>
	<?php
        include 'functions.php';
        sec_session_start();
        //Desconfigura todos los valores de sesi�n
        $_SESSION = array();
        //Obt�n par�metros de sesi�n
        $params = session_get_cookie_params();
        //Borra la cookie actual
        setcookie(session_name(), '', time() - 42000, $params["path"], $params["domain"], $params["secure"], $params["httponly"]);
        //Destruye sesi�n
        session_destroy();
        header('Location: ../');
        echo "Logged out";
?>
  </div>
        
</div>
<div id="copyright" class="container">
	<p>Copyright (c) 2014 Sitename.com. All rights reserved. | Photos by <a href="http://fotogrph.com/">Fotograph</a> | Design by <a href="http://www.freecsstemplates.org/" rel="nofollow">FreeCSSTemplates.org</a>.</p>
</div>
</body>
</html>


