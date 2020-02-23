-- MySQL dump 10.13  Distrib 8.0.17, for osx10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: ccloud
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `business_app_base_config`
--

DROP TABLE IF EXISTS `business_app_base_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_app_base_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '应用id',
  `business_user_id` int NOT NULL DEFAULT '0' COMMENT '应用所属B端用户ID',
  `app_name` varchar(255) NOT NULL DEFAULT '' COMMENT '应用名称',
  `app_pack` varchar(255) NOT NULL DEFAULT '' COMMENT '应用包名',
  `app_icon` varchar(255) NOT NULL DEFAULT '' COMMENT '应用图标',
  `app_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '应用描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `app_status` int DEFAULT '0' COMMENT '应用状态\n0.正常\n1.禁用',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态\n0.正常\n1.删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='B端应用基本信息配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_app_base_config`
--

LOCK TABLES `business_app_base_config` WRITE;
/*!40000 ALTER TABLE `business_app_base_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_app_base_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_config`
--

DROP TABLE IF EXISTS `business_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `config_code` varchar(255) NOT NULL DEFAULT '' COMMENT '配置编码',
  `config_default_value` varchar(255) NOT NULL DEFAULT '' COMMENT '配置默认值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int NOT NULL DEFAULT '0' COMMENT '状态\n0.正常\n1.删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='B端基本配置表\n配置一下Bduan 用户的设置信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_config`
--

LOCK TABLES `business_config` WRITE;
/*!40000 ALTER TABLE `business_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_notice_base_config`
--

DROP TABLE IF EXISTS `business_notice_base_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_notice_base_config` (
  `id` int NOT NULL DEFAULT '0' COMMENT '公告ID',
  `app_id` int NOT NULL DEFAULT '0' COMMENT '所属appid',
  `notice_title` varchar(255) NOT NULL DEFAULT '' COMMENT '公告标题',
  `notice_info` varchar(999) NOT NULL DEFAULT '' COMMENT '公告内容',
  `notice_icon` varchar(255) NOT NULL DEFAULT '' COMMENT '公告图标',
  `notice_release_start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '公告开始时间',
  `notice_release_end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '公告截至时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '公告截至时间',
  `status` int DEFAULT '0' COMMENT '状态\n0.正常\n1.删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `business_notice_base_config_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_notice_base_config`
--

LOCK TABLES `business_notice_base_config` WRITE;
/*!40000 ALTER TABLE `business_notice_base_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_notice_base_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_resource`
--

DROP TABLE IF EXISTS `business_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_resource` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `resource_code` varchar(255) NOT NULL DEFAULT '' COMMENT '资源编码',
  `resource_name` varchar(255) NOT NULL DEFAULT '' COMMENT '资源名称',
  `resource_desc` varchar(255) DEFAULT '' COMMENT '资源描述',
  `resource_type` int NOT NULL DEFAULT '0' COMMENT '资源类型\n\n0.分组\n\n1.菜单\n\n2.按钮\n\n3.接口',
  `resource_pid` int DEFAULT '0' COMMENT '资源父id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态\n0.正常\n1.删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `business_resource_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户基础资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_resource`
--

LOCK TABLES `business_resource` WRITE;
/*!40000 ALTER TABLE `business_resource` DISABLE KEYS */;
INSERT INTO `business_resource` VALUES (1,'user:currUser','当前用户查看权限','当前用户查看权限',1,0,'2020-02-22 13:21:37','2020-02-22 13:21:37',0);
/*!40000 ALTER TABLE `business_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_role`
--

DROP TABLE IF EXISTS `business_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_code` varchar(255) NOT NULL DEFAULT '' COMMENT '角色编码',
  `role_name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '角色说明',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` varchar(255) NOT NULL DEFAULT '' COMMENT '更新时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态\n0.正常\n1.删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `business_role_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户基础角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_role`
--

LOCK TABLES `business_role` WRITE;
/*!40000 ALTER TABLE `business_role` DISABLE KEYS */;
INSERT INTO `business_role` VALUES (1,'admin','管理员','最高权限','2020-02-22 13:20:46','',0);
/*!40000 ALTER TABLE `business_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_role_resource`
--

DROP TABLE IF EXISTS `business_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_role_resource` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id\n',
  `role_id` int NOT NULL DEFAULT '0' COMMENT '角色id',
  `resource_id` int NOT NULL DEFAULT '0' COMMENT '资源id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态\n0.正常\n1.删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `business_role_resource_resource_id_uindex` (`resource_id`),
  UNIQUE KEY `business_role_resource_role_id_uindex` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色资源对应表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_role_resource`
--

LOCK TABLES `business_role_resource` WRITE;
/*!40000 ALTER TABLE `business_role_resource` DISABLE KEYS */;
INSERT INTO `business_role_resource` VALUES (1,1,1,'2020-02-22 13:21:49','2020-02-22 13:21:49',0);
/*!40000 ALTER TABLE `business_role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_user`
--

DROP TABLE IF EXISTS `business_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `mobile` varchar(11) DEFAULT '0' COMMENT '手机号',
  `email` varchar(255) DEFAULT '' COMMENT '邮箱',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态\n0.正常\n1.删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `business_user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户基础信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_user`
--

LOCK TABLES `business_user` WRITE;
/*!40000 ALTER TABLE `business_user` DISABLE KEYS */;
INSERT INTO `business_user` VALUES (1,'wangjie','812e01832533d2cd4c1383951ee366ca1','13373381619','345127857@qq.com','2020-02-21 05:08:48','2020-02-21 05:08:48',0),(2,'wangji','1','0','','2020-02-21 05:11:08','2020-02-21 05:11:08',0),(3,'admin','1','0','','2020-02-21 13:11:37','2020-02-21 13:11:37',0);
/*!40000 ALTER TABLE `business_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_user_config`
--

DROP TABLE IF EXISTS `business_user_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_user_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_id` int NOT NULL COMMENT '配置Id',
  `config_value` varchar(255) NOT NULL DEFAULT '' COMMENT '配置值',
  `user_id` int NOT NULL DEFAULT '0' COMMENT '用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int DEFAULT '0' COMMENT '状态\n0.正常\n1.删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_user_config`
--

LOCK TABLES `business_user_config` WRITE;
/*!40000 ALTER TABLE `business_user_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_user_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_user_role`
--

DROP TABLE IF EXISTS `business_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_user_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` int NOT NULL DEFAULT '0' COMMENT '角色id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态:\n0:正常\n1:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色对应表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_user_role`
--

LOCK TABLES `business_user_role` WRITE;
/*!40000 ALTER TABLE `business_user_role` DISABLE KEYS */;
INSERT INTO `business_user_role` VALUES (1,1,1,'2020-02-22 13:21:57','2020-02-22 13:21:57',0);
/*!40000 ALTER TABLE `business_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-22 16:17:56
