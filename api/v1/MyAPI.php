<?php

require_once './API.php';
include_once '../include/DbHandler.php';

class MyAPI extends API {

    protected $User;

    public function __construct($request, $origin) {
        //echo $request ,$origin;
        parent::__construct($request);

        // Abstracted out for example
        $User = "luis";
        //descomentar para agregar autenticacion a los request
        //$this->authenticate();
        $this->User = $User;
    }
    /**
     * function: authenticate
     * autentica elapikey en el header del request
     */
    protected function authenticate() {
        // Getting request headers
        $headers = apache_request_headers();
        $response = array();
        // Verifying Authorization Header
        if (isset($headers['apiKey'])) {
            $db = new DbHandler();
            // get the api key
            $api_key = $headers['apiKey'];
            // validating api key
            if (!$db->isValidApiKey($api_key)) {
                // api key is not present in users table
                $response["error"] = true;
                $response["message"] = "Access Denied. Invalid Api key";
                $this->_response($response,401);
                $this->allowed=false;
                //echo $response["message"] ;
            } else {
                global $user_id;
                $user_id = $db->getUserId($api_key);
            }
        } else {
            // api key is missing in header
            $response["error"] = true;
            $response["message"] = "Api key is misssing";
            $this->_response($response,400);
            $this->allowed=false;
            //echo $response["message"] ;
        }
    }
    /**
     * valida si el parametro email tiene el formato correcto
     * @param type $email
     * @return boolean
     */
    private function validateEmail($email) {
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            $response["error"] = true;
            $response["message"] = 'Email address is not valid';
            echo $response["message"];
            return FALSE;
        }
        return TRUE;
    }

    private function compararFechas($fecha1,$fecha2){
        //$fecha2 = $_POST['f2'];
        //$fecha1 = $_POST['f1'];
        $datetime1 = date_create($fecha1);
        $datetime2 = date_create($fecha2);
        $interval = date_diff($datetime1, $datetime2);
        return $interval->format('%R%a')>0;
    }
   
    /**
     * url: /login
     * method: POST
     * parametros: email password
     */
    protected function login() {
        // reading post params
        $email = $_POST['email'];
        $password = $_POST['password'];
        $db = new DbHandler();
        $this->validateEmail($email);
        // check for correct email and password
        if ($db->checkLogin($email, $password)) {
            // get the user by email
            $user = $db->getUserByEmail($email);
            if ($user != NULL) {
                $response["success"] = 1;
                $response["error"] = false;
                $response['name'] = $user['name'];
                $response['email'] = $user['email'];
                $response['apiKey'] = $user['api_key'];
                $response['message'] = "login success";
            } else {
                // unknown error occurred
                $response["success"] = 0;
                $response['error'] = true;
                $response['message'] = "An error occurred. Please try again";
            }
        } else {
            // user credentials are wrong
            $response["success"] = 0;
            $response['error'] = true;
            $response['message'] = 'Login failed. Incorrect credentials';
        }
        echo json_encode($response);
    }
    /**
     * url: /register
     * method: POST
     * parametros: nombre email sexo 
     * opcionales(telefono facebook)
     */
    protected function register() {
        $response = array();
        // reading post params
        
        $name = filter_input(INPUT_POST, 'nombre');
                //$_POST['nombre'];
        $email = $_POST['email'];
        $this->validateEmail($email);
        $password = $_POST['password'];

        $sex = $_POST['sexo'];
        $telefono = "";
        $facebook = "";
        if (isset($_POST["telefono"])) {
            $telefono = $_POST['telefono'];
        }
        if (isset($_POST["facebook"])) {
            $facebook = $_POST['facebook'];
        }
        // validating email address
        // if (validateEmail($email)) {
        $db = new DbHandler();
        $res = $db->createUser($name, $email, $telefono, $password, $sex, $facebook);
        if ($res == USER_CREATED_SUCCESSFULLY) {
            $response["error"] = false;
            $response["success"] = 1;
            $response["message"] = "You are successfully registered";
            $user = $db->getUserByEmail($email);

            if ($user != NULL) {
                $response["success"] = 1;
                $response["error"] = false;
                $response['name'] = $user['name'];
                $response['email'] = $user['email'];
                $response['apiKey'] = $user['api_key'];
                $response['message'] = "login success";
            } else {
                // unknown error occurred
                $response["success"] = 0;
                $response['error'] = true;
                $response['message'] = "An error occurred. Please try again";
            }
        } else if ($res == USER_CREATE_FAILED) {
            $response["error"] = true;
            $response["success"] = 0;
            $response["message"] = "Oops! An error occurred while registereing";
        } else if ($res == USER_ALREADY_EXISTED) {
            $response["error"] = true;
            $response["success"] = 0;
            $response["message"] = "Sorry, this email already existed";
        }
        // echo json response
        echo json_encode($response);
        //}
    }
    
  
    
    /**
     * url: /citas
     * method: POST    obtiene las citas de un usuario
     * parametros: usuario (apikey) 
     * method:GET obtiene todas las citas de la tabla
     */
    protected function getCitas(){
        $usuario="";
        if (isset($_POST["usuario"])) {
                   $apiKey = $_POST['usuario'];
                   $dba = new DbHandler();        
                   $usuario = $dba->getUserId($apiKey);                   
        }                 
        
         $dba = new DbHandler();        
         $citasListas = $dba->getCitas($usuario);
         $json = array();
        $json['emp_info'] = $citasListas;       
        echo json_encode($json);  
    }
    
    /**
     * url: /especialidades
     * method: POST    obtiene las especialidades
     * parametros: usuario (apikey) 
     * method:GET obtiene todas las citas de la tabla
     */
    protected function especialidades(){
        $dba = new DbHandler();
        $result=$dba->getEspecialidades();
        $json = array();
        $json['emp_info'] = $result;       
        echo json_encode($json);  
    }
    protected function usuarios_jason() {
        include_once("config.php");
        $con = mysql_connect($server, $db_user, $db_pass)or die("cannot connect");
        mysql_select_db($db)or die("cannot select DB");
        $sql = "SELECT * FROM appointment";
        $result = mysql_query($sql);
        $json = array();
        if (mysql_num_rows($result)) {
            while ($row = mysql_fetch_row($result)) {
                $json['emp_info'][] = $row;
            }
        }
        mysql_close();
        echo json_encode($json);
    }
    protected function getDoctores() {
        $especialista = "";
        if (isset($_POST['especialista'])) {
            $especialista = $_POST['especialista'];
        }

        $dba = new DbHandler();
        $doctoresListas = $dba->getDoctores($especialista);
        $json = array();
        $json['emp_info'] = $doctoresListas;
        echo json_encode($json);
    }

    protected function eliminarCita() {
        include_once("config.php");
        $usr = $_POST['user'];
        $id = $_POST['id'];
        $dba = new DbHandler();
        $usuario = $dba->getUserId($usr);
        $hoy = date("Y-m-d");
        $fecha = $dba->getFechaCita($id);
        if($this->compararFechas($hoy, $fecha)) {
            $bb = $dba->deleteCita($usuario, $id);
            //echo $bb;
            if ($bb) {
                $conn = mysql_connect($server, $db_user, $db_pass);
                if (!$conn) {
                    die('No pudo conectarse: ' . mysql_error());
                }
                mysql_select_db($db);
                $sql = "SELECT appointment.date,appointment.startTime,doctor.nameDoctor,clinica.name,appointment.idAppointment FROM appointment INNER JOIN doctor ON appointment.doctor=doctor.iddoctor INNER JOIN clinica ON appointment.place=clinica.idClinic WHERE appointment.user = " . $usuario . " ";
                $result = mysql_query($sql);
                $json = array();
                if (mysql_num_rows($result)) {
                    while ($row = mysql_fetch_row($result)) {
                        $json['emp_info'][] = $row;
                    }
                }
                $json['success'] = 1;
                mysql_close();
                $json['message'] = "se elimino correctamente";
                echo json_encode($json);
            } else {
                $json = array();
                $json['success'] = 0;
                $json['message'] = "no se elimino ninguna cita";
                echo json_encode($json);
            }
        }else{
            if(!$fecha){
                $json = array();
                $json['success'] = 0;
                $json['message'] = "cita no existe";
                echo json_encode($json);
            }else{
                $json = array();
                $json['success'] = 0;
                $json['message'] = "fecha cercana, no se puede eliminar cita";
                echo json_encode($json);
            }
        }
    }
    
    protected function crearCita() {
        include_once("config.php");
        $usr = $_POST['user'];
        $doc = $_POST['doctor'];
        //$place = $_POST['place'];
        $date = $_POST['fecha'];
        $time = $_POST['hora'];
        
        $dba = new DbHandler();        
        $usuario = $dba->getUserId($usr);
        //echo "usuario ".$usuario."-";
        
        $consultorio=$dba->getConsultorioFromDoctor($doc);
        //echo "consultorio ". $consultorio."-";
        $clinica= $dba->getClinicId($consultorio);
        
        //echo "clinica ".$clinica."-";
        if ($usuario != "") {
            if(!$dba->verificarCita($doc,$time,$date,$usuario)) {
                $conn = mysql_connect($server, $db_user, $db_pass);
                if (!$conn) {
                    die('No pudo conectarse: ' . mysql_error());
                }
                mysql_select_db($db);
                mysql_query("INSERT INTO appointment (date, startTime, doctor, user, place) VALUES('" . $date . "','" . $time . "'," . $doc . "," . $usuario . "," . $clinica . ")");
                $sql = "SELECT appointment.date,appointment.startTime,doctor.nameDoctor,clinica.name,appointment.idAppointment FROM appointment INNER JOIN doctor ON appointment.doctor=doctor.iddoctor INNER JOIN clinica ON appointment.place=clinica.idClinic WHERE appointment.user = " . $usuario . " ";
                $result = mysql_query($sql);
                $json = array();
                if (mysql_num_rows($result)) {
                    while ($row = mysql_fetch_row($result)) {
                        $json['emp_info'][] = $row;
                        $json['success'] = 1;
                    }
                }
                mysql_close();
                echo json_encode($json);
            }else{
                $json = array();
                $json['success'] = 0;
                $json['message'] = "Ya existe una cita con ese horario y doctor";
                echo json_encode($json);
                
            }

            
        }else{
            $json = array();
            $json['success'] = 0;
            echo json_encode($json);
        }
    }
    
    

    
    protected function clinicas_json() {
        include_once("config.php");
        $con = mysql_connect($server, $db_user, $db_pass)or die("cannot connect");
        mysql_select_db($db)or die("cannot select DB");
        $sql = "SELECT * FROM clinica";
        $result = mysql_query($sql);
        $json = array();
        if (mysql_num_rows($result)) {
            while ($row = mysql_fetch_row($result)) {
                $json['emp_info'][] = $row;
            }
        }
        mysql_close();
        echo json_encode($json);
    }

}

// Requests from the same server don't have a HTTP_ORIGIN header
if (!array_key_exists('HTTP_ORIGIN', $_SERVER)) {
    $_SERVER['HTTP_ORIGIN'] = $_SERVER['SERVER_NAME'];
}

try {
    $API = new MyAPI($_REQUEST['request'], $_SERVER['HTTP_ORIGIN']);
    //echo $_REQUEST['request'];
    echo $API->processAPI();
} catch (Exception $e) {
    echo json_encode(Array('error' => $e->getMessage()));
}



