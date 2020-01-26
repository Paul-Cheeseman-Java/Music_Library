## General info
This project is a small JDBC application to help me understand the fundamentals of integrating a database with a Java application.  

It is a console based application for browsing and managing a basic fictitious music library.
	
## Technologies
Project is created with:
* Java (v11)
* Sqlite (v3.30.1)
	
## Setup
To run this application on Windows:
* Ensure you have Java installed and your PATH variable is setup correctly.
* Clone this GIT repo to your computer.
* In the Windows command prompt navigate into the cloned repo's top level Music_Library directory.
* In the command prompt enter: *java -classpath .\out\production\Music_Library;.\src\resources\sqlite-jdbc-3.30.1.jar com.paulc.Application*


## Application
* The database has a small amount of test data loaded, is contains albums and songs for the World renowned artists:
    * Classical Colin
    * Metal Mad Mavis
    * Pan Pipes Percy
    * Raving Ralph

* The test data loaded into the database has been created to make it easy to see the different operations:  
    * The Albums have been named "*artist* - Album *n*"
    * The Songs "*artist* - Album *n*, Song *n*"

* Should the database need to be restored you can either:
    * Delete all the data (but keep the tables) and re-import the original test data using the csv files in the db-cvs folder. This requires the tables to be imported in the order Artist, Album, Song due to foreign key constraints.
    * Delete the database and create a new one using the SQL script in the db-sql folder.  
    
    These operations are simplified by using the DB browser for SQLite: https://sqlitebrowser.org/