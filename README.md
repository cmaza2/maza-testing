# manage-services
 Microservicios para gestion de cuentas movimientos y personas
# Repositorio Christian Maza:

# Microservicio 1 (accounts-movements-service)
Contiene las apis para la creacion y el control de cuentas y transacciones,se despliega en el puerto 8085
# Microservicio 2 (people-management-service)
Contiene las apis para la creacion de clientes se despliega en el puerto 8080.


## Contenedores
Se crea contenedores a traves del docker-compose.yml para el despliegue de los 2 microservicios, ademas se incluye un contenedor para la base de datos mysql, el zookeper y la kafka cluste.

## Tabla de Contenidos
- [Instalacion](#instalaci?n)
- [Uso](#uso)
- [Arquitectura](#arquitectura)
- [Pruebas](#pruebas)

## Instalacion
- Descargar el repositorio https://github.com/cmaza2/maza-testing
- Ingresar por consola al directorio master/Artifacts y ejecutar el comando docker-compose up, esto permitir que los microservicios se desplieguen en los diferentes contenedores.
- ## Uso
- Se incluye collection de postman para las diferentes apis de los diferentes microservicios
- Asimismo se adjunta url de swagger para poder conocer las diferentes tramas de las distinta apis
- A continuacion, se muestra ejemplo de las funciones principales
- Para crear un nuevo cliente se utiliza la siguiente trama

```json
{
    "name":"Alberto Spencer",
    "gender":"MASCULINO",
    "age":"31",
    "id":"1104647236",
    "address":"Otavala sn y principal",
    "phone":"098254785",
    "password":"12345",
    "status":true
}
```
- Para Crear una nueva cuenta se utiliza la siguiente trama
```json
{
    "accountNumber":"478759",
    "accountType":"Ahorros",
    "initialBalance":2000.00,
    "status":true,
    "customer":"1104637911"
}
```
- Para Crear un nuevo movimiento se utiliza la siguiente trama
```json
{
    "account": 
        {
            "accountNumber": "2901085464"
        }
    ,
    "transactionType": "Retiro",
    "value": "300"
}
```
## Arquitectura
- Se a usado una arquitectura hexagonal ,durante el desarrollo, de esta prueba tecnica se ha seguido estrictamente est?ndares de programaci?n y pr?cticas de c?digo limpio adquiridos en todos estos anos de experiencia.
- Se a implementado Swagger para la documentacion de apis
- LAS URLS son las siguientes
  [Banking Service]
  http://localhost:8085/swagger-ui/index.html#/
- [Manage Customer]
  http://localhost:8080/swagger-ui/index.html#/
## Pruebas
- Se a creado una prueba unitaria para la entidad Customer
- Se a creado una prueba de Integracion
