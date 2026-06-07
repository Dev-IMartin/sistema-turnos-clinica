# 🏥 Sistema de Gestión de Turnos Médicos
### Clínica San Juan S.A.

![Java](https://img.shields.io/badge/Java-11-orange?logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)
![Status](https://img.shields.io/badge/Estado-Funcional-brightgreen)

Sistema de gestión de turnos médicos desarrollado en Java con persistencia en MySQL. Permite administrar pacientes, médicos y turnos desde una interfaz de consola, incluyendo una sala de espera digital con estructura de cola FIFO.

Proyecto desarrollado como parte del **TP3 – Seminario de Práctica de Informática**  
**Universidad Siglo 21 – Licenciatura en Informática**

---

## 📋 Funcionalidades

- ✅ Registro y búsqueda de pacientes (por apellido o DNI)
- ✅ Registro y búsqueda de médicos (por especialidad)
- ✅ Gestión completa de turnos: alta, consulta, cambio de estado y cancelación
- ✅ Sala de espera digital con cola FIFO (First In, First Out)
- ✅ Historial de atenciones con pila LIFO (Last In, First Out)
- ✅ Agenda del médico con turnos pendientes ordenados por fecha
- ✅ Algoritmo de búsqueda binaria sobre lista de turnos
- ✅ Validación de entradas y manejo de excepciones

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 11 | Lenguaje principal |
| MySQL | 8.0 | Base de datos relacional |
| Maven | 3.x | Gestión de dependencias |
| mysql-connector-java | 8.0.33 | Conexión Java ↔ MySQL |

---

## 🏗️ Arquitectura y Pilares POO

El sistema aplica los cuatro pilares de la Programación Orientada a Objetos:

- **Abstracción:** clase abstracta `Persona` define el contrato para todas las personas del sistema
- **Herencia:** `Paciente` y `Medico` extienden `Persona`
- **Polimorfismo:** métodos `getRol()` y `getInfoDetallada()` sobreescritos en cada subclase
- **Encapsulamiento:** todos los atributos son `private` con acceso mediante getters/setters

### Patrones de diseño aplicados
- **Singleton** → `ConexionDB` (una sola conexión a MySQL)
- **DAO** → `PacienteDAO`, `MedicoDAO`, `TurnoDAO` (separación lógica de negocio y datos)

---

## 📁 Estructura del proyecto

```
sistema-turnos-clinica/
├── pom.xml
├── sql/
│   └── clinica_db.sql          # Script DDL + datos de prueba
└── src/main/java/com/clinica/
    ├── Main.java
    ├── modelo/
    │   ├── Persona.java         # Clase abstracta
    │   ├── Paciente.java        # Hereda de Persona
    │   ├── Medico.java          # Hereda de Persona
    │   ├── Turno.java           # Implements Comparable<Turno>
    │   └── EstadoTurno.java     # Enum de estados
    ├── dao/
    │   ├── PacienteDAO.java
    │   ├── MedicoDAO.java
    │   └── TurnoDAO.java
    ├── util/
    │   ├── ConexionDB.java      # Singleton
    │   ├── ColaTurnos.java      # Queue - sala de espera
    │   ├── HistorialTurnos.java # Stack - historial
    │   └── Validador.java
    └── menu/
        └── MenuPrincipal.java
```

---

## ⚙️ Instalación y ejecución

### Requisitos previos
- Java 11 o superior
- MySQL 8.0 o superior
- Maven 3.x

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/Dev-IMartin/sistema-turnos-clinica.git
cd sistema-turnos-clinica
```

**2. Crear la base de datos**

Ejecutar el script en MySQL Workbench o desde terminal:
```bash
mysql -u root -p < sql/clinica_db.sql
```

**3. Configurar la conexión**

Editar `src/main/java/com/clinica/util/ConexionDB.java` con tus credenciales:
```java
private static final String USUARIO = "root";
private static final String CLAVE   = "tu_contraseña";
```

**4. Compilar y ejecutar**
```bash
mvn clean package
java -jar target/sistema-turnos.jar
```

---

## 🖥️ Menú principal

```
╔════════════════════════════════════════════╗
║   SISTEMA DE GESTION DE TURNOS MEDICOS     ║
║         Clinica San Juan S.A.              ║
╚════════════════════════════════════════════╝

╔═══════════════════════════╗
║       MENU PRINCIPAL      ║
╠═══════════════════════════╣
║  1. Gestión de Pacientes  ║
║  2. Gestión de Médicos    ║
║  3. Gestión de Turnos     ║
║  4. Sala de Espera (Cola) ║
║  0. Salir                 ║
╚═══════════════════════════╝
```

---

## 👤 Autor

**Iván Martin**  
Estudiante de Licenciatura en Informática – Universidad Siglo 21  
📍 San Juan, Argentina  
🔗 [github.com/Dev-IMartin](https://github.com/Dev-IMartin)
