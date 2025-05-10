package com.pruebatecnica.castores.inventario.security;

import com.pruebatecnica.castores.inventario.model.Usuario;
import com.pruebatecnica.castores.inventario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
            usuario.getCorreo(),
            usuario.getContrasena(),
            Collections.singleton(() -> "ROLE_" + usuario.getRol().toUpperCase())
        );
    }
}