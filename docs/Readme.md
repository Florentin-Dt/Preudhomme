# README
## TMA Application install guide

### Docker install : 


Step 1 create docker image : 

	$ docker run --name mysql-tma -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.6

Step 2 start container : 

	$ docker start mysql-tma

Step 3 launch exec : 

	$ docker exec -ti mysql-tma bash

Step 4 open mysql command : 
	
	mysql --password
	root

Step 5 Create user and database : 
	
	CREATE USER 'tma'@'%' IDENTIFIED BY 'tma';
	UPDATE mysql.user SET Password=PASSWORD('tma') WHERE User='tma' AND Host='localhost';
	FLUSH PRIVILEGES;
	GRANT USAGE ON * . * TO 'tma'@'%' IDENTIFIED BY 'tma';
	GRANT USAGE ON * . * TO 'tma'@'localhost' IDENTIFIED BY 'tma';
	CREATE DATABASE IF NOT EXISTS `tma` ;
	GRANT ALL PRIVILEGES ON `tma` . * TO 'tma'@'%';

Step 6 Open workbench and create database link

Step 7 execute following sql

	CREATE TABLE `DatabaseVersioning` ( `Key` varchar(45) NOT NULL DEFAULT '',
	`Value` int(11) DEFAULT NULL, PRIMARY KEY (`Key`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
    INSERT INTO `DatabaseVersioning` (`Key`, `Value`) VALUES ('DatabaseVersion', 0);
	
Tomcat install : 

Step 1 Get Tomcat 8.0.32 version

Step 2 Update context file in conf folder

Add the ResourceLink under Resources 

	<ResourceLink global="jdbc/cerberusprd" name="jdbc/cerberusprd" type="javax.sql.DataSource"/>
	
Step 3 Add resource in server.xml

	<Resource   name="jdbc/cerberusprd"
                global="jdbc/cerberusprd"
                auth="Container"
                type="javax.sql.DataSource"
                factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
                maxTotal="100"
                maxIdle="30"
                maxWaitMillis="10000"
                validationQuery="select 1"
                testOnBorrow="true"
                username="tma"
                password="tma"
                driverClassName="com.mysql.jdbc.Driver"
                url="jdbc:mysql://192.168.99.100:3306/tma"/>
				
Step 4 add Realm in server.xml

	<Realm className="org.apache.catalina.realm.JDBCRealm"
            driverName="com.mysql.jdbc.Driver"
            connectionURL="jdbc:mysql://192.168.99.100:3306/tma?user=tma&amp;password=tma"
            userTable="user"
            userNameCol="Login"
            userCredCol="Password"
            userRoleTable="usergroup"
            roleNameCol="GroupName">
            <CredentialHandler className="org.apache.catalina.realm.MessageDigestCredentialHandler" algorithm="SHA-1" />
			
	Adapt url with your local environment
	
Step 5 Deploy application in tomcat (by IDE or by tomcat/manager)

Step 6 Call url to update database

	localhost:8080/[Context-path]/rest/monitorWebService/updateDatabase
	
Control step Call url to see database versioning

	localhost:8080/[Context-path]/rest/monitorWebService/getDatabaseVersion
	