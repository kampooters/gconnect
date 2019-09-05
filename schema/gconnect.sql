CREATE DATABASE gconnect;

/*Table structure for table `notification` */

DROP TABLE IF EXISTS gconnect.notification;


USE `gconnect`;


CREATE TABLE `oauth_client` (
  `client_id` varchar(250) NOT NULL,
  `secret` varchar(250) DEFAULT NULL,
  `owner` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `gconnect`.`oauth_token` (
  `client_id` VARCHAR(250) NOT NULL,
  `access_token` VARCHAR(250) NULL,
  `refresh_token` VARCHAR(250) NULL,
  `authorize_token` VARCHAR(250) NULL,
  `callback_url` VARCHAR(250) NULL,
  `access_token_creation_time` bigint(20) NULL,
  PRIMARY KEY (`client_id`));


CREATE TABLE `shedlock` (
  `name` varchar(64) NOT NULL,
  `lock_until` timestamp(3) NULL DEFAULT NULL,
  `locked_at` timestamp(3) NULL DEFAULT NULL,
  `locked_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
