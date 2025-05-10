package com.pruebatecnica.castores.inventario.controller;

import com.pruebatecnica.castores.inventario.model.Producto;
import com.pruebatecnica.castores.inventario.model.Usuario;
import com.pruebatecnica.castores.inventario.model.Movimiento;
import com.pruebatecnica.castores.inventario.repository.ProductoRepository;
import com.pruebatecnica.castores.inventario.repository.MovimientoRepository;
import com.pruebatecnica.castores.inventario.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class MovimientoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/movimiento/entrada")
    public String registrarEntrada(@RequestParam Long idProducto,
                                   @RequestParam Integer cantidad,
                                   Authentication auth) {

        if (cantidad <= 0) {
            return "redirect:/inventario?error=cantidadInvalida";
        }

        Producto producto = productoRepository.findById(idProducto).orElse(null);
        if (producto == null) {
            return "redirect:/inventario?error=productoNoEncontrado";
        }

        producto.setCantidad(producto.getCantidad() + cantidad);
        productoRepository.saveAndFlush(producto);

        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElse(null);
        if (usuario == null) {
            return "redirect:/inventario?error=usuarioNoEncontrado";
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setProducto(producto);
        movimiento.setCantidad(cantidad);
        movimiento.setTipoMovimiento("entrada");
        movimiento.setUsuario(usuario);
        movimiento.setFechaHora(LocalDateTime.now());

        movimientoRepository.save(movimiento);

        return "redirect:/inventario?success=entrada";
    }

    @PostMapping("/movimiento/salida")
    public String registrarSalida(@RequestParam Long idProducto,
                                  @RequestParam Integer cantidad,
                                  Authentication auth) {

        if (cantidad <= 0) {
            return "redirect:/inventario?error=cantidadInvalida";
        }

        Producto producto = productoRepository.findById(idProducto).orElse(null);
        if (producto == null || producto.getCantidad() < cantidad) {
            return "redirect:/inventario?error=inventarioInsuficiente";
        }

        producto.setCantidad(producto.getCantidad() - cantidad);
        productoRepository.saveAndFlush(producto);

        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElse(null);
        if (usuario == null) {
            return "redirect:/inventario?error=usuarioNoEncontrado";
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setProducto(producto);
        movimiento.setCantidad(cantidad);
        movimiento.setTipoMovimiento("salida");
        movimiento.setUsuario(usuario);
        movimiento.setFechaHora(LocalDateTime.now());

        movimientoRepository.save(movimiento);

        return "redirect:/inventario?success=salida";
    }
}