-- ============================================================
--  Script de creacion de base de datos: clinica_db
--  Sistema de Gestion de Turnos Medicos - Clinica San Juan S.A.
--  TP3 - Seminario de Practica de Informatica
-- ============================================================

CREATE DATABASE IF NOT EXISTS clinica_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE clinica_db;

-- ----------------------------
--  Tabla: pacientes
-- ----------------------------
CREATE TABLE IF NOT EXISTS pacientes (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    nombre            VARCHAR(80)  NOT NULL,
    apellido          VARCHAR(80)  NOT NULL,
    dni               VARCHAR(10)  NOT NULL UNIQUE,
    telefono          VARCHAR(20),
    fecha_nacimiento  DATE         NOT NULL,
    obra_social       VARCHAR(100) DEFAULT 'Particular',
    email             VARCHAR(120) NOT NULL,
    fecha_registro    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- ----------------------------
--  Tabla: medicos
-- ----------------------------
CREATE TABLE IF NOT EXISTS medicos (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(80)  NOT NULL,
    apellido      VARCHAR(80)  NOT NULL,
    dni           VARCHAR(10)  NOT NULL UNIQUE,
    telefono      VARCHAR(20),
    especialidad  VARCHAR(100) NOT NULL,
    matricula     VARCHAR(30)  NOT NULL UNIQUE,
    honorarios    DECIMAL(10,2) DEFAULT 0.00,
    activo        TINYINT(1)   DEFAULT 1
);

-- ----------------------------
--  Tabla: turnos
-- ----------------------------
CREATE TABLE IF NOT EXISTS turnos (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id    INT          NOT NULL,
    medico_id      INT          NOT NULL,
    fecha_hora     DATETIME     NOT NULL,
    estado         ENUM('PENDIENTE','CONFIRMADO','ATENDIDO','CANCELADO','AUSENTE')
                                DEFAULT 'PENDIENTE',
    observaciones  TEXT,
    fecha_creacion TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_turno_paciente FOREIGN KEY (paciente_id)
        REFERENCES pacientes(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_turno_medico   FOREIGN KEY (medico_id)
        REFERENCES medicos(id)   ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ----------------------------
--  Indices para mejorar busquedas
-- ----------------------------
CREATE INDEX idx_turnos_fecha     ON turnos (fecha_hora);
CREATE INDEX idx_turnos_estado    ON turnos (estado);
CREATE INDEX idx_pacientes_dni    ON pacientes (dni);
CREATE INDEX idx_pacientes_apellido ON pacientes (apellido);

-- ----------------------------
--  Datos de prueba: medicos
-- ----------------------------
INSERT INTO medicos (nombre, apellido, dni, telefono, especialidad, matricula, honorarios) VALUES
('Carlos',    'Gomez',     '22334455', '2644100001', 'Clinica General', 'MN-12345', 8500.00),
('Maria',     'Fernandez', '33445566', '2644100002', 'Cardiologia',     'MN-23456', 12000.00),
('Roberto',   'Sanchez',   '44556677', '2644100003', 'Pediatria',       'MN-34567', 9500.00),
('Ana Laura', 'Torres',    '55667788', '2644100004', 'Traumatologia',   'MN-45678', 11000.00);

-- ----------------------------
--  Datos de prueba: pacientes
-- ----------------------------
INSERT INTO pacientes (nombre, apellido, dni, telefono, fecha_nacimiento, obra_social, email) VALUES
('Juan',      'Perez',      '12345678', '2644200001', '1985-03-15', 'OSDE',      'juan.perez@email.com'),
('Laura',     'Martinez',   '23456789', '2644200002', '1990-07-22', 'PAMI',      'laura.m@email.com'),
('Diego',     'Alvarez',    '34567890', '2644200003', '1978-11-30', 'Particular','diego.a@email.com'),
('Sofia',     'Rodriguez',  '45678901', '2644200004', '2000-01-10', 'Swiss Med', 'sofia.r@email.com');

-- ----------------------------
--  Datos de prueba: turnos
-- ----------------------------
INSERT INTO turnos (paciente_id, medico_id, fecha_hora, estado, observaciones) VALUES
(1, 1, DATE_ADD(NOW(), INTERVAL 1 DAY),  'PENDIENTE',  'Control anual'),
(2, 2, DATE_ADD(NOW(), INTERVAL 2 DAY),  'CONFIRMADO', 'Electrocardiograma de seguimiento'),
(3, 3, DATE_ADD(NOW(), INTERVAL 3 DAY),  'PENDIENTE',  'Primera consulta'),
(4, 4, DATE_ADD(NOW(), INTERVAL 5 DAY),  'PENDIENTE',  'Dolor en rodilla derecha'),
(1, 3, DATE_ADD(NOW(), INTERVAL 7 DAY),  'PENDIENTE',  'Control pediatrico hijo'),
(2, 1, DATE_ADD(NOW(), INTERVAL -1 DAY), 'ATENDIDO',   'Gripe estacional');

-- Verificacion
SELECT 'Base de datos clinica_db creada exitosamente.' AS mensaje;
SELECT COUNT(*) AS total_medicos   FROM medicos;
SELECT COUNT(*) AS total_pacientes FROM pacientes;
SELECT COUNT(*) AS total_turnos    FROM turnos;
