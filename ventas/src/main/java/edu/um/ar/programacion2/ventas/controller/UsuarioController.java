package edu.um.ar.programacion2.ventas.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.um.ar.programacion2.ventas.dto.TarjetaCreditoDto;
import edu.um.ar.programacion2.ventas.dto.UsuarioDto;
import edu.um.ar.programacion2.ventas.jwt.exceptions.ValidationException;
import edu.um.ar.programacion2.ventas.model.Cliente;

//import com.netflix.config.validation.ValidationException;

import edu.um.ar.programacion2.ventas.model.Usuario;
import edu.um.ar.programacion2.ventas.repository.UsuarioRepository;
import edu.um.ar.programacion2.ventas.service.UsuarioService;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsuarioController {

	@Autowired
	private AuthenticationManager authenticationManager;
	/*
	 * @Autowired private JwtUtil jwtTokenUtil;
	 * 
	 * @Autowired private MyUserDetailsService userDetailsService;
	 */
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("")
	public ResponseEntity<List<UsuarioDto>> getAllTarjetaCredito() {
		return ResponseEntity.ok(usuarioService.findAll());
	}

	@PostMapping("")
	public ResponseEntity create(@RequestBody Map<String, String> body) throws NoSuchAlgorithmException {
		return ResponseEntity.ok(usuarioService.createUsuario(body));
	}

	@DeleteMapping(value = "{usernameToDelete}")
	public ResponseEntity<ResponseEntity> deleteCliente(@PathVariable("usernameToDelete") String username) {
		return ResponseEntity.ok(usuarioService.deleteUsuario(username));
	}
/*
	@PutMapping
	public ResponseEntity<Cliente> updateUsuario(@RequestBody Map<String, String> body) {
		return ResponseEntity.ok(usuarioService.updateUsuario(body));
	}
*/
	 /*
	@GetMapping("/verificar/{username}")
	public ResponseEntity<Usuario> verificarUsusuario(@PathVariable String username) {
		return ResponseEntity.ok(usuarioService.verificarUsuario(username));
	}
	*/

}
