# JWT Spring Security Demo

![Screenshot from running application](etc/screenshot-jwt-spring-security-demo.png?raw=true "Screenshot JWT Spring Security Demo")

## About
This is a demo for using **[JWT (JSON Web Token)](https://jwt.io)** with **[Spring Security](https://spring.io/projects/spring-security)** and
**[Spring Boot](https://spring.io/projects/spring-boot)**. This solution is based on the code base from the [JHipster Project](https://www.jhipster.tech/).
I tried to extract the minimal configuration and classes that are needed for JWT-Authentication and did some changes.

## Requirements
This demo is build with with Maven 3.6.x and Java 11.

## Usage
Just start the application with the Spring Boot maven plugin (`mvn spring-boot:run`). The application is
running at [http://localhost:8080](http://localhost:8080).

## Backend
There are three user accounts present to demonstrate the different levels of access to the endpoints in
the API and the different authorization exceptions:
```
Admin - admin:admin
User - user:password
Disabled - disabled:password (this user is deactivated)
```

There are four endpoints that are reasonable for the demo:
```
/api/authenticate - authentication endpoint with unrestricted access
/api/user - returns detail information for an authenticated user (a valid JWT token must be present in the request header)
/api/persons - an example endpoint that is restricted to authorized users with the authority 'ROLE_USER' (a valid JWT token must be present in the request header)
/api/hiddenmessage - an example endpoint that is restricted to authorized users with the authority 'ROLE_ADMIN' (a valid JWT token must be present in the request header)
```

## Frontend
I've written a small Javascript client and put some comments in the code that hopefully makes this demo understandable.
You can find it at [/src/main/resources/static/js/client.js](/src/main/resources/static/js/client.js).

### Generating password hashes for new users

I'm using [bcrypt](https://en.wikipedia.org/wiki/Bcrypt) to encode passwords. Your can generate your hashes with this simple tool: [Bcrypt Generator](https://www.bcrypt-generator.com)

### Using another database

Actually this demo is using an embedded H2 database that is automatically configured by Spring Boot. If you want to connect 
to another database you have to specify the connection in the *application.yml* in the resource directory. Here is an example for a MySQL DB:

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

You can find a reference of all properties [here](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).

## Author

**Stephan Zerhusen**

* <https://twitter.com/stzerhus>
* <https://github.com/szerhusenBC>

## Copyright and license

The code is released under the [MIT license](LICENSE?raw=true).

---------------------------------------

Please feel free to send me some feedback or questions!
