CREATE TABLE IF NOT EXISTS `userlist` (
	`name` VARCHAR(50) NOT NULL,
	`balance` DOUBLE NOT NULL,
	PRIMARY KEY (`name`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

INSERT INTO userlist (name, balance) VALUES
('bob', '134'),
('carol', '90'),
('frank', '9'),
('steve', '500');