/*
Navicat MySQL Data Transfer

Source Server         : zzz
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2018-06-12 17:53:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address
-- ----------------------------

-- ----------------------------
-- Table structure for adminuser
-- ----------------------------
DROP TABLE IF EXISTS `adminuser`;
CREATE TABLE `adminuser` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of adminuser
-- ----------------------------
INSERT INTO `adminuser` VALUES ('2', 'admin1', 'admin', null, null, null);
INSERT INTO `adminuser` VALUES ('3', 'admin2', 'admin', null, null, null);
INSERT INTO `adminuser` VALUES ('4', 'admin3', 'admin', null, null, null);
INSERT INTO `adminuser` VALUES ('5', 'admin4', 'admin', null, null, null);
INSERT INTO `adminuser` VALUES ('6', 'admin5', 'admin', null, null, null);
INSERT INTO `adminuser` VALUES ('7', 'admin6', 'admin', null, null, null);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '萌宠狗狗');
INSERT INTO `category` VALUES ('2', '可爱猫咪');
INSERT INTO `category` VALUES ('3', '迷你小宠');
INSERT INTO `category` VALUES ('4', '海底水族');
INSERT INTO `category` VALUES ('5', '陆地爬虫');
INSERT INTO `category` VALUES ('6', '天空飞鸟');
INSERT INTO `category` VALUES ('7', '日常用具');

-- ----------------------------
-- Table structure for categorysecond
-- ----------------------------
DROP TABLE IF EXISTS `categorysecond`;
CREATE TABLE `categorysecond` (
  `csid` int(11) NOT NULL AUTO_INCREMENT,
  `csname` varchar(255) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`csid`),
  KEY `FK936FCAF21DB1FD15` (`cid`),
  CONSTRAINT `FK936FCAF21DB1FD15` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of categorysecond
-- ----------------------------
INSERT INTO `categorysecond` VALUES ('1', '哈士奇', '1');
INSERT INTO `categorysecond` VALUES ('2', '萨摩', '1');
INSERT INTO `categorysecond` VALUES ('3', '贵宾犬', '1');
INSERT INTO `categorysecond` VALUES ('4', '比熊犬', '1');
INSERT INTO `categorysecond` VALUES ('5', '金毛', '1');
INSERT INTO `categorysecond` VALUES ('6', '京巴犬', '1');
INSERT INTO `categorysecond` VALUES ('7', '雪纳瑞', '1');
INSERT INTO `categorysecond` VALUES ('9', '布偶猫', '2');
INSERT INTO `categorysecond` VALUES ('10', '短尾猫', '2');
INSERT INTO `categorysecond` VALUES ('11', '加菲猫', '2');
INSERT INTO `categorysecond` VALUES ('12', '缅因猫', '2');
INSERT INTO `categorysecond` VALUES ('13', '波斯猫', '2');
INSERT INTO `categorysecond` VALUES ('14', '东方猫', '2');
INSERT INTO `categorysecond` VALUES ('15', '仓鼠', '3');
INSERT INTO `categorysecond` VALUES ('16', '兔子', '3');
INSERT INTO `categorysecond` VALUES ('17', '蛇', '3');
INSERT INTO `categorysecond` VALUES ('18', '蝎子', '3');
INSERT INTO `categorysecond` VALUES ('19', '荷兰猪', '3');
INSERT INTO `categorysecond` VALUES ('20', '鲸鱼', '4');
INSERT INTO `categorysecond` VALUES ('21', '鲨鱼', '4');
INSERT INTO `categorysecond` VALUES ('22', '乌龟', '4');
INSERT INTO `categorysecond` VALUES ('23', '水母', '4');
INSERT INTO `categorysecond` VALUES ('24', '蛇', '5');
INSERT INTO `categorysecond` VALUES ('25', '甲壳虫', '5');
INSERT INTO `categorysecond` VALUES ('26', '牛羊', '5');
INSERT INTO `categorysecond` VALUES ('27', '陆地蟹', '5');
INSERT INTO `categorysecond` VALUES ('28', '家电', '6');
INSERT INTO `categorysecond` VALUES ('29', '厨房电器', '6');
INSERT INTO `categorysecond` VALUES ('30', '生活电器', '6');
INSERT INTO `categorysecond` VALUES ('31', '个户电器', '6');
INSERT INTO `categorysecond` VALUES ('33', '美容护肤', '7');
INSERT INTO `categorysecond` VALUES ('34', '强效保养', '7');
INSERT INTO `categorysecond` VALUES ('35', '超值彩妆', '7');
INSERT INTO `categorysecond` VALUES ('36', '换季保养', '7');
INSERT INTO `categorysecond` VALUES ('40', '狗粮', null);

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem` (
  `itemid` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `oid` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  KEY `FKE8B2AB6166C01961` (`oid`),
  KEY `FKE8B2AB6171DB7AE4` (`pid`),
  KEY `FKE8B2AB6140ACF87A` (`oid`),
  CONSTRAINT `FKE8B2AB6140ACF87A` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`),
  CONSTRAINT `FKE8B2AB6171DB7AE4` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('2', '1', '198', '73', '2000');
INSERT INTO `orderitem` VALUES ('3', '1', '172', '2', '2000');
INSERT INTO `orderitem` VALUES ('4', '1', '358', '51', '3000');
INSERT INTO `orderitem` VALUES ('5', '1', '590', '7', '4000');
INSERT INTO `orderitem` VALUES ('6', '1', '172', '2', '5000');
INSERT INTO `orderitem` VALUES ('7', '1', '228', '1', '5000');
INSERT INTO `orderitem` VALUES ('8', '1', '119', '5', '5000');
INSERT INTO `orderitem` VALUES ('9', '1', '125', '75', '6000');
INSERT INTO `orderitem` VALUES ('10', '1', '83', '72', '7000');
INSERT INTO `orderitem` VALUES ('11', '1', '358', '51', '8000');
INSERT INTO `orderitem` VALUES ('12', '1', '83', '68', '9000');
INSERT INTO `orderitem` VALUES ('13', '1', '83', '66', '9001');
INSERT INTO `orderitem` VALUES ('14', '1', '172', '2', '9001');
INSERT INTO `orderitem` VALUES ('15', '1', '228', '1', '9002');
INSERT INTO `orderitem` VALUES ('16', '1', '5099', '79', '9003');
INSERT INTO `orderitem` VALUES ('17', '1', '644', '73', '9004');
INSERT INTO `orderitem` VALUES ('18', '1', '455', '74', '9005');
INSERT INTO `orderitem` VALUES ('19', '3', '399', '75', '9006');
INSERT INTO `orderitem` VALUES ('20', '1', '119', '5', '9007');
INSERT INTO `orderitem` VALUES ('21', '5', '12440', '79', '9008');
INSERT INTO `orderitem` VALUES ('22', '1', '644', '73', '9009');
INSERT INTO `orderitem` VALUES ('23', '1', '228', '1', '9010');
INSERT INTO `orderitem` VALUES ('24', '1', '228', '1', '9011');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `total` double DEFAULT NULL,
  `ordertime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `addr` varchar(50) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `FKC3DF62E5AA3D9C7` (`uid`),
  CONSTRAINT `FKC3DF62E5AA3D9C7` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=9012 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('2000', '370', '2015-02-11 10:04:23', '1', null, null, null, null);
INSERT INTO `orders` VALUES ('3000', '358', '2015-02-11 10:46:41', '1', null, null, null, null);
INSERT INTO `orders` VALUES ('4000', '590', '2015-02-11 10:46:47', '1', null, null, null, null);
INSERT INTO `orders` VALUES ('5000', '519', '2015-02-11 10:47:01', '1', null, null, null, null);
INSERT INTO `orders` VALUES ('6000', '125', '2015-02-11 10:47:07', '1', null, null, null, null);
INSERT INTO `orders` VALUES ('7000', '83', '2015-02-11 10:47:15', '1', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', null);
INSERT INTO `orders` VALUES ('8000', '358', '2015-02-11 11:17:22', '2', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', null);
INSERT INTO `orders` VALUES ('9000', '83', '2015-02-11 11:23:26', '2', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', null);
INSERT INTO `orders` VALUES ('9001', '255', '2015-02-23 21:31:19', '1', null, null, null, null);
INSERT INTO `orders` VALUES ('9002', '228', '2015-02-27 09:07:55', '2', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', null);
INSERT INTO `orders` VALUES ('9003', '5099', '2015-02-27 15:35:53', '4', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', null);
INSERT INTO `orders` VALUES ('9004', '644', '2018-03-15 14:37:27', '2', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', null);
INSERT INTO `orders` VALUES ('9005', '455', '2018-03-19 16:22:48', '4', '??', '15726607618', '??????????3?', null);
INSERT INTO `orders` VALUES ('9006', '399', '2018-03-19 16:23:57', '3', '??', '15726607618', '???????s????', null);
INSERT INTO `orders` VALUES ('9007', '119', '2018-03-22 20:09:25', '3', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', null);
INSERT INTO `orders` VALUES ('9008', '12440', '2018-04-12 16:47:39', '2', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', null);
INSERT INTO `orders` VALUES ('9009', '644', '2018-04-12 18:40:46', '2', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', null);
INSERT INTO `orders` VALUES ('9010', '228', '2018-05-18 01:22:48', '1', null, null, null, '7');
INSERT INTO `orders` VALUES ('9011', '228', '2018-05-18 14:26:37', '2', '姜涛', '15726607618', '北京市西三旗中腾建华3楼', '7');

-- ----------------------------
-- Table structure for pjsp
-- ----------------------------
DROP TABLE IF EXISTS `pjsp`;
CREATE TABLE `pjsp` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `spid` varchar(255) DEFAULT NULL,
  `spname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pjsp
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(255) DEFAULT NULL,
  `market_price` double DEFAULT NULL,
  `shop_price` double DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `pdesc` varchar(255) DEFAULT NULL,
  `is_hot` int(11) DEFAULT NULL,
  `pdate` datetime DEFAULT NULL,
  `csid` int(11) DEFAULT NULL,
  `shuliang` int(11) DEFAULT NULL,
  `paynumber` int(11) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `FKED8DCCEFB9B74E02` (`csid`),
  CONSTRAINT `FKED8DCCEFB9B74E02` FOREIGN KEY (`csid`) REFERENCES `categorysecond` (`csid`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '1号萌狗宠哈士奇1号', '558', '228', 'products/1600.jpg', '伯利亚雪橇犬（俄语：Сибирский хаски，英语：Siberian husky），常见别名哈士奇，昵称为二哈。西伯利亚雪橇犬体重介于雄犬20-27公斤，雌犬16-23公斤，身高大约雄犬肩高53-58厘米，雌犬51-56厘米，是一种中型犬。西伯利亚雪橇犬是原始的古老犬种，在西伯利亚东北部、格陵兰南部生活。', '1', '2018-04-28 16:30:06', '1', null, null);
INSERT INTO `product` VALUES ('2', '哈士奇2号萌宠', '436', '172', 'products/1300.jpg', '伯利亚雪橇犬（俄语：Сибирский хаски，英语：Siberian husky），常见别名哈士奇，昵称为二哈。西伯利亚雪橇犬体重介于雄犬20-27公斤，雌犬16-23公斤，身高大约雄犬肩高53-58厘米，雌犬51-56厘米，是一种中型犬。西伯利亚雪橇犬是原始的古老犬种，在西伯利亚东北部、格陵兰南部生活。', '0', '2018-04-28 16:06:46', '1', null, null);
INSERT INTO `product` VALUES ('4', '哈士奇4号AA', '238', '66', 'products/5.jpg', '伯利亚雪橇犬（俄语：Сибирский хаски，英语：Siberian husky），常见别名哈士奇，昵称为二哈。西伯利亚雪橇犬体重介于雄犬20-27公斤，雌犬16-23公斤，身高大约雄犬肩高53-58厘米，雌犬51-56厘米，是一种中型犬。西伯利亚雪橇犬是原始的古老犬种，在西伯利亚东北部、格陵兰南部生活。', '0', '2018-04-28 15:59:41', '1', null, null);
INSERT INTO `product` VALUES ('5', '哈士奇5号P', '238', '119', 'products/111.jpg', '伯利亚雪橇犬（俄语：Сибирский хаски，英语：Siberian husky），常见别名哈士奇，昵称为二哈。西伯利亚雪橇犬体重介于雄犬20-27公斤，雌犬16-23公斤，身高大约雄犬肩高53-58厘米，雌犬51-56厘米，是一种中型犬。西伯利亚雪橇犬是原始的古老犬种，在西伯利亚东北部、格陵兰南部生活。', '0', '2018-04-28 16:37:53', '1', null, null);
INSERT INTO `product` VALUES ('6', '哈士奇6号', '238', '654', 'products/1.jpg', '伯利亚雪橇犬（俄语：Сибирский хаски，英语：Siberian husky），常见别名哈士奇，昵称为二哈。西伯利亚雪橇犬体重介于雄犬20-27公斤，雌犬16-23公斤，身高大约雄犬肩高53-58厘米，雌犬51-56厘米，是一种中型犬。西伯利亚雪橇犬是原始的古老犬种，在西伯利亚东北部、格陵兰南部生活。', '0', '2018-04-28 16:35:20', '1', null, null);
INSERT INTO `product` VALUES ('7', '哈士奇7号', '1120', '223', 'products/4.jpg', '伯利亚雪橇犬（俄语：Сибирский хаски，英语：Siberian husky），常见别名哈士奇，昵称为二哈。西伯利亚雪橇犬体重介于雄犬20-27公斤，雌犬16-23公斤，身高大约雄犬肩高53-58厘米，雌犬51-56厘米，是一种中型犬。西伯利亚雪橇犬是原始的古老犬种，在西伯利亚东北部、格陵兰南部生活。', '0', '2018-05-16 20:41:11', '1', null, null);
INSERT INTO `product` VALUES ('10', '萌狗萨摩A号', '356', '200', 'picturess/666.jpg', '萨摩是一种犬类，是萨摩耶犬的简称。该犬类是一种脊索动物门、哺乳纲、食肉目动物。也是为旧日日本的一个藩国名，现也用于日本的一些地名。', '1', '2018-04-14 17:56:27', '1', null, null);
INSERT INTO `product` VALUES ('11', '萨摩B号萌狗', '19800', '11', 'products/111.jpg', '萨摩是一种犬类，是萨摩耶犬的简称。该犬类是一种脊索动物门、哺乳纲、食肉目动物。也是为旧日日本的一个藩国名，现也用于日本的一些地名。', '0', '2018-04-14 17:57:20', '2', null, null);
INSERT INTO `product` VALUES ('12', '萨摩萌狗C号', '19800', '515', 'products/222.jpg', '萨摩是一种犬类，是萨摩耶犬的简称。该犬类是一种脊索动物门、哺乳纲、食肉目动物。也是为旧日日本的一个藩国名，现也用于日本的一些地名。', '0', '2018-04-28 16:07:23', '2', null, null);
INSERT INTO `product` VALUES ('13', '萨摩萌狗D号', '19800', '123', 'products/222.jpg', '萨摩是一种犬类，是萨摩耶犬的简称。该犬类是一种脊索动物门、哺乳纲、食肉目动物。也是为旧日日本的一个藩国名，现也用于日本的一些地名。', '0', '2018-04-28 16:07:47', '2', null, null);
INSERT INTO `product` VALUES ('14', '萨摩萌狗E号', '19800', '1555', 'products/1000.jpg', '萨摩是一种犬类，是萨摩耶犬的简称。该犬类是一种脊索动物门、哺乳纲、食肉目动物。也是为旧日日本的一个藩国名，现也用于日本的一些地名。', '0', '2018-04-28 16:24:02', '2', null, null);
INSERT INTO `product` VALUES ('15', '萨摩F号', '19800', '1212', 'products/111.jpg', '萨摩是一种犬类，是萨摩耶犬的简称。该犬类是一种脊索动物门、哺乳纲、食肉目动物。也是为旧日日本的一个藩国名，现也用于日本的一些地名。', '0', '2018-04-28 16:24:20', '2', null, null);
INSERT INTO `product` VALUES ('16', '萨摩G号', '19800', '633', 'products/555.jpg', '萨摩是一种犬类，是萨摩耶犬的简称。该犬类是一种脊索动物门、哺乳纲、食肉目动物。也是为旧日日本的一个藩国名，现也用于日本的一些地名。', '0', '2018-04-28 16:24:33', '2', null, null);
INSERT INTO `product` VALUES ('17', '萨摩H号', '19800', '233', 'products/666.jpg', '萨摩是一种犬类，是萨摩耶犬的简称。该犬类是一种脊索动物门、哺乳纲、食肉目动物。也是为旧日日本的一个藩国名，现也用于日本的一些地名。', '0', '2018-04-28 16:24:45', '2', null, null);
INSERT INTO `product` VALUES ('18', '萨摩I号', '19800', '256', 'products/1400.jpg', '萨摩是一种犬类，是萨摩耶犬的简称。该犬类是一种脊索动物门、哺乳纲、食肉目动物。也是为旧日日本的一个藩国名，现也用于日本的一些地名。', '0', '2018-04-28 16:25:02', '2', null, null);
INSERT INTO `product` VALUES ('21', '贵宾犬a', '598', '133', 'products/888.jpg', '贵宾犬（Poodle），也称“贵妇犬”，又称“卷毛狗”，在德语中，Pudel是“水花飞溅”的意思，是犬亚科犬属的一种动物。', '1', '2018-05-05 19:35:07', '3', null, null);
INSERT INTO `product` VALUES ('26', '贵宾犬', '598', '1683', 'products/1.jpg', '贵宾犬（Poodle），也称“贵妇犬”，又称“卷毛狗”，在德语中，Pudel是“水花飞溅”的意思，是犬亚科犬属的一种动物。', '0', '2018-04-28 16:29:00', '3', null, null);
INSERT INTO `product` VALUES ('29', '贵宾犬', '598', '14', 'products/666.jpg', '贵宾犬（Poodle），也称“贵妇犬”，又称“卷毛狗”，在德语中，Pudel是“水花飞溅”的意思，是犬亚科犬属的一种动物。', '0', '2018-04-28 16:32:55', '3', null, null);
INSERT INTO `product` VALUES ('31', '比熊犬', '387', '666', 'picturess/666.jpg', '比熊犬（法语：Bichon Frisé，意指“白色卷毛的玩赏用小狗”）原产于地中海地区，是一种小型犬品种。', '0', '2018-04-28 16:08:02', '4', null, null);
INSERT INTO `product` VALUES ('32', '比熊犬', '387', '4448', 'products/1200.jpg', '比熊犬（法语：Bichon Frisé，意指“白色卷毛的玩赏用小狗”）原产于地中海地区，是一种小型犬品种。', '0', '2018-04-28 16:26:39', '4', null, null);
INSERT INTO `product` VALUES ('34', '比熊犬', '387', '187', 'products/333.jpg', '比熊犬（法语：Bichon Frisé，意指“白色卷毛的玩赏用小狗”）原产于地中海地区，是一种小型犬品种。', '0', '2018-04-28 16:33:15', '4', null, null);
INSERT INTO `product` VALUES ('37', '比熊犬', '387', '446', 'products/2.jpg', '比熊犬（法语：Bichon Frisé，意指“白色卷毛的玩赏用小狗”）原产于地中海地区，是一种小型犬品种。', '0', '2018-04-28 16:31:42', '4', null, null);
INSERT INTO `product` VALUES ('38', '比熊犬', '387', '186', 'products/222.jpg', '比熊犬（法语：Bichon Frisé，意指“白色卷毛的玩赏用小狗”）原产于地中海地区，是一种小型犬品种。', '0', '2018-05-05 19:34:51', '4', null, null);
INSERT INTO `product` VALUES ('45', '温顺金毛', '899', '4644', 'products/666.jpg', '金毛寻回犬（英语：Golden Retriever）是比较现代并很流行的狗的品种，是单猎犬，作为用来在猎捕野禽的寻回犬而培养出来的，游泳的续航力极佳。', '0', '2018-04-28 16:02:06', '5', null, null);
INSERT INTO `product` VALUES ('51', '温顺金毛', '899', '1', 'products/111.jpg', '金毛寻回犬（英语：Golden Retriever）是比较现代并很流行的狗的品种，是单猎犬，作为用来在猎捕野禽的寻回犬而培养出来的，游泳的续航力极佳。', '1', '2018-04-28 16:05:41', '5', null, null);
INSERT INTO `product` VALUES ('56', '小巧京巴', '997', '513', 'picturess/666.jpg', '京巴犬，是中国古老的犬种，已有四千年的历史。北京犬是一种平衡良好，结构紧凑的狗，前躯重而后躯轻。它有个性，表现欲强，其形象酷似狮子', '0', '2014-11-03 20:18:00', '6', null, null);
INSERT INTO `product` VALUES ('60', '小巧京巴', '997', '354', 'products/222.jpg', '京巴犬，是中国古老的犬种，已有四千年的历史。北京犬是一种平衡良好，结构紧凑的狗，前躯重而后躯轻。它有个性，表现欲强，其形象酷似狮子', '1', '2018-04-28 16:06:29', '6', null, null);
INSERT INTO `product` VALUES ('65', '雪纳瑞', '198', '63', 'picturess/1300.jpg', '雪纳瑞犬是梗类犬的一种，起源于15世纪的德国，是唯一在梗犬类中不含英国血统的品种。其名字Schnauzer是德语的“口吻”之意，他们精力充沛、活泼、聪明。', '1', '2014-11-03 20:18:00', '7', null, null);
INSERT INTO `product` VALUES ('66', '雪纳瑞', '198', '35', 'products/5.jpg', '雪纳瑞犬是梗类犬的一种，起源于15世纪的德国，是唯一在梗犬类中不含英国血统的品种。其名字Schnauzer是德语的“口吻”之意，他们精力充沛、活泼、聪明。', '0', '2018-04-28 16:30:44', '7', null, null);
INSERT INTO `product` VALUES ('68', '雪纳瑞', '198', '1654', 'products/4.jpg', '雪纳瑞犬是梗类犬的一种，起源于15世纪的德国，是唯一在梗犬类中不含英国血统的品种。其名字Schnauzer是德语的“口吻”之意，他们精力充沛、活泼、聪明。', '1', '2018-04-28 16:38:06', '7', null, null);
INSERT INTO `product` VALUES ('72', '布偶猫', '198', '133', 'picturess/1.jpg', '布偶猫又称布拉多尔猫，是猫中体型和体重最大的一种猫。布偶猫性格温顺和好静，对人非常友善，忍耐性强，常被误认为缺乏疼痛感，布偶猫由于能容忍孩子... ', '1', '2015-02-10 12:02:54', '9', null, null);
INSERT INTO `product` VALUES ('73', '短尾猫', '299', '644', 'products/5.jpg', '短腿又称布拉多尔猫，是猫中体型和体重最大的一种猫。格温顺和好静，对人非常友善，忍耐性强，常被误认为缺乏疼痛感，由于能容忍孩子... ', '0', '2018-04-14 17:55:57', '10', null, null);
INSERT INTO `product` VALUES ('74', '东方猫', '200', '455', 'picturess/4.jpg', '东方猫根据被毛颜色不同而称为外国白、外国紫等在美国，各种毛色的猫均称为东方猫。', '1', '2014-12-15 00:00:00', '14', null, null);
INSERT INTO `product` VALUES ('75', '仓鼠', '200', '133', 'products/m.jpg', '仓鼠（学名：Cricetidae）是仓鼠亚科动物的总称。仓鼠共七属十八种，主要分布于中亚干旱地区，少数分布于欧洲。', '1', '2018-04-14 17:57:06', '15', null, null);
INSERT INTO `product` VALUES ('76', '缅因猫', '200', '315', 'picturess/2.jpg', '缅因猫（英文：MaineCoon），原产于美国缅因州，并因其而得名。是北美自然产生的第一个长毛品种，约于18世纪中叶形成较稳定品种。', '1', '2014-12-15 00:00:00', '12', null, null);
INSERT INTO `product` VALUES ('79', '乌龟', '5999', '2488', 'products/4a51167a-89d5-4710-aca2-7c76edc355b8-thumbnail.jpg', '乌龟，长寿之星', '0', '2018-04-14 17:56:12', '22', null, null);

-- ----------------------------
-- Table structure for tongji
-- ----------------------------
DROP TABLE IF EXISTS `tongji`;
CREATE TABLE `tongji` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `pname` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tongji
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `code` varchar(64) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7', 'aaa', 'aaa', '姜涛', 'aaa@shop.com', '15726607618', '北京市西三旗中腾建华3楼', '1', '', null, null);
INSERT INTO `user` VALUES ('8', 'bbb', 'aaa', '张三', 'bbb@shop.com', '18726607618', '北京市西三旗中腾建华3楼', '1', '', null, null);
INSERT INTO `user` VALUES ('9', 'ccc', 'aaa', 'sz', '132@133.com', '15546497621', 'cdssdc', '0', '26d00a4dda5f4f56960423044ef5f426362676b37e034670b20ea7a96266339d', null, null);
INSERT INTO `user` VALUES ('10', '333', '111', 'aaa', '1111@qq.com', 'aaa', 'aaaa', '0', '10f791c0793d4d8ca1ea9a94aa392d212436a473658e43c898205f4f25ce5d5f', null, null);

-- ----------------------------
-- Table structure for userid
-- ----------------------------
DROP TABLE IF EXISTS `userid`;
CREATE TABLE `userid` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userid
-- ----------------------------
