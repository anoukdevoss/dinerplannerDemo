DROP DATABASE IF EXISTS dinerplannerDemo;
CREATE DATABASE dinerplannerDemo;

DROP USER IF EXISTS 'userDiner'@'localhost';
CREATE USER 'userDiner'@'localhost' IDENTIFIED BY 'userDinerPW';
GRANT ALL PRIVILEGES ON dinerplannerDemo.* TO 'userDiner'@'localhost';
FLUSH PRIVILEGES;