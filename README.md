# Challenge Mercadolibre
ApiGeoMeli es una aplicación para obtener información desde una dirección ip de cualquier parte del mundo. Además, realiza la persistencia de datos de todas las consultas realizadas con el fin de confieccionar un historial con información útil para ser utilizado a modo estadístico. De éste historial, se podrá conocer información del país mas lejano y el mas cercano de Argentina, el promedio de distancias y un informe más completo con información mas detallada de todos los países.

Para poder utlizar ApiGeoMeli, deberá descargar este repositorio y desde una consola bash ejecutar `./start.sh`<br>
Será necesario contar con Maven y Docker previamente instalados para su correcta ejecución <i>(probado con Maven  3.6.3)</i>.

Para iniciar, abrir el navegador, ingresar a `http://localhost:8080` y listo!

También toda la infomormación puede obtenerse en formato json:

<b>Metodo para todas las request:</b><br>
`POST`

<b>Obtener información de un país desde una dirección ip:</b><br>
url:  `http://localhost:8080/api/country/info`<br>
Body: `5.6.7.8` (ó cualquier dirección de ipv4)

<b>Obtener información respecto al historial de consultas realizadas</b><br>
url: `http://localhost:8080/api/stats`

<b>Obtener información del país consultado mas alejado de Argentina.</b><br>
url: `http://localhost:8080/api/stats/max`

<b>Obtener información del país consultado mas cercano de Argentina.</b><br>
url: `http://localhost:8080/api/stats/min`

<b>Promedio de distancias consultadas</b><br>
url: `http://localhost:8080/api/stats/average`

Para todas las request, la aplicación devuelve un json similar de las siguientes características:

`success` -> {"success":true, "message":"", `object:[objeto no vacìo]`}<br>

`error` -> {"success":false, "message":"Information about error", object:null}<br>

Muchas gracias! :)
