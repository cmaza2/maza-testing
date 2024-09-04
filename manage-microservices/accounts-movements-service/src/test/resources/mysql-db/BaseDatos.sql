-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-09-2024 a las 12:13:51
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `maza-test`
--

-- --------------------------------------------------------




-- --------------------------------------------------------



-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tpersons`
--

CREATE TABLE `tpersons` (
  `id_person` bigint(20) NOT NULL,
  `address` varchar(10) NOT NULL,
  `age` int(3) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `id_card` varchar(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
INSERT INTO `tpersons` (`id_person`, `address`, `age`,`gender`,`id_card`,`name`,`phone`) values(1,'CUENCA',30,'MASCULINO','1101020304','ALBERTO CABRERA','0991929394');
--
-- Estructura de tabla para la tabla `tcustomers`
--

CREATE TABLE `tcustomers` (
                              `id_customer` bigint(20) NOT NULL,
                              `password` varchar(255) NOT NULL,
                              `status` bit(1) NOT NULL,
                              `id_person` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

insert into `tcustomers` (`id_customer`, `password`,`status`,`id_person`) values (1,'PATIOTO.123',1,1);

-- --------------------------------------------------------


--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tcustomers`
--
ALTER TABLE `tcustomers`
  ADD PRIMARY KEY (`id_customer`),
  ADD KEY `id_person` (`id_person`);

--
-- Indices de la tabla `tpersons`
--
ALTER TABLE `tpersons`
  ADD PRIMARY KEY (`id_person`),
  ADD UNIQUE KEY `UK_m3a52d9m754qssrh3h53j1xwq` (`id_card`);


--
-- AUTO_INCREMENT de las tablas volcadas
--


--
-- AUTO_INCREMENT de la tabla `tcustomers`
--
ALTER TABLE `tcustomers`
  MODIFY `id_customer` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tpersons`
--
ALTER TABLE `tpersons`
  MODIFY `id_person` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tcustomers`
--
ALTER TABLE `tcustomers`
  ADD CONSTRAINT `tcustomers_ibfk_1` FOREIGN KEY (`id_person`) REFERENCES `tpersons` (`id_person`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
