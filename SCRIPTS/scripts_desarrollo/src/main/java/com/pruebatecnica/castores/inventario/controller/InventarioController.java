package com.pruebatecnica.castores.inventario.controller;

import com.pruebatecnica.castores.inventario.model.Movimiento;
import com.pruebatecnica.castores.inventario.model.Producto;
import com.pruebatecnica.castores.inventario.repository.MovimientoRepository;
import com.pruebatecnica.castores.inventario.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class InventarioController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @GetMapping("/inventario")
    public String mostrarInventario(Model model, Authentication auth) {
        List<Producto> productos = productoRepository.findAll();

        List<Producto> activos = productos.stream()
                .filter(p -> p.getEstatus() == 1)
                .toList();

        List<Producto> inactivos = productos.stream()
                .filter(p -> p.getEstatus() == 0)
                .toList();

        List<Movimiento> movimientos = movimientoRepository.findAll();

        String rol = auth.getAuthorities().iterator().next().getAuthority();

        model.addAttribute("productos", productos);
        model.addAttribute("activos", activos);
        model.addAttribute("inactivos", inactivos);
        model.addAttribute("movimientos", movimientos);
        model.addAttribute("rol", rol);
        model.addAttribute("usuario", auth.getName());

        return "inventario";
    }
}