# Utiliza la imagen base de MySQL oficial desde Docker Hub
FROM mysql:latest

# Variables de entorno para la configuración de MySQL
ENV MYSQL_ROOT_PASSWORD=cmaza123**
ENV MYSQL_DATABASE=maza-test
ENV MYSQL_USER=cmaza
ENV MYSQL_PASSWORD=cmaza123**

# Copia el script de generacion de tablas SQL
COPY BaseDatos.sql /docker-entrypoint-initdb.d/

# Cambia el propietario y los permisos del archivo para asegurarte de que MySQL pueda leerlo al inicio
RUN chown mysql:mysql /docker-entrypoint-initdb.d/BaseDatos.sql
RUN chmod 755 /docker-entrypoint-initdb.d/BaseDatos.sql

# Puerto expuesto por MySQL
EXPOSE 3306