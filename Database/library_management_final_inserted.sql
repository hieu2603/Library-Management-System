-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.39 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for library_management
CREATE DATABASE IF NOT EXISTS `library_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `library_management`;

-- Dumping structure for table library_management.account
CREATE TABLE IF NOT EXISTS `account` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `permission_id` int NOT NULL,
  `status` enum('Đang hoạt động','Ngừng hoạt động') NOT NULL,
  `staff_id` int DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_permission_idx` (`permission_id`),
  KEY `fk_account_staff_id` (`staff_id`),
  CONSTRAINT `fk_account_staff_id` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `fk_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.account: ~4 rows (approximately)
INSERT INTO `account` (`account_id`, `username`, `password`, `permission_id`, `status`, `staff_id`) VALUES
	(1, 'admin', '12345', 1, 'Đang hoạt động', 3),
	(2, 'staff1', '12345', 2, 'Đang hoạt động', 1),
	(3, 'staff2', '12345', 3, 'Ngừng hoạt động', 2),
	(4, 'staff3', '12345', 3, 'Đang hoạt động', 4);

-- Dumping structure for table library_management.book
CREATE TABLE IF NOT EXISTS `book` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `publisher_id` int NOT NULL,
  `year_publish` int NOT NULL,
  `category_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `book_image` varchar(255) DEFAULT NULL,
  `bookshelf_id` int DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `fk_publisher_publisher_id_idx` (`publisher_id`),
  KEY `fk_category_category_id_idx` (`category_id`),
  KEY `fk_bookshelf_id_idx` (`bookshelf_id`),
  CONSTRAINT `fk_bookshelf_id` FOREIGN KEY (`bookshelf_id`) REFERENCES `bookshelf` (`bookshelf_id`),
  CONSTRAINT `fk_category_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `fk_publisher_publisher_id` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`publisher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.book: ~23 rows (approximately)
INSERT INTO `book` (`book_id`, `title`, `author`, `publisher_id`, `year_publish`, `category_id`, `quantity`, `book_image`, `bookshelf_id`) VALUES
	(1, 'The Great Gatsby', 'F. Scott Fitzgerald', 1, 1925, 1, 10, 'gatsby.jpg', 1),
	(2, '1984', 'George Orwell', 1, 1949, 2, 10, '1984.jpg', 2),
	(3, 'To Kill a Mockingbird', 'Harper Lee', 3, 1960, 1, 10, 'mockingbird.jpg', 3),
	(4, 'House of Leaves', 'Mark Z. Danielewski', 1, 2000, 3, 10, '240house_of_leaves.jpg', 2),
	(5, 'Kafka on the Shore', 'Haruki Murakami', 1, 2002, 2, 10, 'kafka.jpg', 2),
	(6, 'Norwegian Wood', 'Haruki Murakami', 1, 1987, 1, 10, 'norwegian.jpg', 1),
	(7, 'The Wind-Up Bird Chronicle', 'Haruki Murakami', 1, 1994, 3, 10, 'wind_up.jpg', 1),
	(8, 'Xứ cát', 'Frank Herbert', 1, 1965, 2, 10, 'dune.jpg', 3),
	(9, 'Cứu tinh xứ cát', 'Frank Herbert', 1, 1969, 2, 10, 'dune_messiah.jpg', 4),
	(10, 'All Tomorrows', 'C. M. Koseman', 2, 2008, 2, 10, 'all_tomorrows.jpg', 4),
	(11, 'Blood Meridian', 'Cormac McCarthy', 3, 1985, 1, 10, 'blood_meridian.png', 1),
	(12, 'No Country for Old Men', 'Cormac McCarthy', 3, 2005, 1, 10, 'no_country.jpg', 1),
	(13, 'No Longer Human', 'Osamu Dazai', 1, 1948, 1, 10, 'no_longer_human.jpg', 2),
	(14, 'Pride and Prejudice', 'Jane Austen', 2, 1813, 3, 10, 'pride_prejudice.jpg', 4),
	(15, 'The Catcher in the Rye', 'J.D. Salinger', 2, 1951, 3, 10, 'catcher_rye.jpg', 2),
	(16, 'The Hobbit', 'J.R.R. Tolkien', 2, 1937, 3, 10, 'the_hobbit.jpg', 4),
	(17, 'Fahrenheit 451', 'Ray Bradbury', 2, 1953, 2, 10, 'fahrenheit_451.jpg', 3),
	(18, 'Jane Eyre', 'Charlotte Brontë', 3, 1847, 3, 0, 'jane_eyre.jpg', 2),
	(19, 'Brave New World', 'Aldous Huxley', 2, 1932, 2, 0, 'brave_new_world.jpg', 3),
	(20, 'Moby-Dick', 'Herman Melville', 3, 1851, 1, 0, 'moby_dick.jpg', 4),
	(21, 'War and Peace', 'Leo Tolstoy', 3, 1869, 1, 0, 'war_peace.jpg', 3),
	(22, 'The Odyssey', 'Homer', 2, 1614, 2, 0, 'odyssey.jpg', 2),
	(23, 'Crime and Punishment', 'Fyodor Dostoevsky', 3, 1866, 1, 0, 'crime_punishment.jpg', 3);

-- Dumping structure for table library_management.bookitem
CREATE TABLE IF NOT EXISTS `bookitem` (
  `isbn` varchar(255) NOT NULL,
  `book_id` int DEFAULT NULL,
  `purchaseticket_id` int NOT NULL,
  `status` enum('Có sẵn','Đang mượn','Hư hỏng','Mất') NOT NULL,
  `price` bigint DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  KEY `fk_purchaseticket_bookitem_id_idx` (`purchaseticket_id`),
  KEY `fk_book_id_idx` (`book_id`),
  CONSTRAINT `fk_bookitem_book_id` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
  CONSTRAINT `fk_purchaseticket_bookitem_id` FOREIGN KEY (`purchaseticket_id`) REFERENCES `purchaseticket` (`purchase_ticket_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.bookitem: ~170 rows (approximately)
INSERT INTO `bookitem` (`isbn`, `book_id`, `purchaseticket_id`, `status`, `price`) VALUES
	('16798201985', 4, 1, 'Có sẵn', 24000),
	('16798201986', 4, 1, 'Có sẵn', 24000),
	('16798201987', 4, 1, 'Có sẵn', 24000),
	('16798201988', 4, 1, 'Đang mượn', 24000),
	('16798201989', 4, 1, 'Có sẵn', 24000),
	('16798201990', 4, 1, 'Có sẵn', 24000),
	('16798201991', 4, 1, 'Có sẵn', 24000),
	('16798201992', 4, 1, 'Có sẵn', 24000),
	('16798201993', 4, 1, 'Có sẵn', 24000),
	('16798201994', 4, 1, 'Có sẵn', 24000),
	('17364517896', 6, 1, 'Có sẵn', 24000),
	('17364517897', 6, 1, 'Có sẵn', 24000),
	('17364517898', 6, 1, 'Có sẵn', 24000),
	('17364517899', 6, 1, 'Có sẵn', 24000),
	('17364517900', 6, 1, 'Có sẵn', 24000),
	('17364517901', 6, 1, 'Có sẵn', 24000),
	('17364517902', 6, 1, 'Có sẵn', 24000),
	('17364517903', 6, 1, 'Có sẵn', 24000),
	('17364517904', 6, 1, 'Có sẵn', 24000),
	('17364517905', 6, 1, 'Có sẵn', 24000),
	('45312647895', 13, 3, 'Có sẵn', 30000),
	('45312647896', 13, 3, 'Có sẵn', 30000),
	('45312647897', 13, 3, 'Có sẵn', 30000),
	('45312647898', 13, 3, 'Có sẵn', 30000),
	('45312647899', 13, 3, 'Có sẵn', 30000),
	('45312647900', 13, 3, 'Có sẵn', 30000),
	('45312647901', 13, 3, 'Có sẵn', 30000),
	('45312647902', 13, 3, 'Có sẵn', 30000),
	('45312647903', 13, 3, 'Có sẵn', 30000),
	('45312647904', 13, 3, 'Có sẵn', 30000),
	('45321648795', 12, 3, 'Có sẵn', 26000),
	('45321648796', 12, 3, 'Có sẵn', 26000),
	('45321648797', 12, 3, 'Có sẵn', 26000),
	('45321648798', 12, 3, 'Có sẵn', 26000),
	('45321648799', 12, 3, 'Có sẵn', 26000),
	('45321648800', 12, 3, 'Có sẵn', 26000),
	('45321648801', 12, 3, 'Có sẵn', 26000),
	('45321648802', 12, 3, 'Có sẵn', 26000),
	('45321648803', 12, 3, 'Có sẵn', 26000),
	('45321648804', 12, 3, 'Có sẵn', 26000),
	('45621301542', 14, 4, 'Có sẵn', 30000),
	('45621301543', 14, 4, 'Có sẵn', 30000),
	('45621301544', 14, 4, 'Có sẵn', 30000),
	('45621301545', 14, 4, 'Có sẵn', 30000),
	('45621301546', 14, 4, 'Có sẵn', 30000),
	('45621301547', 14, 4, 'Có sẵn', 30000),
	('45621301548', 14, 4, 'Có sẵn', 30000),
	('45621301549', 14, 4, 'Có sẵn', 30000),
	('45621301550', 14, 4, 'Có sẵn', 30000),
	('45621301551', 14, 4, 'Có sẵn', 30000),
	('61543215484', 17, 4, 'Có sẵn', 24000),
	('61543215485', 17, 4, 'Có sẵn', 24000),
	('61543215486', 17, 4, 'Có sẵn', 24000),
	('61543215487', 17, 4, 'Có sẵn', 24000),
	('61543215488', 17, 4, 'Đang mượn', 24000),
	('61543215489', 17, 4, 'Có sẵn', 24000),
	('61543215490', 17, 4, 'Có sẵn', 24000),
	('61543215491', 17, 4, 'Có sẵn', 24000),
	('61543215492', 17, 4, 'Có sẵn', 24000),
	('61543215493', 17, 4, 'Có sẵn', 24000),
	('68491532791', 16, 4, 'Có sẵn', 24000),
	('68491532792', 16, 4, 'Có sẵn', 24000),
	('68491532793', 16, 4, 'Có sẵn', 24000),
	('68491532794', 16, 4, 'Có sẵn', 24000),
	('68491532795', 16, 4, 'Có sẵn', 24000),
	('68491532796', 16, 4, 'Có sẵn', 24000),
	('68491532797', 16, 4, 'Có sẵn', 24000),
	('68491532798', 16, 4, 'Đang mượn', 24000),
	('68491532799', 16, 4, 'Có sẵn', 24000),
	('68491532800', 16, 4, 'Có sẵn', 24000),
	('73654897629', 2, 1, 'Có sẵn', 26000),
	('73654897630', 2, 1, 'Có sẵn', 26000),
	('73654897631', 2, 1, 'Có sẵn', 26000),
	('73654897632', 2, 1, 'Có sẵn', 26000),
	('73654897633', 2, 1, 'Có sẵn', 26000),
	('73654897634', 2, 1, 'Có sẵn', 26000),
	('73654897635', 2, 1, 'Có sẵn', 26000),
	('73654897636', 2, 1, 'Có sẵn', 26000),
	('73654897637', 2, 1, 'Có sẵn', 26000),
	('73654897638', 2, 1, 'Có sẵn', 26000),
	('74586381956', 7, 2, 'Có sẵn', 25000),
	('74586381957', 7, 2, 'Có sẵn', 25000),
	('74586381958', 7, 2, 'Có sẵn', 25000),
	('74586381959', 7, 2, 'Có sẵn', 25000),
	('74586381960', 7, 2, 'Có sẵn', 25000),
	('74586381961', 7, 2, 'Có sẵn', 25000),
	('74586381962', 7, 2, 'Có sẵn', 25000),
	('74586381963', 7, 2, 'Có sẵn', 25000),
	('74586381964', 7, 2, 'Có sẵn', 25000),
	('74586381965', 7, 2, 'Có sẵn', 25000),
	('74613254892', 15, 4, 'Có sẵn', 20000),
	('74613254893', 15, 4, 'Có sẵn', 20000),
	('74613254894', 15, 4, 'Có sẵn', 20000),
	('74613254895', 15, 4, 'Có sẵn', 20000),
	('74613254896', 15, 4, 'Có sẵn', 20000),
	('74613254897', 15, 4, 'Có sẵn', 20000),
	('74613254898', 15, 4, 'Có sẵn', 20000),
	('74613254899', 15, 4, 'Có sẵn', 20000),
	('74613254900', 15, 4, 'Có sẵn', 20000),
	('74613254901', 15, 4, 'Có sẵn', 20000),
	('76098108700', 10, 2, 'Có sẵn', 24000),
	('76098108701', 10, 2, 'Có sẵn', 24000),
	('76098108702', 10, 2, 'Có sẵn', 24000),
	('76098108703', 10, 2, 'Có sẵn', 24000),
	('76098108704', 10, 2, 'Có sẵn', 24000),
	('76098108705', 10, 2, 'Có sẵn', 24000),
	('76098108706', 10, 2, 'Có sẵn', 24000),
	('76098108707', 10, 2, 'Có sẵn', 24000),
	('76098108708', 10, 2, 'Có sẵn', 24000),
	('76098108709', 10, 2, 'Có sẵn', 24000),
	('76859203750', 9, 2, 'Có sẵn', 21000),
	('76859203751', 9, 2, 'Có sẵn', 21000),
	('76859203752', 9, 2, 'Có sẵn', 21000),
	('76859203753', 9, 2, 'Có sẵn', 21000),
	('76859203754', 9, 2, 'Có sẵn', 21000),
	('76859203755', 9, 2, 'Có sẵn', 21000),
	('76859203756', 9, 2, 'Đang mượn', 21000),
	('76859203757', 9, 2, 'Có sẵn', 21000),
	('76859203758', 9, 2, 'Có sẵn', 21000),
	('76859203759', 9, 2, 'Có sẵn', 21000),
	('78189290828', 11, 2, 'Có sẵn', 21000),
	('78189290829', 11, 2, 'Có sẵn', 21000),
	('78189290830', 11, 2, 'Có sẵn', 21000),
	('78189290831', 11, 2, 'Có sẵn', 21000),
	('78189290832', 11, 2, 'Có sẵn', 21000),
	('78189290833', 11, 2, 'Có sẵn', 21000),
	('78189290834', 11, 2, 'Có sẵn', 21000),
	('78189290835', 11, 2, 'Đang mượn', 21000),
	('78189290836', 11, 2, 'Có sẵn', 21000),
	('78189290837', 11, 2, 'Có sẵn', 21000),
	('86736574891', 8, 2, 'Có sẵn', 21000),
	('86736574892', 8, 2, 'Có sẵn', 21000),
	('86736574893', 8, 2, 'Có sẵn', 21000),
	('86736574894', 8, 2, 'Có sẵn', 21000),
	('86736574895', 8, 2, 'Có sẵn', 21000),
	('86736574896', 8, 2, 'Có sẵn', 21000),
	('86736574897', 8, 2, 'Có sẵn', 21000),
	('86736574898', 8, 2, 'Có sẵn', 21000),
	('86736574899', 8, 2, 'Có sẵn', 21000),
	('86736574900', 8, 2, 'Có sẵn', 21000),
	('87615289057', 5, 1, 'Có sẵn', 22000),
	('87615289058', 5, 1, 'Có sẵn', 22000),
	('87615289059', 5, 1, 'Có sẵn', 22000),
	('87615289060', 5, 1, 'Hư hỏng', 22000),
	('87615289061', 5, 1, 'Có sẵn', 22000),
	('87615289062', 5, 1, 'Có sẵn', 22000),
	('87615289063', 5, 1, 'Có sẵn', 22000),
	('87615289064', 5, 1, 'Có sẵn', 22000),
	('87615289065', 5, 1, 'Có sẵn', 22000),
	('87615289066', 5, 1, 'Có sẵn', 22000),
	('98076785231', 1, 1, 'Có sẵn', 25000),
	('98076785232', 1, 1, 'Đang mượn', 25000),
	('98076785233', 1, 1, 'Có sẵn', 25000),
	('98076785234', 1, 1, 'Có sẵn', 25000),
	('98076785235', 1, 1, 'Có sẵn', 25000),
	('98076785236', 1, 1, 'Có sẵn', 25000),
	('98076785237', 1, 1, 'Có sẵn', 25000),
	('98076785238', 1, 1, 'Có sẵn', 25000),
	('98076785239', 1, 1, 'Có sẵn', 25000),
	('98076785240', 1, 1, 'Có sẵn', 25000),
	('98716529587', 3, 1, 'Có sẵn', 26000),
	('98716529588', 3, 1, 'Có sẵn', 26000),
	('98716529589', 3, 1, 'Có sẵn', 26000),
	('98716529590', 3, 1, 'Có sẵn', 26000),
	('98716529591', 3, 1, 'Có sẵn', 26000),
	('98716529592', 3, 1, 'Có sẵn', 26000),
	('98716529593', 3, 1, 'Có sẵn', 26000),
	('98716529594', 3, 1, 'Có sẵn', 26000),
	('98716529595', 3, 1, 'Có sẵn', 26000),
	('98716529596', 3, 1, 'Có sẵn', 26000);

-- Dumping structure for table library_management.bookshelf
CREATE TABLE IF NOT EXISTS `bookshelf` (
  `bookshelf_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`bookshelf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.bookshelf: ~4 rows (approximately)
INSERT INTO `bookshelf` (`bookshelf_id`, `name`) VALUES
	(1, 'Khu vực A'),
	(2, 'Khu vực BD'),
	(3, 'Khu vực C'),
	(4, 'Kệ 4');

-- Dumping structure for table library_management.borrowticket
CREATE TABLE IF NOT EXISTS `borrowticket` (
  `borrow_ticket_id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int NOT NULL,
  `member_id` int NOT NULL,
  `borrow_date` date NOT NULL,
  `due_date` date NOT NULL,
  `status` enum('Chưa trả hết','Đã trả hết') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`borrow_ticket_id`),
  KEY `staff_id_idx` (`staff_id`),
  KEY `member_id_idx` (`member_id`),
  CONSTRAINT `fk_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `fk_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.borrowticket: ~0 rows (approximately)
INSERT INTO `borrowticket` (`borrow_ticket_id`, `staff_id`, `member_id`, `borrow_date`, `due_date`, `status`) VALUES
	(1, 3, 3, '2024-12-12', '2024-12-19', 'Chưa trả hết'),
	(2, 3, 3, '2024-12-12', '2024-12-19', 'Chưa trả hết');

-- Dumping structure for table library_management.borrowticket_details
CREATE TABLE IF NOT EXISTS `borrowticket_details` (
  `borrow_ticket_details_id` int NOT NULL AUTO_INCREMENT,
  `borrow_ticket_id` int NOT NULL,
  `isbn` varchar(255) NOT NULL,
  `status` enum('Chưa trả','Đã trả') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`borrow_ticket_details_id`),
  KEY `fk_borrowticket_id_idx` (`borrow_ticket_id`),
  KEY `fk_borrowticket_bookitem_id_idx` (`isbn`),
  CONSTRAINT `fk_borrowticket` FOREIGN KEY (`borrow_ticket_id`) REFERENCES `borrowticket` (`borrow_ticket_id`),
  CONSTRAINT `fk_borrowticket_bookitem_id` FOREIGN KEY (`isbn`) REFERENCES `bookitem` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.borrowticket_details: ~0 rows (approximately)
INSERT INTO `borrowticket_details` (`borrow_ticket_details_id`, `borrow_ticket_id`, `isbn`, `status`) VALUES
	(1, 1, '87615289060', 'Đã trả'),
	(2, 1, '16798201988', 'Chưa trả'),
	(3, 1, '76859203756', 'Đã trả'),
	(4, 1, '78189290835', 'Chưa trả'),
	(5, 2, '98076785232', 'Chưa trả'),
	(6, 2, '68491532798', 'Chưa trả'),
	(7, 2, '61543215488', 'Chưa trả');

-- Dumping structure for table library_management.category
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.category: ~3 rows (approximately)
INSERT INTO `category` (`category_id`, `name`) VALUES
	(1, 'Kinh dị'),
	(2, 'Truyện tranh'),
	(3, 'Tiểu thuyết');

-- Dumping structure for table library_management.function
CREATE TABLE IF NOT EXISTS `function` (
  `function_id` int NOT NULL,
  `function_name` varchar(255) NOT NULL,
  PRIMARY KEY (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.function: ~13 rows (approximately)
INSERT INTO `function` (`function_id`, `function_name`) VALUES
	(1, 'Quản lý sách'),
	(2, 'Quản lý mượn sách'),
	(3, 'Quản lý trả sách'),
	(4, 'Quản lý nhập sách'),
	(5, 'Quản lý nhân viên'),
	(6, 'Quản lý thành viên'),
	(7, 'Quản lý tài khoản'),
	(8, 'Quản lý vi phạm'),
	(9, 'Quản lý phân quyền'),
	(10, 'Thống kê'),
	(11, 'Quản lý kệ sách'),
	(12, 'Quản lý nhà xuất bản'),
	(13, 'Quản lý nhà cung cấp');

-- Dumping structure for table library_management.member
CREATE TABLE IF NOT EXISTS `member` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `membership_date` date NOT NULL,
  `status` enum('Đang hoạt động','Ngừng hoạt động') NOT NULL,
  `violationCount` int NOT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.member: ~28 rows (approximately)
INSERT INTO `member` (`member_id`, `full_name`, `phone`, `email`, `address`, `membership_date`, `status`, `violationCount`) VALUES
	(1, 'Thẩm Trung Hiếu', '0123456789', 'nvana@gmail.com', '123 Main St', '2022-01-01', 'Đang hoạt động', 0),
	(2, 'Dương Thành Phát', '0987654321', 'tthib@gmail.com', '456 Side Street', '2022-02-01', 'Đang hoạt động', 0),
	(3, 'Đỗ Lê Anh Khoa', '0222333444', 'lvanc@gmail.com', '789 West St', '2022-03-01', 'Đang hoạt động', 1),
	(4, 'Nguyễn Thị Minh', '0312345678', 'minh.nguyen@example.com', '123 Đường Lê Lợi, Quận 1, TP.HCM', '2022-04-01', 'Đang hoạt động', 0),
	(5, 'Trần Văn An', '0312345679', 'an.tran@example.com', '456 Đường Nguyễn Huệ, Quận 1, TP.HCM', '2022-04-02', 'Đang hoạt động', 0),
	(6, 'Lê Thị Thanh', '0312345680', 'thanh.le@example.com', '789 Đường Đồng Khởi, Quận 1, TP.HCM', '2022-04-03', 'Đang hoạt động', 0),
	(7, 'Phạm Văn Bình', '0312345681', 'binh.pham@example.com', '12 Đường Lý Tự Trọng, Quận 1, TP.HCM', '2022-04-04', 'Đang hoạt động', 0),
	(8, 'Hoàng Thị Nga', '0312345682', 'nga.hoang@example.com', '34 Đường Pasteur, Quận 1, TP.HCM', '2022-04-05', 'Đang hoạt động', 0),
	(9, 'Vũ Văn Hùng', '0312345683', 'hung.vu@example.com', '56 Đường Nam Kỳ Khởi Nghĩa, Quận 1, TP.HCM', '2022-04-06', 'Đang hoạt động', 0),
	(10, 'Đặng Thị Lan', '0312345684', 'lan.dang@example.com', '78 Đường Hai Bà Trưng, Quận 1, TP.HCM', '2022-04-07', 'Đang hoạt động', 0),
	(11, 'Lý Văn Phúc', '0312345685', 'phuc.ly@example.com', '90 Đường Tôn Đức Thắng, Quận 1, TP.HCM', '2022-04-08', 'Đang hoạt động', 0),
	(12, 'Bùi Thị Hoa', '0312345686', 'hoa.bui@example.com', '123 Đường Trần Hưng Đạo, Quận 1, TP.HCM', '2022-04-09', 'Đang hoạt động', 0),
	(13, 'Phan Văn Đức', '0312345687', 'duc.phan@example.com', '456 Đường Nguyễn Thị Minh Khai, Quận 1, TP.HCM', '2022-04-10', 'Đang hoạt động', 0),
	(14, 'Trịnh Thị Mai', '0312345688', 'mai.trinh@example.com', '789 Đường Lê Thánh Tôn, Quận 1, TP.HCM', '2022-04-11', 'Đang hoạt động', 0),
	(15, 'Đoàn Văn Tuấn', '0312345689', 'tuan.doan@example.com', '12 Đường Phạm Ngũ Lão, Quận 1, TP.HCM', '2022-04-12', 'Đang hoạt động', 0),
	(16, 'Vương Thị Hồng', '0312345690', 'hong.vuong@example.com', '34 Đường Bùi Viện, Quận 1, TP.HCM', '2022-04-13', 'Đang hoạt động', 0),
	(17, 'Kiều Văn Minh', '0312345691', 'minh.kieu@example.com', '56 Đường Nguyễn Văn Cừ, Quận 1, TP.HCM', '2022-04-14', 'Đang hoạt động', 0),
	(18, 'Tạ Thị Oanh', '0312345692', 'oanh.ta@example.com', '78 Đường Võ Văn Kiệt, Quận 1, TP.HCM', '2022-04-15', 'Đang hoạt động', 0),
	(19, 'Đinh Văn Phong', '0312345693', 'phong.dinh@example.com', '90 Đường Cách Mạng Tháng 8, Quận 1, TP.HCM', '2022-04-16', 'Đang hoạt động', 0),
	(20, 'Ngô Thị Liên', '0312345694', 'lien.ngo@example.com', '123 Đường Phạm Hồng Thái, Quận 1, TP.HCM', '2022-04-17', 'Đang hoạt động', 0),
	(21, 'Đỗ Văn Hải', '0312345695', 'hai.do@example.com', '456 Đường Nguyễn Trãi, Quận 1, TP.HCM', '2022-04-18', 'Đang hoạt động', 0),
	(22, 'Chu Thị Hạnh', '0312345696', 'hanh.chu@example.com', '789 Đường Lê Văn Sỹ, Quận 1, TP.HCM', '2022-04-19', 'Đang hoạt động', 0),
	(23, 'Hà Văn Sơn', '0312345697', 'son.ha@example.com', '12 Đường Hoàng Sa, Quận 1, TP.HCM', '2022-04-20', 'Đang hoạt động', 0),
	(24, 'Quách Thị Thảo', '0312345698', 'thao.quach@example.com', '34 Đường Trường Sa, Quận 1, TP.HCM', '2022-04-21', 'Đang hoạt động', 0),
	(25, 'Mai Văn Long', '0312345699', 'long.mai@example.com', '56 Đường Nguyễn Đình Chiểu, Quận 1, TP.HCM', '2022-04-22', 'Đang hoạt động', 0),
	(26, 'Phùng Thị Yến', '0312345700', 'yen.phung@example.com', '78 Đường Điện Biên Phủ, Quận 1, TP.HCM', '2022-04-23', 'Đang hoạt động', 0),
	(27, 'Dương Văn Quang', '0312345701', 'quang.duong@example.com', '90 Đường Lý Chính Thắng, Quận 1, TP.HCM', '2022-04-24', 'Đang hoạt động', 0),
	(28, 'Lương Thị Trang', '0312345702', 'trang.luong@example.com', '123 Đường Nguyễn Thông, Quận 1, TP.HCM', '2022-04-25', 'Đang hoạt động', 0);

-- Dumping structure for table library_management.penalty
CREATE TABLE IF NOT EXISTS `penalty` (
  `penalty_id` int NOT NULL AUTO_INCREMENT,
  `penalty_name` varchar(45) NOT NULL,
  `fine` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`penalty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.penalty: ~4 rows (approximately)
INSERT INTO `penalty` (`penalty_id`, `penalty_name`, `fine`) VALUES
	(0, 'Nguyên vẹn', 0),
	(1, 'Trễ hạn', 10000),
	(2, 'Hư hỏng', 50000),
	(3, 'Mất', 0);

-- Dumping structure for table library_management.penaltyticket
CREATE TABLE IF NOT EXISTS `penaltyticket` (
  `penalty_ticket_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `staff_id` int NOT NULL,
  `penalty_date` date NOT NULL,
  `return_ticket_id` int NOT NULL,
  `total_fine` int DEFAULT NULL,
  PRIMARY KEY (`penalty_ticket_id`),
  KEY `fk_return_penalty_idx` (`return_ticket_id`),
  KEY `fk_mem_id_idx` (`member_id`),
  KEY `fk_staff_id_idx` (`staff_id`),
  CONSTRAINT `fk_mem_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `fk_return_penalty` FOREIGN KEY (`return_ticket_id`) REFERENCES `returnticket` (`return_ticket_id`),
  CONSTRAINT `fk_staff_penticket` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.penaltyticket: ~0 rows (approximately)
INSERT INTO `penaltyticket` (`penalty_ticket_id`, `member_id`, `staff_id`, `penalty_date`, `return_ticket_id`, `total_fine`) VALUES
	(1, 3, 3, '2024-12-12', 1, 50000);

-- Dumping structure for table library_management.penaltyticket_detail
CREATE TABLE IF NOT EXISTS `penaltyticket_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `penalty_ticket_id` int DEFAULT NULL,
  `penalty_id` int DEFAULT NULL,
  `isbn` varchar(50) DEFAULT NULL,
  `fine` int DEFAULT NULL,
  `days_passed` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_penalty_ticket_id` (`penalty_ticket_id`),
  KEY `fk_penalty_id` (`penalty_id`),
  CONSTRAINT `fk_penalty` FOREIGN KEY (`penalty_id`) REFERENCES `penalty` (`penalty_id`),
  CONSTRAINT `fk_ticket` FOREIGN KEY (`penalty_ticket_id`) REFERENCES `penaltyticket` (`penalty_ticket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.penaltyticket_detail: ~2 rows (approximately)
INSERT INTO `penaltyticket_detail` (`id`, `penalty_ticket_id`, `penalty_id`, `isbn`, `fine`, `days_passed`) VALUES
	(1, 1, 2, '87615289060', 50000, 0),
	(2, 1, 0, '76859203756', 0, 0);

-- Dumping structure for table library_management.permission
CREATE TABLE IF NOT EXISTS `permission` (
  `permission_id` int NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(50) NOT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.permission: ~3 rows (approximately)
INSERT INTO `permission` (`permission_id`, `permission_name`) VALUES
	(1, 'Admin'),
	(2, 'Nhân viên thủ thư'),
	(3, 'Nhân viên nhập hàng');

-- Dumping structure for table library_management.permission_details
CREATE TABLE IF NOT EXISTS `permission_details` (
  `permission_id` int NOT NULL,
  `function_id` int NOT NULL,
  `action` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`permission_id`,`function_id`,`action`) USING BTREE,
  KEY `fk_function_idx` (`function_id`),
  CONSTRAINT `fk_function` FOREIGN KEY (`function_id`) REFERENCES `function` (`function_id`),
  CONSTRAINT `fk_permission` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.permission_details: ~65 rows (approximately)
INSERT INTO `permission_details` (`permission_id`, `function_id`, `action`) VALUES
	(1, 1, 'add'),
	(1, 1, 'delete'),
	(1, 1, 'edit'),
	(1, 1, 'view'),
	(2, 1, 'view'),
	(1, 2, 'add'),
	(1, 2, 'delete'),
	(1, 2, 'edit'),
	(1, 2, 'view'),
	(2, 2, 'view'),
	(1, 3, 'add'),
	(1, 3, 'delete'),
	(1, 3, 'edit'),
	(1, 3, 'view'),
	(2, 3, 'view'),
	(1, 4, 'add'),
	(1, 4, 'delete'),
	(1, 4, 'edit'),
	(1, 4, 'view'),
	(2, 4, 'view'),
	(1, 5, 'add'),
	(1, 5, 'delete'),
	(1, 5, 'edit'),
	(1, 5, 'view'),
	(2, 5, 'view'),
	(1, 6, 'add'),
	(1, 6, 'delete'),
	(1, 6, 'edit'),
	(1, 6, 'view'),
	(2, 6, 'view'),
	(1, 7, 'add'),
	(1, 7, 'delete'),
	(1, 7, 'edit'),
	(1, 7, 'view'),
	(2, 7, 'view'),
	(1, 8, 'add'),
	(1, 8, 'delete'),
	(1, 8, 'edit'),
	(1, 8, 'view'),
	(2, 8, 'view'),
	(1, 9, 'add'),
	(1, 9, 'delete'),
	(1, 9, 'edit'),
	(1, 9, 'view'),
	(2, 9, 'view'),
	(1, 10, 'add'),
	(1, 10, 'delete'),
	(1, 10, 'edit'),
	(1, 10, 'view'),
	(2, 10, 'view'),
	(1, 11, 'add'),
	(1, 11, 'delete'),
	(1, 11, 'edit'),
	(1, 11, 'view'),
	(2, 11, 'view'),
	(1, 12, 'add'),
	(1, 12, 'delete'),
	(1, 12, 'edit'),
	(1, 12, 'view'),
	(2, 12, 'view'),
	(1, 13, 'add'),
	(1, 13, 'delete'),
	(1, 13, 'edit'),
	(1, 13, 'view'),
	(2, 13, 'view');

-- Dumping structure for table library_management.publisher
CREATE TABLE IF NOT EXISTS `publisher` (
  `publisher_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  PRIMARY KEY (`publisher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.publisher: ~3 rows (approximately)
INSERT INTO `publisher` (`publisher_id`, `name`, `address`, `phone`) VALUES
	(1, 'Nhà xuất bản Kim Đồng', 'Hà Nội', '0900571595'),
	(2, 'Nhà xuất bản Sự Thật', 'Huế', '0243822158'),
	(3, 'Nhà xuất bản Trẻ', 'Thành phố Hồ Chí Minh', '039317849');

-- Dumping structure for table library_management.purchaseticket
CREATE TABLE IF NOT EXISTS `purchaseticket` (
  `purchase_ticket_id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` int NOT NULL,
  `staff_id` int NOT NULL,
  `purchase_date` date NOT NULL,
  `status` enum('Hoàn thành','Hủy') NOT NULL,
  `total_price` bigint DEFAULT NULL,
  PRIMARY KEY (`purchase_ticket_id`),
  KEY `fk_supplier_supplier_id_idx` (`supplier_id`),
  KEY `fk_staff_id_pt_id_idx` (`staff_id`),
  CONSTRAINT `fk_staff_id_pt_id` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `fk_supplier_supplier_id` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.purchaseticket: ~4 rows (approximately)
INSERT INTO `purchaseticket` (`purchase_ticket_id`, `supplier_id`, `staff_id`, `purchase_date`, `status`, `total_price`) VALUES
	(1, 1, 3, '2024-11-12', 'Hoàn thành', 1470000),
	(2, 3, 3, '2024-11-24', 'Hoàn thành', 1120000),
	(3, 1, 3, '2024-12-01', 'Hoàn thành', 560000),
	(4, 2, 3, '2024-12-07', 'Hoàn thành', 980000);

-- Dumping structure for table library_management.purchaseticket_details
CREATE TABLE IF NOT EXISTS `purchaseticket_details` (
  `purchase_ticket_details_id` int NOT NULL AUTO_INCREMENT,
  `purchase_ticket_id` int NOT NULL,
  `book_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` bigint DEFAULT NULL,
  `total_price` bigint DEFAULT NULL,
  PRIMARY KEY (`purchase_ticket_details_id`),
  KEY `fk_purchaseticket_idx` (`purchase_ticket_id`),
  KEY `fk_bookid_book` (`book_id`),
  CONSTRAINT `fk_bookid_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
  CONSTRAINT `fk_purchaseticket` FOREIGN KEY (`purchase_ticket_id`) REFERENCES `purchaseticket` (`purchase_ticket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.purchaseticket_details: ~0 rows (approximately)
INSERT INTO `purchaseticket_details` (`purchase_ticket_details_id`, `purchase_ticket_id`, `book_id`, `quantity`, `price`, `total_price`) VALUES
	(1, 1, 1, 10, 25000, 250000),
	(2, 1, 2, 10, 26000, 260000),
	(3, 1, 3, 10, 26000, 260000),
	(4, 1, 4, 10, 24000, 240000),
	(5, 1, 5, 10, 22000, 220000),
	(6, 1, 6, 10, 24000, 240000),
	(7, 2, 7, 10, 25000, 250000),
	(8, 2, 8, 10, 21000, 210000),
	(9, 2, 9, 10, 21000, 210000),
	(10, 2, 10, 10, 24000, 240000),
	(11, 2, 11, 10, 21000, 210000),
	(12, 3, 12, 10, 26000, 260000),
	(13, 3, 13, 10, 30000, 300000),
	(14, 4, 14, 10, 30000, 300000),
	(15, 4, 15, 10, 20000, 200000),
	(16, 4, 16, 10, 24000, 240000),
	(17, 4, 17, 10, 24000, 240000);

-- Dumping structure for table library_management.returnticket
CREATE TABLE IF NOT EXISTS `returnticket` (
  `return_ticket_id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int NOT NULL,
  `member_id` int NOT NULL,
  `return_date` date NOT NULL,
  `status` enum('Hoàn thành','Hủy') NOT NULL,
  PRIMARY KEY (`return_ticket_id`),
  KEY `staff_id_idx` (`staff_id`),
  KEY `member_id_idx` (`member_id`),
  CONSTRAINT `fk_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `fk_staff_id` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.returnticket: ~0 rows (approximately)
INSERT INTO `returnticket` (`return_ticket_id`, `staff_id`, `member_id`, `return_date`, `status`) VALUES
	(1, 3, 3, '2024-12-12', 'Hoàn thành');

-- Dumping structure for table library_management.returnticket_details
CREATE TABLE IF NOT EXISTS `returnticket_details` (
  `return_ticket_details_id` int NOT NULL AUTO_INCREMENT,
  `return_ticket_id` int NOT NULL,
  `borrow_ticket_id` int NOT NULL,
  `isbn` varchar(255) NOT NULL,
  `status` enum('Nguyên vẹn','Hư hỏng','Mất') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `days_passed` int DEFAULT NULL,
  PRIMARY KEY (`return_ticket_details_id`),
  KEY `fk_return_idx` (`return_ticket_id`),
  KEY `fk_returnticket_bookitem_id_idx` (`isbn`),
  KEY `fk_borrow_ticket_id` (`borrow_ticket_id`),
  CONSTRAINT `fk_borrow_ticket_id` FOREIGN KEY (`borrow_ticket_id`) REFERENCES `borrowticket` (`borrow_ticket_id`),
  CONSTRAINT `fk_return` FOREIGN KEY (`return_ticket_id`) REFERENCES `returnticket` (`return_ticket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.returnticket_details: ~0 rows (approximately)
INSERT INTO `returnticket_details` (`return_ticket_details_id`, `return_ticket_id`, `borrow_ticket_id`, `isbn`, `status`, `days_passed`) VALUES
	(1, 1, 1, '87615289060', 'Hư hỏng', 0),
	(2, 1, 1, '76859203756', 'Nguyên vẹn', 0);

-- Dumping structure for table library_management.staff
CREATE TABLE IF NOT EXISTS `staff` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `gender` enum('Nam','Nữ') NOT NULL,
  `birthday` date DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `hire_date` date NOT NULL,
  `status` enum('Đang làm việc','Đã nghỉ việc') NOT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.staff: ~28 rows (approximately)
INSERT INTO `staff` (`staff_id`, `full_name`, `email`, `phone`, `gender`, `birthday`, `address`, `hire_date`, `status`) VALUES
	(1, 'Lý Tấn Hùng', 'a.nguyen@example.com', '0123456789', 'Nam', '2001-12-14', 'Hà Nội', '2023-01-01', 'Đang làm việc'),
	(2, 'Đỗ Việt Anh', 'b.tran@example.com', '0987654321', 'Nam', '2004-11-07', 'Hồ Chí Minh', '2023-01-15', 'Đang làm việc'),
	(3, 'Phan Văn Hảo', 'c.le@example.com', '0112233445', 'Nam', '2004-11-07', 'Đà Nẵng', '2023-02-01', 'Đã nghỉ việc'),
	(4, 'Nguyễn Văn An', 'nguyenvana@gmail.com', '0312345678', 'Nam', '1990-01-05', 'Quận 1, Hồ Chí Minh', '2022-01-10', 'Đang làm việc'),
	(5, 'Trần Thị Ban', 'tranthib@yahoo.com', '0312345679', 'Nữ', '1992-02-10', 'Quận 3, Hồ Chí Minh', '2022-02-15', 'Đang làm việc'),
	(6, 'Lê Văn Cường', 'levanc@gmail.com', '0312345680', 'Nam', '1988-03-15', 'Quận 5, Hồ Chí Minh', '2022-03-20', 'Đang làm việc'),
	(7, 'Phạm Thị Dương', 'phamthid@yahoo.com', '0312345681', 'Nữ', '1995-04-20', 'Quận 10, Hồ Chí Minh', '2022-04-25', 'Đang làm việc'),
	(8, 'Hoàng Văn Em', 'hoangvane@gmail.com', '0312345682', 'Nam', '1993-05-25', 'Quận Phú Nhuận, Hồ Chí Minh', '2022-05-30', 'Đang làm việc'),
	(9, 'Vũ Thị Fan', 'vuthif@yahoo.com', '0312345683', 'Nữ', '1991-06-30', 'Quận Bình Thạnh, Hồ Chí Minh', '2022-06-05', 'Đang làm việc'),
	(10, 'Đặng Văn Gặp', 'dangvang@gmail.com', '0312345684', 'Nam', '1989-07-05', 'Quận 7, Hồ Chí Minh', '2022-07-10', 'Đang làm việc'),
	(11, 'Nguyễn Thị Hoành', 'nguyenthih@yahoo.com', '0312345685', 'Nữ', '1996-08-10', 'Quận 9, Hồ Chí Minh', '2022-08-15', 'Đang làm việc'),
	(12, 'Trần Văn In', 'tranvani@gmail.com', '0312345686', 'Nam', '1990-09-15', 'Quận Thủ Đức, Hồ Chí Minh', '2022-09-20', 'Đang làm việc'),
	(13, 'Lê Thị John', 'lethij@yahoo.com', '0312345687', 'Nữ', '1994-10-20', 'Quận Tân Bình, Hồ Chí Minh', '2022-10-25', 'Đang làm việc'),
	(14, 'Phạm Văn Khôi', 'phamvank@gmail.com', '0312345688', 'Nam', '1987-11-25', 'Quận Bình Tân, Hồ Chí Minh', '2022-11-30', 'Đang làm việc'),
	(15, 'Hoàng Thị Linh', 'hoangthil@yahoo.com', '0312345689', 'Nữ', '1993-12-30', 'Quận 4, Hồ Chí Minh', '2023-01-05', 'Đang làm việc'),
	(16, 'Vũ Văn Minh', 'vuvanm@gmail.com', '0312345690', 'Nam', '1991-01-10', 'Quận 6, Hồ Chí Minh', '2023-02-10', 'Đang làm việc'),
	(17, 'Đặng Thị Nhàn', 'dangthin@yahoo.com', '0312345691', 'Nữ', '1990-02-15', 'Quận 8, Hồ Chí Minh', '2023-03-15', 'Đang làm việc'),
	(18, 'Nguyễn Văn Oanh', 'nguyenvano@gmail.com', '0312345692', 'Nam', '1988-03-20', 'Quận 12, Hồ Chí Minh', '2023-04-20', 'Đang làm việc'),
	(19, 'Trần Thị Phượng', 'tranthip@yahoo.com', '0312345693', 'Nữ', '1992-04-25', 'Quận Gò Vấp, Hồ Chí Minh', '2023-05-25', 'Đang làm việc'),
	(20, 'Lê Văn Quyên', 'levanq@gmail.com', '0312345694', 'Nam', '1989-05-30', 'Quận Tân Phú, Hồ Chí Minh', '2023-06-30', 'Đang làm việc'),
	(21, 'Phạm Thị Rầu', 'phamthir@yahoo.com', '0312345695', 'Nữ', '1995-06-05', 'Quận Bình Chánh, Hồ Chí Minh', '2023-07-05', 'Đang làm việc'),
	(22, 'Hoàng Văn Sang', 'hoangvans@gmail.com', '0312345696', 'Nam', '1993-07-10', 'Quận Nhà Bè, Hồ Chí Minh', '2023-08-10', 'Đang làm việc'),
	(23, 'Vũ Thị Tình', 'vuthit@yahoo.com', '0312345697', 'Nữ', '1991-08-15', 'Quận Cần Giờ, Hồ Chí Minh', '2023-09-15', 'Đang làm việc'),
	(24, 'Đặng Văn Uy', 'dangvanu@gmail.com', '0312345698', 'Nam', '1987-09-20', 'Quận Hóc Môn, Hồ Chí Minh', '2023-10-20', 'Đang làm việc'),
	(25, 'Nguyễn Thị Vàng', 'nguyenthiv@yahoo.com', '0312345699', 'Nữ', '1994-10-25', 'Quận Củ Chi, Hồ Chí Minh', '2023-11-25', 'Đang làm việc'),
	(26, 'Trần Văn Wu', 'tranvanw@gmail.com', '0312345700', 'Nam', '1990-11-30', 'Quận 2, Hồ Chí Minh', '2023-12-30', 'Đang làm việc'),
	(27, 'Lê Thị Xuyên', 'lethix@yahoo.com', '0312345701', 'Nữ', '1992-12-05', 'Quận Thủ Đức, Hồ Chí Minh', '2024-01-05', 'Đang làm việc'),
	(28, 'Phạm Văn Yến', 'phamvany@gmail.com', '0312345702', 'Nam', '1988-01-10', 'Quận 11, Hồ Chí Minh', '2024-02-10', 'Đang làm việc');

-- Dumping structure for table library_management.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `supplier_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.supplier: ~3 rows (approximately)
INSERT INTO `supplier` (`supplier_id`, `name`, `address`, `phone`) VALUES
	(1, 'ABC Supplies', '123 Market St', '0912345678'),
	(2, 'Book Distributors', '456 Elm St', '0998765432'),
	(3, 'Book World', '735 Wall Street', '0746257489');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
