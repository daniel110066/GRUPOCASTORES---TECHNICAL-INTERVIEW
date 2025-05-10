-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS technical_task_castores;
USE technical_task_castores;

-- Tabla de roles
CREATE TABLE roles (
    idRol INT PRIMARY KEY,
    nombreRol VARCHAR(50) NOT NULL
);

-- Inserción de roles
INSERT INTO roles (idRol, nombreRol) VALUES
(1, 'Administrador'),
(2, 'Almacenista');

-- Tabla de usuarios (ya definida, incluida aquí para integridad)
CREATE TABLE usuarios (
    idUsuario INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(25) NOT NULL,
    idRol INT NOT NULL,
    estatus INT DEFAULT 1,
    FOREIGN KEY (idRol) REFERENCES roles(idRol)
);

-- Tabla de productos
CREATE TABLE productos (
    idProducto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    cantidad INT DEFAULT 0,
    estatus INT DEFAULT 1 -- 1=Activo, 0=Inactivo
);

-- Tabla de movimientos (entradas y salidas)
CREATE TABLE movimientos (
    idMovimiento INT AUTO_INCREMENT PRIMARY KEY,
    idProducto INT NOT NULL,
    tipoMovimiento ENUM('entrada', 'salida') NOT NULL,
    cantidad INT NOT NULL,
    fechaHora DATETIME DEFAULT CURRENT_TIMESTAMP,
    idUsuario INT NOT NULL,
    FOREIGN KEY (idProducto) REFERENCES productos(idProducto),
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
);

-- Trigger para validar que no se puede hacer una salida con inventario insuficiente
DELIMITER //

CREATE TRIGGER validar_salida
BEFORE INSERT ON movimientos
FOR EACH ROW
BEGIN
    DECLARE stock_actual INT;

    IF NEW.tipoMovimiento = 'salida' THEN
        SELECT cantidad INTO stock_actual FROM productos WHERE idProducto = NEW.idProducto;

        IF stock_actual < NEW.cantidad THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: No hay suficiente inventario para esta salida';
        END IF;
    END IF;
END;
//

DELIMITER ;