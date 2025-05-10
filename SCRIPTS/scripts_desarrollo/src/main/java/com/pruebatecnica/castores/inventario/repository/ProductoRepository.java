package com.pruebatecnica.castores.inventario.repository;

import com.pruebatecnica.castores.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean existsByNombreIgnoreCase(String nombre);
}
