# Test-MercadoLibre

ApiGeoMeli is an application to get country information from ip address.<br>

To use, dewonload this repository and excecute in bash console: `./start.sh`<br>

Is necesary you have Maven installed. <br>
  
Open browser and go to http://localhost:8080 .<br><br>
 
If you want use as ApiRest for data json only:<br>
Method to use: POST<br><br>

Uris to get json data:<br>

get json of all countries consulted:
`http://localhost:8080/api/country/info`<br>
on `body` remember write ip adrees i.e: `5.6.7.8` (Germany ip address)
