## Blog SrPromax :)
![blogs](src/main/resources/static/imgReadme/blogss.PNG)

## Tecnologías
<div align="center">
    <img width="50" src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png" alt="Spring Boot" title="Spring Boot"/>
    <img width="50" src="https://user-images.githubusercontent.com/25181517/117208740-bfb78400-adf5-11eb-97bb-09072b6bedfc.png" alt="PostgreSQL" title="PostgreSQL"/>
    <img width="50" src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png"/>
    <img width="50" src="https://www.thymeleaf.org/images/thymeleaf.png"/>
<img width="50" src="https://user-images.githubusercontent.com/25181517/117207330-263ba280-adf4-11eb-9b97-0ac5b40bc3be.png" alt="Docker" title="Docker"/>
    <img width="50" src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/RecaptchaLogo.svg/800px-RecaptchaLogo.svg.png"/>
    <img width="50" src="https://pbs.twimg.com/profile_images/1235945452304031744/w55Uc_O9_400x400.png"/>
    <img width="50" src="https://user-images.githubusercontent.com/25181517/183898674-75a4a1b1-f960-4ea9-abcb-637170a00a75.png"/>
    <img width="50" src="https://user-images.githubusercontent.com/25181517/192158954-f88b5814-d510-4564-b285-dff7d6400dad.png"/>
    
</div>

## Configuración
### Application.properties
El nombre de la base de datos es el de tu elección, solo basta con crearla, JPA se encargará de crear las tablas necesarias.
```properties
### Data Source
spring.datasource.url=jdbc:postgresql://localhost:5432/aquielnombredetubasededatos
spring.datasource.username=tuusuario
spring.datasource.password=tucontraseña
spring.datasource.driver-class-name=org.postgresql.Driver
````
Puedes modificar estas propiedades según tus necesidades.
```properties
### Hibernate Properties
spring.jpa.database=POSTGRESQL
spring.sql.init.platform=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.open-in-view=false
````
Aquí debes crear una cuenta en Google y obtener las credenciales para poder enviar correos.  
Para obtener las credenciales, debes acceder a **Administrar tu Cuenta de Google** y buscar **Contraseñas de Aplicaciones**.
```properties
## Config Mail
email.sender=aquituemail
email.password=aquitucontraseñadeaplicacion
secret.key=aquituclavederecaptcha
````
El `secret.key` es la llave secreta para el reCAPTCHA. La puedes conseguir aquí [reCAPTCHA](https://www.google.com/recaptcha/admin/create)

### Aplicación
Para ejecutar la aplicación, puedes hacerlo desde tu IDE favorito o desde la terminal.
```bash
mvn spring-boot:run
````

## Imágenes
### Login
![home](src/main/resources/static/imgReadme/loginBlog.PNG)

### Registro
![home](src/main/resources/static/imgReadme/register.PNG)

### Crear Post
![home](src/main/resources/static/imgReadme/crearBlog.PNG)

### Home
![home](src/main/resources/static/imgReadme/blogss.PNG)
