-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 07, 2023 at 06:40 PM
-- Server version: 5.7.11
-- PHP Version: 5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `personalapidb`
--

-- --------------------------------------------------------

--
-- Table structure for table `cheque`
--

DROP TABLE IF EXISTS `cheque`;
CREATE TABLE `cheque` (
  `id` int(11) NOT NULL,
  `beneficiario` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `importe` double DEFAULT NULL,
  `cuenta_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `dtype` varchar(31) NOT NULL,
  `id` int(11) NOT NULL,
  `activo` bit(1) NOT NULL,
  `alta` date DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `moroso` bit(1) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `cif` varchar(255) DEFAULT NULL,
  `unidades_de_negocio` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
CREATE TABLE `cuenta` (
  `dtype` varchar(31) NOT NULL,
  `id` int(11) NOT NULL,
  `comision` double DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `interes` double DEFAULT NULL,
  `saldo` double DEFAULT NULL,
  `cliente_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `cuenta_transacciones`
--

DROP TABLE IF EXISTS `cuenta_transacciones`;
CREATE TABLE `cuenta_transacciones` (
  `cuenta_id` int(11) NOT NULL,
  `transacciones_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `mora`
--

DROP TABLE IF EXISTS `mora`;
CREATE TABLE `mora` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
CREATE TABLE `pago` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
CREATE TABLE `prestamo` (
  `id` int(11) NOT NULL,
  `anios` int(11) DEFAULT NULL,
  `fecha_concesion` date DEFAULT NULL,
  `interes` int(11) DEFAULT NULL,
  `interes_mora` int(11) DEFAULT NULL,
  `liquidado` bit(1) NOT NULL,
  `mensualidad` double DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `moroso` bit(1) NOT NULL,
  `saldo` double DEFAULT NULL,
  `cliente_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaccion`
--

DROP TABLE IF EXISTS `transaccion`;
CREATE TABLE `transaccion` (
  `id` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `importe` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cheque`
--
ALTER TABLE `cheque`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKju68ghku0sfdjore2ao4ip4to` (`cuenta_id`);

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cuenta`
--
ALTER TABLE `cuenta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4p224uogyy5hmxvn8fwa2jlug` (`cliente_id`);

--
-- Indexes for table `cuenta_transacciones`
--
ALTER TABLE `cuenta_transacciones`
  ADD UNIQUE KEY `UK_79dxuir9ppn0esbo5erd6hj1o` (`transacciones_id`),
  ADD KEY `FKfgm4d29rn5bl1kxbnyr44ihcg` (`cuenta_id`);

--
-- Indexes for table `mora`
--
ALTER TABLE `mora`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `prestamo`
--
ALTER TABLE `prestamo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnuxm078vsm77xihcgtkk4cnns` (`cliente_id`);

--
-- Indexes for table `transaccion`
--
ALTER TABLE `transaccion`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cheque`
--
ALTER TABLE `cheque`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `cuenta`
--
ALTER TABLE `cuenta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `mora`
--
ALTER TABLE `mora`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pago`
--
ALTER TABLE `pago`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `prestamo`
--
ALTER TABLE `prestamo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `transaccion`
--
ALTER TABLE `transaccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `cheque`
--
ALTER TABLE `cheque`
  ADD CONSTRAINT `FKju68ghku0sfdjore2ao4ip4to` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`);

--
-- Constraints for table `cuenta`
--
ALTER TABLE `cuenta`
  ADD CONSTRAINT `FK4p224uogyy5hmxvn8fwa2jlug` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);

--
-- Constraints for table `cuenta_transacciones`
--
ALTER TABLE `cuenta_transacciones`
  ADD CONSTRAINT `FK4xlif3958u92760rb4udgmix5` FOREIGN KEY (`transacciones_id`) REFERENCES `transaccion` (`id`),
  ADD CONSTRAINT `FKfgm4d29rn5bl1kxbnyr44ihcg` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`);

--
-- Constraints for table `prestamo`
--
ALTER TABLE `prestamo`
  ADD CONSTRAINT `FKnuxm078vsm77xihcgtkk4cnns` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
