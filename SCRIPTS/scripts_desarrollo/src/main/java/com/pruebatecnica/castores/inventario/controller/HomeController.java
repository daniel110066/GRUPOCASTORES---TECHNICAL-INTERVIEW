package com.pruebatecnica.castores.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirigir() {
        return "redirect:/login";
    }
}
