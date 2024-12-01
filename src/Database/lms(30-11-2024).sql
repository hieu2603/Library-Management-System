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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.account: ~3 rows (approximately)
INSERT INTO `account` (`account_id`, `username`, `password`, `permission_id`, `status`, `staff_id`) VALUES
	(1, 'admin', '12345', 1, 'Đang hoạt động', 1),
	(2, 'staff1', '12345', 2, 'Đang hoạt động', 1),
	(3, 'staff2', '12345', 3, 'Ngừng hoạt động', 2);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.book: ~3 rows (approximately)
INSERT INTO `book` (`book_id`, `title`, `author`, `publisher_id`, `year_publish`, `category_id`, `quantity`, `book_image`, `bookshelf_id`) VALUES
	(1, 'The Great Gatsby', 'F. Scott Fitzgerald', 1, 1925, 1, 1, 'gatsby.jpg', 1),
	(2, '1984', 'George Orwell', 1, 1949, 2, 12, '1984.jpg', 2),
	(3, 'To Kill a Mockingbird', 'Harper Lee', 3, 1960, 1, 20, 'mockingbird.jpg', 3);

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

-- Dumping data for table library_management.bookitem: ~33 rows (approximately)
INSERT INTO `bookitem` (`isbn`, `book_id`, `purchaseticket_id`, `status`, `price`) VALUES
	('70898484871', 3, 2, 'Có sẵn', 50000),
	('70898484872', 3, 2, 'Có sẵn', 50000),
	('70898484873', 3, 2, 'Có sẵn', 50000),
	('70898484874', 3, 2, 'Có sẵn', 50000),
	('70898484875', 3, 2, 'Có sẵn', 50000),
	('70898484876', 3, 2, 'Có sẵn', 50000),
	('70898484877', 3, 2, 'Có sẵn', 50000),
	('70898484878', 3, 2, 'Có sẵn', 50000),
	('70898484879', 3, 2, 'Có sẵn', 50000),
	('70898484880', 3, 2, 'Có sẵn', 50000),
	('70898484881', 3, 2, 'Có sẵn', 50000),
	('70898484882', 3, 2, 'Có sẵn', 50000),
	('70898484883', 3, 2, 'Có sẵn', 50000),
	('70898484884', 3, 2, 'Có sẵn', 50000),
	('70898484885', 3, 2, 'Có sẵn', 50000),
	('70898484886', 3, 2, 'Có sẵn', 50000),
	('70898484887', 3, 2, 'Có sẵn', 50000),
	('70898484888', 3, 2, 'Có sẵn', 50000),
	('70898484889', 3, 2, 'Có sẵn', 50000),
	('70898484890', 3, 2, 'Có sẵn', 50000),
	('86947392721', 2, 3, 'Có sẵn', 30000),
	('86947392722', 2, 3, 'Có sẵn', 30000),
	('86947392723', 2, 3, 'Có sẵn', 30000),
	('86947392724', 2, 3, 'Có sẵn', 30000),
	('86947392725', 2, 3, 'Có sẵn', 30000),
	('86947392726', 2, 3, 'Có sẵn', 30000),
	('86947392727', 2, 3, 'Có sẵn', 30000),
	('86947392728', 2, 3, 'Có sẵn', 30000),
	('86947392729', 2, 3, 'Có sẵn', 30000),
	('86947392730', 2, 3, 'Có sẵn', 30000),
	('86947392731', 2, 3, 'Có sẵn', 30000),
	('86947392732', 2, 3, 'Có sẵn', 30000),
	('90808081711', 1, 1, 'Có sẵn', 50000);

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

-- Dumping data for table library_management.borrowticket: ~2 rows (approximately)
INSERT INTO `borrowticket` (`borrow_ticket_id`, `staff_id`, `member_id`, `borrow_date`, `due_date`, `status`) VALUES
	(1, 1, 1, '2024-11-30', '2024-12-07', 'Đã trả hết'),
	(2, 1, 1, '2024-11-30', '2024-11-07', 'Đã trả hết');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.borrowticket_details: ~5 rows (approximately)
INSERT INTO `borrowticket_details` (`borrow_ticket_details_id`, `borrow_ticket_id`, `isbn`, `status`) VALUES
	(1, 1, '90808081711', 'Đã trả'),
	(2, 1, '86947392721', 'Đã trả'),
	(3, 1, '70898484871', 'Đã trả'),
	(4, 2, '86947392723', 'Đã trả'),
	(5, 2, '70898484873', 'Đã trả');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.member: ~3 rows (approximately)
INSERT INTO `member` (`member_id`, `full_name`, `phone`, `email`, `address`, `membership_date`, `status`, `violationCount`) VALUES
	(1, 'Nguyễn Văn A', '0123456789', 'nvana@gmail.com', '123 Main St', '2022-01-01', 'Đang hoạt động', 0),
	(2, 'Trần Thị B', '0987654321', 'tthib@gmail.com', '456 Side Street', '2022-02-01', 'Đang hoạt động', 1),
	(3, 'Lê Văn C', '0222333444', 'lvanc@gmail.com', '789 West St', '2022-03-01', 'Đang hoạt động', 0);

-- Dumping structure for table library_management.penalty
CREATE TABLE IF NOT EXISTS `penalty` (
  `penalty_id` int NOT NULL AUTO_INCREMENT,
  `penalty_name` varchar(45) NOT NULL,
  `fine` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`penalty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.penalty: ~3 rows (approximately)
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

-- Dumping data for table library_management.penaltyticket: ~1 rows (approximately)
INSERT INTO `penaltyticket` (`penalty_ticket_id`, `member_id`, `staff_id`, `penalty_date`, `return_ticket_id`, `total_fine`) VALUES
	(1, 1, 1, '2024-11-30', 2, 50000);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.penaltyticket_detail: ~3 rows (approximately)
INSERT INTO `penaltyticket_detail` (`id`, `penalty_ticket_id`, `penalty_id`, `isbn`, `fine`, `days_passed`) VALUES
	(1, 1, 0, '90808081711', 0, 0),
	(2, 1, 2, '86947392721', 50000, 0),
	(3, 1, 0, '70898484871', 0, 0);

-- Dumping structure for table library_management.permission
CREATE TABLE IF NOT EXISTS `permission` (
  `permission_id` int NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(50) NOT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

-- Dumping data for table library_management.permission_details: ~57 rows (approximately)
INSERT INTO `permission_details` (`permission_id`, `function_id`, `action`) VALUES
	(1, 1, 'add'),
	(1, 1, 'delete'),
	(1, 1, 'edit'),
	(1, 1, 'view'),
	(1, 2, 'add'),
	(1, 2, 'delete'),
	(1, 2, 'edit'),
	(1, 2, 'view'),
	(1, 3, 'add'),
	(1, 3, 'delete'),
	(1, 3, 'edit'),
	(1, 3, 'view'),
	(2, 3, 'view'),
	(1, 4, 'add'),
	(1, 4, 'delete'),
	(1, 4, 'edit'),
	(1, 4, 'view'),
	(1, 5, 'add'),
	(1, 5, 'delete'),
	(1, 5, 'edit'),
	(1, 5, 'view'),
	(2, 5, 'view'),
	(1, 6, 'add'),
	(1, 6, 'delete'),
	(1, 6, 'edit'),
	(1, 6, 'view'),
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
	(1, 10, 'add'),
	(1, 10, 'delete'),
	(1, 10, 'edit'),
	(1, 10, 'view'),
	(2, 10, 'view'),
	(1, 11, 'add'),
	(1, 11, 'delete'),
	(1, 11, 'edit'),
	(1, 11, 'view'),
	(1, 12, 'add'),
	(1, 12, 'delete'),
	(1, 12, 'edit'),
	(1, 12, 'view'),
	(1, 13, 'add'),
	(1, 13, 'delete'),
	(1, 13, 'edit'),
	(1, 13, 'view');

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
	(1, 'Nhà xuất bản Kim Đồng', 'Hà Nội', '1234567890'),
	(2, 'Nhà xuất bản Sự Thật', 'Huế', '0987654321'),
	(3, 'Nhà xuất bản Tuổi Trẻ', 'Thành phố Hồ Chí Minh', '1122334455');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.purchaseticket: ~3 rows (approximately)
INSERT INTO `purchaseticket` (`purchase_ticket_id`, `supplier_id`, `staff_id`, `purchase_date`, `status`, `total_price`) VALUES
	(1, 1, 1, '2024-11-25', 'Hoàn thành', 50000),
	(2, 3, 1, '2024-11-26', 'Hoàn thành', 1000000),
	(3, 2, 1, '2024-11-29', 'Hoàn thành', 360000);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.purchaseticket_details: ~3 rows (approximately)
INSERT INTO `purchaseticket_details` (`purchase_ticket_details_id`, `purchase_ticket_id`, `book_id`, `quantity`, `price`, `total_price`) VALUES
	(1, 1, 1, 1, 50000, 50000),
	(2, 2, 3, 20, 50000, 1000000),
	(3, 3, 2, 12, 30000, 360000);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.returnticket: ~2 rows (approximately)
INSERT INTO `returnticket` (`return_ticket_id`, `staff_id`, `member_id`, `return_date`, `status`) VALUES
	(1, 1, 1, '2024-11-30', 'Hoàn thành'),
	(2, 1, 1, '2024-11-30', 'Hoàn thành');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.returnticket_details: ~3 rows (approximately)
INSERT INTO `returnticket_details` (`return_ticket_details_id`, `return_ticket_id`, `borrow_ticket_id`, `isbn`, `status`, `days_passed`) VALUES
	(1, 1, 2, '86947392723', 'Nguyên vẹn', 23),
	(2, 1, 2, '70898484873', 'Nguyên vẹn', 23),
	(3, 2, 1, '90808081711', 'Nguyên vẹn', 0),
	(4, 2, 1, '86947392721', 'Hư hỏng', 0),
	(5, 2, 1, '70898484871', 'Nguyên vẹn', 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table library_management.staff: ~3 rows (approximately)
INSERT INTO `staff` (`staff_id`, `full_name`, `email`, `phone`, `gender`, `birthday`, `address`, `hire_date`, `status`) VALUES
	(1, 'Nguyễn Văn An', 'a.nguyen@example.com', '0123456789', 'Nam', '2001-12-14', 'Hà Nội', '2023-01-01', 'Đang làm việc'),
	(2, 'Trần Thị B', 'b.tran@example.com', '0987654321', 'Nam', '2004-11-07', 'Hồ Chí Minh', '2023-01-15', 'Đang làm việc'),
	(3, 'Lê Văn C', 'c.le@example.com', '0112233445', 'Nam', '2004-11-07', 'Đà Nẵng', '2023-02-01', 'Đã nghỉ việc');

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
	(3, 'Book World', '789 Oak St', '0123456789');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
