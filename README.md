Spring Boot JPA + H2

Desarrollo de una aplicación que exponga una API RESTful para la creación de usuarios y manejo de JWT como token.

REQUISITO:

● Banco de datos en memoria. H2.

● Proceso de build vía Maven.

● Persistencia con JPA. Ejemplo: Hibernate.

● Framework SpringBoot.

● Java 8+

Project Structure

![image](https://github.com/fhernandez204/francisco/assets/35818550/73666b89-838a-470b-98ac-34b67f488b73)

ENDPOINT

CREAR USUARIO
 http://localhost:8080/api/users

 
![image](https://github.com/fhernandez204/francisco/blob/main/createUser.png)

SI EL CORREO YA EXISTE
![image](https://github.com/fhernandez204/francisco/blob/main/createUser2.png)

SI EL CORREO NO TIENE EL FORMATO CORRECTO

![image](https://github.com/fhernandez204/francisco/blob/main/createUser3.png)


GET USUARIO

 http://localhost:8080/api/users

 ![image](https://github.com/fhernandez204/francisco/blob/main/getUsers.png)


 MODIFICAR USUARIO

 http://localhost:8080/api/users/13502

 ![image](https://github.com/fhernandez204/francisco/blob/main/putUser.png)
