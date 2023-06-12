CREATE TABLE IF NOT EXISTS `customers`
(
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `customer_id` VARCHAR (255) NOT NULL,
    `first_name` VARCHAR (255) NOT NULL,
    `last_name` VARCHAR (255) NOT NULL,
    `password` VARCHAR (255) NOT NULL,
    `email` VARCHAR (255) NOT NULL,
    `phone_number` VARCHAR (255) NOT NULL
);