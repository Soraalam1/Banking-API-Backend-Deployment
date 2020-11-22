# Docker with MYSQL

This is a tutorial of how to use docker and utilize mysql and create a database

## Prerequisites

You will need to download Docker for Windows

https://www.docker.com/products/docker-desktop
[Docker](https://www.docker.com/products/docker-desktop)

### Using Docker 

1. You can type in docker into the terminal / git bash and should see a list of commands for it.

2. Now you can run/create a container
```
 docker run --name [name] -p 3306:3306 -e MYSQL_ROOT_PASSWORD=[password] mysql
 ```
 You need to use docker as the prefix and use the command run. The command --name allows you to name the container. The -p command allows you to specify the port.
 The port 3306 on the left of the : is what we will expose, and can be anything but the right side has to be 3306. -e allows you to create an environment variable. mysql for the repo
 
 Example: 
  ```
 docker run --name ms -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password mysql
 ```
 After it initializes it will occupy the terminal and you will have create another one. Dont close it.
 
 3. Bashing into the container
 
 Disclaimer: We want an interactive terminal and if you are using Git Bash like myself you will need to use winpty prior in order to execute
 
 Use the name you created in step 2.
```
 docker exec -it [name] bash
 ```
  With the extra commmand for git bash
```
  winpty docker exec -it ms bash
 ```
  
  4. Logging into MySql
  
  You are in now in bash, I used to root user and you want to put no space between -p and the password we created prior in step 2.
 
 ```
   mysql -u root -ppassword
   ```
   An example of mine with password of password
   ```
   mysql -u root -ppassword
   ```
  

  5. MYSQL
  
  We are now in mysql , dont forget the semicolons at the end. if it takes you to a new line just add a semicolon and enter;
  ```
  create database banking;
  ```
  Use the database
  ```
  use banking;
  ```
  6. Comment out / delete h2 from dependencies and add mysql
  ```
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<scope>runtime</scope>
</dependency>
  ```
  
  and rebuild your maven.
  
  7. Now go to application properties and use this snippet below from mikaila. 
  
  username will be root for the way we did it, and password is the same from step 2.
  
  the database name will be the same from step 5., and the port will be the one you on the left of the colon in step 2.
  
  make sure you replac the filler texts !
  
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

8. Update the above code to use your own database and *match your port* very important.
  
  
  
  
  
  
  
