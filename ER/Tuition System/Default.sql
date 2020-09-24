CREATE DATABASE  IF NOT EXISTS `tuition_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tuition_db`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: tuition_db
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ac_attendence`
--

DROP TABLE IF EXISTS `ac_attendence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ac_attendence` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `tbl_user_id` int NOT NULL,
  `tbl_ac_type_details_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_ac_attendence_tbl_user1_idx` (`tbl_user_id`),
  KEY `fk_tbl_ac_attendence_tbl_ac_type_details1_idx` (`tbl_ac_type_details_id`),
  CONSTRAINT `fk_tbl_ac_attendence_tbl_ac_type_details1` FOREIGN KEY (`tbl_ac_type_details_id`) REFERENCES `ac_type_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_ac_attendence_tbl_user1` FOREIGN KEY (`tbl_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_attendence`
--

LOCK TABLES `ac_attendence` WRITE;
/*!40000 ALTER TABLE `ac_attendence` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_attendence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_class`
--

DROP TABLE IF EXISTS `ac_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ac_class` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ac_id` int NOT NULL,
  `teacher_has_subject_id` int NOT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_ac_class_tbl_academic_course1_idx` (`ac_id`),
  KEY `fk_tbl_ac_class_tbl_teacher_has_subject1_idx` (`teacher_has_subject_id`),
  KEY `fk_tbl_ac_class_tbl_status1_idx` (`status_id`),
  CONSTRAINT `fk_tbl_ac_class_tbl_academic_course1` FOREIGN KEY (`ac_id`) REFERENCES `academic_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_ac_class_tbl_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_ac_class_tbl_teacher_has_subject1` FOREIGN KEY (`teacher_has_subject_id`) REFERENCES `teacher_has_subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_class`
--

LOCK TABLES `ac_class` WRITE;
/*!40000 ALTER TABLE `ac_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_payment`
--

DROP TABLE IF EXISTS `ac_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ac_payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ac_type_details_id` int NOT NULL,
  `year` year DEFAULT NULL,
  `month` varchar(15) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_ac_payment_tbl_ac_type_details1_idx` (`ac_type_details_id`),
  KEY `fk_tbl_ac_payment_tbl_user1_idx` (`user_id`),
  CONSTRAINT `fk_tbl_ac_payment_tbl_ac_type_details1` FOREIGN KEY (`ac_type_details_id`) REFERENCES `ac_type_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_ac_payment_tbl_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_payment`
--

LOCK TABLES `ac_payment` WRITE;
/*!40000 ALTER TABLE `ac_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_type_details`
--

DROP TABLE IF EXISTS `ac_type_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ac_type_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ac_class_id` int NOT NULL,
  `tbl_acc_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_ac_type_details_tbl_ac_class1_idx` (`ac_class_id`),
  KEY `fk_tbl_ac_type_details_tbl_acc_type1_idx` (`tbl_acc_type_id`),
  CONSTRAINT `fk_tbl_ac_type_details_tbl_ac_class1` FOREIGN KEY (`ac_class_id`) REFERENCES `ac_class` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_ac_type_details_tbl_acc_type1` FOREIGN KEY (`tbl_acc_type_id`) REFERENCES `acc_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_type_details`
--

LOCK TABLES `ac_type_details` WRITE;
/*!40000 ALTER TABLE `ac_type_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_type_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_type_list`
--

DROP TABLE IF EXISTS `ac_type_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ac_type_list` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tbl_student_id` int NOT NULL,
  `ac_type_details_id` int NOT NULL,
  `status` tinyint DEFAULT NULL,
  `absent_count` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_tbl_ac_type_list_tbl_ac_type_details1_idx` (`ac_type_details_id`),
  KEY `fk_tbl_ac_type_list_tbl_student1_idx` (`tbl_student_id`),
  CONSTRAINT `fk_tbl_ac_type_list_tbl_ac_type_details1` FOREIGN KEY (`ac_type_details_id`) REFERENCES `ac_type_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_ac_type_list_tbl_student1` FOREIGN KEY (`tbl_student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_type_list`
--

LOCK TABLES `ac_type_list` WRITE;
/*!40000 ALTER TABLE `ac_type_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_type_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aca_details`
--

DROP TABLE IF EXISTS `aca_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aca_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ac_attendence_id` int NOT NULL,
  `student_id` int NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_aca_details_tbl_ac_attendence1_idx` (`ac_attendence_id`),
  KEY `fk_tbl_aca_details_tbl_student1_idx` (`student_id`),
  CONSTRAINT `fk_tbl_aca_details_tbl_ac_attendence1` FOREIGN KEY (`ac_attendence_id`) REFERENCES `ac_attendence` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_aca_details_tbl_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aca_details`
--

LOCK TABLES `aca_details` WRITE;
/*!40000 ALTER TABLE `aca_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `aca_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academic_course`
--

DROP TABLE IF EXISTS `academic_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academic_course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam_id` int NOT NULL,
  `stream_id` int NOT NULL,
  `exam_year` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_academic_course_tbl_exam1_idx` (`exam_id`),
  KEY `fk_tbl_academic_course_tbl_stream1_idx` (`stream_id`),
  CONSTRAINT `fk_tbl_academic_course_tbl_exam1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_academic_course_tbl_stream1` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academic_course`
--

LOCK TABLES `academic_course` WRITE;
/*!40000 ALTER TABLE `academic_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `academic_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acc_type`
--

DROP TABLE IF EXISTS `acc_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_type`
--

LOCK TABLES `acc_type` WRITE;
/*!40000 ALTER TABLE `acc_type` DISABLE KEYS */;
INSERT INTO `acc_type` VALUES (1,'THEORY'),(2,'REVISION'),(3,'PAPER');
/*!40000 ALTER TABLE `acc_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acp_details`
--

DROP TABLE IF EXISTS `acp_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acp_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ac_payment_id` int NOT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `student_id` int NOT NULL,
  `pay_status` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_acp_details_tbl_ac_payment1_idx` (`ac_payment_id`),
  KEY `fk_tbl_acp_details_tbl_student1_idx` (`student_id`),
  CONSTRAINT `fk_tbl_acp_details_tbl_ac_payment1` FOREIGN KEY (`ac_payment_id`) REFERENCES `ac_payment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_acp_details_tbl_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acp_details`
--

LOCK TABLES `acp_details` WRITE;
/*!40000 ALTER TABLE `acp_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `acp_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `nic_number` varchar(15) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `contact_number` varchar(15) DEFAULT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_employee_tbl_status1_idx` (`status_id`),
  CONSTRAINT `fk_tbl_employee_tbl_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Buddhika','Kumarasinghe','943293007V','None','0711998310',1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam` (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES (1,'OL'),(2,'AL');
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gardien`
--

DROP TABLE IF EXISTS `gardien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gardien` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `home_number` varchar(15) DEFAULT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gardien`
--

LOCK TABLES `gardien` WRITE;
/*!40000 ALTER TABLE `gardien` DISABLE KEYS */;
/*!40000 ALTER TABLE `gardien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `near_city`
--

DROP TABLE IF EXISTS `near_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `near_city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `near_city`
--

LOCK TABLES `near_city` WRITE;
/*!40000 ALTER TABLE `near_city` DISABLE KEYS */;
/*!40000 ALTER TABLE `near_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `school` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'ACTIVE'),(2,'DEACTIVE');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stream`
--

DROP TABLE IF EXISTS `stream`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stream` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stream` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stream`
--

LOCK TABLES `stream` WRITE;
/*!40000 ALTER TABLE `stream` DISABLE KEYS */;
/*!40000 ALTER TABLE `stream` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index_number` varchar(45) DEFAULT NULL,
  `fname` varchar(45) DEFAULT NULL,
  `mname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `nic_number` varchar(15) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `contact_number` varchar(15) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `school_id` int NOT NULL,
  `grade` varchar(10) DEFAULT NULL,
  `near_city_id` int NOT NULL,
  `gardien_id` int NOT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_student_tbl_school1_idx` (`school_id`),
  KEY `fk_tbl_student_tbl_near_city1_idx` (`near_city_id`),
  KEY `fk_tbl_student_tbl_status1_idx` (`status_id`),
  KEY `fk_tbl_student_tbl_gardien1_idx` (`gardien_id`),
  CONSTRAINT `fk_tbl_student_tbl_gardien1` FOREIGN KEY (`gardien_id`) REFERENCES `gardien` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_student_tbl_near_city1` FOREIGN KEY (`near_city_id`) REFERENCES `near_city` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_student_tbl_school1` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_student_tbl_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `nic_number` varchar(45) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `home_number` varchar(15) DEFAULT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_teacher_tbl_status1_idx` (`status_id`),
  CONSTRAINT `fk_tbl_teacher_tbl_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_has_subject`
--

DROP TABLE IF EXISTS `teacher_has_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_has_subject` (
  `id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int DEFAULT NULL,
  `subject_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_teacher_has_tbl_subject_tbl_subject1_idx` (`subject_id`),
  KEY `fk_tbl_teacher_has_tbl_subject_tbl_teacher1_idx` (`teacher_id`),
  CONSTRAINT `fk_tbl_teacher_has_tbl_subject_tbl_subject1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_teacher_has_tbl_subject_tbl_teacher1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_has_subject`
--

LOCK TABLES `teacher_has_subject` WRITE;
/*!40000 ALTER TABLE `teacher_has_subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher_has_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_paymet`
--

DROP TABLE IF EXISTS `teacher_paymet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_paymet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int NOT NULL,
  `year` year DEFAULT NULL,
  `month` varchar(15) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_teacher_paymet_tbl_user1_idx` (`user_id`),
  KEY `fk_tbl_teacher_paymet_tbl_teacher1_idx` (`teacher_id`),
  CONSTRAINT `fk_tbl_teacher_paymet_tbl_teacher1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_teacher_paymet_tbl_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_paymet`
--

LOCK TABLES `teacher_paymet` WRITE;
/*!40000 ALTER TABLE `teacher_paymet` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher_paymet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tp_details`
--

DROP TABLE IF EXISTS `tp_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tp_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `teacher_paymet_id` int NOT NULL,
  `ac_type_details_id` int NOT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `pay_amount` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_tp_details_tbl_teacher_paymet1_idx` (`teacher_paymet_id`),
  KEY `fk_tbl_tp_details_tbl_ac_type_details1_idx` (`ac_type_details_id`),
  CONSTRAINT `fk_tbl_tp_details_tbl_ac_type_details1` FOREIGN KEY (`ac_type_details_id`) REFERENCES `ac_type_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_tp_details_tbl_teacher_paymet1` FOREIGN KEY (`teacher_paymet_id`) REFERENCES `teacher_paymet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tp_details`
--

LOCK TABLES `tp_details` WRITE;
/*!40000 ALTER TABLE `tp_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `tp_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `user_type_id` int NOT NULL,
  `employee_id` int NOT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_user_tbl_employee_idx` (`employee_id`),
  KEY `fk_tbl_user_tbl_status1_idx` (`status_id`),
  KEY `fk_tbl_user_tbl_user_type1_idx` (`user_type_id`),
  CONSTRAINT `fk_tbl_user_tbl_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_user_tbl_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_user_tbl_user_type1` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'SUPER ADMIN','admin',1,1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'Super Admin'),(2,'Admin'),(3,'Geust');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'tuition_db'
--

--
-- Dumping routines for database 'tuition_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-24 21:32:43
