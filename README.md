# Spaceship Manager API

## Paginación
La paginación está hecha a través de la anotación @PageableDefault, a la cual le establezco un tamaño de 5.

Se puede acceder a la siguiente página usando el parámetro "page" y asignándole el valor de la página a la que quieres acceder.

## Seguridad
He implementado una seguridad básica de usuario y contraseña a la api, el usuario y la contraseña utilizados se deben establecer en 2 variables de sistema, el "API_USER" y el "API_PASS", y al realizar la petición deberán ser incluidos como Basic Auth

## @Aspect 
Para imprimir la línea de log cuando nos envían un id negativo en las llamadas GET /spaceship/{id}, DEL /spaceship/{id} y PUT /spaceship/{id} he creado un aspecto llamado NegativeIdAspect que para la ejecución antes de que entre en la función y comprueba si el id recibido es negativo, si lo es se imprime una línea por consola con el método que se ha llamado y el id recibido.

## OpenApi 
Se puede acceder a OpenApi a través de http://localhost:8080/swagger.html, al acceder habrá que insertar el usuario y la contraseñas definidas en el paso de seguridad.

## Cacheo de peticiones
El cacheo de peticiones lo he realizado a través de 2 anotaciones, @EnableCaching la aplicación y @Cacheable en cada método que quiera cachear.

## Dockerización
Para dockerizar mi aplicación he creado un dockerfile que coje el jar de la carpeta target y crea la imagen, acto seguido se lanza el docker-compose.yml para crear los contenedores de zookeeper, kafka y de mi aplicación

## Mantenimiento de archivos DDL
Para el mantenimiento de archivos DDL he decidido usar flyway, una librería que no había usado aún, el único script que he necesitado se encuentra en resources/db/migration

## Kafka
He creado tanto un producer como un consumer que intercambian mensajes cuando se crea, elimina o modifica un registro