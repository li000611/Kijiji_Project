-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema kijijidb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `kijijidb` ;

-- -----------------------------------------------------
-- Schema kijijidb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kijijidb` DEFAULT CHARACTER SET utf8 ;
USE `kijijidb` ;

-- -----------------------------------------------------
-- Table `kijijidb`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kijijidb`.`image` ;

CREATE TABLE IF NOT EXISTS `kijijidb`.`image` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(255) NOT NULL,
  `path` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ID_UNIQUE` (`id` ASC),
  UNIQUE INDEX `path_UNIQUE` (`path` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kijijidb`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kijijidb`.`account` ;

CREATE TABLE IF NOT EXISTS `kijijidb`.`account` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `display_name` VARCHAR(45) NOT NULL,
  `user` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `display-name_UNIQUE` (`display_name` ASC),
  UNIQUE INDEX `user_UNIQUE` (`user` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kijijidb`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kijijidb`.`category` ;

CREATE TABLE IF NOT EXISTS `kijijidb`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `url_UNIQUE` (`url` ASC),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kijijidb`.`item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kijijidb`.`item` ;

CREATE TABLE IF NOT EXISTS `kijijidb`.`item` (
  `id` INT UNSIGNED NOT NULL,
  `image_id` INT UNSIGNED NOT NULL,
  `category_id` INT NOT NULL,
  `price` DECIMAL(15,2) NULL,
  `title` VARCHAR(255) NOT NULL,
  `date` DATE NULL,
  `location` VARCHAR(45) NULL,
  `description` TEXT NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_item_image_idx` (`image_id` ASC),
  INDEX `fk_item_category1_idx` (`category_id` ASC),
  UNIQUE INDEX `url_UNIQUE` (`url` ASC),
  CONSTRAINT `fk_item_image`
    FOREIGN KEY (`image_id`)
    REFERENCES `kijijidb`.`image` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `kijijidb`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
-- SET SQL_MODE = '';
-- DROP USER IF EXISTS 'cst8288'@'localhost';
-- SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
-- SHOW WARNINGS;
-- CREATE USER 'cst8288'@'localhost' IDENTIFIED BY '8288';

-- GRANT ALL ON *.* TO 'cst8288'@'localhost' WITH GRANT OPTION;
SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `kijijidb`.`account`
-- -----------------------------------------------------
START TRANSACTION;
USE `kijijidb`;
INSERT INTO `kijijidb`.`account` (`display_name`, `user`, `password`) VALUES ('CST8288', 'cst8288', 'cst8288');

COMMIT;


-- -----------------------------------------------------
-- Data for table `kijijidb`.`category`
-- -----------------------------------------------------
START TRANSACTION;
USE `kijijidb`;
INSERT INTO `kijijidb`.`category` (`url`, `title`) VALUES ('https://www.kijiji.ca/b-computer-accessories/ottawa-gatineau-area/c128l1700184?sort=dateDesc', 'Computer Accessories');
INSERT INTO `kijijidb`.`category` (`url`, `title`) VALUES ('https://www.kijiji.ca/b-laptop-accessories/ottawa-gatineau-area/c780l1700184?sort=dateDesc', 'Laptop Accessories');

INSERT INTO `kijijidb`.`image` (`url`, `path`, `name` ) VALUES ('www.kiki.com', 's','o');

COMMIT;

