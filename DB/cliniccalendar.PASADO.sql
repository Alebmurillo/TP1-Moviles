-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 06, 2014 at 08:41 PM
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`idAppointment`, `date`, `startTime`, `doctor`, `user`, `place`, `endTime`) VALUES
(1, '2014-04-09', '00:00:02', 0, 0, 0, '00:00:00'),
(2, '2014-04-03', '01:03:00', 1, 1, 6, '11:33:00'),
(3, '2014-04-04', '08:30:00', 4, 2, 7, '09:00:00'),
(4, '0000-00-00', '20:00:00', 4, 2, 6, '22:30:00');

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `clinica`
--

INSERT INTO `clinica` (`idClinic`, `name`, `latitud`, `longitud`) VALUES
(1, 'La Lily', '56.871', '78'),
(2, 'KILL Luis', '34.1', '53'),
(3, 'Muelita Feliz', '89', '98'),
(4, 'La 89', '776', '9'),
(5, 'Solmerth', '93.2', '234.1');

-- --------------------------------------------------------

--
-- Table structure for table `consultorio`
--

CREATE TABLE IF NOT EXISTS `consultorio` (
  `idConsultorio` int(11) NOT NULL AUTO_INCREMENT,
  `idClinica` int(11) NOT NULL,
  `numConsultorio` int(11) NOT NULL,
  PRIMARY KEY (`idConsultorio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

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
(12, 2, 777);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`iddoctor`, `nameDoctor`, `tel`, `cel`, `facebook`, `especialidad`) VALUES
(1, 'fab', 88253585, 2314524, NULL, 3),
(2, 'ale', 88253585, 2314524, NULL, 3),
(3, 'Lily', 2314524, 83479540, NULL, 3),
(4, 'Ruffus', 24944280, 45720384, NULL, 3),
(5, 'Kimmy', 4536976, 78574379, NULL, 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idUser`, `nameUser`, `tel`, `facebook`, `sex`) VALUES
(1, 'ale', 69837201, 'none', 'femenino'),
(2, 'Adri', 24944280, 'adri123', 'femenino'),
(7, 'Ligia Murillo', 24593095, 'NA', 'femenino');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
