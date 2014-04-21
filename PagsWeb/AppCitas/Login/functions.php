<?php

function sec_session_start() {
    $session_name = 'sec_session_id'; //Configura un nombre de sesión personalizado
    $secure = false; //Configura en verdadero (true) si utilizas https
    $httponly = true; //Esto detiene que javascript sea capaz de accesar la identificación de la sesión.
    ini_set('session.use_only_cookies', 1); //Forza a las sesiones a sólo utilizar cookies.
    $cookieParams = session_get_cookie_params(); //Obtén params de cookies actuales.
    session_set_cookie_params($cookieParams["lifetime"], $cookieParams["path"], $cookieParams["domain"], $secure, $httponly);
    session_name($session_name); //Configura el nombre de sesión a el configurado arriba.
    session_start(); //Inicia la sesión php
    session_regenerate_id(true); //Regenera la sesión, borra la previa.                        
}
function login($email, $password, $mysqli) {
   //Uso de sentencias preparadas significa que la inyección de SQL no es posible.
   if ($stmt = $mysqli->prepare("SELECT idMember, name, password, salt FROM members WHERE email = ? LIMIT 1")) {
        $stmt->bind_param('s', $email); //Liga "$email" a parámetro.
        $stmt->execute(); //Ejecuta la consulta preparada.
        $stmt->store_result();
        $stmt->bind_result($user_id, $username, $db_password, $salt); //Obtiene las variables del resultado.
        $stmt->fetch();
        $password = hash('sha512', $password.$salt); //Hash de la contraseña con salt única.
        if($stmt->num_rows == 1) { //Si el usuario existe.
        //Revisamos si la cuenta está bloqueada de muchos intentos de conexión.
        if(checkbrute($user_id, $mysqli) == true) {
                //La cuenta está bloqueada
                //Envia un correo electrónico al usuario que le informa que su cuenta está bloqueada
                return false;
        } else {
        if($db_password == $password) { //Revisa si la contraseña en la base de datos coincide con la contraseña que el usuario envió.
//¡La contraseña es correcta!
                $user_browser = $_SERVER['HTTP_USER_AGENT']; //Obtén el agente de usuario del usuario
                $user_id = preg_replace("/[^0-9]+/", "", $user_id); //protección XSS ya que podemos imprimir este valor
                $_SESSION['user_id'] = $user_id;
                $username = preg_replace("/[^a-zA-Z0-9_\-]+/", "", $username); //protección XSS ya que podemos imprimir este valor
                $_SESSION['username'] = $username;
                $_SESSION['login_string'] = hash('sha512', $password.$user_browser);
             //Inicio de sesión exitosa
                return true;    
        } else {
                //La conexión no es correcta
                //Grabamos este intento en la base de datos
                $now = time();
                $mysqli->query("INSERT INTO login_attempts (memberID, time) VALUES ('$user_id', '$now')");
                return false;
        }
        }
        } else {
        //No existe el usuario.
        return false;
        }
   }
}
function checkbrute($user_id, $mysqli) {
   //Obtén timestamp en tiempo actual
   $now = time();
   //Todos los intentos de inicio de sesión son contados desde las 2 horas anteriores.
   $valid_attempts = $now - (2 * 60 * 60);
   if ($stmt = $mysqli->prepare("SELECT time FROM login_attempts WHERE memberID = ? AND time > '$valid_attempts'")) {
        $stmt->bind_param('i', $user_id);
        //Ejecuta la consulta preparada.
        $stmt->execute();
        $stmt->store_result();
        //Si ha habido más de 5 intentos de inicio de sesión fallidos
        if($stmt->num_rows > 5) {
        return true;
        } else {
        return false;
        }
   }
}
function login_check($mysqli) {
   //Revisa si todas las variables de sesión están configuradas.
if(isset($_SESSION['user_id'], $_SESSION['username'], $_SESSION['login_string'])) {
     $user_id = $_SESSION['user_id'];
     $login_string = $_SESSION['login_string'];
     $username = $_SESSION['username'];
     $user_browser = $_SERVER['HTTP_USER_AGENT']; //Obtén la cadena de caractéres del agente de usuario
 
     if ($stmt = $mysqli->prepare("SELECT password FROM members WHERE idMember = ? LIMIT 1")) {
        $stmt->bind_param('i', $user_id); //Liga "$user_id" a parámetro.
        $stmt->execute(); //Ejecuta la consulta preparada.
        $stmt->store_result();
        if($stmt->num_rows == 1) { //Si el usuario existe
        $stmt->bind_result($password); //Obtén variables del resultado.
$stmt->fetch();
        $login_check = hash('sha512', $password.$user_browser);
        if($login_check == $login_string) {
                //¡¡¡¡Conectado!!!!
                return true;
        } else {
                //No conectado
                return false;
        }
        } else {
                //No conectado
                return false;
        }
     } else {
        //No conectado
        return false;
     }
   } else {
     //No conectado
     return false;
   }
}
function login_create($mysqli,$username,$email,$password) {
//La contraseña en hash desde la forma
//$password = $_POST['p'];
//Crea una salt al azar
$random_salt = hash('sha512', uniqid(mt_rand(1, mt_getrandmax()), true));
//Crea una contraseña en salt (Cuidado de no pasarte)
$password = hash('sha512', $password.$random_salt);
//Agrega tu inserto a la secuencia de comandos de la base de datos aquí.
//¡Asegúrate de usar comandos preparados!
if ($insert_stmt = $mysqli->prepare("INSERT INTO members (name, email, password, salt) VALUES (?, ?, ?, ?)")) {     
   $insert_stmt->bind_param('ssss', $username, $email, $password, $random_salt);
   //Ejecuta la consulta preparada.
$insert_stmt->execute();return true;}
else{return false;}

}
?>