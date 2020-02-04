package edu.um.ar.programacion2.tarjetacredito.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.um.ar.programacion2.tarjetacredito.repository.UsuarioRepository;
import edu.um.ar.programacion2.tarjetacredito.service.UsuarioService;

//import com.netflix.config.validation.ValidationException;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
//@RequestMapping("/usuario")
@RequestMapping("/user")
public class UsuarioController {

	@Autowired
	private AuthenticationManager authenticationManager;
/*
	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
*/
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	/*
	 @PostMapping("")
	    public Boolean create(@RequestBody Map<String, String> body) throws NoSuchAlgorithmException {
		 System.out.println("el nombre es: "+body.get("username"));
	        String username = body.get("username");
	        if (usuarioRepository.existsByUsername(username)){

	            throw new ValidationException("Username already existed");

	        }

	        String password = body.get("password");
	        String encodedPassword = new BCryptPasswordEncoder().encode(password);
//	        String hashedPassword = hashData.get_SHA_512_SecurePassword(password);
	        String fullname = body.get("fullname");
	        usuarioRepository.save(new Usuario(username, encodedPassword, fullname));
	        return true;
	    }
	    */
/*
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(this.userService.findAll());
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getAllUsers(@PathVariable("userId") UUID id) {
		return ResponseEntity.ok(this.userService.findById(id));
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User newUser = this.userService.save(user);
		return ResponseEntity.ok(newUser);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
*/
}
