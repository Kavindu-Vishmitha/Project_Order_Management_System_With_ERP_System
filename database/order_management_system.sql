-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.32 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.4.0.6659
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for order_management_system_with_erp_db
CREATE DATABASE IF NOT EXISTS `order_management_system_with_erp_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `order_management_system_with_erp_db`;

-- Dumping structure for table order_management_system_with_erp_db.advanced_payroll
CREATE TABLE IF NOT EXISTS `advanced_payroll` (
  `ad_pay_id` int NOT NULL AUTO_INCREMENT,
  `employee_employee_id` varchar(55) NOT NULL,
  `employee_name` varchar(120) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `advanced_price` double DEFAULT NULL,
  `employee_payrolls_payroll_id` int NOT NULL,
  `advance_status_or_note` text,
  PRIMARY KEY (`ad_pay_id`),
  KEY `fk_advanced_payroll_employee1_idx` (`employee_employee_id`),
  KEY `fk_advanced_payroll_employee_payrolls1_idx` (`employee_payrolls_payroll_id`),
  CONSTRAINT `fk_advanced_payroll_employee1` FOREIGN KEY (`employee_employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `fk_advanced_payroll_employee_payrolls1` FOREIGN KEY (`employee_payrolls_payroll_id`) REFERENCES `employee_payrolls` (`payroll_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.advanced_payroll: ~4 rows (approximately)
INSERT INTO `advanced_payroll` (`ad_pay_id`, `employee_employee_id`, `employee_name`, `date`, `advanced_price`, `employee_payrolls_payroll_id`, `advance_status_or_note`) VALUES
	(1, 'EMP1747681629852', 'Jude Thamel', '2025-05-20 00:00:00', 1500, 1, 'Paid'),
	(2, 'EMP1747681629852', 'Jude Thamel', '2025-05-20 00:00:00', 1000, 1, 'Not Paid'),
	(3, 'EMP1747686825748', 'Pahan Kodikara', '2025-05-20 00:00:00', 2000, 2, 'Not Paid'),
	(4, 'EMP1747875415886', 'Shehan Thilon', '2025-05-22 00:00:00', 12500, 543033, 'Not Paid');

-- Dumping structure for table order_management_system_with_erp_db.attendence_type
CREATE TABLE IF NOT EXISTS `attendence_type` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `attendence_type_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.attendence_type: ~2 rows (approximately)
INSERT INTO `attendence_type` (`type_id`, `attendence_type_name`) VALUES
	(1, 'Present'),
	(2, 'Absent');

-- Dumping structure for table order_management_system_with_erp_db.basic_salary
CREATE TABLE IF NOT EXISTS `basic_salary` (
  `basic_id` int NOT NULL AUTO_INCREMENT,
  `employee_employee_id` varchar(55) NOT NULL,
  `employee_name` varchar(100) DEFAULT NULL,
  `department_department_id` int DEFAULT NULL,
  `employee_position_employee_position_id` int NOT NULL,
  `basic` double DEFAULT NULL,
  PRIMARY KEY (`basic_id`),
  KEY `fk_basic_salary_employee1_idx` (`employee_employee_id`),
  KEY `fk_basic_salary_department1_idx` (`department_department_id`),
  KEY `fk_basic_salary_employee_position1_idx` (`employee_position_employee_position_id`),
  CONSTRAINT `fk_basic_salary_department1` FOREIGN KEY (`department_department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `fk_basic_salary_employee1` FOREIGN KEY (`employee_employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `fk_basic_salary_employee_position1` FOREIGN KEY (`employee_position_employee_position_id`) REFERENCES `employee_position` (`employee_position_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.basic_salary: ~5 rows (approximately)
INSERT INTO `basic_salary` (`basic_id`, `employee_employee_id`, `employee_name`, `department_department_id`, `employee_position_employee_position_id`, `basic`) VALUES
	(1, 'EMP1747681629852', 'Jude Thamel', 2, 2, 18000),
	(2, 'EMP1747686825748', 'Pahan Kodikara', 3, 3, 25000),
	(3, 'EMP1747772098803', 'Lihan Wijesena', 5, 5, 30150),
	(4, 'EMP1747875415886', 'Shehan Thilon', 1, 1, 1185955),
	(5, 'EMP1747873786005', 'Shiyan Gunasingha', 2, 2, 20000);

-- Dumping structure for table order_management_system_with_erp_db.batch
CREATE TABLE IF NOT EXISTS `batch` (
  `batch_id` int NOT NULL AUTO_INCREMENT,
  `batch_name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.batch: ~4 rows (approximately)
INSERT INTO `batch` (`batch_id`, `batch_name`) VALUES
	(1, 'Maliban Creamcracker B1'),
	(2, 'Cocacola L 250 B1'),
	(3, 'Maliban Creamcracker B2'),
	(4, 'Munchee Tikiri marie B1');

-- Dumping structure for table order_management_system_with_erp_db.brands
CREATE TABLE IF NOT EXISTS `brands` (
  `brands_id` int NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(45) DEFAULT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`brands_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.brands: ~3 rows (approximately)
INSERT INTO `brands` (`brands_id`, `brand_name`, `company_name`) VALUES
	(1, 'Cocacola', 'Cocacola'),
	(2, 'Maliban', 'Maliban'),
	(3, 'Munchee', 'Munchee');

-- Dumping structure for table order_management_system_with_erp_db.category
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.category: ~3 rows (approximately)
INSERT INTO `category` (`category_id`, `category_name`) VALUES
	(1, 'Busicut'),
	(2, 'Drinks'),
	(3, 'Bites');

-- Dumping structure for table order_management_system_with_erp_db.city
CREATE TABLE IF NOT EXISTS `city` (
  `city_id` int NOT NULL AUTO_INCREMENT,
  `city_name` text,
  `district_district_id` int NOT NULL,
  PRIMARY KEY (`city_id`),
  KEY `fk_city_district1_idx` (`district_district_id`),
  CONSTRAINT `fk_city_district1` FOREIGN KEY (`district_district_id`) REFERENCES `district` (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.city: ~9 rows (approximately)
INSERT INTO `city` (`city_id`, `city_name`, `district_district_id`) VALUES
	(1, 'Galle', 1),
	(2, 'Matara', 2),
	(3, 'Gampaha', 5),
	(4, 'Ganemulla', 5),
	(5, 'Kadawatha', 5),
	(6, 'JaEla', 5),
	(7, 'Colombo', 4),
	(8, 'Ederamulla', 5),
	(9, 'Ihalagama', 11);

-- Dumping structure for table order_management_system_with_erp_db.company
CREATE TABLE IF NOT EXISTS `company` (
  `company_id` varchar(55) NOT NULL,
  `company_name` text,
  `company_address` text,
  `company_email` varchar(100) DEFAULT NULL,
  `hotline_number` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.company: ~3 rows (approximately)
INSERT INTO `company` (`company_id`, `company_name`, `company_address`, `company_email`, `hotline_number`) VALUES
	('ECR1747682442716', 'Cocacola', '78/A Colombo,Colombo 01', 'cocacola@gmail.com', '0334567898'),
	('ECR1747734024119', 'Maliban', '28/5,Colombo Rd,Pitakotuwa', 'maliban.contact@gmail.com', '0337894554'),
	('ECR1747876445709', 'Munchee', '28/A,Colombo,Kotuwa', 'munchee@gmail.com', '0334568944');

-- Dumping structure for table order_management_system_with_erp_db.company_expences
CREATE TABLE IF NOT EXISTS `company_expences` (
  `company_expences_id` int NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `price` double DEFAULT NULL,
  `company_expences_type_company_expences_type_id` int NOT NULL,
  PRIMARY KEY (`company_expences_id`),
  KEY `fk_company_expences_company_expences_type1_idx` (`company_expences_type_company_expences_type_id`),
  CONSTRAINT `fk_company_expences_company_expences_type1` FOREIGN KEY (`company_expences_type_company_expences_type_id`) REFERENCES `company_expences_type` (`company_expences_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.company_expences: ~1 rows (approximately)
INSERT INTO `company_expences` (`company_expences_id`, `date`, `price`, `company_expences_type_company_expences_type_id`) VALUES
	(1, '2025-05-22', 12500.5, 1);

-- Dumping structure for table order_management_system_with_erp_db.company_expences_type
CREATE TABLE IF NOT EXISTS `company_expences_type` (
  `company_expences_type_id` int NOT NULL AUTO_INCREMENT,
  `expences_type_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`company_expences_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.company_expences_type: ~2 rows (approximately)
INSERT INTO `company_expences_type` (`company_expences_type_id`, `expences_type_name`) VALUES
	(1, 'Car Wash'),
	(2, 'Clean the office floor');

-- Dumping structure for table order_management_system_with_erp_db.company_promotion
CREATE TABLE IF NOT EXISTS `company_promotion` (
  `company_promotion_id` int NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `promotion_description` text,
  `cost` double DEFAULT NULL,
  `company_promotion_types_promotiontypes_id` int NOT NULL,
  PRIMARY KEY (`company_promotion_id`),
  KEY `fk_company_promotion_company_promotion_types1_idx` (`company_promotion_types_promotiontypes_id`),
  CONSTRAINT `fk_company_promotion_company_promotion_types1` FOREIGN KEY (`company_promotion_types_promotiontypes_id`) REFERENCES `company_promotion_types` (`promotiontypes_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.company_promotion: ~1 rows (approximately)
INSERT INTO `company_promotion` (`company_promotion_id`, `date`, `promotion_description`, `cost`, `company_promotion_types_promotiontypes_id`) VALUES
	(1, '2025-05-22', 'Test', 125, 1);

-- Dumping structure for table order_management_system_with_erp_db.company_promotion_types
CREATE TABLE IF NOT EXISTS `company_promotion_types` (
  `promotiontypes_id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(45) DEFAULT NULL,
  `description` text,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`promotiontypes_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.company_promotion_types: ~2 rows (approximately)
INSERT INTO `company_promotion_types` (`promotiontypes_id`, `type_name`, `description`, `date`) VALUES
	(1, 'Biriyani', 'Buy one get one', '2025-05-22'),
	(2, 'NewdelYoghurt', 'Buy one get one', '2025-05-24');

-- Dumping structure for table order_management_system_with_erp_db.cooking_status
CREATE TABLE IF NOT EXISTS `cooking_status` (
  `cooking_status_id` int NOT NULL AUTO_INCREMENT,
  `cooking_status_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cooking_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.cooking_status: ~3 rows (approximately)
INSERT INTO `cooking_status` (`cooking_status_id`, `cooking_status_name`) VALUES
	(1, 'Prepared'),
	(2, 'Not Ready'),
	(3, 'Cooked');

-- Dumping structure for table order_management_system_with_erp_db.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` varchar(55) NOT NULL,
  `first_name` varchar(80) DEFAULT NULL,
  `last_name` varchar(80) DEFAULT NULL,
  `address` text,
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.customer: ~1 rows (approximately)
INSERT INTO `customer` (`customer_id`, `first_name`, `last_name`, `address`, `email`, `mobile`) VALUES
	('CUS1747682371677', 'Pasindu', 'Nethsara', '289/A,Kadawatha RD,Kadawatha', 'pasindu@gmail.com', '0778965254');

-- Dumping structure for table order_management_system_with_erp_db.department
CREATE TABLE IF NOT EXISTS `department` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.department: ~6 rows (approximately)
INSERT INTO `department` (`department_id`, `department_name`) VALUES
	(1, 'Management Department'),
	(2, 'Hr Department'),
	(3, 'Finance Department'),
	(4, 'Stock Department'),
	(5, 'Kitchen Department'),
	(6, 'Cleaning');

-- Dumping structure for table order_management_system_with_erp_db.district
CREATE TABLE IF NOT EXISTS `district` (
  `district_id` int NOT NULL AUTO_INCREMENT,
  `district_name` varchar(45) DEFAULT NULL,
  `province_province_id` int NOT NULL,
  PRIMARY KEY (`district_id`),
  KEY `fk_district_province1_idx` (`province_province_id`),
  CONSTRAINT `fk_district_province1` FOREIGN KEY (`province_province_id`) REFERENCES `province` (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.district: ~25 rows (approximately)
INSERT INTO `district` (`district_id`, `district_name`, `province_province_id`) VALUES
	(1, 'Galle', 1),
	(2, 'Matara', 1),
	(3, 'Hambantota', 1),
	(4, 'Colombo', 2),
	(5, 'Gampaha', 2),
	(6, 'Kalutara', 2),
	(7, 'Kandy', 3),
	(8, 'Matale', 3),
	(9, 'Nuwara Eliya', 3),
	(10, 'Kegalle', 4),
	(11, 'Rathnapura', 4),
	(12, 'Ampara', 5),
	(13, 'Batticaloa', 5),
	(14, 'Trincomalee', 5),
	(15, 'Badulla', 6),
	(16, 'Monaragala', 6),
	(17, 'Kurunegala', 7),
	(18, 'Puttalam', 7),
	(19, 'Anuradhapura', 8),
	(20, 'Polonnaruwa', 8),
	(21, 'Jaffna', 9),
	(22, 'Kilinochchi', 9),
	(23, 'Mullativu', 9),
	(24, 'Vauniya', 9),
	(25, 'Mannar', 9);

-- Dumping structure for table order_management_system_with_erp_db.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `employee_id` varchar(55) NOT NULL,
  `nic` varchar(15) DEFAULT NULL,
  `first_name` varchar(80) DEFAULT NULL,
  `last_name` varchar(80) DEFAULT NULL,
  `contact_number` varchar(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `date_of_hire` date DEFAULT NULL,
  `date_of_final` date DEFAULT NULL,
  `gender_gender_id` int NOT NULL,
  `employee_address_em_address_id` int NOT NULL,
  `employee_status_employee_status_id` int DEFAULT NULL,
  `employee_type_employee_type_id` int NOT NULL,
  `employee_position_employee_position_id` int DEFAULT NULL,
  `department_department_id` int DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  KEY `fk_employee_gender_idx` (`gender_gender_id`),
  KEY `fk_employee_employee_address1_idx` (`employee_address_em_address_id`),
  KEY `fk_employee_employee_type1_idx` (`employee_type_employee_type_id`),
  KEY `fk_employee_employee_position1_idx` (`employee_position_employee_position_id`),
  KEY `fk_employee_department1_idx` (`department_department_id`),
  KEY `fk_employee_employee_status1_idx` (`employee_status_employee_status_id`),
  CONSTRAINT `fk_employee_department1` FOREIGN KEY (`department_department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `fk_employee_employee_address1` FOREIGN KEY (`employee_address_em_address_id`) REFERENCES `employee_address` (`em_address_id`),
  CONSTRAINT `fk_employee_employee_position1` FOREIGN KEY (`employee_position_employee_position_id`) REFERENCES `employee_position` (`employee_position_id`),
  CONSTRAINT `fk_employee_employee_status1` FOREIGN KEY (`employee_status_employee_status_id`) REFERENCES `employee_status` (`employee_status_id`),
  CONSTRAINT `fk_employee_employee_type1` FOREIGN KEY (`employee_type_employee_type_id`) REFERENCES `employee_type` (`employee_type_id`),
  CONSTRAINT `fk_employee_gender` FOREIGN KEY (`gender_gender_id`) REFERENCES `gender` (`gender_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.employee: ~6 rows (approximately)
INSERT INTO `employee` (`employee_id`, `nic`, `first_name`, `last_name`, `contact_number`, `email`, `date_of_birth`, `date_of_hire`, `date_of_final`, `gender_gender_id`, `employee_address_em_address_id`, `employee_status_employee_status_id`, `employee_type_employee_type_id`, `employee_position_employee_position_id`, `department_department_id`) VALUES
	('EMP1747681629852', '200045658954', 'Jude', 'Thamel', '0789554651', 'jude@gmail.com', '2000-01-16', '2025-05-16', NULL, 1, 1, 1, 1, 1, 1),
	('EMP1747686825748', '789564651124', 'Pahan', 'Kodikara', '0748951365', 'pahan@gmail.com', '2000-02-14', '2025-05-20', NULL, 1, 2, 1, 1, 2, 2),
	('EMP1747772098803', '187841123145', 'Lihan', 'Wijesena', '0789454165', 'lihan@gmail.com', '2002-01-07', '2025-05-12', NULL, 1, 3, 1, 1, 5, 3),
	('EMP1747873786005', '187897894565', 'Shiyan', 'Gunasingha', '0771158545', 'shiyan@gmail.com', '2000-05-07', '2025-05-21', NULL, 1, 4, 1, 1, 4, 4),
	('EMP1747875415886', '112123415165', 'Shehan', 'Thilon', '0789411213', 'shehan@gmail.com', '2000-02-14', '2025-05-22', NULL, 1, 5, 1, 1, 5, 5),
	('EMP1747875661110', '200378884511', 'Mineth', 'Sumanasena', '0754616454', 'mineth@gmail.com', '2003-05-13', '2025-05-21', NULL, 1, 6, 1, 1, 6, 6);

-- Dumping structure for table order_management_system_with_erp_db.employee_address
CREATE TABLE IF NOT EXISTS `employee_address` (
  `em_address_id` int NOT NULL AUTO_INCREMENT,
  `address_line01` text,
  `address_line02` text,
  `postal_code` varchar(45) DEFAULT NULL,
  `city_city_id` int NOT NULL,
  `province_province_id` int NOT NULL,
  `district_district_id` int NOT NULL,
  PRIMARY KEY (`em_address_id`),
  KEY `fk_employee_address_city1_idx` (`city_city_id`),
  KEY `fk_employee_address_province1_idx` (`province_province_id`),
  KEY `fk_employee_address_district1_idx` (`district_district_id`),
  CONSTRAINT `fk_employee_address_city1` FOREIGN KEY (`city_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_employee_address_district1` FOREIGN KEY (`district_district_id`) REFERENCES `district` (`district_id`),
  CONSTRAINT `fk_employee_address_province1` FOREIGN KEY (`province_province_id`) REFERENCES `province` (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.employee_address: ~7 rows (approximately)
INSERT INTO `employee_address` (`em_address_id`, `address_line01`, `address_line02`, `postal_code`, `city_city_id`, `province_province_id`, `district_district_id`) VALUES
	(1, '7/A,Ja-Ela,', 'Ja-Ela', '45684', 4, 2, 2),
	(2, '78/A,Ganemulla,', 'Galahitiyawa', '46546', 2, 2, 2),
	(3, '45/A,Colombo RD,', 'Colombo.', '79651', 1, 2, 1),
	(4, '78/A,Gunasenapura,', 'JaEla', '78874', 4, 2, 2),
	(5, '78/A,Gampaha,', 'Ederamulla', '12565', 5, 2, 2),
	(6, '78/A,Ederamulla,', 'Gampaha', '78965', 5, 2, 2),
	(7, '25/A,IhalagamaRd', 'Rathnapura', '78944', 1, 4, 2);

-- Dumping structure for table order_management_system_with_erp_db.employee_attendence
CREATE TABLE IF NOT EXISTS `employee_attendence` (
  `attendance_id` int NOT NULL AUTO_INCREMENT,
  `employee_name` varchar(100) NOT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `employee_employee_id` varchar(55) NOT NULL,
  `attendence_type_type_id` int NOT NULL,
  PRIMARY KEY (`attendance_id`,`employee_name`),
  KEY `fk_employee_attendence_employee1_idx` (`employee_employee_id`),
  KEY `fk_employee_attendence_attendence_type1_idx` (`attendence_type_type_id`),
  CONSTRAINT `fk_employee_attendence_attendence_type1` FOREIGN KEY (`attendence_type_type_id`) REFERENCES `attendence_type` (`type_id`),
  CONSTRAINT `fk_employee_attendence_employee1` FOREIGN KEY (`employee_employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.employee_attendence: ~2 rows (approximately)
INSERT INTO `employee_attendence` (`attendance_id`, `employee_name`, `date`, `time`, `employee_employee_id`, `attendence_type_type_id`) VALUES
	(1, 'Jude Thamel', '2025-05-20', '00:48:57', 'EMP1747681629852', 1),
	(2, 'Shehan Thilon', '2025-05-22', '06:40:50', 'EMP1747875415886', 1);

-- Dumping structure for table order_management_system_with_erp_db.employee_payrolls
CREATE TABLE IF NOT EXISTS `employee_payrolls` (
  `payroll_id` int NOT NULL AUTO_INCREMENT,
  `employee_employee_id` varchar(55) NOT NULL,
  `employee_name` varchar(100) DEFAULT NULL,
  `basic_salary_basic_id` int NOT NULL,
  `no_pay_deduction` double DEFAULT NULL,
  `other_deduction` double DEFAULT NULL,
  `other_allowances` double DEFAULT NULL,
  `net_bonus` double DEFAULT NULL,
  `owner_bonus` double DEFAULT NULL,
  `net_salary` double DEFAULT NULL,
  `sign_on_bonus` double DEFAULT NULL,
  `performance_bonus` double DEFAULT NULL,
  `holiday_bonus` double DEFAULT NULL,
  `team_bonus` double DEFAULT NULL,
  `payable_salary` double DEFAULT NULL,
  `month` varchar(100) DEFAULT NULL,
  `date_issued` date DEFAULT NULL,
  `authorized_by` varchar(70) DEFAULT NULL,
  `advanced_payroll_ad_pay_id` int DEFAULT NULL,
  PRIMARY KEY (`payroll_id`),
  KEY `fk_employee_payrolls_employee1_idx` (`employee_employee_id`),
  KEY `fk_employee_payrolls_advanced_payroll1_idx` (`advanced_payroll_ad_pay_id`),
  KEY `fk_employee_payrolls_basic_salary1_idx` (`basic_salary_basic_id`),
  CONSTRAINT `fk_employee_payrolls_advanced_payroll1` FOREIGN KEY (`advanced_payroll_ad_pay_id`) REFERENCES `advanced_payroll` (`ad_pay_id`),
  CONSTRAINT `fk_employee_payrolls_basic_salary1` FOREIGN KEY (`basic_salary_basic_id`) REFERENCES `basic_salary` (`basic_id`),
  CONSTRAINT `fk_employee_payrolls_employee1` FOREIGN KEY (`employee_employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=983788 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.employee_payrolls: ~4 rows (approximately)
INSERT INTO `employee_payrolls` (`payroll_id`, `employee_employee_id`, `employee_name`, `basic_salary_basic_id`, `no_pay_deduction`, `other_deduction`, `other_allowances`, `net_bonus`, `owner_bonus`, `net_salary`, `sign_on_bonus`, `performance_bonus`, `holiday_bonus`, `team_bonus`, `payable_salary`, `month`, `date_issued`, `authorized_by`, `advanced_payroll_ad_pay_id`) VALUES
	(240849, 'EMP1747873786005', 'Shiyan Gunasingha', 5, 10000, 200, 300, 300, 500, 11000, 300, 400, 500, 500, 11000, 'May', '2025-05-24', NULL, NULL),
	(528806, 'EMP1747686825748', 'Pahan Kodikara', 2, 200, 200, 300, 200, 200, 25300, 500, 500, 500, 500, 23300, 'May', '2025-05-24', NULL, 3),
	(543033, 'EMP1747875415886', 'Shehan Thilon', 4, 0, 0, 12500, 0, 1000, 1124578.6, 18000, 1000, 1000, 0, 1124578.6, 'May', '2025-05-22', NULL, NULL),
	(983787, 'EMP1747681629852', 'Jude Thamel', 1, 0, 0, 0, 0, 0, 16560, 0, 0, 0, 0, 15060, 'May', '2025-05-21', NULL, 1);

-- Dumping structure for table order_management_system_with_erp_db.employee_position
CREATE TABLE IF NOT EXISTS `employee_position` (
  `employee_position_id` int NOT NULL AUTO_INCREMENT,
  `position_name` varchar(50) DEFAULT NULL,
  `department_department_id` int NOT NULL,
  PRIMARY KEY (`employee_position_id`),
  KEY `fk_employee_position_department1_idx` (`department_department_id`),
  CONSTRAINT `fk_employee_position_department1` FOREIGN KEY (`department_department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.employee_position: ~6 rows (approximately)
INSERT INTO `employee_position` (`employee_position_id`, `position_name`, `department_department_id`) VALUES
	(1, 'Super admin', 1),
	(2, 'Hr Manager', 2),
	(3, 'Finance Manager', 3),
	(4, 'Stock Manager', 4),
	(5, 'Head Chef', 5),
	(6, 'Head Of Cleaning', 5);

-- Dumping structure for table order_management_system_with_erp_db.employee_status
CREATE TABLE IF NOT EXISTS `employee_status` (
  `employee_status_id` int NOT NULL AUTO_INCREMENT,
  `status_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`employee_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.employee_status: ~4 rows (approximately)
INSERT INTO `employee_status` (`employee_status_id`, `status_name`) VALUES
	(1, 'Activated'),
	(2, 'Not Activated'),
	(3, 'Susspend'),
	(4, 'Terminate');

-- Dumping structure for table order_management_system_with_erp_db.employee_type
CREATE TABLE IF NOT EXISTS `employee_type` (
  `employee_type_id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`employee_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.employee_type: ~3 rows (approximately)
INSERT INTO `employee_type` (`employee_type_id`, `type_name`) VALUES
	(1, 'Full Time'),
	(2, 'Part Time'),
	(3, 'Intern');

-- Dumping structure for table order_management_system_with_erp_db.employee_user
CREATE TABLE IF NOT EXISTS `employee_user` (
  `e_user_id` int NOT NULL AUTO_INCREMENT,
  `employee_name` varchar(45) DEFAULT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `user_password` varchar(45) DEFAULT NULL,
  `employee_employee_id` varchar(55) NOT NULL,
  `employee_position_employee_position_id` int NOT NULL,
  `department_department_id` int NOT NULL,
  PRIMARY KEY (`e_user_id`),
  KEY `fk_employee_user_employee1_idx` (`employee_employee_id`),
  KEY `fk_employee_user_employee_position1_idx` (`employee_position_employee_position_id`),
  KEY `fk_employee_user_department1_idx` (`department_department_id`),
  CONSTRAINT `fk_employee_user_department1` FOREIGN KEY (`department_department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `fk_employee_user_employee1` FOREIGN KEY (`employee_employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `fk_employee_user_employee_position1` FOREIGN KEY (`employee_position_employee_position_id`) REFERENCES `employee_position` (`employee_position_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.employee_user: ~2 rows (approximately)
INSERT INTO `employee_user` (`e_user_id`, `employee_name`, `user_name`, `user_password`, `employee_employee_id`, `employee_position_employee_position_id`, `department_department_id`) VALUES
	(1, 'Pahan Kodikara', 'pahan', 'Pahan#@54', 'EMP1747686825748', 1, 1),
	(2, 'Shehan Thilon', 'shehan', 'shehan@S78', 'EMP1747875415886', 4, 4);

-- Dumping structure for table order_management_system_with_erp_db.foods
CREATE TABLE IF NOT EXISTS `foods` (
  `food_id` varchar(55) NOT NULL,
  `food_name` varchar(100) DEFAULT NULL,
  `description` text,
  `portion_types_portion_types_id` int NOT NULL,
  `qty` varchar(45) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `food_status_food_status_id` int NOT NULL,
  PRIMARY KEY (`food_id`),
  KEY `fk_foods_food_status1_idx` (`food_status_food_status_id`),
  KEY `fk_foods_portion_types1_idx` (`portion_types_portion_types_id`),
  CONSTRAINT `fk_foods_food_status1` FOREIGN KEY (`food_status_food_status_id`) REFERENCES `food_status` (`food_status_id`),
  CONSTRAINT `fk_foods_portion_types1` FOREIGN KEY (`portion_types_portion_types_id`) REFERENCES `portion_types` (`portion_types_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.foods: ~6 rows (approximately)
INSERT INTO `foods` (`food_id`, `food_name`, `description`, `portion_types_portion_types_id`, `qty`, `price`, `food_status_food_status_id`) VALUES
	('FDI1747776931961', 'chicken Kotthu', 'Our Chicken Koththu is made with freshly chopped godhamba roti, wok-tossed with juicy, well-marinated chicken, eggs, onions, leeks, and a special blend of Sri Lankan spices. Infused with rich curry flavor and a touch of heat, this dish offers a perfect balance of spice, savoriness, and comfort. Every bite delivers soft roti, tender meat, and mouthwatering aroma – a true favorite among koththu lovers!', 1, '16', '1500', 1),
	('FDI1747777184961', 'Egg Kotthu', 'Our Egg Koththu is a delicious fusion of freshly shredded godhamba roti, scrambled eggs, onions, green chilies, leeks, and our house-made Sri Lankan spice mix. Light yet flavorful, this dish delivers the perfect comfort food experience — with soft roti, fluffy egg, and that unmistakable koththu aroma. Ideal for anyone who loves classic street food with a milder, wholesome twist!', 1, '20', '1250', 1),
	('FDI1747777210383', 'Cheese Kotthu', 'Our Cheese Koththu blends chopped godhamba roti with gooey melted cheese, scrambled eggs, and your choice of chicken or vegetables, all stir-fried with onions, leeks, and a rich mix of Sri Lankan spices. The creamy texture of cheese combined with bold koththu flavors creates a mouthwatering fusion that’s both comforting and indulgent. Perfect for cheese lovers looking for something uniquely Lankan!', 2, '20', '1000', 1),
	('FDI1747777235664', 'Fried Rice - Chicken', 'Our Chicken Fried Rice is made with fragrant basmati rice stir-fried together with tender, marinated chicken pieces, scrambled eggs, fresh vegetables, and spring onions. Enhanced with soy sauce and our signature seasoning, this dish offers a perfect balance of savory flavor, light smokiness, and mouthwatering texture. A classic favorite, made with a Lankan touch!\n\n', 2, '17', '1000', 1),
	('FDI1747777257968', 'Fried Rice - Egg', 'Our Egg Fried Rice is cooked with fragrant basmati rice, fluffy scrambled eggs, crisp vegetables, and spring onions, all stir-fried with soy sauce and a touch of our signature seasoning. It\'s a simple yet flavorful dish that’s perfect on its own or as a side — light, satisfying, and full of taste.', 1, '19', '100', 1),
	('FDI1747777257969', 'Rols', 'Our Sri Lankan-style rolls are perfectly crisp on the outside and filled with a delicious mix of spiced potatoes, vegetables, and your choice of chicken, fish, or egg. Wrapped in a thin pancake, crumbed and deep-fried to golden perfection, each bite delivers a satisfying crunch with a warm, flavorful filling. A classic Lankan snack that’s perfect any time of day!', 3, '48', '100', 1);

-- Dumping structure for table order_management_system_with_erp_db.food_status
CREATE TABLE IF NOT EXISTS `food_status` (
  `food_status_id` int NOT NULL AUTO_INCREMENT,
  `food_status_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`food_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.food_status: ~2 rows (approximately)
INSERT INTO `food_status` (`food_status_id`, `food_status_name`) VALUES
	(1, 'Available'),
	(2, 'Not Available');

-- Dumping structure for table order_management_system_with_erp_db.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `gender_id` int NOT NULL AUTO_INCREMENT,
  `gender_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`gender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.gender: ~2 rows (approximately)
INSERT INTO `gender` (`gender_id`, `gender_name`) VALUES
	(1, 'Male'),
	(2, 'Female');

-- Dumping structure for table order_management_system_with_erp_db.grn
CREATE TABLE IF NOT EXISTS `grn` (
  `grn_id` varchar(55) NOT NULL,
  `product_name` varchar(80) DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `batch_batch_id` int NOT NULL,
  `suppliers_order_status_suppliers_order_status_id` int NOT NULL,
  `mfd` date DEFAULT NULL,
  `exp` date DEFAULT NULL,
  `buying_price` double DEFAULT NULL,
  `selling_price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `payment_status_payment_status_id` int NOT NULL,
  `total` double DEFAULT NULL,
  `added_date` datetime DEFAULT NULL,
  `sub_total` double DEFAULT NULL,
  `payable_amount` double DEFAULT NULL,
  `payment_due` varchar(45) DEFAULT NULL,
  `supplier_supplier_id` varchar(55) NOT NULL,
  PRIMARY KEY (`grn_id`),
  KEY `fk_grn_batch1_idx` (`batch_batch_id`),
  KEY `fk_grn_suppliers_order_status1_idx` (`suppliers_order_status_suppliers_order_status_id`),
  KEY `fk_grn_payment_status1_idx` (`payment_status_payment_status_id`),
  KEY `fk_grn_supplier1_idx` (`supplier_supplier_id`),
  CONSTRAINT `fk_grn_batch1` FOREIGN KEY (`batch_batch_id`) REFERENCES `batch` (`batch_id`),
  CONSTRAINT `fk_grn_payment_status1` FOREIGN KEY (`payment_status_payment_status_id`) REFERENCES `payment_status` (`payment_status_id`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_supplier_id`) REFERENCES `supplier` (`supplier_id`),
  CONSTRAINT `fk_grn_suppliers_order_status1` FOREIGN KEY (`suppliers_order_status_suppliers_order_status_id`) REFERENCES `suppliers_order_status` (`suppliers_order_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.grn: ~4 rows (approximately)
INSERT INTO `grn` (`grn_id`, `product_name`, `qty`, `batch_batch_id`, `suppliers_order_status_suppliers_order_status_id`, `mfd`, `exp`, `buying_price`, `selling_price`, `discount`, `payment_status_payment_status_id`, `total`, `added_date`, `sub_total`, `payable_amount`, `payment_due`, `supplier_supplier_id`) VALUES
	('GRN1747738553388', 'Maliban Creamcracker', 200, 1, 1, '2025-02-03', '2027-02-01', 150, 160, 0, 1, 30000, '2025-05-20 00:00:00', 30000, 30000, '0.00', 'ESR1747734357800'),
	('GRN1747738863005', 'Cocacola 250L', 150, 2, 1, '2025-05-12', '2026-02-02', 150, 180, 0, 1, 22500, '2025-05-20 00:00:00', 22500, 22500, '0.00', 'ESR1747734089747'),
	('GRN1747763265983', 'Maliban Creamcracker familly pack 150g', 300, 3, 1, '2025-05-15', '2025-12-02', 120, 150, 0, 1, 36000, '2025-05-20 00:00:00', 36000, 36000, '0.00', 'ESR1747734357800'),
	('GRN1747877151614', 'Munhee Tikirimarie 200g', 200, 4, 1, '2025-05-20', '2026-01-20', 120, 1350, 0, 1, 24000, '2025-05-22 00:00:00', 24000, 24000, '0.00', 'ESR1747876507007');

-- Dumping structure for table order_management_system_with_erp_db.holiday
CREATE TABLE IF NOT EXISTS `holiday` (
  `holiday_id` int NOT NULL AUTO_INCREMENT,
  `holiday_name` varchar(45) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`holiday_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.holiday: ~0 rows (approximately)

-- Dumping structure for table order_management_system_with_erp_db.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `invoice_id` varchar(55) NOT NULL,
  `order_details_order_id` varchar(55) NOT NULL,
  `total_amount` double DEFAULT NULL,
  `paid_price` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `employee_employee_id` varchar(55) NOT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `fk_invoice_order_details1_idx` (`order_details_order_id`),
  KEY `fk_invoice_employee1_idx` (`employee_employee_id`),
  CONSTRAINT `fk_invoice_employee1` FOREIGN KEY (`employee_employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `fk_invoice_order_details1` FOREIGN KEY (`order_details_order_id`) REFERENCES `order_details` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.invoice: ~0 rows (approximately)
INSERT INTO `invoice` (`invoice_id`, `order_details_order_id`, `total_amount`, `paid_price`, `balance`, `employee_employee_id`) VALUES
	('INV00001', 'OID2237', 200, 200, 0, 'EMP1747875415886'),
	('INV00002', 'OID2617', 2600, 2600, 0, 'EMP1747875415886');

-- Dumping structure for table order_management_system_with_erp_db.invoice_items
CREATE TABLE IF NOT EXISTS `invoice_items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `invoice_invoice_id` varchar(55) NOT NULL,
  `order_details_order_id` varchar(55) NOT NULL,
  `order_items_oid` int NOT NULL,
  `qty` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `fk_invoice_items_invoice1_idx` (`invoice_invoice_id`),
  KEY `fk_invoice_items_order_details1_idx` (`order_details_order_id`),
  KEY `fk_invoice_items_order_items1_idx` (`order_items_oid`),
  CONSTRAINT `fk_invoice_items_invoice1` FOREIGN KEY (`invoice_invoice_id`) REFERENCES `invoice` (`invoice_id`),
  CONSTRAINT `fk_invoice_items_order_details1` FOREIGN KEY (`order_details_order_id`) REFERENCES `order_details` (`order_id`),
  CONSTRAINT `fk_invoice_items_order_items1` FOREIGN KEY (`order_items_oid`) REFERENCES `order_items` (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.invoice_items: ~0 rows (approximately)
INSERT INTO `invoice_items` (`item_id`, `invoice_invoice_id`, `order_details_order_id`, `order_items_oid`, `qty`, `price`) VALUES
	(1, 'INV00001', 'OID2237', 4, 2, 200),
	(2, 'INV00002', 'OID2617', 1, 1, 1500),
	(3, 'INV00002', 'OID2617', 2, 1, 100),
	(4, 'INV00002', 'OID2617', 3, 1, 1000);

-- Dumping structure for table order_management_system_with_erp_db.leave
CREATE TABLE IF NOT EXISTS `leave` (
  `leave_id` varchar(55) NOT NULL,
  `employee_name` varchar(100) DEFAULT NULL,
  `date_from` datetime DEFAULT NULL,
  `date_to` datetime DEFAULT NULL,
  `employee_employee_id` varchar(55) NOT NULL,
  PRIMARY KEY (`leave_id`),
  KEY `fk_leave_employee1_idx` (`employee_employee_id`),
  CONSTRAINT `fk_leave_employee1` FOREIGN KEY (`employee_employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.leave: ~0 rows (approximately)
INSERT INTO `leave` (`leave_id`, `employee_name`, `date_from`, `date_to`, `employee_employee_id`) VALUES
	('LEA1747876220143', 'Shehan Thilon', '2025-05-14 00:00:00', '2025-05-13 00:00:00', 'EMP1747875415886');

-- Dumping structure for table order_management_system_with_erp_db.manage_returns
CREATE TABLE IF NOT EXISTS `manage_returns` (
  `return_id` varchar(55) NOT NULL,
  `foods_food_id` varchar(55) NOT NULL,
  `product_name` text,
  `invoice_invoice_id` varchar(55) NOT NULL,
  `customer_name` varchar(120) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`return_id`),
  KEY `fk_manage_returns_foods1_idx` (`foods_food_id`),
  KEY `fk_manage_returns_invoice1_idx` (`invoice_invoice_id`),
  CONSTRAINT `fk_manage_returns_foods1` FOREIGN KEY (`foods_food_id`) REFERENCES `foods` (`food_id`),
  CONSTRAINT `fk_manage_returns_invoice1` FOREIGN KEY (`invoice_invoice_id`) REFERENCES `invoice` (`invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.manage_returns: ~0 rows (approximately)

-- Dumping structure for table order_management_system_with_erp_db.order_details
CREATE TABLE IF NOT EXISTS `order_details` (
  `order_id` varchar(55) NOT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `orderd_time` datetime DEFAULT NULL,
  `delivered_time` datetime DEFAULT NULL,
  `cooking_status_cooking_status_id` int NOT NULL,
  `special_note` longtext,
  PRIMARY KEY (`order_id`),
  KEY `fk_order_details_cooking_status1_idx` (`cooking_status_cooking_status_id`),
  CONSTRAINT `fk_order_details_cooking_status1` FOREIGN KEY (`cooking_status_cooking_status_id`) REFERENCES `cooking_status` (`cooking_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.order_details: ~0 rows (approximately)
INSERT INTO `order_details` (`order_id`, `customer_name`, `orderd_time`, `delivered_time`, `cooking_status_cooking_status_id`, `special_note`) VALUES
	('OID2237', 'null', '2025-05-25 08:36:53', NULL, 2, NULL),
	('OID2617', 'Pasindu nethsara', '2025-05-22 05:45:43', NULL, 1, NULL);

-- Dumping structure for table order_management_system_with_erp_db.order_items
CREATE TABLE IF NOT EXISTS `order_items` (
  `oid` int NOT NULL AUTO_INCREMENT,
  `order_details_order_id` varchar(55) NOT NULL,
  `portion_types_portion_types_id` int NOT NULL,
  `foods_food_id` varchar(55) NOT NULL,
  `qty` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `fk_order_items_order_details1_idx` (`order_details_order_id`),
  KEY `fk_order_items_portion_types1_idx` (`portion_types_portion_types_id`),
  KEY `fk_order_items_foods1_idx` (`foods_food_id`),
  CONSTRAINT `fk_order_items_foods1` FOREIGN KEY (`foods_food_id`) REFERENCES `foods` (`food_id`),
  CONSTRAINT `fk_order_items_order_details1` FOREIGN KEY (`order_details_order_id`) REFERENCES `order_details` (`order_id`),
  CONSTRAINT `fk_order_items_portion_types1` FOREIGN KEY (`portion_types_portion_types_id`) REFERENCES `portion_types` (`portion_types_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.order_items: ~3 rows (approximately)
INSERT INTO `order_items` (`oid`, `order_details_order_id`, `portion_types_portion_types_id`, `foods_food_id`, `qty`, `price`) VALUES
	(1, 'OID2617', 1, 'FDI1747776931961', 1, 1500),
	(2, 'OID2617', 3, 'FDI1747777257968', 1, 100),
	(3, 'OID2617', 2, 'FDI1747777235664', 1, 1000),
	(4, 'OID2237', 3, 'FDI1747777257969', 2, 200);

-- Dumping structure for table order_management_system_with_erp_db.payment_status
CREATE TABLE IF NOT EXISTS `payment_status` (
  `payment_status_id` int NOT NULL AUTO_INCREMENT,
  `payment_status_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`payment_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.payment_status: ~3 rows (approximately)
INSERT INTO `payment_status` (`payment_status_id`, `payment_status_name`) VALUES
	(1, 'Full Paid'),
	(2, 'Half Paid'),
	(3, 'Not Paid');

-- Dumping structure for table order_management_system_with_erp_db.payroll_int_hr_details
CREATE TABLE IF NOT EXISTS `payroll_int_hr_details` (
  `payroll_id` varchar(55) NOT NULL,
  `date` datetime DEFAULT NULL,
  `employee_name` varchar(70) DEFAULT NULL,
  `total_advanced` double DEFAULT NULL,
  `paid_price` double DEFAULT NULL,
  `employee_employee_id` varchar(55) NOT NULL,
  `employee_payrolls_payroll_id` int NOT NULL,
  PRIMARY KEY (`payroll_id`),
  KEY `fk_payroll_int_hr_details_employee1_idx` (`employee_employee_id`),
  KEY `fk_payroll_int_hr_details_employee_payrolls1_idx` (`employee_payrolls_payroll_id`),
  CONSTRAINT `fk_payroll_int_hr_details_employee1` FOREIGN KEY (`employee_employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `fk_payroll_int_hr_details_employee_payrolls1` FOREIGN KEY (`employee_payrolls_payroll_id`) REFERENCES `employee_payrolls` (`payroll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.payroll_int_hr_details: ~3 rows (approximately)
INSERT INTO `payroll_int_hr_details` (`payroll_id`, `date`, `employee_name`, `total_advanced`, `paid_price`, `employee_employee_id`, `employee_payrolls_payroll_id`) VALUES
	('PID1747683695411', '2025-05-20 00:00:00', 'Jude Thamel', 18000, 0, 'EMP1747681629852', 1),
	('PID1747693824074', '2025-05-20 00:00:00', 'Pahan Kodikara', 23000, 0, 'EMP1747686825748', 2),
	('PID1747877007062', '2025-05-22 00:00:00', 'Shehan Thilon', 1124578.6, 0, 'EMP1747875415886', 543033);

-- Dumping structure for table order_management_system_with_erp_db.portion_types
CREATE TABLE IF NOT EXISTS `portion_types` (
  `portion_types_id` int NOT NULL AUTO_INCREMENT,
  `portion_types_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`portion_types_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.portion_types: ~3 rows (approximately)
INSERT INTO `portion_types` (`portion_types_id`, `portion_types_name`) VALUES
	(1, 'Full'),
	(2, 'Half'),
	(3, 'No Portion');

-- Dumping structure for table order_management_system_with_erp_db.product
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` varchar(10) NOT NULL,
  `batch_batch_id` int NOT NULL,
  `product_name` varchar(60) DEFAULT NULL,
  `brands_brands_id` int NOT NULL,
  `category_category_id` int NOT NULL,
  `description` text,
  `grn_grn_id` varchar(55) NOT NULL,
  `qty` double DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `fk_product_brands1_idx` (`brands_brands_id`),
  KEY `fk_product_category1_idx` (`category_category_id`),
  KEY `fk_product_batch1_idx` (`batch_batch_id`),
  KEY `fk_product_grn1_idx` (`grn_grn_id`),
  CONSTRAINT `fk_product_batch1` FOREIGN KEY (`batch_batch_id`) REFERENCES `batch` (`batch_id`),
  CONSTRAINT `fk_product_brands1` FOREIGN KEY (`brands_brands_id`) REFERENCES `brands` (`brands_id`),
  CONSTRAINT `fk_product_category1` FOREIGN KEY (`category_category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `fk_product_grn1` FOREIGN KEY (`grn_grn_id`) REFERENCES `grn` (`grn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.product: ~3 rows (approximately)
INSERT INTO `product` (`product_id`, `batch_batch_id`, `product_name`, `brands_brands_id`, `category_category_id`, `description`, `grn_grn_id`, `qty`) VALUES
	('PID5399', 4, 'Munhee Tikirimarie 200g', 3, 1, 'Munchee Tikiri Marie 200g Small Handi Pack', 'GRN1747877151614', NULL),
	('PID5703', 1, 'Maliban Creamcracker', 2, 1, 'Maliban Busicut is the best in the world', 'GRN1747738553388', 200),
	('PID7808', 2, 'Cocacola 250L', 1, 2, 'Cocacola is best drink for summer days', 'GRN1747738863005', 260);

-- Dumping structure for table order_management_system_with_erp_db.province
CREATE TABLE IF NOT EXISTS `province` (
  `province_id` int NOT NULL AUTO_INCREMENT,
  `province_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.province: ~9 rows (approximately)
INSERT INTO `province` (`province_id`, `province_name`) VALUES
	(1, 'SOUTHERN PROVINCE'),
	(2, 'WESTERN PROVINCE'),
	(3, 'CENTRAL PROVINCE'),
	(4, 'SABARAGAMUWA PROVINCE'),
	(5, 'EASTERN PROVINCE'),
	(6, 'UVA PROVINCE'),
	(7, 'NORTH WESTERN PROVINCE'),
	(8, 'NORTH CENTRAL PROVINCE'),
	(9, 'NORTHERN PROVINCE');

-- Dumping structure for table order_management_system_with_erp_db.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `supplier_id` varchar(55) NOT NULL,
  `first_name` varchar(80) DEFAULT NULL,
  `last_name` varchar(80) DEFAULT NULL,
  `contact_number` varchar(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `company_company_id` varchar(55) NOT NULL,
  `gender_gender_id` int NOT NULL,
  PRIMARY KEY (`supplier_id`),
  KEY `fk_supplier_company1_idx` (`company_company_id`),
  KEY `fk_supplier_gender1_idx` (`gender_gender_id`),
  CONSTRAINT `fk_supplier_company1` FOREIGN KEY (`company_company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_supplier_gender1` FOREIGN KEY (`gender_gender_id`) REFERENCES `gender` (`gender_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.supplier: ~3 rows (approximately)
INSERT INTO `supplier` (`supplier_id`, `first_name`, `last_name`, `contact_number`, `email`, `company_company_id`, `gender_gender_id`) VALUES
	('ESR1747734089747', 'Sahan', 'Premasiri', '0785459874', 'sahan@gmail.com', 'ECR1747682442716', 1),
	('ESR1747734357800', 'Nimal', 'Sinhagamage', '0788954110', 'nimal@gmail.com', 'ECR1747734024119', 1),
	('ESR1747876507007', 'Senaka', 'Bibile', '0789544554', 'senaka@gmail.com', 'ECR1747876445709', 1);

-- Dumping structure for table order_management_system_with_erp_db.suppliers_order_status
CREATE TABLE IF NOT EXISTS `suppliers_order_status` (
  `suppliers_order_status_id` int NOT NULL AUTO_INCREMENT,
  `suppliers_order_status_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`suppliers_order_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.suppliers_order_status: ~3 rows (approximately)
INSERT INTO `suppliers_order_status` (`suppliers_order_status_id`, `suppliers_order_status_name`) VALUES
	(1, 'Order Placed'),
	(2, 'Order Arrived'),
	(3, 'Order Not Arrived');

-- Dumping structure for table order_management_system_with_erp_db.temp_employee_attendance
CREATE TABLE IF NOT EXISTS `temp_employee_attendance` (
  `attendance_id` int NOT NULL AUTO_INCREMENT,
  `employee_name` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `employee_employee_id` varchar(55) NOT NULL,
  `attendence_type_type_id` int NOT NULL,
  PRIMARY KEY (`attendance_id`),
  KEY `fk_temp_employee_attendance_employee1_idx` (`employee_employee_id`),
  KEY `fk_temp_employee_attendance_attendence_type1_idx` (`attendence_type_type_id`),
  CONSTRAINT `fk_temp_employee_attendance_attendence_type1` FOREIGN KEY (`attendence_type_type_id`) REFERENCES `attendence_type` (`type_id`),
  CONSTRAINT `fk_temp_employee_attendance_employee1` FOREIGN KEY (`employee_employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table order_management_system_with_erp_db.temp_employee_attendance: ~2 rows (approximately)
INSERT INTO `temp_employee_attendance` (`attendance_id`, `employee_name`, `date`, `time`, `employee_employee_id`, `attendence_type_type_id`) VALUES
	(1, 'Jude Thamel', '2025-05-20', '00:48:57', 'EMP1747681629852', 1),
	(2, 'Shehan Thilon', '2025-05-22', '06:40:50', 'EMP1747875415886', 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
