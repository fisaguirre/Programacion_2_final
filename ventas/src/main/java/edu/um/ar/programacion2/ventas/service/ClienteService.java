package edu.um.ar.programacion2.ventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.transaction.annotation.Transactional;

import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.repository.ClienteRepository;
import edu.um.ar.programacion2.ventas.repository.VentasRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Cliente> findById(Long id) {
		// log.debug("Request to get Ventas : {}", id);
		return clienteRepository.findById(id);
	}

	public ResponseEntity createCliente(Cliente cliente) {
		if (this.verify_nombre_apellido(cliente.getNombre(), cliente.getApellido())) {
			return new ResponseEntity<String>("Ya existe un cliente con esos datos", HttpStatus.BAD_REQUEST);
		}
		Cliente cliente_creado = clienteRepository.save(cliente);
		return new ResponseEntity<>("Cliente registrado --- Su ID es: " + cliente_creado.getId(), HttpStatus.OK);
	}

	public ResponseEntity getClienteByNombreApellido(String nombre, String apellido) {
		if (!verify_nombre_apellido(nombre, apellido)) {
			return new ResponseEntity<>("0", HttpStatus.OK);
		}
		Cliente cliente_encontrado = clienteRepository.findByNombreAndApellido(nombre, apellido);
		return new ResponseEntity<>("ID: " + cliente_encontrado.getId(), HttpStatus.OK);
	}

	public boolean verify_nombre_apellido(String nombre, String apellido) {
		boolean verificar_cliente = clienteRepository.existsByNombreAndApellido(nombre, apellido);
		return verificar_cliente;
	}

	public boolean exist(Long id) {
		return clienteRepository.existsById(id);
	}

	public ResponseEntity<String> deleteCliente(Long id) {
		Optional<Cliente> buscar_cliente = this.findById(id);
		if (buscar_cliente.isPresent()) {
			Cliente nuevo_cliente = buscar_cliente.get();
			if (nuevo_cliente.getActivo()) {
				Cliente inactivarCliente = new Cliente(nuevo_cliente.getId(), nuevo_cliente.getNombre(),
						nuevo_cliente.getApellido(), false);
				clienteRepository.save(inactivarCliente);
				return new ResponseEntity<String>("Se cambio el cliente a inactivo", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("El cliente ya se encuentra inactivo", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>("El cliente con esa ID no se encuentra registrado", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> updateCliente(Cliente cliente) {
		Optional<Cliente> optionalCliente = this.findById(cliente.getId());
		if (optionalCliente.isPresent()) {
			Cliente datosCliente = optionalCliente.get();
			Cliente actualizarCliente = new Cliente(datosCliente.getId(), cliente.getNombre(), cliente.getApellido(),
					cliente.getActivo());

			if ((actualizarCliente.getNombre().equals(datosCliente.getNombre()))
					&& (actualizarCliente.getApellido().equals(datosCliente.getApellido()))) {
				clienteRepository.save(actualizarCliente);
				return new ResponseEntity<String>("Cliente actualizado", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Verifique que el nombre y apellido sean correctos",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("No se encontro ningun cliente con esa ID", HttpStatus.BAD_REQUEST);
			// return ResponseEntity.notFound().build();
		}
	}

	public Cliente findByNombreApellido(String nombre, String apellido) {
		if (!verify_nombre_apellido(nombre, apellido)) {
			return null;
		}
		Cliente cliente_encontrado = clienteRepository.findByNombreAndApellido(nombre, apellido);
		return cliente_encontrado;
	}
}