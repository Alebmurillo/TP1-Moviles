-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 18, 2014 at 04:38 PM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cliniccalendar`
--
CREATE DATABASE IF NOT EXISTS `cliniccalendar` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `cliniccalendar`;

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE IF NOT EXISTS `appointment` (
  `idAppointment` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `startTime` time NOT NULL,
  `doctor` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `place` int(11) NOT NULL,
  `endTime` time NOT NULL,
  PRIMARY KEY (`idAppointment`),
  UNIQUE KEY `id` (`idAppointment`),
  KEY `user` (`user`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`idAppointment`, `date`, `startTime`, `doctor`, `user`, `place`, `endTime`) VALUES
(1, '2014-04-09', '00:00:02', 0, 0, 0, '00:00:00'),
(2, '2014-04-03', '01:03:00', 1, 1, 6, '11:33:00'),
(3, '2014-04-04', '08:30:00', 4, 2, 7, '09:00:00'),
(4, '2014-01-30', '20:00:00', 9, 7, 6, '22:30:00'),
(5, '2014-04-02', '12:59:00', 4, 8, 10, '13:00:00'),
(6, '0000-00-00', '00:00:00', 1, 1, 6, '00:00:00'),
(7, '2014-04-01', '13:59:00', 5, 7, 13, '14:59:00'),
(8, '2014-04-03', '03:58:00', 9, 7, 7, '04:10:00'),
(9, '2014-01-01', '01:59:00', 5, 8, 15, '02:59:00');

-- --------------------------------------------------------

--
-- Table structure for table `clinica`
--

CREATE TABLE IF NOT EXISTS `clinica` (
  `idClinic` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `latitud` varchar(40) NOT NULL,
  `longitud` varchar(40) NOT NULL,
  PRIMARY KEY (`idClinic`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `clinica`
--

INSERT INTO `clinica` (`idClinic`, `name`, `latitud`, `longitud`) VALUES
(1, 'La Lily', '56.871', '78'),
(2, 'KILL Luis', '34.1', '53'),
(3, 'Muelita Feliz', '89', '98'),
(4, 'La 89', '776', '9'),
(5, 'Solmerth', '93.2', '234.1'),
(6, 'jj', '7', '8'),
(7, 'Ultra', '11', '22'),
(8, 'Azowr', '33', '44'),
(9, 'Azowr', '33', '44');

-- --------------------------------------------------------

--
-- Table structure for table `consultorio`
--

CREATE TABLE IF NOT EXISTS `consultorio` (
  `idConsultorio` int(11) NOT NULL AUTO_INCREMENT,
  `idClinica` int(11) NOT NULL,
  `numConsultorio` int(11) NOT NULL,
  PRIMARY KEY (`idConsultorio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `consultorio`
--

INSERT INTO `consultorio` (`idConsultorio`, `idClinica`, `numConsultorio`) VALUES
(1, 0, 65),
(2, 0, 65),
(3, 0, 789),
(4, 0, 0),
(5, 0, 56),
(6, 3, 67),
(7, 2, 1),
(8, 3, 765),
(9, 4, 0),
(10, 3, 1),
(11, 1, 67),
(12, 2, 777),
(13, 5, 568324),
(14, 7, 300),
(15, 1, 1),
(16, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE IF NOT EXISTS `doctor` (
  `iddoctor` int(11) NOT NULL AUTO_INCREMENT,
  `nameDoctor` varchar(256) NOT NULL,
  `tel` int(11) NOT NULL,
  `cel` int(11) NOT NULL,
  `facebook` varchar(500) DEFAULT NULL,
  `especialidad` int(11) NOT NULL,
  PRIMARY KEY (`iddoctor`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`iddoctor`, `nameDoctor`, `tel`, `cel`, `facebook`, `especialidad`) VALUES
(1, 'fab', 88253585, 2314524, NULL, 3),
(2, 'ale', 88253585, 2314524, NULL, 3),
(3, 'Lily', 2314524, 83479540, NULL, 3),
(4, 'Ruffus', 24944280, 45720384, NULL, 3),
(5, 'Kimmy', 4536976, 78574379, NULL, 1),
(9, 'Oscar Saborio', 56885446, 76578654, NULL, 4),
(10, 'Elisa Brenes', 2391384, 3204824, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `doctorporconsultorio`
--

CREATE TABLE IF NOT EXISTS `doctorporconsultorio` (
  `doctor` int(11) NOT NULL,
  `consultorio` int(11) NOT NULL,
  `duration` int(11) NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `especialidadesdoctor`
--

CREATE TABLE IF NOT EXISTS `especialidadesdoctor` (
  `idEspecialidad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) NOT NULL,
  PRIMARY KEY (`idEspecialidad`),
  UNIQUE KEY `id` (`idEspecialidad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `horaxdoctorxconsultorio`
--

CREATE TABLE IF NOT EXISTS `horaxdoctorxconsultorio` (
  `id` int(11) NOT NULL,
  `consultorioReservado` int(11) NOT NULL,
  `hora` time NOT NULL,
  `state` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `login_attempts`
--

CREATE TABLE IF NOT EXISTS `login_attempts` (
  `memberID` int(11) NOT NULL,
  `time` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login_attempts`
--

INSERT INTO `login_attempts` (`memberID`, `time`) VALUES
(1, '1397794908'),
(1, '1397794912'),
(1, '1397794976'),
(1, '1397794981');

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE IF NOT EXISTS `members` (
  `idMember` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` char(128) NOT NULL,
  `salt` char(128) NOT NULL,
  PRIMARY KEY (`idMember`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`idMember`, `name`, `email`, `password`, `salt`) VALUES
(1, 'test_user', 'test@example.com', '00807432eae173f652f2064bdca1b61b290b52d40e429a7d295d76a71084aa96c0233b82f1feac45529e0726559645acaed6f3ae58a286b9f075916ebf66cacc', 'f9aab579fc1b41ed0c44fe4ecdbfcdb4cb99b9023abb241a6db833288f4eea3c02f76e0d35204a8695077dcf81932aa59006423976224be0390395bae152d4ef');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `nameUser` varchar(222) NOT NULL,
  `tel` int(11) NOT NULL,
  `facebook` varchar(222) NOT NULL,
  `sex` varchar(10) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `id` (`idUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idUser`, `nameUser`, `tel`, `facebook`, `sex`) VALUES
(1, 'ale', 69837201, 'none', 'femenino'),
(2, 'Adri', 24944280, 'adri123', 'femenino'),
(7, 'Ligia Murillo', 24593095, 'NA', 'femenino'),
(8, 'Juan Diego Chacon Rojas', 128472, 'www.jd.com', 'masculino'),
(9, 'Raquel Perez', 94859385, 'na', 'femenino');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
