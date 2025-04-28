# 💻 Ejercicio Integrador - JPA + Hibernate

Este proyecto implementa un conjunto de funcionalidades que interactúan con una base de datos relacional utilizando Java Persistence API (JPA) y Hibernate como proveedor de persistencia. A través de este sistema, se gestionan entidades como carreras y estudiantes, permitiendo la carga de datos desde archivos CSV, persistencia en la base de datos y ejecución de consultas.

---

## 👥 Integrantes

- Brost, Simon
- Cordeiro, Federico
- Elis, Abigail
- Piliavsky, Pablo

---

## 📈 Funcionalidades principales

- Carga de datos desde archivos CSV a la base de datos.
- Gestión de entidades Estudiante, Carrera y EstudianteCarrera.
- Inserción, actualización, eliminación y búsqueda de registros.
- Consultas personalizadas utilizando JPA (JPQL).

---

## 🗂️ Contenido

`src/main/resources/csv`
  * `estudiantes.csv`
  * `carreras.csv`
  * `estudianteCarrera.csv`

`./diagrams`
  * `DiagramaEntidadRelacion.png`
  * `DiagramaObjetos.png`

---

## ✅ Requisitos

- Java 11+
- Maven
- Hibernate (JPA Provider)
- JPA (Jakarta Persistence API)
- MySQL o cualquier base de datos relacional compatible con JDBC
- OpenCSV (para lectura de archivos CSV)

---

## ⚙️ Configuración y ejecución

1. **Clonar el repositorio**

```bash
git clone https://github.com/abigailelis/integrador2.git
```
2. **Configurar la base de datos:** Crear una base de datos llamada "integrador2".

3. **Ejecutar el proyecto:** Utilizar Maven o tu IDE preferido para compilar y correr el proyecto.

---

## 🛢️ Conexión a la Base de Datos

Este proyecto se conecta a una base de datos con la siguiente configuración:

- **Usuario**: `root`
- **Contraseña**: (vacía)
- **Nombre de la base de datos**: `integrador2`


