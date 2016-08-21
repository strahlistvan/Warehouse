# Warehouse

Warehouse simulator Java Console application. 
It is an Eclipse project. I used Java 1.7 with MySQL 5.5.27.

## How it works?

 Reads a list of items from the database and outputs the latest list of stock.
 Program using properties file to access database and FTP server.
 Default files: `database_properties.csv` and `ftp_properties.csv`, but you can use other files if add names to program's command line arguments. The properties files must be tabulator separated CSV text files.
 
## Program usage: 
 `java -jar Warehouse.jar [output csv name - OPTIONAL] [db property file - OPTIONAL] [ftp property file - OPTIONAL]`
 
