# Examen-MercadoLibre

ApiGeoMeli es una aplicación desarrollada en Java bajo el framework de Spring con el objetivo de brindar información para direcciones IP.

Para correr la aplicación solo se deberá ejecutar el comando en consola:
  docker-compose up
  
 
 Para utilizar la aplicación<br>
 
 - Ingresar desde el navegador http://localhost:8080<br>
   
 - Consumir datos desde como ApiRest:<br>
  
    Método : POST<br>
    
    - para obtener la información del país enviando la url:<br>
        http://localhost:8080/api/country/info<br>
        
        body: 5.6.7.8 // o cualquier dirección IP<br>
        
    - para obtener información de todos los paises consultados:<br>
        http://localhost:8080/api/stats<br>
        
    - para obtener el promedio de distancias consultadas:<br>
        http://localhost:8082/api/stats/average<br>
        
    - para obtener el país con la máxima distancia consultada:<br>
        http://localhost:8080/api/stats/max<br>
        
    - para obtener el país con la mínima distancia consultada:<br>
        http://localhost:8080/api/stats/min<br>
        
        
 
