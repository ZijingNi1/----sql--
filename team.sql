/*
Navicat MySQL Data Transfer

Source Server         : con_register
Source Server Version : 80035
Source Host           : localhost:3306
Source Database       : team

Target Server Type    : MYSQL
Target Server Version : 80035
File Encoding         : 65001

Date: 2023-12-26 11:14:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `账号` char(10) NOT NULL,
  `姓名` char(10) DEFAULT NULL,
  `职位` enum('教师','社长','副社长','社员') DEFAULT NULL,
  PRIMARY KEY (`账号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('1', '王勤颖', '教师');
INSERT INTO `member` VALUES ('2', '倪梓敬', '社长');
INSERT INTO `member` VALUES ('3', '张三', '副社长');
INSERT INTO `member` VALUES ('4', '李四', '副社长');
INSERT INTO `member` VALUES ('5', '王五', '社员');
INSERT INTO `member` VALUES ('6', '赵六', '社员');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `发布者` char(10) DEFAULT NULL,
  `公告内容` char(30) NOT NULL,
  PRIMARY KEY (`公告内容`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('倪梓敬', '1');
INSERT INTO `notice` VALUES ('张三', '11111');
INSERT INTO `notice` VALUES ('张三', '123');
INSERT INTO `notice` VALUES ('倪梓敬', '哈哈哈');
INSERT INTO `notice` VALUES ('倪梓敬', '堆雪人');

-- ----------------------------
-- Table structure for password
-- ----------------------------
DROP TABLE IF EXISTS `password`;
CREATE TABLE `password` (
  `账号` char(10) NOT NULL,
  `密码` char(10) DEFAULT NULL,
  PRIMARY KEY (`账号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of password
-- ----------------------------
INSERT INTO `password` VALUES ('1', '1');
INSERT INTO `password` VALUES ('2', '1');
INSERT INTO `password` VALUES ('3', '123456');
INSERT INTO `password` VALUES ('4', '123456');
INSERT INTO `password` VALUES ('5', '123456');
INSERT INTO `password` VALUES ('6', '111');

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `社团名称` char(10) NOT NULL,
  `指导老师` char(10) DEFAULT NULL,
  `社长` char(10) DEFAULT NULL,
  PRIMARY KEY (`社团名称`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES ('好社团', '王勤颖', '倪梓敬');
