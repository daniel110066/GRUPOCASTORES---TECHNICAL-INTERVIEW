package com.pruebatecnica.castores.inventario.controller;

import com.pruebatecnica.castores.inventario.model.Producto;
import com.pruebatecnica.castores.inventario.model.Usuario;
import com.pruebatecnica.castores.inventario.model.Movimiento;
import com.pruebatecnica.castores.inventario.repository.MovimientoRepository;
import com.pruebatecnica.castores.inventario.repository.ProductoRepository;
import com.pruebatecnica.castores.inventario.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class InventarioController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/inventario")
    public String mostrarInventario(Model model, Authentication auth) {
        List<Producto> productos = productoRepository.findAll();
        String rol = auth.getAuthorities().iterator().next().getAuthority(); // ADMINISTRADOR o ALMACENISTA

        model.addAttribute("productos", productos);
        model.addAttribute("rol", rol);
        model.addAttribute("usuario", auth.getName());
        return "inventario";
    }

    @PostMapping("/inventario/entrada")
    public String entradaInventario(@RequestParam Integer idProducto,
                                    @RequestParam Integer cantidad,
                                    Authentication auth) {

        if (cantidad <= 0) {
            return "redirect:/inventario?error=cantidadInvalida";
        }

        Producto producto = productoRepository.findById(idProducto).orElse(null);
        if (producto == null) {
            return "redirect:/inventario?error=productoNoEncontrado";
        }

        System.out.println("Producto encontrado: " + producto.getIdProducto());

        // Aumentar inventario
        producto.setCantidad(producto.getCantidad() + cantidad);
        productoRepository.save(producto);
        productoRepository.flush(); // Forzar sincronización con la BD
        System.out.println("Producto actualizado");

        // Obtener usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElse(null);
        if (usuario == null) {
            return "redirect:/inventario?error=usuarioNoEncontrado";
        }

        System.out.println("Usuario encontrado: " + usuario.getCorreo());

        // Registrar movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setProducto(producto);
        movimiento.setCantidad(cantidad);
        movimiento.setTipoMovimiento("entrada");
        movimiento.setUsuario(usuario);
        movimiento.setFechaHora(LocalDateTime.now());

        movimientoRepository.save(movimiento);
        System.out.println("Movimiento guardado");

        return "redirect:/inventario?success=entrada";
    }
}