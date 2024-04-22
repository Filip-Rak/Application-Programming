-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2024 at 09:57 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ems`
--

-- --------------------------------------------------------

--
-- Table structure for table `classemployee`
--

CREATE TABLE `classemployee` (
  `id` int(11) NOT NULL,
  `workgroup` varchar(255) NOT NULL,
  `maxEmployees` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `classemployee`
--

INSERT INTO `classemployee` (`id`, `workgroup`, `maxEmployees`) VALUES
(31, 'C1: HR', 4),
(32, 'C2: R&D', 4),
(33, 'C3: SALES', 8),
(34, 'C4: THE BOSS', 1),
(35, 'C5: QUALITY ASSURANCE', 3),
(36, 'C6: DELIVERY', 4);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `employeeCondition` varchar(255) NOT NULL,
  `birthYear` int(11) DEFAULT NULL,
  `salary` decimal(10,0) DEFAULT NULL,
  `class_employee_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `name`, `surname`, `employeeCondition`, `birthYear`, `salary`, `class_employee_id`) VALUES
(19, 'Eliza', 'Parowiec', 'DELEGACJA', 1998, 2450, 31),
(20, 'Magdalena', 'Bugaj', 'CHORY', 2002, 1890, 31),
(21, 'Andrzej', 'Niemic', 'DELEGACJA', 2001, 3090, 31),
(22, 'Antoni', 'Wilno', 'NIEOBECNY', 2019, 147, 32),
(23, 'Wojciech', 'Mozart', 'OBECNY', 1897, 30459, 32),
(24, 'Justyna', 'Dziura', 'DELEGACJA', 2002, 2060, 33),
(25, 'Kazimierz', 'Wielki', 'NIEOBECNY', 2004, 5060, 33),
(26, 'Rafał', 'Zabłotni', 'OBECNY', 1920, 10450, 34);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `classemployee`
--
ALTER TABLE `classemployee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `class_employee_id` (`class_employee_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `classemployee`
--
ALTER TABLE `classemployee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`class_employee_id`) REFERENCES `classemployee` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
