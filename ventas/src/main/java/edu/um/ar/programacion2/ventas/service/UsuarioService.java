package edu.um.ar.programacion2.ventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.um.ar.programacion2.ventas.dto.UsuarioDto;
import edu.um.ar.programacion2.ventas.jwt.exceptions.ValidationException;
import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.Usuario;
import edu.um.ar.programacion2.ventas.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<UsuarioDto> findAll() {
		List<Usuario> list = usuarioRepository.findAll();
		List<UsuarioDto> usuarioList = new ArrayList<UsuarioDto>();
		for (Usuario usuario : list) {
			UsuarioDto usuarioDto = new UsuarioDto(usuario.getId(), usuario.getUsername(), usuario.getFullname(),
					usuario.getActivo());
			usuarioList.add(usuarioDto);
		}
		return usuarioList;
	}

	public ResponseEntity getByUsername(String username) {
		Optional<Usuario> buscarUsuario = usuarioRepository.findByUsername(username);
		if (buscarUsuario.isPresent()) {
			Usuario usuarioEncontrado = buscarUsuario.get();
			return new ResponseEntity<>(usuarioEncontrado, HttpStatus.OK);
		}
		return new ResponseEntity<>("El usuario no esta registrado", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity createUsuario(Map<String, String> body) {
		if (usuarioExists(body.get("username"))) {
			return new ResponseEntity<String>("Ya existe un usuario con esos datos", HttpStatus.BAD_REQUEST);
		}
		String encodedPassword = new BCryptPasswordEncoder().encode(body.get("password"));
		Usuario crearUsuario = new Usuario(body.get("username"), encodedPassword, body.get("fullname"), true);
		usuarioRepository.save(crearUsuario);
		return new ResponseEntity<>("Usuario registrado", HttpStatus.OK);
	}

	public ResponseEntity deleteUsuario(String username) {
		Optional<Usuario> buscarUsuario = usuarioRepository.findByUsername(username);
		if (buscarUsuario.isPresent()) {
			Usuario usuarioEncontrado = buscarUsuario.get();
			if (usuarioEncontrado.getActivo()) {
				Usuario eliminarUsuario = new Usuario(usuarioEncontrado.getId(), usuarioEncontrado.getUsername(),
						usuarioEncontrado.getPassword(), usuarioEncontrado.getFullname(), false);
				usuarioRepository.save(eliminarUsuario);
				return new ResponseEntity<>("Se cambio el usuario a inactivo", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("El usuario ya se encuentra inactivo", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>("El usuario no se encuentra registrado", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Object> updateUsuario(Usuario usuario) {
		Optional<Usuario> buscarUsuario = usuarioRepository.findByUsername(usuario.getUsername());
		if (buscarUsuario.isPresent()) {
			Usuario datosUsuario = buscarUsuario.get();
			Usuario actualizarUsuario = new Usuario(datosUsuario.getId(), usuario.getUsername(), usuario.getPassword(),
					usuario.getFullname(), usuario.getActivo());

			if ((actualizarUsuario.getUsername().equals(datosUsuario.getUsername()))) {
				usuarioRepository.save(actualizarUsuario);
				return new ResponseEntity<>("Usuario actualizado", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Verifique que el nombre de usuario este correcto", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>("No se encontro ningun usuario con ese nombre", HttpStatus.BAD_REQUEST);
		}
	}

	public boolean usuarioExists(String username) {
		boolean usuarioExists = usuarioRepository.existsByUsername(username);
		return usuarioExists;
	}
	/*
	 * public Usuario verificarUsuario(String username) { //Optional<Usuario>
	 * userToFind = this.usuarioRepository.buscarPorUsername(username); Usuario
	 * userToFind = this.usuarioRepository.findByUsername(username); return
	 * userToFind; }
	 */

}
