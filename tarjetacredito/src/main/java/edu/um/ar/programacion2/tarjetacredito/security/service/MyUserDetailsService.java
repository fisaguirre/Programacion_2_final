package edu.um.ar.programacion2.tarjetacredito.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import edu.um.ar.programacion2.tarjetacredito.model.Usuario;
import edu.um.ar.programacion2.tarjetacredito.repository.UsuarioRepository;

import java.util.ArrayList;

@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	//@Override
	public UserDetails loadUserByUsername(String username, String token) throws UsernameNotFoundException {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8300/user/verificar/"+username;
		MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", token);
	//
		HttpEntity request = new HttpEntity("", headers);
	//
		System.out.println("antes de mandar");
		final ResponseEntity<Usuario> exchange = restTemplate.exchange(url, HttpMethod.GET, request, Usuario.class);
		
		System.out.println("esto es userdetails");
		
		
		System.out.println("lo que se extrajo de servicio tarjeta en metodo de busqueda de usuario es: ");
		System.out.println("nombre usuario: "+exchange.getBody().getUsername());
		System.out.println(exchange.getStatusCodeValue());
		

		Usuario user = usuarioRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
