-- MySQL dump 10.13  Distrib 5.6.17, for osx10.7 (x86_64)
--
-- Host: localhost    Database: itsnow_msc
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ACT_GE_BYTEARRAY`
--

DROP TABLE IF EXISTS `ACT_GE_BYTEARRAY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_GE_BYTEARRAY` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_RE_DEPLOYMENT` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_GE_BYTEARRAY`
--

LOCK TABLES `ACT_GE_BYTEARRAY` WRITE;
/*!40000 ALTER TABLE `ACT_GE_BYTEARRAY` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_GE_BYTEARRAY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_GE_PROPERTY`
--

DROP TABLE IF EXISTS `ACT_GE_PROPERTY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_GE_PROPERTY` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_GE_PROPERTY`
--

LOCK TABLES `ACT_GE_PROPERTY` WRITE;
/*!40000 ALTER TABLE `ACT_GE_PROPERTY` DISABLE KEYS */;
INSERT INTO `ACT_GE_PROPERTY` VALUES ('next.dbid','1',1),('schema.history','create(5.14)',1),('schema.version','5.14',1);
/*!40000 ALTER TABLE `ACT_GE_PROPERTY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_HI_ACTINST`
--

DROP TABLE IF EXISTS `ACT_HI_ACTINST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_HI_ACTINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_HI_ACTINST`
--

LOCK TABLES `ACT_HI_ACTINST` WRITE;
/*!40000 ALTER TABLE `ACT_HI_ACTINST` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_HI_ACTINST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_HI_ATTACHMENT`
--

DROP TABLE IF EXISTS `ACT_HI_ATTACHMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_HI_ATTACHMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_HI_ATTACHMENT`
--

LOCK TABLES `ACT_HI_ATTACHMENT` WRITE;
/*!40000 ALTER TABLE `ACT_HI_ATTACHMENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_HI_ATTACHMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_HI_COMMENT`
--

DROP TABLE IF EXISTS `ACT_HI_COMMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_HI_COMMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_HI_COMMENT`
--

LOCK TABLES `ACT_HI_COMMENT` WRITE;
/*!40000 ALTER TABLE `ACT_HI_COMMENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_HI_COMMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_HI_DETAIL`
--

DROP TABLE IF EXISTS `ACT_HI_DETAIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_HI_DETAIL` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_HI_DETAIL`
--

LOCK TABLES `ACT_HI_DETAIL` WRITE;
/*!40000 ALTER TABLE `ACT_HI_DETAIL` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_HI_DETAIL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_HI_IDENTITYLINK`
--

DROP TABLE IF EXISTS `ACT_HI_IDENTITYLINK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_HI_IDENTITYLINK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_HI_IDENTITYLINK`
--

LOCK TABLES `ACT_HI_IDENTITYLINK` WRITE;
/*!40000 ALTER TABLE `ACT_HI_IDENTITYLINK` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_HI_IDENTITYLINK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_HI_PROCINST`
--

DROP TABLE IF EXISTS `ACT_HI_PROCINST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_HI_PROCINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  UNIQUE KEY `ACT_UNIQ_HI_BUS_KEY` (`PROC_DEF_ID_`,`BUSINESS_KEY_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_HI_PROCINST`
--

LOCK TABLES `ACT_HI_PROCINST` WRITE;
/*!40000 ALTER TABLE `ACT_HI_PROCINST` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_HI_PROCINST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_HI_TASKINST`
--

DROP TABLE IF EXISTS `ACT_HI_TASKINST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_HI_TASKINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `CLAIM_TIME_` datetime DEFAULT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_HI_TASKINST`
--

LOCK TABLES `ACT_HI_TASKINST` WRITE;
/*!40000 ALTER TABLE `ACT_HI_TASKINST` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_HI_TASKINST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_HI_VARINST`
--

DROP TABLE IF EXISTS `ACT_HI_VARINST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_HI_VARINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_HI_VARINST`
--

LOCK TABLES `ACT_HI_VARINST` WRITE;
/*!40000 ALTER TABLE `ACT_HI_VARINST` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_HI_VARINST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_ID_GROUP`
--

DROP TABLE IF EXISTS `ACT_ID_GROUP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_ID_GROUP` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_ID_GROUP`
--

LOCK TABLES `ACT_ID_GROUP` WRITE;
/*!40000 ALTER TABLE `ACT_ID_GROUP` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_ID_GROUP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_ID_INFO`
--

DROP TABLE IF EXISTS `ACT_ID_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_ID_INFO` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_ID_INFO`
--

LOCK TABLES `ACT_ID_INFO` WRITE;
/*!40000 ALTER TABLE `ACT_ID_INFO` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_ID_INFO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_ID_MEMBERSHIP`
--

DROP TABLE IF EXISTS `ACT_ID_MEMBERSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_ID_MEMBERSHIP` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `ACT_ID_USER` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `ACT_ID_GROUP` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_ID_MEMBERSHIP`
--

LOCK TABLES `ACT_ID_MEMBERSHIP` WRITE;
/*!40000 ALTER TABLE `ACT_ID_MEMBERSHIP` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_ID_MEMBERSHIP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_ID_USER`
--

DROP TABLE IF EXISTS `ACT_ID_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_ID_USER` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_ID_USER`
--

LOCK TABLES `ACT_ID_USER` WRITE;
/*!40000 ALTER TABLE `ACT_ID_USER` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_ID_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_RE_DEPLOYMENT`
--

DROP TABLE IF EXISTS `ACT_RE_DEPLOYMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_RE_DEPLOYMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOY_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_RE_DEPLOYMENT`
--

LOCK TABLES `ACT_RE_DEPLOYMENT` WRITE;
/*!40000 ALTER TABLE `ACT_RE_DEPLOYMENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_RE_DEPLOYMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_RE_MODEL`
--

DROP TABLE IF EXISTS `ACT_RE_MODEL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_RE_MODEL` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_RE_DEPLOYMENT` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_RE_MODEL`
--

LOCK TABLES `ACT_RE_MODEL` WRITE;
/*!40000 ALTER TABLE `ACT_RE_MODEL` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_RE_MODEL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_RE_PROCDEF`
--

DROP TABLE IF EXISTS `ACT_RE_PROCDEF`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_RE_PROCDEF` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_RE_PROCDEF`
--

LOCK TABLES `ACT_RE_PROCDEF` WRITE;
/*!40000 ALTER TABLE `ACT_RE_PROCDEF` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_RE_PROCDEF` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_RU_EVENT_SUBSCR`
--

DROP TABLE IF EXISTS `ACT_RU_EVENT_SUBSCR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_RU_EVENT_SUBSCR` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_RU_EVENT_SUBSCR`
--

LOCK TABLES `ACT_RU_EVENT_SUBSCR` WRITE;
/*!40000 ALTER TABLE `ACT_RU_EVENT_SUBSCR` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_RU_EVENT_SUBSCR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_RU_EXECUTION`
--

DROP TABLE IF EXISTS `ACT_RU_EXECUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_RU_EXECUTION` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_RU_BUS_KEY` (`PROC_DEF_ID_`,`BUSINESS_KEY_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_RU_EXECUTION`
--

LOCK TABLES `ACT_RU_EXECUTION` WRITE;
/*!40000 ALTER TABLE `ACT_RU_EXECUTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_RU_EXECUTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_RU_IDENTITYLINK`
--

DROP TABLE IF EXISTS `ACT_RU_IDENTITYLINK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_RU_IDENTITYLINK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `ACT_RU_TASK` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_RU_IDENTITYLINK`
--

LOCK TABLES `ACT_RU_IDENTITYLINK` WRITE;
/*!40000 ALTER TABLE `ACT_RU_IDENTITYLINK` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_RU_IDENTITYLINK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_RU_JOB`
--

DROP TABLE IF EXISTS `ACT_RU_JOB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_RU_JOB` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_RU_JOB`
--

LOCK TABLES `ACT_RU_JOB` WRITE;
/*!40000 ALTER TABLE `ACT_RU_JOB` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_RU_JOB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_RU_TASK`
--

DROP TABLE IF EXISTS `ACT_RU_TASK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_RU_TASK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DUE_DATE_` datetime DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_RU_TASK`
--

LOCK TABLES `ACT_RU_TASK` WRITE;
/*!40000 ALTER TABLE `ACT_RU_TASK` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_RU_TASK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACT_RU_VARIABLE`
--

DROP TABLE IF EXISTS `ACT_RU_VARIABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACT_RU_VARIABLE` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACT_RU_VARIABLE`
--

LOCK TABLES `ACT_RU_VARIABLE` WRITE;
/*!40000 ALTER TABLE `ACT_RU_VARIABLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACT_RU_VARIABLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CHANGELOG`
--

DROP TABLE IF EXISTS `CHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CHANGELOG` (
  `ID` decimal(20,0) NOT NULL,
  `APPLIED_AT` varchar(25) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CHANGELOG`
--

LOCK TABLES `CHANGELOG` WRITE;
/*!40000 ALTER TABLE `CHANGELOG` DISABLE KEYS */;
INSERT INTO `CHANGELOG` VALUES (20140704020245,'2014-09-25 08:47:09','create changelog'),(20140717130000,'2014-09-25 08:47:11','create act engine'),(20140717130001,'2014-09-25 08:47:12','create act history'),(20140717130002,'2014-09-25 08:47:12','create act identity'),(20140717130003,'2014-09-25 08:47:12','insert act data'),(20140720144647,'2014-09-25 08:47:12','create process dictionaries'),(20140722112724,'2014-09-25 08:47:13','create accounts'),(20140722125016,'2014-09-25 08:47:13','create users'),(20140722125410,'2014-09-25 08:47:13','create roles'),(20140722125420,'2014-09-25 08:47:13','insert roles'),(20140722125933,'2014-09-25 08:47:13','create public service catalogs'),(20140722125944,'2014-09-25 08:47:13','create public service items'),(20140722125955,'2014-09-25 08:47:13','create account service items'),(20140722130414,'2014-09-25 08:47:13','create contracts'),(20140722130419,'2014-09-25 08:47:13','create contract details'),(20140722135343,'2014-09-25 08:47:14','create authorities'),(20140722135407,'2014-09-25 08:47:14','create groups'),(20140722135412,'2014-09-25 08:47:14','create group members'),(20140722135425,'2014-09-25 08:47:14','create group authorities'),(20140722135447,'2014-09-25 08:47:14','create persistent logins'),(20140722135554,'2014-09-25 08:47:14','create acl sids'),(20140722135605,'2014-09-25 08:47:14','create acl classes'),(20140722135616,'2014-09-25 08:47:14','create acl object identities'),(20140722135641,'2014-09-25 08:47:14','create acl entries'),(20140722183030,'2014-09-25 08:47:14','create slas'),(20140722183034,'2014-09-25 08:47:15','create slos'),(20140728142611,'2014-09-25 08:47:15','insert accounts'),(20140728143025,'2014-09-25 08:47:15','insert users'),(20140728144631,'2014-09-25 08:47:15','insert public service catalogs'),(20140728144637,'2014-09-25 08:47:15','insert public service items'),(20140728144639,'2014-09-25 08:47:15','insert account service items'),(20140728144711,'2014-09-25 08:47:15','insert slas'),(20140728144715,'2014-09-25 08:47:15','insert slos'),(20140728144841,'2014-09-25 08:47:15','insert contracts'),(20140728144847,'2014-09-25 08:47:15','insert contract details'),(20140728161900,'2014-09-25 08:47:15','insert authorities'),(20140728161920,'2014-09-25 08:47:15','insert groups'),(20140728161929,'2014-09-25 08:47:15','insert group members'),(20140728161939,'2014-09-25 08:47:15','insert group authorities'),(20140819094115,'2014-09-25 08:47:15','create icons'),(20140819094130,'2014-09-25 08:47:15','create menu items'),(20140819101016,'2014-09-25 08:47:16','insert icons'),(20140819145353,'2014-09-25 08:47:16','create ci relation types'),(20140819145721,'2014-09-25 08:47:16','create ci types'),(20140819145730,'2014-09-25 08:47:16','create ci type relations'),(20140819150805,'2014-09-25 08:47:16','insert ci relation types'),(20140819150813,'2014-09-25 08:47:16','insert ci types'),(20140819152142,'2014-09-25 08:47:16','insert ci type relations'),(20140825140057,'2014-09-25 08:47:16','insert msc menu items'),(20140827112025,'2014-09-25 08:47:16','create itsnow hosts'),(20140827112030,'2014-09-25 08:47:16','create itsnow schemas'),(20140827112032,'2014-09-25 08:47:16','create itsnow processes'),(20140827112047,'2014-09-25 08:47:16','insert itsnow hosts'),(20140827142537,'2014-09-25 08:47:17','insert itsnow schemas'),(20140827142851,'2014-09-25 08:47:17','insert itsnow processes'),(20140902164015,'2014-09-25 08:47:17','create user roles'),(20140904124504,'2014-09-25 08:47:17','insert process dictionaries'),(20140912164525,'2014-09-25 08:47:17','create group roles'),(20140912164639,'2014-09-25 08:47:17','insert group roles');
/*!40000 ALTER TABLE `CHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_service_items`
--

DROP TABLE IF EXISTS `account_service_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_service_items` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account_id` int(10) unsigned NOT NULL,
  `item_id` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `account_service_items_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`),
  CONSTRAINT `account_service_items_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `public_service_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_service_items`
--

LOCK TABLES `account_service_items` WRITE;
/*!40000 ALTER TABLE `account_service_items` DISABLE KEYS */;
INSERT INTO `account_service_items` VALUES (1,4,2,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,4,4,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,4,3,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(4,2,2,'2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `account_service_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL,
  `sn` varchar(20) NOT NULL,
  `domain` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(100) NOT NULL,
  `status` varchar(50) DEFAULT 'New',
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`sn`),
  UNIQUE KEY `domain` (`domain`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,1,'msc','www','Itsnow Carrier','msc','Valid',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,3,'msu_001','csvw','Shanghai VW','msu','Valid',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,8,'msu_002','csgm','Shanghai GM','msu','New',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(4,6,'msp_001','dnt','DNT','msp','Valid',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(5,10,'msp_002','teamsun','TeamSun','msp','Rejected',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_classes`
--

DROP TABLE IF EXISTS `acl_classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acl_classes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `class` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_acl_classes` (`class`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_classes`
--

LOCK TABLES `acl_classes` WRITE;
/*!40000 ALTER TABLE `acl_classes` DISABLE KEYS */;
/*!40000 ALTER TABLE `acl_classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_entries`
--

DROP TABLE IF EXISTS `acl_entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acl_entries` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `acl_object_identity` int(10) unsigned NOT NULL,
  `ace_order` int(10) NOT NULL,
  `sid` int(10) unsigned NOT NULL,
  `mask` int(10) unsigned NOT NULL,
  `granting` tinyint(1) NOT NULL,
  `audit_success` tinyint(1) NOT NULL,
  `audit_failure` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_acl_entry` (`acl_object_identity`,`ace_order`),
  KEY `fk_acl_entry_acl` (`sid`),
  CONSTRAINT `acl_entries_ibfk_1` FOREIGN KEY (`acl_object_identity`) REFERENCES `acl_object_identities` (`id`),
  CONSTRAINT `acl_entries_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `acl_sids` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_entries`
--

LOCK TABLES `acl_entries` WRITE;
/*!40000 ALTER TABLE `acl_entries` DISABLE KEYS */;
/*!40000 ALTER TABLE `acl_entries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_object_identities`
--

DROP TABLE IF EXISTS `acl_object_identities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acl_object_identities` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `object_id_class` int(10) unsigned NOT NULL,
  `object_id_identity` int(10) unsigned NOT NULL,
  `parent_object` int(10) unsigned DEFAULT NULL,
  `owner_sid` int(10) unsigned DEFAULT NULL,
  `entries_inheriting` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_acl_object_identity` (`object_id_class`,`object_id_identity`),
  KEY `fk_aoi_parent` (`parent_object`),
  KEY `fk_aoi_owner` (`owner_sid`),
  CONSTRAINT `acl_object_identities_ibfk_1` FOREIGN KEY (`parent_object`) REFERENCES `acl_object_identities` (`id`),
  CONSTRAINT `acl_object_identities_ibfk_2` FOREIGN KEY (`object_id_class`) REFERENCES `acl_classes` (`id`),
  CONSTRAINT `acl_object_identities_ibfk_3` FOREIGN KEY (`owner_sid`) REFERENCES `acl_sids` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_object_identities`
--

LOCK TABLES `acl_object_identities` WRITE;
/*!40000 ALTER TABLE `acl_object_identities` DISABLE KEYS */;
/*!40000 ALTER TABLE `acl_object_identities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_sids`
--

DROP TABLE IF EXISTS `acl_sids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acl_sids` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `principal` tinyint(1) NOT NULL,
  `sid` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_acl_sids` (`sid`,`principal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_sids`
--

LOCK TABLES `acl_sids` WRITE;
/*!40000 ALTER TABLE `acl_sids` DISABLE KEYS */;
/*!40000 ALTER TABLE `acl_sids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(255) NOT NULL,
  UNIQUE KEY `idx_authorities_username` (`username`,`authority`),
  KEY `authority` (`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`authority`) REFERENCES `roles` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES ('admin','ROLE_ADMIN'),('root','ROLE_ADMIN'),('jason.wang','ROLE_ITER'),('mike.wei','ROLE_ITER'),('jacky.cao','ROLE_LINE_ONE'),('susie.qian','ROLE_LINE_ONE'),('smile.tian','ROLE_LINE_TWO'),('susie.qian','ROLE_LINE_TWO'),('rose.zhou','ROLE_SERVICE_DESK'),('stone.xin','ROLE_SERVICE_DESK'),('jacky.cao','ROLE_USER'),('sharp.liu','ROLE_USER'),('smile.tian','ROLE_USER'),('steve.li','ROLE_USER'),('stone.xin','ROLE_USER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_relation_types`
--

DROP TABLE IF EXISTS `ci_relation_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_relation_types` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `reverse_name` varchar(50) NOT NULL,
  `source_filter` varchar(255) DEFAULT NULL,
  `dest_filter` varchar(255) DEFAULT NULL,
  `css` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_relation_types`
--

LOCK TABLES `ci_relation_types` WRITE;
/*!40000 ALTER TABLE `ci_relation_types` DISABLE KEYS */;
INSERT INTO `ci_relation_types` VALUES (1,'depends_on','used_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','Runnable depends on container','2014-09-25 00:47:16','2014-09-25 00:47:16'),(2,'uses','used_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','Something uses another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(3,'uses','owned_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','Something uses another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(4,'sends_data_to','receives_data_from',NULL,NULL,'{array: true,  line: solid, weight: 10}','Someone sends data to another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(5,'runs','runs_on',NULL,NULL,'{array: true,  line: solid, weight: 10}','Some system runs on another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(6,'connected_to','connected_to',NULL,NULL,'{array: false, line: solid, weight: 10}','Some system connected to another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(7,'subscribes_to','subscribed_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','Some one subscribes to another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(8,'impacts','impacted_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','Some system impacts another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(9,'submits','submitted_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','Some one submits something','2014-09-25 00:47:16','2014-09-25 00:47:16'),(10,'supports','supported_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','Some group supports some system','2014-09-25 00:47:16','2014-09-25 00:47:16'),(11,'consists_of','are_parts_of',NULL,NULL,'{array: true,  line: solid, weight: 10}','Something consists of another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(12,'author_of','is_written_by','People','Document','{array: true,  line: solid, weight: 10}','People is author of documents','2014-09-25 00:47:16','2014-09-25 00:47:16'),(13,'is_edited_by','editor_of','Document','Technician','{array: true,  line: solid, weight: 10}','Document is edited by technician','2014-09-25 00:47:16','2014-09-25 00:47:16'),(14,'hosted_on','hosts','Application','Host','{array: true,  line: solid, weight: 10}','Business service is hosted on server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(15,'enables','is_enabled_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','IT service enables by business service','2014-09-25 00:47:16','2014-09-25 00:47:16'),(16,'includes','member_of',NULL,NULL,'{array: true,  line: solid, weight: 10}','Network includes switches','2014-09-25 00:47:16','2014-09-25 00:47:16'),(17,'contains','in_rack',NULL,NULL,'{array: true,  line: solid, weight: 10}','Rack contains server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(18,'contains','member_of',NULL,NULL,'{array: true,  line: solid, weight: 10}','Something contains another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(19,'located_in','houses',NULL,NULL,'{array: true,  line: solid, weight: 10}','Rack located in data center','2014-09-25 00:47:16','2014-09-25 00:47:16'),(20,'exchanges_data_with','exchanges_data_with',NULL,NULL,'{array: false, line: solid, weight: 10}','Some system exchange data with another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(21,'backed_up_by','backed_up_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','Some system backed up by another','2014-09-25 00:47:16','2014-09-25 00:47:16'),(22,'managed_by','manages',NULL,'People','{array: true,  line: solid, weight: 10}','Some system is managed by someone','2014-09-25 00:47:16','2014-09-25 00:47:16'),(23,'virtualizes','virtualized_by',NULL,NULL,'{array: true,  line: solid, weight: 10}','Something virtualizes another','2014-09-25 00:47:16','2014-09-25 00:47:16');
/*!40000 ALTER TABLE `ci_relation_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_type_relations`
--

DROP TABLE IF EXISTS `ci_type_relations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_type_relations` (
  `ci_type_a_id` int(10) unsigned NOT NULL,
  `ci_type_b_id` int(10) unsigned NOT NULL,
  `ci_relation_type_id` int(10) unsigned NOT NULL,
  KEY `ci_type_a_id` (`ci_type_a_id`),
  KEY `ci_type_b_id` (`ci_type_b_id`),
  KEY `ci_relation_type_id` (`ci_relation_type_id`),
  CONSTRAINT `ci_type_relations_ibfk_1` FOREIGN KEY (`ci_type_a_id`) REFERENCES `ci_types` (`id`),
  CONSTRAINT `ci_type_relations_ibfk_2` FOREIGN KEY (`ci_type_b_id`) REFERENCES `ci_types` (`id`),
  CONSTRAINT `ci_type_relations_ibfk_3` FOREIGN KEY (`ci_relation_type_id`) REFERENCES `ci_relation_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_type_relations`
--

LOCK TABLES `ci_type_relations` WRITE;
/*!40000 ALTER TABLE `ci_type_relations` DISABLE KEYS */;
INSERT INTO `ci_type_relations` VALUES (3,1,15),(18,1,10),(1,17,22),(1,30,14),(80,1,4),(18,3,10),(3,17,22),(3,30,14),(81,3,4),(3,80,2),(1,2,16),(10,15,16),(14,15,16),(14,30,2),(14,60,2),(19,17,13),(17,19,12),(80,17,22),(254,17,22),(253,17,22),(200,17,22),(30,17,22),(60,17,22),(261,17,22),(18,250,10),(18,254,10),(18,253,10),(233,30,17),(30,253,1),(30,30,21),(30,230,19),(30,200,20),(30,261,6),(30,255,6),(210,30,18),(250,30,16),(30,80,5),(60,200,20),(60,281,6),(60,261,6),(250,60,16),(60,80,5),(80,200,2),(80,80,1),(250,253,16),(250,254,16),(253,255,6),(253,251,6),(253,253,21),(254,255,16),(254,254,21),(255,251,6),(251,261,6),(210,200,6),(233,230,19);
/*!40000 ALTER TABLE `ci_type_relations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_types`
--

DROP TABLE IF EXISTS `ci_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_types` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `parent_id` int(10) unsigned DEFAULT NULL,
  `icon_id` int(10) unsigned DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `parent_id` (`parent_id`),
  KEY `icon_id` (`icon_id`),
  CONSTRAINT `ci_types_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `ci_types` (`id`),
  CONSTRAINT `ci_types_ibfk_2` FOREIGN KEY (`icon_id`) REFERENCES `icons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=296 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_types`
--

LOCK TABLES `ci_types` WRITE;
/*!40000 ALTER TABLE `ci_types` DISABLE KEYS */;
INSERT INTO `ci_types` VALUES (1,'Business Service',NULL,1,'The Business Service','2014-09-25 00:47:16','2014-09-25 00:47:16'),(2,'Business Process',NULL,2,'The Business Process','2014-09-25 00:47:16','2014-09-25 00:47:16'),(3,'IT Service',NULL,3,'The IT Service','2014-09-25 00:47:16','2014-09-25 00:47:16'),(10,'Account',NULL,4,'The Account','2014-09-25 00:47:16','2014-09-25 00:47:16'),(11,'MSC Account',10,5,'The MSC Account','2014-09-25 00:47:16','2014-09-25 00:47:16'),(12,'MSU Account',10,6,'The MSU Account','2014-09-25 00:47:16','2014-09-25 00:47:16'),(13,'MSP Account',10,7,'The MSP Account','2014-09-25 00:47:16','2014-09-25 00:47:16'),(14,'Department',NULL,8,'The Department','2014-09-25 00:47:16','2014-09-25 00:47:16'),(15,'People',NULL,9,'The People','2014-09-25 00:47:16','2014-09-25 00:47:16'),(16,'Requester',16,10,'The Requester','2014-09-25 00:47:16','2014-09-25 00:47:16'),(17,'Technician',16,11,'The Technician','2014-09-25 00:47:16','2014-09-25 00:47:16'),(18,'Support Group',NULL,12,'The Support Group','2014-09-25 00:47:16','2014-09-25 00:47:16'),(19,'Document',NULL,13,'The Document','2014-09-25 00:47:16','2014-09-25 00:47:16'),(30,'Computer Server',NULL,14,'The Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(31,'Application Server',30,15,'The Application Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(32,'Database Server',30,16,'The Database Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(33,'File Server',30,17,'The File Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(34,'Storage Server',30,18,'The Storage Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(35,'IBM Main Frame',30,19,'The IBM Mainframe','2014-09-25 00:47:16','2014-09-25 00:47:16'),(36,'Windows Server',30,20,'The Windows Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(37,'Linux Server',30,21,'The Linux Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(38,'Unix Server',30,22,'The Unix Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(39,'AIX Server',38,23,'The AIX Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(40,'HPUX Server',38,24,'The HP Unix Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(41,'FreeBSD Server',38,25,'The Free BSD Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(42,'Mac OSX Server',38,26,'The Mac OSX Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(43,'Netware Server',38,27,'The Novell Netware Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(50,'Virtualization Server',30,28,'The Virtualization Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(51,'Hyper-V Server',50,29,'The Hyper-V Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(52,'ESX Server',50,30,'The ESX Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(60,'Workstation',NULL,31,'The Workstation','2014-09-25 00:47:16','2014-09-25 00:47:16'),(61,'IBM Workstation',NULL,32,'The IBM Workstation','2014-09-25 00:47:16','2014-09-25 00:47:16'),(62,'Windows Workstation',NULL,33,'The Windows Workstation','2014-09-25 00:47:16','2014-09-25 00:47:16'),(63,'Unix Workstation',NULL,34,'The Unix Workstation','2014-09-25 00:47:16','2014-09-25 00:47:16'),(64,'AIX Workstation',NULL,35,'The AIX Workstation','2014-09-25 00:47:16','2014-09-25 00:47:16'),(65,'HPUX Workstation',NULL,36,'The HPUX Workstation','2014-09-25 00:47:16','2014-09-25 00:47:16'),(66,'Linux Workstation',NULL,37,'The Linux Workstation','2014-09-25 00:47:16','2014-09-25 00:47:16'),(67,'Mac Workstation',NULL,38,'The Mac Workstation','2014-09-25 00:47:16','2014-09-25 00:47:16'),(68,'Solaris Workstation',NULL,39,'The Solaris Workstation','2014-09-25 00:47:16','2014-09-25 00:47:16'),(80,'Software',NULL,40,'The Software','2014-09-25 00:47:16','2014-09-25 00:47:16'),(81,'Application',80,41,'The Application','2014-09-25 00:47:16','2014-09-25 00:47:16'),(82,'Web Service',81,42,'The Web Service','2014-09-25 00:47:16','2014-09-25 00:47:16'),(83,'REST Service',81,43,'The Rest Service','2014-09-25 00:47:16','2014-09-25 00:47:16'),(84,'Web Site',81,44,'The Web Site','2014-09-25 00:47:16','2014-09-25 00:47:16'),(90,'Web Server',80,45,'The Web Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(91,'Apache',90,46,'The Apache Web Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(92,'IIS',90,47,'The Microsoft IIS Web Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(93,'Nginx',90,48,'The Nginx Web Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(100,'App Server',80,49,'The APP Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(101,'Weblogic',100,50,'The Weblogic Application Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(102,'Websphere',100,51,'The Websphere Application Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(103,'Domino',100,52,'The Domino Application Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(104,'JBoss',100,53,'The JBoss Application Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(105,'Tomcat',100,54,'The Tomcat Application Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(106,'Jetty',100,55,'The Jetty Application Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(110,'Database',80,56,'The Database','2014-09-25 00:47:16','2014-09-25 00:47:16'),(111,'DB2',110,57,'The DB2 Database','2014-09-25 00:47:16','2014-09-25 00:47:16'),(112,'MSSQL',110,58,'The MS SQL Server Database','2014-09-25 00:47:16','2014-09-25 00:47:16'),(113,'MySQL',110,59,'The MySQL Database','2014-09-25 00:47:16','2014-09-25 00:47:16'),(114,'Oracle',110,60,'The Oracle Database','2014-09-25 00:47:16','2014-09-25 00:47:16'),(115,'Sybase',110,61,'The Sybase Database','2014-09-25 00:47:16','2014-09-25 00:47:16'),(120,'Infrastructure Service',80,62,'The Infrastructure Service','2014-09-25 00:47:16','2014-09-25 00:47:16'),(121,'Directory Server',120,63,'The Directory Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(122,'Email Server',120,64,'The Email Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(123,'FTP Server',120,65,'The FTP Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(124,'LDAP Server',120,66,'The LDAP Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(130,'Virtual Machine',80,67,'The Virtual Machine','2014-09-25 00:47:16','2014-09-25 00:47:16'),(131,'KVM',130,68,'The KVM Virtual Machine','2014-09-25 00:47:16','2014-09-25 00:47:16'),(132,'Parallels',130,69,'The Parallels Virtual Machine','2014-09-25 00:47:16','2014-09-25 00:47:16'),(133,'VMWare',130,70,'The VMWare Virtual Machine','2014-09-25 00:47:16','2014-09-25 00:47:16'),(134,'Citrix XEN',130,71,'The Citrix XEN Virtual Machine','2014-09-25 00:47:16','2014-09-25 00:47:16'),(140,'Virtual Machine Object',NULL,72,'The Virtual Machine Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(141,'Hyper-V Object',140,73,'The Hyper-V Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(142,'KVM Object',140,74,'The KVM Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(143,'Virtual Machine Instance',140,75,'The Virtual Machine Instance','2014-09-25 00:47:16','2014-09-25 00:47:16'),(144,'Virtual Machine Template',140,81,'The Virtual Machine Template','2014-09-25 00:47:16','2014-09-25 00:47:16'),(145,'VMWare VCenter Object',140,83,'The VMWare VCenter Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(146,'VMWare VCenter Instance',NULL,90,'The VMWare VCenter Instance','2014-09-25 00:47:16','2014-09-25 00:47:16'),(147,'VMWare Virtual Machine Template',144,82,'The VMWare Virtual Machine Template','2014-09-25 00:47:16','2014-09-25 00:47:16'),(150,'EC2 Virtual Machine Instance',143,76,'The EC2 Virtual Machine Instance','2014-09-25 00:47:16','2014-09-25 00:47:16'),(151,'Hyper-V Virtual Machine Instance',143,77,'The Hyper-V Virtual Machine Instance','2014-09-25 00:47:16','2014-09-25 00:47:16'),(152,'KVM Virtual Machine Instance',143,78,'The KVM Virtual Machine Instance','2014-09-25 00:47:16','2014-09-25 00:47:16'),(153,'Solaris Virtual Machine Instance',143,79,'The Solaris Virtual Machine Instance','2014-09-25 00:47:16','2014-09-25 00:47:16'),(154,'VMWare Virtual Machine Instance',143,80,'The VMWare Virtual Machine Instance','2014-09-25 00:47:16','2014-09-25 00:47:16'),(160,'ESX Resource Pool',145,84,'The VMWare VCenter Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(161,'VMWare VCenter Cluster',145,85,'The VMWare VCenter Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(162,'VMWare VCenter DataCenter',145,86,'The VMWare VCenter Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(163,'VMWare VCenter DataStore',145,87,'The VMWare VCenter Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(164,'VMWare VCenter Folder',145,88,'The VMWare VCenter Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(165,'VMWare VCenter Network',145,89,'The VMWare VCenter Object','2014-09-25 00:47:16','2014-09-25 00:47:16'),(180,'Database Schema',NULL,91,'The database schema','2014-09-25 00:47:16','2014-09-25 00:47:16'),(181,'DB2 Schema',180,92,'The DB2 schema','2014-09-25 00:47:16','2014-09-25 00:47:16'),(182,'MSSQL Schema',180,93,'The MSSQL schema','2014-09-25 00:47:16','2014-09-25 00:47:16'),(183,'MySQL Schema',180,94,'The MySQL schema','2014-09-25 00:47:16','2014-09-25 00:47:16'),(184,'Oracle Schema',180,95,'The Oracle schema','2014-09-25 00:47:16','2014-09-25 00:47:16'),(185,'Sybase Schema',180,96,'The Sybase schema','2014-09-25 00:47:16','2014-09-25 00:47:16'),(190,'File System',NULL,97,'The File System','2014-09-25 00:47:16','2014-09-25 00:47:16'),(191,'NFS File System',190,98,'The NFS File System','2014-09-25 00:47:16','2014-09-25 00:47:16'),(192,'SMB File System',190,99,'The SMB File System','2014-09-25 00:47:16','2014-09-25 00:47:16'),(195,'Service Instance',NULL,101,'The IP Service Instance','2014-09-25 00:47:16','2014-09-25 00:47:16'),(196,'Unix Daemon',195,102,'The Unix Daemon','2014-09-25 00:47:16','2014-09-25 00:47:16'),(197,'Windows Service',195,103,'The Windows Service','2014-09-25 00:47:16','2014-09-25 00:47:16'),(200,'Storage Device',NULL,104,'The Storage Device','2014-09-25 00:47:16','2014-09-25 00:47:16'),(201,'Storage HBA',NULL,105,'The Storage HBA','2014-09-25 00:47:16','2014-09-25 00:47:16'),(202,'Storage Pool',NULL,106,'The Storage Pool','2014-09-25 00:47:16','2014-09-25 00:47:16'),(203,'Storage Port',NULL,107,'The Storage Port','2014-09-25 00:47:16','2014-09-25 00:47:16'),(204,'Storage Volume',NULL,108,'The Storage Volume','2014-09-25 00:47:16','2014-09-25 00:47:16'),(205,'Storage File Share',NULL,109,'The Storage File Share','2014-09-25 00:47:16','2014-09-25 00:47:16'),(210,'Cluster',NULL,110,'The Cluster','2014-09-25 00:47:16','2014-09-25 00:47:16'),(211,'Cluster Node',NULL,111,'The Cluster Node','2014-09-25 00:47:16','2014-09-25 00:47:16'),(212,'Cluster Resource',NULL,112,'The Cluster Resource','2014-09-25 00:47:16','2014-09-25 00:47:16'),(213,'Cluster Virtual IP',NULL,113,'The Cluster Virtual IP','2014-09-25 00:47:16','2014-09-25 00:47:16'),(220,'Load Balancer Server',NULL,114,'The Load balancer Server','2014-09-25 00:47:16','2014-09-25 00:47:16'),(221,'Load Balancer Interface',NULL,115,'The Load balancer Interface','2014-09-25 00:47:16','2014-09-25 00:47:16'),(222,'Load Balancer Pool',NULL,116,'The Load balancer Pool','2014-09-25 00:47:16','2014-09-25 00:47:16'),(223,'Load Balancer Pool Member',NULL,117,'The Load balancer Pool Member','2014-09-25 00:47:16','2014-09-25 00:47:16'),(224,'Load Balancer Service',NULL,118,'The Load balancer Service','2014-09-25 00:47:16','2014-09-25 00:47:16'),(225,'Load Balancer VLAN',NULL,119,'The Load balancer VLAN','2014-09-25 00:47:16','2014-09-25 00:47:16'),(230,'Data Center',NULL,120,'The Data Center','2014-09-25 00:47:16','2014-09-25 00:47:16'),(231,'Data Center Zone',NULL,121,'The Data Center Zone','2014-09-25 00:47:16','2014-09-25 00:47:16'),(232,'Computer Room',NULL,122,'The Computer Room','2014-09-25 00:47:16','2014-09-25 00:47:16'),(233,'Rack',NULL,123,'The Rack','2014-09-25 00:47:16','2014-09-25 00:47:16'),(240,'UPS',NULL,124,'The UPS','2014-09-25 00:47:16','2014-09-25 00:47:16'),(241,'UPS Input',NULL,125,'The UPS Input','2014-09-25 00:47:16','2014-09-25 00:47:16'),(242,'UPS Output',NULL,126,'The UPS Output','2014-09-25 00:47:16','2014-09-25 00:47:16'),(243,'UPS Alarm',NULL,127,'The UPS Alarm','2014-09-25 00:47:16','2014-09-25 00:47:16'),(244,'UPS Bypass',NULL,128,'The UPS Bypass','2014-09-25 00:47:16','2014-09-25 00:47:16'),(250,'Network',NULL,129,'The IP Network','2014-09-25 00:47:16','2014-09-25 00:47:16'),(251,'Firewall',NULL,130,'The Firewall','2014-09-25 00:47:16','2014-09-25 00:47:16'),(252,'Network Gear',NULL,131,'The Network Gear','2014-09-25 00:47:16','2014-09-25 00:47:16'),(253,'Router',252,132,'The IP Router','2014-09-25 00:47:16','2014-09-25 00:47:16'),(254,'Switch',252,133,'The IP Switch','2014-09-25 00:47:16','2014-09-25 00:47:16'),(255,'Switch Port',NULL,134,'The Switch Port','2014-09-25 00:47:16','2014-09-25 00:47:16'),(256,'IP Phone',NULL,135,'The IP Phone','2014-09-25 00:47:16','2014-09-25 00:47:16'),(257,'IP Address',NULL,136,'The IP Address','2014-09-25 00:47:16','2014-09-25 00:47:16'),(258,'DNS Name',NULL,137,'The DNS Name','2014-09-25 00:47:16','2014-09-25 00:47:16'),(259,'IP Device',NULL,138,'The IP Device','2014-09-25 00:47:16','2014-09-25 00:47:16'),(260,'VPN',NULL,139,'The Virtual Private Network','2014-09-25 00:47:16','2014-09-25 00:47:16'),(261,'Access Point',NULL,140,'The Access Point','2014-09-25 00:47:16','2014-09-25 00:47:16'),(280,'Accessory',NULL,141,'The Accessory','2014-09-25 00:47:16','2014-09-25 00:47:16'),(281,'Printer',280,142,'The Printer','2014-09-25 00:47:16','2014-09-25 00:47:16'),(282,'Smart Phone',280,143,'The Smart Phone','2014-09-25 00:47:16','2014-09-25 00:47:16'),(283,'Tablet',280,144,'The Tablet','2014-09-25 00:47:16','2014-09-25 00:47:16'),(290,'Computer Peripheral',NULL,145,'The Computer Peripheral','2014-09-25 00:47:16','2014-09-25 00:47:16'),(291,'Projector',290,146,'The Projector','2014-09-25 00:47:16','2014-09-25 00:47:16'),(292,'Keyboard',290,147,'The Keyboard','2014-09-25 00:47:16','2014-09-25 00:47:16'),(293,'Scanner',290,148,'The Scanner','2014-09-25 00:47:16','2014-09-25 00:47:16'),(294,'Disk',290,149,'The Disk','2014-09-25 00:47:16','2014-09-25 00:47:16'),(295,'Network Adapter',290,150,'The Network Adapter','2014-09-25 00:47:16','2014-09-25 00:47:16');
/*!40000 ALTER TABLE `ci_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_details`
--

DROP TABLE IF EXISTS `contract_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract_details` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `contract_id` int(10) unsigned NOT NULL,
  `title` varchar(255) NOT NULL,
  `brief` varchar(255) DEFAULT NULL,
  `description` tinytext,
  `icon` varchar(100) DEFAULT NULL,
  `item_id` int(10) unsigned DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `contract_id` (`contract_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `contract_details_ibfk_1` FOREIGN KEY (`contract_id`) REFERENCES `contracts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `contract_details_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `public_service_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_details`
--

LOCK TABLES `contract_details` WRITE;
/*!40000 ALTER TABLE `contract_details` DISABLE KEYS */;
INSERT INTO `contract_details` VALUES (1,1,'??1','?????','???????','/assets/cd01.png',1,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,1,'??2','?????','???????','/assets/cd02.png',2,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,1,'??3','?????','???????','/assets/cd03.png',3,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(4,1,'??4','?????','???????','/assets/cd04.png',4,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(5,2,'??A','A??','?A?????','/assets/cd.png',1,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(6,2,'??B','B??','?B?????','/assets/cd.png',2,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(7,2,'??C','C??','?C?????','/assets/cd.png',3,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(8,3,'??X','X??','?X?????','/assets/cd.png',2,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(9,3,'??Y','Y??','?Y?????','/assets/cd.png',3,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(10,3,'??Z','Z??','?Z?????','/assets/cd.png',4,'2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `contract_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contracts`
--

DROP TABLE IF EXISTS `contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contracts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `msu_account_id` int(10) unsigned NOT NULL,
  `msp_account_id` int(10) unsigned DEFAULT NULL,
  `sn` varchar(20) NOT NULL,
  `msu_status` varchar(20) NOT NULL DEFAULT 'Draft',
  `msp_status` varchar(20) NOT NULL DEFAULT 'Draft',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `msu_account_id` (`msu_account_id`),
  KEY `msp_account_id` (`msp_account_id`),
  CONSTRAINT `contracts_ibfk_1` FOREIGN KEY (`msu_account_id`) REFERENCES `accounts` (`id`),
  CONSTRAINT `contracts_ibfk_2` FOREIGN KEY (`msp_account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracts`
--

LOCK TABLES `contracts` WRITE;
/*!40000 ALTER TABLE `contracts` DISABLE KEYS */;
INSERT INTO `contracts` VALUES (1,2,5,'SWG-201403080003','Draft','Draft','2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,2,4,'SWG-201407010010','Draft','Draft','2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,2,4,'SWG-201408050002','Draft','Draft','2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `contracts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_authorities`
--

DROP TABLE IF EXISTS `group_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_authorities` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(10) unsigned NOT NULL,
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  KEY `authority` (`authority`),
  CONSTRAINT `group_authorities_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `group_authorities_ibfk_2` FOREIGN KEY (`authority`) REFERENCES `roles` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_authorities`
--

LOCK TABLES `group_authorities` WRITE;
/*!40000 ALTER TABLE `group_authorities` DISABLE KEYS */;
INSERT INTO `group_authorities` VALUES (1,1,'ROLE_ADMIN'),(2,1,'ROLE_USER'),(3,3,'ROLE_MONITOR'),(4,3,'ROLE_USER'),(5,4,'ROLE_REPORTER'),(6,4,'ROLE_USER'),(7,2,'ROLE_GUEST');
/*!40000 ALTER TABLE `group_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_members`
--

DROP TABLE IF EXISTS `group_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_members` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `group_id` int(10) unsigned NOT NULL,
  `group_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  KEY `username` (`username`),
  CONSTRAINT `group_members_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `group_members_ibfk_2` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_members`
--

LOCK TABLES `group_members` WRITE;
/*!40000 ALTER TABLE `group_members` DISABLE KEYS */;
INSERT INTO `group_members` VALUES (1,'admin',1,'administrators'),(2,'admin',3,'monitors'),(3,'admin',4,'reporters'),(4,'root',1,'administrators'),(5,'root',3,'monitors'),(6,'root',4,'reporters'),(7,'steve.li',4,'reporters'),(8,'jason.wang',3,'monitors'),(9,'stone.xin',4,'reporters'),(10,'jacky.cao',3,'monitors'),(11,'smile.tian',3,'monitors'),(12,'sharp.liu',4,'reporters'),(13,'mike.wei',3,'monitors'),(14,'jacky.cao',5,'first_line'),(15,'jay.xiong',6,'second_line'),(16,'steve.li',5,'first_line'),(17,'jason.wang',6,'second_line');
/*!40000 ALTER TABLE `group_members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_roles`
--

DROP TABLE IF EXISTS `group_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_roles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(10) unsigned NOT NULL,
  `role_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_roles`
--

LOCK TABLES `group_roles` WRITE;
/*!40000 ALTER TABLE `group_roles` DISABLE KEYS */;
INSERT INTO `group_roles` VALUES (1,1,'1'),(2,1,'2'),(3,3,'3'),(4,3,'2'),(5,4,'3'),(6,4,'2'),(7,2,'3');
/*!40000 ALTER TABLE `group_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sn` varchar(10) NOT NULL,
  `group_name` varchar(255) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (1,'001','administrators',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,'002','guests',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,'003','monitors',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(4,'004','reporters',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(5,'005','first_line',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(6,'006','second_line',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `icons`
--

DROP TABLE IF EXISTS `icons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `icons` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `base` varchar(100) NOT NULL,
  `sizes` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `icons`
--

LOCK TABLES `icons` WRITE;
/*!40000 ALTER TABLE `icons` DISABLE KEYS */;
INSERT INTO `icons` VALUES (1,'icons/business_service.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(2,'icons/business_process.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(3,'icons/it_service.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(4,'icons/account.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(5,'icons/msc_account.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(6,'icons/msu_account.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(7,'icons/msp_account.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(8,'icons/department.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(9,'icons/people.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(10,'icons/requester.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(11,'icons/technician.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(12,'icons/support_group.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(13,'icons/document.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(14,'icons/server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(15,'icons/app_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(16,'icons/db_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(17,'icons/file_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(18,'icons/storage_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(19,'icons/ibm_mainframe.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(20,'icons/windows_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(21,'icons/linux_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(22,'icons/unix_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(23,'icons/aix_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(24,'icons/hpux_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(25,'icons/free_bsd_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(26,'icons/mac_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(27,'icons/netware_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(28,'icons/virtualization_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(29,'icons/hyper_v_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(30,'icons/esx_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(31,'icons/workstation.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(32,'icons/ibm_workstation.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(33,'icons/windows_workstation.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(34,'icons/unix_workstation.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(35,'icons/aix_workstation.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(36,'icons/hpux_workstation.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(37,'icons/linux_workstation.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(38,'icons/mac_workstation.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(39,'icons/solaris_workstation.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(40,'icons/software.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(41,'icons/application.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(42,'icons/web_service.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(43,'icons/rest_service.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(44,'icons/web_site.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(45,'icons/web_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(46,'icons/apache.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(47,'icons/iis.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(48,'icons/nginx.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(49,'icons/app_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(50,'icons/weblogic.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(51,'icons/websphere.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(52,'icons/domino.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(53,'icons/jboss.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(54,'icons/tomcat.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(55,'icons/jetty.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(56,'icons/database.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(57,'icons/db2.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(58,'icons/mssql.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(59,'icons/mysql.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(60,'icons/oracle.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(61,'icons/sybase.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(62,'icons/infrastructure_service.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(63,'icons/directory_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(64,'icons/email_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(65,'icons/ftp_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(66,'icons/ldap_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(67,'icons/vm.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(68,'icons/kvm.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(69,'icons/parallels.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(70,'icons/vmware.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(71,'icons/citrix.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(72,'icons/vm_object.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(73,'icons/hyper_v_vm_object.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(74,'icons/kvm_vm_object.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(75,'icons/vm_instance.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(76,'icons/ec2_vm_instance.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(77,'icons/hyper_v_vm_instance.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(78,'icons/kvm_vm_instance.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(79,'icons/solaris_vm_instance.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(80,'icons/vmware_vm_instance.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(81,'icons/vm_template.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(82,'icons/vmware_vm_template.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(83,'icons/vcenter_object.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(84,'icons/esx_resource_pool.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(85,'icons/vcenter_cluster.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(86,'icons/vcenter_data_center.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(87,'icons/vcenter_data_store.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(88,'icons/vcenter_folder.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(89,'icons/vcenter_network.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(90,'icons/vcenter.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(91,'icons/db_schema.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(92,'icons/db2_schema.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(93,'icons/mssql_schema.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(94,'icons/mysql_schema.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(95,'icons/oracle_schema.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(96,'icons/sybase_schema.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(97,'icons/file_system.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(98,'icons/nfs_file_system.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(99,'icons/smb_file_system.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(101,'icons/ip_service_instance.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(102,'icons/unix_daemon.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(103,'icons/windows_service.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(104,'icons/storage.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(105,'icons/storage_hba.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(106,'icons/storage_pool.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(107,'icons/storage_port.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(108,'icons/storage_volume.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(109,'icons/storage_file_share.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(110,'icons/cluster.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(111,'icons/cluster_node.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(112,'icons/cluster_resource.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(113,'icons/cluster_virtual_ip.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(114,'icons/lb_server.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(115,'icons/lb_interface.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(116,'icons/lb_pool.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(117,'icons/lb_pool_member.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(118,'icons/lb_service.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(119,'icons/lb_vlan.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(120,'icons/data_center.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(121,'icons/data_center_zone.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(122,'icons/computer_room.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(123,'icons/rack.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(124,'icons/ups.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(125,'icons/ups_input.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(126,'icons/ups_output.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(127,'icons/ups_alarm.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(128,'icons/ups_bypass.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(129,'icons/network.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(130,'icons/firewall.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(131,'icons/network_gear.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(132,'icons/router.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(133,'icons/switch.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(134,'icons/switch_port.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(135,'icons/ip_phone.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(136,'icons/ip_address.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(137,'icons/dns_name.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(138,'icons/ip_device.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(139,'icons/vpn.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(140,'icons/access_point.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(141,'icons/accessory.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(142,'icons/printer.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(143,'icons/smart_phone.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(144,'icons/tablet.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(145,'icons/peripheral.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(146,'icons/projector.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(147,'icons/keyboard.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(148,'icons/scanner.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(149,'icons/disk.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16'),(150,'icons/network_adapter.png','[16x16,24x24,32x32]','2014-09-25 00:47:16','2014-09-25 00:47:16');
/*!40000 ALTER TABLE `icons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itsnow_hosts`
--

DROP TABLE IF EXISTS `itsnow_hosts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itsnow_hosts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `capacity` int(10) unsigned NOT NULL DEFAULT '20',
  `status` varchar(50) DEFAULT 'Planing',
  `configuration` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `address` (`address`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itsnow_hosts`
--

LOCK TABLES `itsnow_hosts` WRITE;
/*!40000 ALTER TABLE `itsnow_hosts` DISABLE KEYS */;
INSERT INTO `itsnow_hosts` VALUES (1,'srv1.itsnow.com','172.16.3.3',1,'Running','{\"mem\": \"8g\", \"cpu\" : \"4x2533Mhz\", \"disk\": \"100G\"}','The default host only run MSC, master mysql, redis, nginx','2014-09-25 00:47:16','2014-09-25 00:47:16'),(2,'MSU/P Host A','172.16.3.4',20,'Running','{\"mem\": \"8g\", \"cpu\" : \"4x2533Mhz\", \"disk\": \"100G\"}','The host runs MSU/MSP and mysql slave','2014-09-25 00:47:16','2014-09-25 00:47:16');
/*!40000 ALTER TABLE `itsnow_hosts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itsnow_processes`
--

DROP TABLE IF EXISTS `itsnow_processes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itsnow_processes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account_id` int(10) unsigned NOT NULL,
  `host_id` int(10) unsigned NOT NULL,
  `schema_id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `pid` int(10) unsigned DEFAULT NULL,
  `wd` varchar(255) NOT NULL,
  `configuration` varchar(255) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'Stopped',
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `account_id` (`account_id`),
  KEY `host_id` (`host_id`),
  KEY `schema_id` (`schema_id`),
  CONSTRAINT `itsnow_processes_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`),
  CONSTRAINT `itsnow_processes_ibfk_2` FOREIGN KEY (`host_id`) REFERENCES `itsnow_hosts` (`id`),
  CONSTRAINT `itsnow_processes_ibfk_3` FOREIGN KEY (`schema_id`) REFERENCES `itsnow_schemas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itsnow_processes`
--

LOCK TABLES `itsnow_processes` WRITE;
/*!40000 ALTER TABLE `itsnow_processes` DISABLE KEYS */;
INSERT INTO `itsnow_processes` VALUES (1,1,1,1,'itsnow-msc',NULL,'/opt/releases/itsnow/msc','{\"http.port\": \"8071\", \"jmx.port\": \"1072\", \"debug.port\" : \"1071\", \"rmi.port\" : \"1073\"}','Running','The MSC process','2014-09-25 00:47:17','2014-09-25 00:47:17');
/*!40000 ALTER TABLE `itsnow_processes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itsnow_schemas`
--

DROP TABLE IF EXISTS `itsnow_schemas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itsnow_schemas` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `host_id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `configuration` text,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `host_id` (`host_id`),
  CONSTRAINT `itsnow_schemas_ibfk_1` FOREIGN KEY (`host_id`) REFERENCES `itsnow_hosts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itsnow_schemas`
--

LOCK TABLES `itsnow_schemas` WRITE;
/*!40000 ALTER TABLE `itsnow_schemas` DISABLE KEYS */;
INSERT INTO `itsnow_schemas` VALUES (1,1,'itsnow_msc','{\"user\": \"root\", \"password\": \"secret\"}','The MSC schema','2014-09-25 00:47:16','2014-09-25 00:47:16');
/*!40000 ALTER TABLE `itsnow_schemas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_items`
--

DROP TABLE IF EXISTS `menu_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_items` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) unsigned DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `css` varchar(100) DEFAULT NULL,
  `position` int(10) unsigned DEFAULT NULL,
  `shortcut` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `state` (`state`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_items`
--

LOCK TABLES `menu_items` WRITE;
/*!40000 ALTER TABLE `menu_items` DISABLE KEYS */;
INSERT INTO `menu_items` VALUES (1,NULL,'????','accounts','icon-globe',0,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(2,NULL,'????','contracts','icon-th-list',1,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(3,NULL,'????','services','icon-tag',2,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(4,NULL,'????','system','icon-tag',3,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(11,1,'????','accounts.msu','icon-tag',0,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(12,1,'?????','accounts.msp','icon-tag',1,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(13,1,'????','accounts.service','icon-play',2,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(21,2,'????','contracts.contract','icon-tag',3,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(31,3,'????','services.catalog','icon-tag',0,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(32,3,'SLA??','services.sla','icon-eye-open',1,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(41,4,'????','system.role','icon-group',0,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(42,4,'????','system.user','icon-user',1,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16'),(43,4,'????','system.privilege','icon-authenticate',2,NULL,NULL,'2014-09-25 00:47:16','2014-09-25 00:47:16');
/*!40000 ALTER TABLE `menu_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_persistent_logins_serial` (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_dictionaries`
--

DROP TABLE IF EXISTS `process_dictionaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_dictionaries` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `level` varchar(255) NOT NULL,
  `level_name` varchar(255) NOT NULL,
  `state` varchar(1) NOT NULL,
  `type` varchar(1) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_dictionaries`
--

LOCK TABLES `process_dictionaries` WRITE;
/*!40000 ALTER TABLE `process_dictionaries` DISABLE KEYS */;
INSERT INTO `process_dictionaries` VALUES (1,'inc001','????','high','?','1','1',NULL,'2014-09-25 00:47:17','2014-09-25 00:47:17'),(2,'inc002','????','middle','?','1','1',NULL,'2014-09-25 00:47:17','2014-09-25 00:47:17'),(3,'inc003','????','low','?','1','1',NULL,'2014-09-25 00:47:17','2014-09-25 00:47:17'),(4,'inc004','????','high','?','1','1',NULL,'2014-09-25 00:47:17','2014-09-25 00:47:17'),(5,'inc005','????','middle','?','1','1',NULL,'2014-09-25 00:47:17','2014-09-25 00:47:17'),(6,'inc006','????','low','?','1','1',NULL,'2014-09-25 00:47:17','2014-09-25 00:47:17'),(7,'inc007','??','normal','??','1','1',NULL,'2014-09-25 00:47:17','2014-09-25 00:47:17'),(8,'inc008','??','normal','??','1','1',NULL,'2014-09-25 00:47:17','2014-09-25 00:47:17');
/*!40000 ALTER TABLE `process_dictionaries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `public_service_catalogs`
--

DROP TABLE IF EXISTS `public_service_catalogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `public_service_catalogs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) unsigned DEFAULT NULL,
  `sn` varchar(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` tinytext,
  `icon` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `public_service_catalogs_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `public_service_catalogs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `public_service_catalogs`
--

LOCK TABLES `public_service_catalogs` WRITE;
/*!40000 ALTER TABLE `public_service_catalogs` DISABLE KEYS */;
INSERT INTO `public_service_catalogs` VALUES (1,NULL,'SC_100','Hardware','Services related to hardware purchase, upgrading RAM, requesting a laptop or a desktop','/assets/sc/hardware.png','2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,NULL,'SC_200','Internet','Services related to internet access, WiFi access, creating a VPN account, booking a domain','/assets/sc/internet.png','2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,NULL,'SC_300','Software','Services related to software installation/un-installation, software purchase, license ','/assets/sc/software.png','2014-09-25 00:47:15','2014-09-25 00:47:15'),(4,NULL,'SC_400','User Accounts','Services related to hiring a new employee, requesting a department/place change','/assets/sc/user-accounts.png','2014-09-25 00:47:15','2014-09-25 00:47:15'),(5,NULL,'SC_500','Application Login','Services related to application login like creating CRM account, SAP account, MSSQL account','/assets/sc/application-login.png','2014-09-25 00:47:15','2014-09-25 00:47:15'),(6,1,'SC_101','Desktop','Services related to desktop computer','/assets/sc/desktop.png','2014-09-25 00:47:15','2014-09-25 00:47:15'),(7,1,'SC_102','Laptop','Services related to laptop','/assets/sc/laptop.png','2014-09-25 00:47:15','2014-09-25 00:47:15'),(8,1,'SC_103','Printer','Services related to printer','/assets/sc/printer.png','2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `public_service_catalogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `public_service_items`
--

DROP TABLE IF EXISTS `public_service_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `public_service_items` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `catalog_id` int(10) unsigned NOT NULL,
  `title` varchar(255) NOT NULL,
  `brief` varchar(255) DEFAULT NULL,
  `description` tinytext,
  `icon` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `catalog_id` (`catalog_id`),
  CONSTRAINT `public_service_items_ibfk_1` FOREIGN KEY (`catalog_id`) REFERENCES `public_service_catalogs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `public_service_items`
--

LOCK TABLES `public_service_items` WRITE;
/*!40000 ALTER TABLE `public_service_items` DISABLE KEYS */;
INSERT INTO `public_service_items` VALUES (1,1,'General Hardware Problem','General','General problems which can not be concluded into desktop/laptop/printer','/assets/si/hardware_general.png','2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,6,'Desktop Clean','Clean Desktop','Clean the desktop drivers',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,7,'Laptop Maintain','Maintain laptop','Maintain the laptop',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(4,6,'Laptop Env configure','Env configure','Configure the laptop',NULL,'2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `public_service_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN',NULL,'2014-09-25 00:47:13','2014-09-25 00:47:13'),(2,'ROLE_USER',NULL,'2014-09-25 00:47:13','2014-09-25 00:47:13'),(3,'ROLE_ITER',NULL,'2014-09-25 00:47:13','2014-09-25 00:47:13'),(4,'ROLE_SERVICE_DESK',NULL,'2014-09-25 00:47:13','2014-09-25 00:47:13'),(5,'ROLE_LINE_ONE',NULL,'2014-09-25 00:47:13','2014-09-25 00:47:13'),(6,'ROLE_LINE_TWO',NULL,'2014-09-25 00:47:13','2014-09-25 00:47:13'),(7,'ROLE_MONITOR',NULL,'2014-09-25 00:47:13','2014-09-25 00:47:13'),(8,'ROLE_REPORTER',NULL,'2014-09-25 00:47:13','2014-09-25 00:47:13'),(9,'ROLE_GUEST',NULL,'2014-09-25 00:47:13','2014-09-25 00:47:13');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slas`
--

DROP TABLE IF EXISTS `slas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slas` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` tinytext,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slas`
--

LOCK TABLES `slas` WRITE;
/*!40000 ALTER TABLE `slas` DISABLE KEYS */;
INSERT INTO `slas` VALUES (1,'SLA-001','THE FIRST  DEMO SLA','2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,'SLA-002','THE SECOND DEMO SLA','2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,'SLA-003','THE THIRD  DEMO SLA','2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `slas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slos`
--

DROP TABLE IF EXISTS `slos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sla_id` int(10) unsigned NOT NULL,
  `title` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `sla_id` (`sla_id`),
  CONSTRAINT `slos_ibfk_1` FOREIGN KEY (`sla_id`) REFERENCES `slas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slos`
--

LOCK TABLES `slos` WRITE;
/*!40000 ALTER TABLE `slos` DISABLE KEYS */;
INSERT INTO `slos` VALUES (1,1,'SLO-01 of first SLA','2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,1,'SLO-02 of first SLA','2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,2,'SLO-01 of second SLA','2014-09-25 00:47:15','2014-09-25 00:47:15'),(4,2,'SLO-02 of second SLA','2014-09-25 00:47:15','2014-09-25 00:47:15'),(5,3,'SLO-01 of third SLA','2014-09-25 00:47:15','2014-09-25 00:47:15'),(6,3,'SLO-02 of third SLA','2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `slos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account_id` int(20) unsigned DEFAULT NULL,
  `username` varchar(25) NOT NULL,
  `nick_name` varchar(25) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `expired` tinyint(1) NOT NULL DEFAULT '0',
  `locked` tinyint(1) NOT NULL DEFAULT '0',
  `password_expired` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'admin','Administrator','admin@itsnow.com','13012345678','54442704733e9cddaeb28d54fb631f56247d326288d9d4e09da2039dff070bd47430a0e6b3560cd2',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(2,1,'root','Super Admin','root@itsnow.com','13112345678','e81a8a2dc76c227258a1bd5551f3d3f45e21deeec634c052dc4e7acd82c0dd92db674b56d22dd637',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(3,2,'steve.li','Steven Li','steve@csvw.com','13212345678','d73e6f76127849f457d17eb663f3f9605c89d1b9d596a5842e13bbcb56e658bbaceb07b8da9886b8',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(4,2,'jason.wang','Jason Wang','jason@csvw.com','13312345678','cf145bec215522df207511897ee8c581db56ff33c59ad2e2367216c19e84036a7dfa107740f344c5',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(5,4,'stone.xin','Stone Xin','stone@dnt.com.cn','13412345678','6d5478d48e305f2d83ee64bb19387fa538f338b2b461a1ef0f8d257a1570d159901a84d3875797f3',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(6,4,'jacky.cao','Jacky Cao','jacky@dnt.com.cn','13512345678','b80b1ebe266d9f52af07283a7819edda2673cca6a0f9cda9c05d8d31864d5d9793796fadc92f96e4',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(7,4,'smile.tian','Smile Tian','smile@dnt.com.cn','13612345678','aa4e6ddfb9544727dfc68f58f86f052e4c7d34f3307b0c7fe6fbc1c9d56bc677eb87aa3165a72c2d',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(8,3,'sharp.liu','Sharp Liu','sharp@gm.com','13712345678','86b6132dbf5ebf9eb9227d259626a527f4caf5a92d4bdd6db182306dbbd61c601954028d1184ff3c',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(9,3,'mike.wei','Mile Wei','mike@gm.com','13812345678','f8d016511819edf96fb6dff7e9f012d9f9556f60e756793080e5ff7fa8ea02369aedcc3010c05465',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(10,5,'rose.zhou','Rose Zhou','rose@teamsun.com','13912345678','7e579a86efe48d0e3d83a757cee256aef2e70929cbc7ba8eccc9c82469878510860a4c41a9ac650b',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(11,5,'susie.qian','Susie Qian','susie@teamsun.com','18912345678','d0073ac092e2252c264ffd08132f9331965b88c5150cc2b50739e7e2403d3d2336fb7784859a4434',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15'),(12,NULL,'jay.xiong','Jay Xiong','jay@kadvin.com','18612345678','b078a7f871a1de97972bb1441eded5e26930f024d19335b8c2e93f14e50488560057823fc9dbdc2c',1,0,0,0,'2014-09-25 00:47:15','2014-09-25 00:47:15');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-25  8:47:45
