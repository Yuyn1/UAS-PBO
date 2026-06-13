-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 13, 2026 at 03:46 PM
-- Server version: 8.4.3
-- PHP Version: 8.3.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_yu_fashionstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `password`) VALUES
(1, 'adminyu', 'admin123');

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `id` int NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `kategori` enum('Atasan','Bawahan','Outerwear','Alas Kaki','Aksesoris') NOT NULL,
  `harga` double NOT NULL,
  `stok` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id`, `nama_barang`, `kategori`, `harga`, `stok`) VALUES
(7, 'Kaos', 'Atasan', 90000, 37),
(8, 'Celana Jeans', 'Bawahan', 200000, 15),
(9, 'Celana Pendek', 'Bawahan', 100000, 25),
(11, 'Sweater', 'Outerwear', 180000, 12),
(12, 'Sepatu', 'Alas Kaki', 300000, 8),
(13, 'Sandal', 'Alas Kaki', 80000, 10),
(14, 'Topi', 'Aksesoris', 60000, 24),
(15, 'Tas', 'Aksesoris', 120000, 10),
(16, 'anting', 'Aksesoris', 20000, 23),
(17, 'kalung', 'Aksesoris', 75000, 90),
(18, 'junkok', 'Atasan', 900000, 32);

-- --------------------------------------------------------

--
-- Table structure for table `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `id` int NOT NULL,
  `transaksi_id` int DEFAULT NULL,
  `barang_id` int DEFAULT NULL,
  `jumlah` int NOT NULL,
  `subtotal` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `detail_transaksi`
--

INSERT INTO `detail_transaksi` (`id`, `transaksi_id`, `barang_id`, `jumlah`, `subtotal`) VALUES
(1, 1, 7, 12, 972000),
(2, 2, 7, 1, 90000),
(3, 3, 13, 5, 360000),
(4, 4, 14, 6, 324000);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id` int NOT NULL,
  `tanggal` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `total_bayar` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id`, `tanggal`, `total_bayar`) VALUES
(1, '2026-06-13 14:09:27', 972000),
(2, '2026-06-13 14:19:31', 90000),
(3, '2026-06-13 14:20:06', 360000),
(4, '2026-06-13 14:30:51', 324000),
(5, '2026-06-13 14:45:38', 675000);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` enum('admin','user') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'adminyu', 'admin123', 'admin'),
(2, 'useryu', 'user123', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `transaksi_id` (`transaksi_id`),
  ADD KEY `detail_transaksi_ibfk_2` (`barang_id`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `detail_transaksi_ibfk_1` FOREIGN KEY (`transaksi_id`) REFERENCES `transaksi` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `detail_transaksi_ibfk_2` FOREIGN KEY (`barang_id`) REFERENCES `barang` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
