CREATE DATABASE IF NOT EXISTS hassandb;

CREATE TABLE hassandb.deal(
	`id` bigint NOT NULL,
  	`from_currency` varchar(45) NOT NULL,
  	`to_currency` varchar(45) NOT NULL,
  	`amount` double NOT NULL,
  	`timestamp` datetime NOT NULL,
  	PRIMARY KEY (`id`)
);