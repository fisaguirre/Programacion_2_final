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
			UsuarioDto usuarioDto = new UsuarioDto(usuario.getId(), usuario.getUsername(), usuario.getFullname(), usuario.getActivo());
			usuarioList.add(usuarioDto);
		}
		return usuarioList;
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
	/*
	public ResponseEntity<Object> updateCliente(Cliente cliente) {
		Optional<Cliente> optionalCliente = this.findById(cliente.getId());
		if (optionalCliente.isPresent()) {
			Cliente datosCliente = optionalCliente.get();
			Cliente actualizarCliente = new Cliente(datosCliente.getId(), cliente.getNombre(), cliente.getApellido(),
					cliente.getActivo());

			if ((actualizarCliente.getNombre().equals(datosCliente.getNombre()))
					&& (actualizarCliente.getApellido().equals(datosCliente.getApellido()))) {
				clienteRepository.save(actualizarCliente);
				return new ResponseEntity<>("Cliente actualizado", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Verifique que el nombre y apellido sean correctos",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>("No se encontro ningun cliente con esa ID", HttpStatus.BAD_REQUEST);
			// return ResponseEntity.notFound().build();
		}
	}
*/
	public boolean usuarioExists(String username) {
		boolean usuarioExists = usuarioRepository.existsByUsername(username);
		return usuarioExists;
	}
/*
	public Usuario verificarUsuario(String username) {
		//Optional<Usuario> userToFind = this.usuarioRepository.buscarPorUsername(username);
		Usuario userToFind = this.usuarioRepository.findByUsername(username);
		return userToFind;
	}
*/

	/*
	public Usuario findByUsername(String username) {
		Optional<Usuario> userToFind = this.usuarioRepository.findByUsername(username);
		if (userToFind.isPresent()) {
			return userToFind.get();
		} else {
			return null;
		}
	}

	*/

	

	
}
