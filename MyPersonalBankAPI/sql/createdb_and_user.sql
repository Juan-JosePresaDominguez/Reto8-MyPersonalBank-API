CREATE SCHEMA `personalapidb` ;

USE mysql;

CREATE USER 'apidb_user'@'%' IDENTIFIED BY 'api123';

GRANT ALL PRIVILEGES ON personalapidb.* TO 'apidb_user'@'%';
FLUSH PRIVILEGES;