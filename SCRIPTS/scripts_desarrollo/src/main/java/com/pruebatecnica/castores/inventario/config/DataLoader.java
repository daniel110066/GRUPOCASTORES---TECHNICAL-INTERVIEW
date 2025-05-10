package com.pruebatecnica.castores.inventario.config;

import com.pruebatecnica.castores.inventario.model.Usuario;
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
    public CommandLineRunner loadUsuarios(UsuarioRepository repo, BCryptPasswordEncoder encoder) {
        return args -> {

            if (repo.findByCorreo(adminEmail).isEmpty()) {
                repo.save(new Usuario(
                        "Administrador",
                        adminEmail,
                        encoder.encode(adminPassword),
                        "ADMINISTRADOR"
                ));
                System.out.println("Usuario ADMINISTRADOR creado: " + adminEmail);
            }

            if (repo.findByCorreo(almacenistaEmail).isEmpty()) {
                repo.save(new Usuario(
                        "Almacenista",
                        almacenistaEmail,
                        encoder.encode(almacenistaPassword),
                        "ALMACENISTA"
                ));
                System.out.println("Usuario ALMACENISTA creado: " + almacenistaEmail);
            }
        };
    }
}