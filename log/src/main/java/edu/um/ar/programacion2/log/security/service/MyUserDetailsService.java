package edu.um.ar.programacion2.log.security.service;

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

import edu.um.ar.programacion2.log.repository.UsuarioRepository;
import edu.um.ar.programacion2.log.model.Usuario;

import java.util.ArrayList;

@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//ResponseEntity<String> user = verificarUsuario(username, token);
		
		Usuario user = usuarioRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
	/*
	public ResponseEntity<String> verificarUsuario(String username, String token) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8300/user/verificar/"+username;
		MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", token);
	
		HttpEntity request = new HttpEntity("", headers);
	
		final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		
		if(exchange.getBody()!=null) {
			return new ResponseEntity<String>(exchange.getBody(),exchange.getStatusCode());
		}
		return null;
		
	}
	*/
	/*

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
