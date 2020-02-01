package edu.um.ar.programacion2.ventas.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.um.ar.programacion2.ventas.model.Usuario;
import edu.um.ar.programacion2.ventas.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/*
	public Usuario findByUsername(String username) {
		Optional<Usuario> userToFind = this.usuarioRepository.findByUsername(username);
		if (userToFind.isPresent()) {
			return userToFind.get();
		} else {
			return null;
		}
	}

	public Usuario findById(Integer id) {
		Optional<Usuario> userToFind = this.usuarioRepository.findById(id);
		if (userToFind.isPresent()) {
			return userToFind.get();
		} else {
			return null;
		}
	}

	public List<Usuario> findAll() {
		return this.usuarioRepository.findAll();
	}

	public Usuario save(Usuario user) {
		System.out.println("USER PARAMETER: " + user.toString());
		Usuario savedUser = this.usuarioRepository.save(user);
		System.out.println("SAVED USER: " + savedUser.toString());
		return this.usuarioRepository.save(user);

	}
*/
}
