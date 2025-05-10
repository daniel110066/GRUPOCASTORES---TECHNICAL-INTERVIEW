package com.pruebatecnica.castores.inventario.repository;

import com.pruebatecnica.castores.inventario.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
}