/*
 Navicat MySQL Data Transfer

 Source Server         : Java EE Assignment
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : onlinestock

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 15/12/2018 03:15:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stocknum` int(10) UNSIGNED NOT NULL,
  `price` double(10, 2) NOT NULL,
  `cname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, 'P1', 96, 10.00, '食品');
INSERT INTO `products` VALUES (2, 'P2', 63, 22.00, '食品');
INSERT INTO `products` VALUES (3, 'P3', 89, 20.00, '药物');
INSERT INTO `products` VALUES (4, 'P4', 222, 12.00, '药物');
INSERT INTO `products` VALUES (5, 'P5', 234, 45.00, '药物');
INSERT INTO `products` VALUES (6, 'P6', 467, 45.00, '药物');
INSERT INTO `products` VALUES (7, 'P7', 120, 24.00, '食品');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `uid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deposit` double(10, 2) NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'Fortune', 'root', 9944.00);

SET FOREIGN_KEY_CHECKS = 1;
