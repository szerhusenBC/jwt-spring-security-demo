# JWT Spring Security Demo

![Screenshot from running application](etc/screenshot-jwt-spring-security-demo.png?raw=true "Screenshot JWT Spring Security Demo")

##About
This is just a simple demo for using **JWT (JSON Web Token)** with **Spring Security** and
**Spring Boot**. This solution is partially based on the blog entry
[REST Security with JWT using Java and Spring Security](https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java)
and the demo project [Cerberus](https://github.com/brahalla/Cerberus). Thanks to the authors!

[![Build Status](https://travis-ci.org/szerhusenBC/jwt-spring-security-demo.svg?branch=master)](https://travis-ci.org/szerhusenBC/jwt-spring-security-demo)

##Requirements
This demo is build with with Maven 3 and Java 1.8.

##Usage
Just start the application with the Spring Boot maven plugin (`mvn spring-boot:run`). The application is
running at [http://localhost:8080](http://localhost:8080).

There are three user accounts present to demonstrate the different levels of access to the endpoints in
the API and the different authorization exceptions:
```
Admin - admin:admin
User - user:password
Disabled - disabled:password (this user is disabled)
```

There are three endpoints that are reasonable for the demo:
```
/auth - authentication endpoint with unrestricted access
/persons - an example endpoint that is restricted to authorized users (a valid JWT token must be present in the request header)
/protected - an example endpoint that is restricted to authorized users with the role 'ROLE_ADMIN' (a valid JWT token must be present in the request header)
```

I've written a small Javascript client and put some comments in the code that hopefully makes this demo
understandable.

###Generating password hash for new users

I'm using [bcrypt](https://en.wikipedia.org/wiki/Bcrypt) to encode passwords. Your can generate your hashes with this simple tool: [Bcrypt Generator](https://www.bcrypt-generator.com)

###Using another database

Actually this demo is using an embedded H2 database that is automatically configured by Spring Boot. If you want to connect to another database you have to specify the connection in the *application.yml* in the resource directory. Here is an example for a MySQL DB:

```
spring:
  jpa:
    hibernate:
      # possible values: validate | update | create | create-drop
      ddl-auto: create-drop
  datasource:
    url: jdbc:mysql://localhost/myDatabase
    username: myUser
    password: myPassword
    driver-class-name: com.mysql.jdbc.Driver
```

*Hint: For other databases like MySQL sequences don't work for ID generation. So you have to change the GenerationType in the entity beans to 'AUTO' or 'IDENTITY'.*

You can find a reference of all application properties [here](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).

##Docker
This project has a docker image. You can find it at [https://hub.docker.com/r/hubae/jwt-spring-security-demo/](https://hub.docker.com/r/hubae/jwt-spring-security-demo/).

##Questions
If you have project related questions please take a look at the [past questions](https://github.com/szerhusenBC/jwt-spring-security-demo/issues?utf8=%E2%9C%93&q=is%3Aissue%20is%3Aopen%2Cclosed%20label%3Aquestion%20) or create a new ticket with your question.

*If you have questions that are not directly related to this project (e.g. common questions to the Spring Framework or Spring Security etc.) please search the web or look at [Stackoverflow](http://www.stackoverflow.com).*

Sorry for that but I'm very busy right now and don't have much time.

## Interesting projects

* [spring-security-pac4j](https://github.com/pac4j/spring-security-pac4j) a Spring Boot integration for Pac4j (a Java security engine that coveres JWT beside others)

##External resources

Dan Vega (https://twitter.com/therealdanvega) created a video that explained this project quite fine. Thanks to him!

https://youtu.be/mD3vmgksvz8

##Creator

**Stephan Zerhusen**

* <https://twitter.com/stzerhus>
* <https://github.com/szerhusenBC>

##Copyright and license

The code is released under the [MIT license](LICENSE?raw=true).

---------------------------------------

Please feel free to send me some feedback or questions!
