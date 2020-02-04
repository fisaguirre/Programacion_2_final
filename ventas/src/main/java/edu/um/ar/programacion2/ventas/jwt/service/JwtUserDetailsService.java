package edu.um.ar.programacion2.ventas.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import edu.um.ar.programacion2.ventas.model.Usuario;
import edu.um.ar.programacion2.ventas.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Usuario> buscarUsuario = usuarioRepository.findByUsername(username);

		if (buscarUsuario.isPresent()) {
			Usuario user = buscarUsuario.get();
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					new ArrayList<>());
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
		/*
		 * if (user == null) { throw new
		 * UsernameNotFoundException("User not found with username: " + username); }
		 * return new
		 * org.springframework.security.core.userdetails.User(user.getUsername(),
		 * user.getPassword(), new ArrayList<>()); }
		 */
	}
}
