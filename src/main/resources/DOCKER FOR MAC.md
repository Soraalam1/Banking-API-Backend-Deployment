1. Install Docker Desktop For Mac OS

2. After installing go inside your terminal and type “ docker run “ you should see a list of docker commands which signifies you have downloaded docker correctly.

3.  Run this command as follows “ docker run --name [give container a name] -p 3306:3306 -e MYSQL_ROOT_PASSWORD=[give a password] mysql " you should see your database intializing

4. To see container run " docker ps"

5. make a new tab in terminal

6.Run the command " docker exec -it [name of container from step 3] bash "
Sample:
```bash
Asher@SiriusSingsMini ~ % docker exec -it mycontainer bash
```

7. Now we want to run mysql so we run "mysql -u root -p[password created from step 3] " (NO SPACE BETWEEN P TAG AND PASSWORD!!!)
Sample: 

```bash
root@8f11abf902e5:/# mysql -u root -pmypassword2355
```
8. You should see "mysql>" and that means you are now using mysql. You now use your standard sql commands here and out

9. When you are done creating your database and tables commit your changes by typing "commit;"

10. To exit mysql run command "EXIT"

11. To exit the execution of the container run the command "exit".

12. Add mysql dependency 

```
  <dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
  ```
  
  13. Add the following to your application.properties file replacing what is neccesary 
  
    ```
  spring.datasource.url= jdbc:mysql://localhost:3306/your-db-name-goes-here?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=your-db-username-goes-her
spring.datasource.password=your-db-password-goes-her
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.testWhileIdle = true
spring.datasource.validationQuery = SELECT 1
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect

```
 
 
 -p - port tag
 -e - environment variable
 exec - execute 
 -it - interactive terminal
 
 
 Asher Louis-Jean
 
 
 
