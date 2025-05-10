package com.pruebatecnica.castores.inventario.controller;

import com.pruebatecnica.castores.inventario.model.Producto;
import com.pruebatecnica.castores.inventario.repository.ProductoRepository;
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
}