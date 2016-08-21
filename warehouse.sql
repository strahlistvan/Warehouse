-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Hoszt: 127.0.0.1
-- Létrehozás ideje: 2016. aug. 21. 20:14
-- Szerver verzió: 5.5.27
-- PHP verzió: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Adatbázis: `warehouse`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet: `inventory`
--

CREATE TABLE IF NOT EXISTS `inventory` (
  `InventoryItemId` int(11) NOT NULL AUTO_INCREMENT,
  `SKU` varchar(30) NOT NULL,
  `ItemReceivedDate` date NOT NULL,
  `WarehouseLocation` varchar(150) NOT NULL,
  PRIMARY KEY (`InventoryItemId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- A tábla adatainak kiíratása `inventory`
--

INSERT INTO `inventory` (`InventoryItemId`, `SKU`, `ItemReceivedDate`, `WarehouseLocation`) VALUES
(1, 'SOCKS43B', '2016-08-19', 'Budapest, Hungary'),
(2, 'SOCKS43B', '2016-08-17', 'Pécs, Hungary'),
(3, 'SOCKS47W', '2016-08-11', 'Budapest, Hungary'),
(4, 'SOCKS47W', '2016-08-10', 'Pécs, Hungary'),
(5, 'SOCKS38R', '2016-08-10', 'New York, USA'),
(6, 'SOCKS43B', '2016-08-11', 'New York, USA'),
(7, 'SOCKS43B', '2016-07-20', 'Pécs, Hungary'),
(8, 'SOCKS43B', '2016-07-20', 'Pécs, Hungary'),
(9, 'SOCKS47W', '2016-05-05', 'Budapest, Hungary'),
(10, 'SOCKS47W', '2016-07-19', 'New York, USA');

-- --------------------------------------------------------

--
-- Tábla szerkezet: `sku_data`
--

CREATE TABLE IF NOT EXISTS `sku_data` (
  `SKU` varchar(30) NOT NULL,
  `Barcode` varchar(30) NOT NULL,
  `RetailPrice` decimal(10,4) NOT NULL,
  PRIMARY KEY (`SKU`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `sku_data`
--

INSERT INTO `sku_data` (`SKU`, `Barcode`, `RetailPrice`) VALUES
('SOCKS26G', '5 991234 654321', 299.0000),
('SOCKS38R', '5 123456 666777', 500.0000),
('SOCKS43B', '5 123654 989898', 799.0000),
('SOCKS47W', '5 999222 132965', 150.0000);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
