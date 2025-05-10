# [Evaluación Técnica - Castores 2024](https://github.com/tu-usuario/tu-repositorio)

## Autor

- **Nombre:** Daniel Hidalgo Badillo  
- **Correo:** hidalgobadillodaniel@gmail.com 
- **Fecha de entrega:** 2025/05/09

## [📄 Instrucciones de la Evaluación Técnica](./Evaluación%20Técnica%20Castores%202024%20-%20Desarrollador.pdf)

## Contenido

1. [**Parte 1:** *CONOCIMIENTOS SQL*](#1-conocimientos-sql)
2. [**Parte 2:** *EJERCICIO PRÁCTICO: BD*](#2-ejercicio-práctico-bd)
3. [**Parte 3:** *EJERCICIO PRÁCTICO: DESARROLLO*](#3-ejercicio-práctico-desarrollo)
4. [**Parte 4:** *DOCUMENTACIÓN*](#4-documentación)

## 1. CONOCIMIENTOS SQL

**1.1** ¿Cuál es el funcionamiento general de la sentencia `JOIN`?  
> Su función es combinar registros de dos o más tablas, usando una regla lógica que normalmente es una relación entre una `PRIMARY KEY` y una `FOREIGN KEY`. Creando un conjunto de datos relacionados con columnas de otras tablas, permitiendo visualizar o acceder de forma clara a una tabla más completa según la consulta.

**1.2** ¿Cuáles son los tipos de `JOIN` y cómo funcionan?  
> Son 4 tipos principales: `INNER JOIN`, `FULL OUTER JOIN`, `RIGHT JOIN` y `LEFT JOIN`. Cada una tiene una función:
> - **INNER JOIN** → Devuelve solo las filas que tienen coincidencias en ambas tablas según la condición establecida.
> - **FULL OUTER JOIN** → Devuelve todas las filas de ambas tablas, aunque no haya coincidencias.
> - **RIGHT JOIN** → Devuelve todas las filas de la tabla derecha y las coincidencias de la tabla izquierda.
> - **LEFT JOIN** → Devuelve todas las filas de la tabla izquierda y las coincidencias de la tabla derecha.

**1.3** ¿Qué es un `TRIGGER` y para qué sirve?  
> Un *TRIGGER* es una función activadora que responde a un evento específico en una base de datos, como operaciones de `INSERT`, `UPDATE` o `DELETE`. Se utiliza para validar datos, generar registros automáticos o sincronizar tablas, contribuyendo a mantener la integridad de la base de datos.

**1.4** ¿Qué es un `STORED PROCEDURE`?  
> Un *STORE PROCEDURE* o *PROCEDIMIENTO ALMACENADO* es un conjunto ejecutable de instrucciones SQL predefinidas y almacenadas en la base de datos. Se utiliza para encapsular lógica compleja o repetitiva, y ofrece el beneficio adicional de permitir su reutilización sin exponer directamente el acceso a la base de datos, lo que es de grán ayuda en temas de seguridad.

**1.5** Consulta para traer todos los productos que tengan una venta.
```sql
SELECT DISTINCT productos.* from productos
JOIN ventas ON productos.idProducto = ventas.idProducto;
```

**1.6** Traer todos los productos que tengan ventas y la cantidad total de productos vendidos.
```sql
SELECT productos.idProducto, productos.nombre, SUM(ventas.cantidad) AS cantidad_total FROM productos
JOIN ventas ON productos.idProducto = ventas.idProducto GROUP BY productos.idProducto;
```

**1.7** Traer todos los productos (independientemente de si tienen ventas o no) y la suma total ($) vendida por producto.
```sql
SELECT productos.idProducto, productos.nombre, COALESCE(SUM(ventas.cantidad*productos.precio), 0)
AS suma_total FROM productos
LEFT JOIN ventas ON productos.idProducto = ventas.idProducto GROUP BY productos.idProducto;
```

## 2. EJERCICIO PRÁCTICO: BD

**2.1** Crea un `diagrama relacional` de BD para el escenario descrito anteriormente. 
<div class="mermaid-container" style="padding: 20px;">
  <div class="mermaid">
    erDiagram
        usuarios ||--o{ movimientos : realiza
        roles ||--o{ usuarios : tiene
        productos ||--o{ movimientos : afecta

        roles {
            INT idRol PK
            VARCHAR nombreRol
        }

        usuarios {
            INT idUsuario PK
            VARCHAR nombre
            VARCHAR correo
            VARCHAR contrasena
            INT idRol FK
            INT estatus
        }

        productos {
            INT idProducto PK
            VARCHAR nombre
            TEXT descripcion
            INT cantidad
            INT estatus
        }

        movimientos {
            INT idMovimiento PK
            INT idProducto FK
            ENUM tipoMovimiento
            INT cantidad
            DATETIME fechaHora
            INT idUsuario FK
        }
  </div>
</div>

**2.2** Hacer el `script` para crear las tablas del punto anterior
> [➡️ script_base_de_datos.sql](./SCRIPTS/script_base_de_datos.sql)

## 3. EJERCICIO PRÁCTICO: DESARROLLO

**3.1**  Crear una aplicación WEB para el escenario que se planteó previamente.
- Preferiblemente utilizar el diseño MVC para su desarrollo.
- Preferiblemente utilizar JAVA.
- Utilizar MySQL o SQLServer.

## 4. DOCUMENTACIÓN



