package com.pruebatecnica.castores.inventario.repository;

import com.pruebatecnica.castores.inventario.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
    List<Movimiento> findByTipoMovimiento(String tipoMovimiento);
}
