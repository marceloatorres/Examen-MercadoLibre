# Examen-MercadoLibre

Necesario:<br>
Docker <br>
MySql<br>
Spring Boot<br>
Java 11<br>
<br>
Pasos:<br>
- Desacargar el pryecto de git<br>
- Crear en Docker una imagen de mySql<br>
  docker pull mysql<br>
  docker run -d -p 13306:3306 --name mysql_container -e MYSQL_ROOT_PASSWORD=secret mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
  <br>
  Dentro de mySql en consola:<br>
    docker exec -it mysql_container mysql -uroot -p //perdirá password el cual es secret<br>
    create user 'mysqluser' identified by 'secret';<br>
    GRANT ALL PRIVILEGES ON *.* TO 'mysqluser'@'%';<br>
    CREATE DATABASE ApiGeoMeli;<br>
    USE ApiGeoMeli;<br>
  
    CREATE TABLE countries(<br>
      alpha3Code VARCHAR(5) PRIMARY KEY,<br>
      alpha2Code VARCHAR(5),<br>
      name VARCHAR(255),<br>
      capital VARCHAR(255),<br>
      flag VARCHAR(255),<br>
      distance_to_argentina int,<br>
      lat decimal(5,2),<br>
      lng decimal(5,2),<br>
      request_count int<br>
    );<br>

Con esto ya tendrìamos la tabla principal 'countries' para el funcionamiento de la aplicación. <br>
Luego la misma aplciación generará las tablas que se relacionan con la principal.<br>


- Crear una nueva red en Docker<br>
  docker network create --driver bridge my-net<br>
  docker network disconnect bridge mysql_container<br>
  docker network connect my-net mysql_container<br>
  
- Generar el jar de nuestro proyecto ejecutando el siguiente comando en consola<br>
    mvn clean package<br>
    
- continuar con la compilación<br>
    docker build -t user/examenMeli:V1 .<br>
    
- Ver listado de todas las imágenes que tenemos en nuestro contenedor<br>
  docker images<br>

- Correr la imagen de nuestra aplicación en Spring<br>
    docker run --network my-net -d -p 18082:8082 --name examenMeli {ImageID} //ImageID es el token de imagen que nos devulve al ejecutar el comando anterior<br>
    
- Para consultar si esta nuestra imagen corriendo <br>
  docker ps<br>
  
 
 Para utilizar la aplicación<br>
 
 - Ingresar desde el navegador http://localhost:8082<br>
   
 - Consumir datos desde como ApiRest:<br>
  
    Método : POST<br>
    
    - para obtener la información del país enviando la url:<br>
        http://localhost:8082/api/country/info<br>
        
        body: 5.6.7.8 // o cualquier dirección IP<br>
        
    - para obtener información de todos los paises consultados:<br>
        http://localhost:8082/api/stats<br>
        
    - para obtener el promedio de distancias consultadas:<br>
        http://localhost:8082/api/stats/average<br>
        
    - para obtener el país con la máxima distancia consultada:<br>
        http://localhost:8082/api/stats/max<br>
        
    - para obtener el país con la mínima distancia consultada:<br>
        http://localhost:8082/api/stats/min<br>
        
        
 
