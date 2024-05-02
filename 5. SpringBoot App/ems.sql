-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 02, 2024 at 02:16 AM
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
(36, 'C6: DELIVERY', 4),
(37, 'C7: FINANCE', 3);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `employeeCondition` enum('OBECNY','DELEGACJA','CHORY','NIEOBECNY') DEFAULT NULL,
  `birthYear` int(11) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `class_employee_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `name`, `surname`, `employeeCondition`, `birthYear`, `salary`, `class_employee_id`) VALUES
(19, 'Eliza', 'Parowiec', 'DELEGACJA', 1998, 3000, 31),
(20, 'Magdalena', 'Bugaj', 'CHORY', 2002, 1890, 31),
(21, 'Andrzej', 'Niemic', 'DELEGACJA', 2001, 3090, 31),
(22, 'Antoni', 'Wilno', 'NIEOBECNY', 2019, 147, 32),
(23, 'Wojciech', 'Mozart', 'OBECNY', 1897, 30459, 32),
(24, 'Justyna', 'Dziura', 'DELEGACJA', 2002, 2060, 33),
(25, 'Kazimierz', 'Wielki', 'NIEOBECNY', 2004, 5060, 33),
(26, 'Rafał', 'Zabłotni', 'DELEGACJA', 1920, 10450, 34),
(27, 'Martyna', 'Makrela', 'DELEGACJA', 1998, 37, 37),
(29, 'Andrzej', 'Kolano', 'NIEOBECNY', 2001, 1980, 37);

-- --------------------------------------------------------

--
-- Table structure for table `rate`
--

CREATE TABLE `rate` (
  `id` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `group_id` int(11) NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rate`
--

INSERT INTO `rate` (`id`, `rating`, `comment`, `group_id`, `date`) VALUES
(2, 1, 'ass', 33, '2024-04-28 22:59:17'),
(3, 6, 'goot', 33, '2024-04-28 23:00:38'),
(4, 6, 'area between lines', 34, '2024-04-28 23:09:19'),
(5, 6, 'very reliable', 34, '2024-04-28 23:12:33'),
(6, 0, '', 34, '2024-04-28 23:12:55'),
(7, 6, 'last review was mistake. how do I delete review???:(((', 34, '2024-04-28 23:13:13'),
(8, 2, 'not goofy enough', 33, '2024-04-28 23:21:28'),
(9, 0, 'stole my cat', 31, '2024-04-28 23:27:29'),
(10, 1, 'bad neighbour', 32, '2024-04-29 10:42:55'),
(11, 0, 'says he like black nuts', 34, '2024-05-01 19:54:50');

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
-- Indexes for table `rate`
--
ALTER TABLE `rate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `group_id` (`group_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `classemployee`
--
ALTER TABLE `classemployee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `rate`
--
ALTER TABLE `rate`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`class_employee_id`) REFERENCES `classemployee` (`id`);

--
-- Constraints for table `rate`
--
ALTER TABLE `rate`
  ADD CONSTRAINT `rate_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `classemployee` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
