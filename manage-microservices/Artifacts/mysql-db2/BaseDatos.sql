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

--
-- Estructura de tabla para la tabla `taccounts`
--

CREATE TABLE `taccounts` (
  `id_account` bigint(20) NOT NULL,
  `account_number` varchar(255) NOT NULL,
  `account_type` varchar(255) NOT NULL,
  `id_customer` bigint(20) NOT NULL,
  `initial_balance` decimal(10,2) NOT NULL,
  `status` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ttransactions`
--

CREATE TABLE `ttransactions` (
  `id_transaction` bigint(20) NOT NULL,
  `balance` decimal(15,2) NOT NULL,
  `date` date NOT NULL,
  `transaction_type` varchar(255) NOT NULL,
  `value` decimal(38,2) NOT NULL,
  `id_account` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `taccounts`
--
ALTER TABLE `taccounts`
  ADD PRIMARY KEY (`id_account`);


--
-- Indices de la tabla `ttransactions`
--
ALTER TABLE `ttransactions`
  ADD PRIMARY KEY (`id_transaction`),
  ADD KEY `id_account` (`id_account`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `taccounts`
--
ALTER TABLE `taccounts`
  MODIFY `id_account` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `ttransactions`
--
ALTER TABLE `ttransactions`
  MODIFY `id_transaction` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;



--
-- Filtros para la tabla `ttransactions`
--
ALTER TABLE `ttransactions`
  ADD CONSTRAINT `ttransactions_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `taccounts` (`id_account`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
