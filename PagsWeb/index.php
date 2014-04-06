<!DOCTYPE html>
<html>
	<head>
            <title>Clinic Calendar</title></head>
	<body>
            <center> 
            <form method="post" action="showTable.php">
                <h1 align="center">Clinic Calendar</h1>
                <h2 align="center">Ventana Administrativa</h2>

                <h3 align="center">Seleccione la accion a realizar</h3>

                <a href="crearUsuario.php">Crear Usuario</a>
                <a href="creardoctor.php">Crear Doctor</a>
                <a href="crearclinica.php">Crear Clinica</a>
                <a href="crearconsultorio.php">Crear Consultorio</a>
                <a href="crearcita.php">Crear Cita</a>
                <br>
                <a href="editarDoctor.php?table=user&id=Editar Usuario">Editar Usuario</a>
                <a href="editarDoctor.php?table=doctor&id=Editar Doctor">Editar Doctor</a>
                <a href="editarDoctor.php?table=clinica&id=Editar Clinica">Editar Clinica</a>
                <a href="editarConsultorio.php">Editar Consultorio</a>
                <a href="editarCita.php">Editar Cita</a>
             </form>

                </center> 
	</body>
</html>
