# Examen-MercadoLibre

Necesario:
Docker 
MySql
Spring Boot
Java 11
<br>
Pasos:
- Desacargar el pryecto de git
- Crear en Docker una imagen de mySql
  docker pull mysql
  docker run -d -p 13306:3306 --name mysql_container -e MYSQL_ROOT_PASSWORD=secret mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
  
  Dentro de mySql en consola:
    docker exec -it mysql_container mysql -uroot -p //perdirá password el cual es secret
    create user 'mysqluser' identified by 'secret';
    GRANT ALL PRIVILEGES ON *.* TO 'mysqluser'@'%';
    CREATE DATABASE ApiGeoMeli;
    USE ApiGeoMeli;
  
    CREATE TABLE countries(
      alpha3Code VARCHAR(5) PRIMARY KEY,
      alpha2Code VARCHAR(5),
      name VARCHAR(255),
      capital VARCHAR(255),
      flag VARCHAR(255),
      distance_to_argentina int,
      lat decimal(5,2),
      lng decimal(5,2),
      request_count int
    );

Con esto ya tendrìamos la tabla principal 'countries' para el funcionamiento de la aplicación. Luego la misma aplciación generará las tablas que se relacionan con la principal.


- Crear una nueva red en Docker<br>
  docker network create --driver bridge my-net<br>
  docker network disconnect bridge mysql_container<br>
  docker network connect my-net mysql_container<br>
  
- Generar el jar de nuestro proyecto ejecutando el siguiente comando en consola
    mvn clean package
    
- continuar con la compilación
    docker build -t user/examenMeli:V1 .
    
- Ver listado de todas las imágenes que tenemos en nuestro contenedor
  docker images

- Correr la imagen de nuestra aplicación en Spring
    docker run --network my-net -d -p 18082:8082 --name examenMeli {ImageID} //ImageID es el token de imagen que nos devulve al ejecutar el comando anterior
    
- Para consultar si esta nuestra imagen corriendo 
  docker ps
  
 
 Para utilizar la aplicación
 
 - Ingresar desde el navegador http://localhost:8082
   
 - Consumir datos desde como ApiRest:
  
    Método : POST
    
    - para obtener la información del país enviando la url:
        http://localhost:8082/api/country/info
        
        body: 5.6.7.8 // o cualquier dirección IP
        
    - para obtener información de todos los paises consultados:
        http://localhost:8082/api/stats
        
    - para obtener el promedio de distancias consultadas:
        http://localhost:8082/api/stats/average
        
    - para obtener el país con la máxima distancia consultada:
        http://localhost:8082/api/stats/max
        
    - para obtener el país con la mínima distancia consultada:
        http://localhost:8082/api/stats/min
        
        
 
