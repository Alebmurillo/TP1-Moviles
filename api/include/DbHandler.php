<?php

/**
 * Class to handle all db operations
 * This class will have CRUD methods for database tables
 *
 * @author Ravi Tamada
 * @link URL Tutorial link
 */
class DbHandler {

    private $conn;

    function __construct() {
        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    /* ------------- `users` table method ------------------ */

    /**
     * Creating new user
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function createUser($name, $email,$telefono, $password,$sex,$facebook) {
        require_once 'PassHash.php';
        $response = array();

        // First check if user already existed in db
        if (!$this->isUserExists($email)) {
            // Generating password hash
            $password_hash = PassHash::hash($password);

            // Generating API key
            $api_key = $this->generateApiKey();

            // insert query
           
            $stmt = $this->conn->prepare("INSERT INTO user (nameUser, tel, sex,facebook, email, password_hash, api_key) VALUES(?,?,?,?,?,?,?)");
            $stmt->bind_param("sssssss", $name,$telefono,$sex,$facebook, $email, $password_hash, $api_key);

            $result = $stmt->execute();

            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        } else {
            // User with same email already existed in the db
            return USER_ALREADY_EXISTED;
        }

        return $response;
    }

    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public function checkLogin($email, $password) {
        require_once 'PassHash.php';
        // fetching user by email
        $stmt = $this->conn->prepare("SELECT password_hash FROM user WHERE email = ?");

        $stmt->bind_param("s", $email);
        
        $stmt->execute();

        $stmt->bind_result($password_hash);

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            $stmt->close();
            

            if (PassHash::check_password($password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                return FALSE;
            }
        } else {
            $stmt->close();

            // user not existed with the email
            return FALSE;
        }
    }

    /**
     * Checking for duplicate user by email address
     * @param String $email email to check in db
     * @return boolean
     */
    private function isUserExists($email) {
        $stmt = $this->conn->prepare("SELECT idUser from user WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    /**
     * Fetching user by email
     * @param String $email User email id
     */
    public function getUserByEmail($email) {
        $stmt = $this->conn->prepare("SELECT nameUser, email, api_key FROM user WHERE email = ? ");
        
        $stmt->bind_param("s", $email);
        
        if ($stmt->execute()) {
            //$user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result($name, $email, $api_key);
            $stmt->fetch();
            $user = array();
            $user["name"] = $name;
            $user["email"] = $email;
            $user["api_key"] = $api_key;
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }

    /**
     * Fetching user api key
     * @param String $user_id user id primary key in user table
     */
    public function getApiKeyById($user_id) {
        $stmt = $this->conn->prepare("SELECT api_key FROM user WHERE id = ?");
        $stmt->bind_param("i", $user_id);
        if ($stmt->execute()) {
            // $api_key = $stmt->get_result()->fetch_assoc();
            // TODO
            $stmt->bind_result($api_key);
            $stmt->close();
            return $api_key;
        } else {
            return NULL;
        }
    }
     public function verificarCita($doctor,$hora,$fecha,$usuario){
        $stmt = $this->conn->prepare("SELECT idAppointment from appointment WHERE (doctor = ? OR user = ?)AND date= ? AND startTime =?");
        $stmt->bind_param("ssss", $doctor,$usuario,$fecha,$hora);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
                           
     }
    public function getConsultorioFromDoctor($doctor){
        $stmt = $this->conn->prepare("SELECT idconsultorio FROM doctor WHERE iddoctor = ?");
        $stmt->bind_param("s", $doctor);
        if ($stmt->execute()) {
            $stmt->bind_result($consultorio_id);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $consultorio_id;
        } else {
            return "";
        }
    }
    public function getClinicId($consultorio) {
        $stmt = $this->conn->prepare("SELECT idClinica FROM consultorio WHERE idClinica = ?");
        $stmt->bind_param("s", $consultorio);
        if ($stmt->execute()) {
            $stmt->bind_result($clinic_id);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $clinic_id;
        } else {
            return "";
        }
    }

    /**
     * Fetching user id by api key
     * @param String $api_key user api key
     */
    public function getUserId($api_key) {
        $stmt = $this->conn->prepare("SELECT idUser FROM user WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        if ($stmt->execute()) {
            $stmt->bind_result($user_id);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user_id;
        } else {
            return "";
        }
    }

    /**
     * Validating user api key
     * If the api key is there in db, it is a valid key
     * @param String $api_key user api key
     * @return boolean
     */
    public function isValidApiKey($api_key) {
        $stmt = $this->conn->prepare("SELECT idUser from user WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    /**
     * Generating random Unique MD5 String for user Api key
     */
    private function generateApiKey() {
        return md5(uniqid(rand(), true));
    }

    /* ------------- `tasks` table method ------------------ */

    /**
     * Creating new task
     * @param String $user_id user id to whom task belongs to
     * @param String $task task text
     */
    public function createTask($user_id, $task) {
        $stmt = $this->conn->prepare("INSERT INTO tasks(task) VALUES(?)");
        $stmt->bind_param("s", $task);
        $result = $stmt->execute();
        $stmt->close();

        if ($result) {
            // task row created
            // now assign the task to user
            $new_task_id = $this->conn->insert_id;
            $res = $this->createUserTask($user_id, $new_task_id);
            if ($res) {
                // task created successfully
                return $new_task_id;
            } else {
                // task failed to create
                return NULL;
            }
        } else {
            // task failed to create
            return NULL;
        }
    }
    public function getDoctores($especialista){  
 
        if ($especialista == "") {
           $stmt = $this->conn->prepare("SELECT iddoctor,nameDoctor,tel,cel,facebook,especialidad, idconsultorio FROM doctor ");
        } else{
            $stmt = $this->conn->prepare("SELECT iddoctor,nameDoctor,tel,cel,facebook,especialidad, idconsultorio FROM doctor WHERE especialidad = ? ");
            $stmt->bind_param("s", $especialista);            
        }
        
         $response = array();
         $result=$stmt->execute();
          if ($result) {
            $stmt->bind_result($iddoctor,$nameDoctor,$tel,$cel,$facebook,$especialidad,$idconsultorio);
            while ($fila = $stmt->fetch()) {
                $res = array();
                array_push($res, $iddoctor);
                array_push($res, $nameDoctor);
                array_push($res, $tel);
                array_push($res, $cel);
                array_push($res, $facebook); 
                array_push($res, $especialidad);   
                array_push($response, $res);
            }
            $stmt->close();
            return $response;
        } else {
            return NULL;
        }  
    }

    public function getCitas($user_id) {
        if ($user_id == "") {
           $stmt = $this->conn->prepare("SELECT appointment.date,appointment.startTime,doctor.nameDoctor,clinica.name,appointment.idAppointment FROM appointment INNER JOIN doctor ON appointment.doctor=doctor.iddoctor INNER JOIN clinica ON appointment.place=clinica.idClinic ");
        } else{
            $stmt = $this->conn->prepare("SELECT appointment.date,appointment.startTime,doctor.nameDoctor,clinica.name,appointment.idAppointment FROM appointment INNER JOIN doctor ON appointment.doctor=doctor.iddoctor INNER JOIN clinica ON appointment.place=clinica.idClinic WHERE appointment.user = ? ");
            $stmt->bind_param("i", $user_id);            
        }        
         $response = array();
         $result=$stmt->execute();
          if ($result) {
              $stmt->bind_result($date, $start, $doctor, $clinica,$id);     
            while ($fila = $stmt->fetch()) {
                $res = array();
                array_push($res, $id);
                array_push($res, $date);
                array_push($res, $start);
                array_push($res, $doctor);
                array_push($res, $clinica);               
                array_push($response, $res);
            }
            $stmt->close();
            return $response;
        } else {
            return NULL;
        }            
    }

    /**
     * Fetching single task
     * @param String $task_id id of the task
     */
    public function getTask($task_id, $user_id) {
        $stmt = $this->conn->prepare("SELECT t.id, t.task, t.status, t.created_at from tasks t, user_tasks ut WHERE t.id = ? AND ut.task_id = t.id AND ut.user_id = ?");
        $stmt->bind_param("ii", $task_id, $user_id);
        if ($stmt->execute()) {
            $res = array();
            $stmt->bind_result($id, $task, $status, $created_at);
            // TODO
            // $task = $stmt->get_result()->fetch_assoc();
            $stmt->fetch();
            $res["id"] = $id;
            $res["task"] = $task;
            $res["status"] = $status;
            $res["created_at"] = $created_at;
            $stmt->close();
            return $res;
        } else {
            return NULL;
        }
    }
    
     public function getEspecialidades() {
                 
        $response = array();

       if ($resultado = $this->conn->query("SELECT * FROM especialidadesdoctor ")) {
            while ($fila = $resultado->fetch_row()) {
               // printf("(%s,%s)\n", $fila[0], $fila[1]);
                $tmp = array();
                $tmp[0] = $fila[0];
                $tmp[1] = $fila[1];
                array_push($response, $tmp);
            }
            $resultado->close();
        }

        return $response;
    }

    /**
     * Fetching all user tasks
     * @param String $user_id id of the user
     */
    public function getAllUserTasks($user_id) {
        $stmt = $this->conn->prepare("SELECT t.* FROM tasks t, user_tasks ut WHERE t.id = ut.task_id AND ut.user_id = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }

    /**
     * Updating task
     * @param String $task_id id of the task
     * @param String $task task text
     * @param String $status task status
     */
    public function updateTask($user_id, $task_id, $task, $status) {
        $stmt = $this->conn->prepare("UPDATE tasks t, user_tasks ut set t.task = ?, t.status = ? WHERE t.id = ? AND t.id = ut.task_id AND ut.user_id = ?");
        $stmt->bind_param("siii", $task, $status, $task_id, $user_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }

    /**
     * Deleting a task
     * @param String $task_id id of the task to delete
     */
    public function deleteCita($user_id, $cita_id) {
        //DELETE FROM `cliniccalendar`.`appointment` WHERE `appointment`.`idAppointment` = 30
        $stmt = $this->conn->prepare("DELETE FROM appointment WHERE idAppointment = ? AND user = ? ");
        $stmt->bind_param("ss", $cita_id, $user_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }

    /* ------------- `user_tasks` table method ------------------ */

    /**
     * Function to assign a task to user
     * @param String $user_id id of the user
     * @param String $task_id id of the task
     */
    public function createUserTask($user_id, $task_id) {
        $stmt = $this->conn->prepare("INSERT INTO user_tasks(user_id, task_id) values(?, ?)");
        $stmt->bind_param("ii", $user_id, $task_id);
        $result = $stmt->execute();

        if (false === $result) {
            die('execute() failed: ' . htmlspecialchars($stmt->error));
        }
        $stmt->close();
        return $result;
    }

}

?>
