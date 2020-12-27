create user 'mysqluser'@'%' identified by 'pass123';
UPDATE mysql.user SET Grant_priv='Y', Super_priv='Y' WHERE User='mysqluser';
FLUSH PRIVILEGES;
GRANT ALL ON *.* TO 'mysqluser'@'%';