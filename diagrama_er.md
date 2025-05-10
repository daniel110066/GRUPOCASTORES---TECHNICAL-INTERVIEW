```mermaid
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
```