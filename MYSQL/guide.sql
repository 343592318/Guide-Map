/*
SQLyog 企业版 - MySQL GUI v7.14 
MySQL - 8.0.16 : Database - guide
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`guide` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `guide`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `admin_name` varchar(255) NOT NULL,
  `admin_password` varchar(255) NOT NULL,
  PRIMARY KEY (`admin_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `admin` */

insert  into `admin`(`admin_name`,`admin_password`) values ('admin','123456');

/*Table structure for table `listimage` */

DROP TABLE IF EXISTS `listimage`;

CREATE TABLE `listimage` (
  `name` varchar(45) NOT NULL,
  `Image` mediumblob NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `listimage` */


/*Table structure for table `road` */

DROP TABLE IF EXISTS `road`;

CREATE TABLE `road` (
  `start` varchar(255) NOT NULL,
  `end` varchar(255) NOT NULL,
  `length` int(11) NOT NULL,
  `meihua` int(11) NOT NULL,
  `lvhua` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `road` */

insert  into `road`(`start`,`end`,`length`,`meihua`,`lvhua`,`name`) values ('逸夫楼','小操场',44,44,4,'色鬼'),('逸夫楼','旭日苑',280,40,10,'食长路'),('旭日苑','东升苑',102,100,20,'饭饭路'),('洗手间','旭日苑',120,12,10,'大号路'),('小操场','旭日苑',100,30,20,'开学路'),('体育馆','逸夫楼',123,12,31,'体育路'),('逸夫楼','逸夫楼',123,1231,123,'123'),('东升苑',' 软协',123,35,33,'大声路');

/*Table structure for table `spot` */

DROP TABLE IF EXISTS `spot`;

CREATE TABLE `spot` (
  `x` double NOT NULL,
  `y` double NOT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `nature` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `introduction` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `spot` */

insert  into `spot`(`x`,`y`,`phoneNumber`,`nature`,`name`,`introduction`) values (134,132,'17670624037','教学楼','逸夫楼','好地方'),(398,393,'15096381013','食堂','东升苑','吃饭的地方啦!'),(147,363,'769613245','体育馆','小操场','运动的地方哒!'),(249,464,'18740400596','食堂','旭日苑','又是吃饭的地方!'),(453,530,'343592318','实验室',' 软协','工作的地方!'),(284,161,'17670624037','洗手间','洗手间','上厕所罗'),(74,241,'17670624037','体育馆','体育馆','运动的地方哒!');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_name` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `likespot` varchar(255) DEFAULT NULL,
  `user_check` int(11) DEFAULT NULL,
  `autologin` int(11) DEFAULT NULL,
  `rememberpassword` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`user_name`,`user_password`,`likespot`,`user_check`,`autologin`,`rememberpassword`) values ('heyuchao','e10adc3949ba59abbe56e057f20f883e','0&',1,0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;