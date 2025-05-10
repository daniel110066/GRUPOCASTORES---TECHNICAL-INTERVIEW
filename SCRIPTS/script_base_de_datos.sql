
#DROP DATABASE IF EXISTS technical_task_castores;
CREATE DATABASE technical_task_castores;
USE technical_task_castores;

-- Tabla de roles
CREATE TABLE roles (
    id_rol INT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL
);

-- Datos iniciales para roles
INSERT INTO roles (id_rol, nombre_rol) VALUES
(1, 'Administrador'),
(2, 'Almacenista');

-- Tabla de usuarios
CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    estatus INT DEFAULT 1,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Tabla de productos
CREATE TABLE productos (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    cantidad INT DEFAULT 0,
    estatus INT DEFAULT 1 -- 1=Activo, 0=Inactivo
);

-- Tabla de movimientos
CREATE TABLE movimientos (
    id_movimiento BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    tipo_movimiento ENUM('entrada', 'salida') NOT NULL,
    cantidad INT NOT NULL,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Trigger para validar inventario en salidas
DELIMITER //

CREATE TRIGGER validar_salida
BEFORE INSERT ON movimientos
FOR EACH ROW
BEGIN
    DECLARE stock_actual INT;

    IF NEW.tipo_movimiento = 'salida' THEN
        SELECT cantidad INTO stock_actual FROM productos WHERE id_producto = NEW.id_producto;

        IF stock_actual < NEW.cantidad THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: No hay suficiente inventario para esta salida';
        END IF;
    END IF;
END;
//

DELIMITER ;