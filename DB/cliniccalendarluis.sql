-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 21-04-2014 a las 06:05:58
-- Versión del servidor: 5.6.12-log
-- Versión de PHP: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `cliniccalendar`
--
CREATE DATABASE IF NOT EXISTS `cliniccalendar` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `cliniccalendar`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `appointment`
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=78 ;

--
-- Volcado de datos para la tabla `appointment`
--

INSERT INTO `appointment` (`idAppointment`, `date`, `startTime`, `doctor`, `user`, `place`, `endTime`) VALUES
(55, '2014-04-21', '08:00:00', 2, 11, 2, '00:00:00'),
(59, '2014-04-20', '08:00:00', 2, 15, 2, '00:00:00'),
(60, '2014-04-20', '08:00:00', 1, 17, 1, '00:00:00'),
(61, '2014-04-20', '13:00:00', 5, 17, 5, '00:00:00'),
(62, '2014-04-20', '08:00:00', 3, 18, 3, '00:00:00'),
(71, '2014-04-21', '13:00:00', 1, 11, 1, '00:00:00'),
(75, '2014-04-20', '10:00:00', 2, 11, 2, '00:00:00'),
(77, '2014-04-23', '08:00:00', 2, 11, 2, '00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clinica`
--

CREATE TABLE IF NOT EXISTS `clinica` (
  `idClinic` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `latitud` varchar(40) NOT NULL,
  `longitud` varchar(40) NOT NULL,
  PRIMARY KEY (`idClinic`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Volcado de datos para la tabla `clinica`
--

INSERT INTO `clinica` (`idClinic`, `name`, `latitud`, `longitud`) VALUES
(1, 'La Lily', '9.9835619', '-84.0450022'),
(2, 'KILL Luis', '9.55', '9.86555'),
(3, 'Muelita Feliz', '9.94', '9.8'),
(4, 'La 89', '9.23', '9.21'),
(5, 'Solmerth', '9.4', '9.4'),
(6, 'jj', '9.12', '9.66'),
(7, 'Ultra', '9.27', '9.22'),
(8, 'Azowr', '9.186', '9.59'),
(9, 'Azowr', '9.47', '9.68');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consultorio`
--

CREATE TABLE IF NOT EXISTS `consultorio` (
  `idConsultorio` int(11) NOT NULL AUTO_INCREMENT,
  `idClinica` int(11) NOT NULL,
  `numConsultorio` int(11) NOT NULL,
  PRIMARY KEY (`idConsultorio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Volcado de datos para la tabla `consultorio`
--

INSERT INTO `consultorio` (`idConsultorio`, `idClinica`, `numConsultorio`) VALUES
(1, 1, 65),
(2, 2, 65),
(3, 3, 789),
(4, 5, 0),
(5, 6, 56),
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
-- Estructura de tabla para la tabla `doctor`
--

CREATE TABLE IF NOT EXISTS `doctor` (
  `iddoctor` int(11) NOT NULL AUTO_INCREMENT,
  `nameDoctor` varchar(256) NOT NULL,
  `tel` int(11) NOT NULL,
  `cel` int(11) NOT NULL,
  `facebook` varchar(500) DEFAULT NULL,
  `especialidad` int(11) NOT NULL,
  `idconsultorio` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`iddoctor`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Volcado de datos para la tabla `doctor`
--

INSERT INTO `doctor` (`iddoctor`, `nameDoctor`, `tel`, `cel`, `facebook`, `especialidad`, `idconsultorio`, `iduser`) VALUES
(1, 'fab', 88253585, 2314524, NULL, 3, 1, 11),
(2, 'ale', 88253585, 2314524, NULL, 3, 2, 0),
(3, 'Lily', 2314524, 83479540, NULL, 3, 3, 18),
(4, 'Ruffus', 24944280, 45720384, NULL, 3, 4, 0),
(5, 'Kimmy', 4536976, 78574379, NULL, 1, 5, 0),
(9, 'Oscar Saborio', 56885446, 76578654, NULL, 4, 6, 0),
(10, 'Elisa Brenes', 2391384, 3204824, NULL, 1, 7, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `doctorporconsultorio`
--

CREATE TABLE IF NOT EXISTS `doctorporconsultorio` (
  `doctor` int(11) NOT NULL,
  `consultorio` int(11) NOT NULL,
  `duration` int(11) NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidadesdoctor`
--

CREATE TABLE IF NOT EXISTS `especialidadesdoctor` (
  `idEspecialidad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) NOT NULL,
  PRIMARY KEY (`idEspecialidad`),
  UNIQUE KEY `id` (`idEspecialidad`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `especialidadesdoctor`
--

INSERT INTO `especialidadesdoctor` (`idEspecialidad`, `nombre`) VALUES
(1, 'odontologia'),
(2, 'pediatria'),
(3, 'neurologia'),
(4, 'oftalmologia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horaxdoctorxconsultorio`
--

CREATE TABLE IF NOT EXISTS `horaxdoctorxconsultorio` (
  `id` int(11) NOT NULL,
  `consultorioReservado` int(11) NOT NULL,
  `hora` time NOT NULL,
  `state` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login_attempts`
--

CREATE TABLE IF NOT EXISTS `login_attempts` (
  `memberID` int(11) NOT NULL,
  `time` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `login_attempts`
--

INSERT INTO `login_attempts` (`memberID`, `time`) VALUES
(1, '1397794908'),
(1, '1397794912'),
(1, '1397794976'),
(1, '1397794981');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `members`
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
-- Volcado de datos para la tabla `members`
--

INSERT INTO `members` (`idMember`, `name`, `email`, `password`, `salt`) VALUES
(1, 'test_user', 'test@example.com', '00807432eae173f652f2064bdca1b61b290b52d40e429a7d295d76a71084aa96c0233b82f1feac45529e0726559645acaed6f3ae58a286b9f075916ebf66cacc', 'f9aab579fc1b41ed0c44fe4ecdbfcdb4cb99b9023abb241a6db833288f4eea3c02f76e0d35204a8695077dcf81932aa59006423976224be0390395bae152d4ef');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `nameUser` varchar(222) NOT NULL,
  `tel` int(11) NOT NULL,
  `facebook` varchar(222) NOT NULL,
  `sex` varchar(10) NOT NULL,
  `password_hash` text NOT NULL,
  `api_key` text NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `id` (`idUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`idUser`, `nameUser`, `tel`, `facebook`, `sex`, `password_hash`, `api_key`, `email`) VALUES
(1, 'test', 111111, 'facebook', 'masculino', '1', '1', '1@1.com'),
(11, 'roberto', 0, '', 'masculino', '$2a$10$85602da91b29b2c41bdb5uQU5UahGCOGyIkvHrEQKW.7PLK6Ab7my', '67760c355252d830f2c4837cfe23e196', 'a@gmail.com'),
(12, 'gufi', 0, '', 'masculino', '$2a$10$26ab0b5d98e5d22666729u5SPuUIBVqOXAVZIJpoCoaYuZeCec3HG', 'aab65dcc263983a9c30f52928000206b', 'd@gmail.com'),
(13, 'ale', 0, '', 'femenino', '$2a$10$45deef3755197303a223aeQFT0aFjDAUuH/xXjCl0EOhuWo.tQIGK', '625ae68dea4cd55a1d5650d535c09333', 'ale@gmail.com'),
(18, 'Luis', 0, '', 'male', '$2a$10$eeb8ec17d38a255fe84c0e7ZxmH6wRPTICARR6LsSjp8XRN7LVFTG', '3a41f6a765866b979c1717033ddc17b8', 'changi007@hotmail.com'),
(19, 'reaper', 0, '', 'masculino', '$2a$10$2bd1a7d7c24fb394a367du1JcXibgWPhK4Hyc9AN7z.x4umHu8VWK', '66eede779a38de81c73417c3e4324f67', 'e@f.com');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
