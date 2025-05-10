package com.pruebatecnica.castores.inventario.controller;

import com.pruebatecnica.castores.inventario.model.Producto;
import com.pruebatecnica.castores.inventario.repository.ProductoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        if (productoRepository.existsByNombreIgnoreCase(producto.getNombre())) {
            return "redirect:/inventario?error=duplicado";
        }

        try {
            producto.setCantidad(0);
            productoRepository.save(producto);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "redirect:/inventario?success=true";
    }

    @PostMapping("/baja")
    public String darDeBajaProductos(@RequestParam("idProducto") List<Long> ids) {
        for (Long id : ids) {
            productoRepository.findById(id).ifPresent(p -> {
                p.setEstatus(0);
                productoRepository.save(p);
            });
        }
        return "redirect:/inventario?success=baja";
    }

    @PostMapping("/alta")
    public String darDeAltaProductos(@RequestParam("idProducto") List<Long> ids) {
        for (Long id : ids) {
            productoRepository.findById(id).ifPresent(p -> {
                p.setEstatus(1);
                productoRepository.save(p);
            });
        }
        return "redirect:/inventario?success=alta";
    }
}