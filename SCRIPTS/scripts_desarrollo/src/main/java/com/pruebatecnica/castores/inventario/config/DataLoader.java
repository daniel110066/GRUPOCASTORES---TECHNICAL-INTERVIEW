package com.pruebatecnica.castores.inventario.config;

import com.pruebatecnica.castores.inventario.model.Rol;
import com.pruebatecnica.castores.inventario.model.Usuario;
import com.pruebatecnica.castores.inventario.repository.RolRepository;
import com.pruebatecnica.castores.inventario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataLoader {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${almacenista.email}")
    private String almacenistaEmail;

    @Value("${almacenista.password}")
    private String almacenistaPassword;

    @Bean
    public CommandLineRunner loadUsuarios(UsuarioRepository usuarioRepo, RolRepository rolRepo, BCryptPasswordEncoder encoder) {
        return args -> {
            Rol rolAdmin = rolRepo.findByNombreRol("ADMINISTRADOR")
                    .orElseThrow(() -> new RuntimeException("Rol ADMINISTRADOR no encontrado"));
            Rol rolAlmacenista = rolRepo.findByNombreRol("ALMACENISTA")
                    .orElseThrow(() -> new RuntimeException("Rol ALMACENISTA no encontrado"));

            if (usuarioRepo.findByCorreo(adminEmail).isEmpty()) {
                usuarioRepo.save(new Usuario(
                        "Administrador",
                        adminEmail,
                        encoder.encode(adminPassword),
                        rolAdmin
                ));
                System.out.println("Usuario ADMINISTRADOR creado: " + adminEmail);
            }

            if (usuarioRepo.findByCorreo(almacenistaEmail).isEmpty()) {
                usuarioRepo.save(new Usuario(
                        "Almacenista",
                        almacenistaEmail,
                        encoder.encode(almacenistaPassword),
                        rolAlmacenista
                ));
                System.out.println("Usuario ALMACENISTA creado: " + almacenistaEmail);
            }
        };
    }
}