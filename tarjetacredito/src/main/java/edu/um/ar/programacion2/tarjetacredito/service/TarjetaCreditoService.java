package edu.um.ar.programacion2.tarjetacredito.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.transaction.annotation.Transactional;

import edu.um.ar.programacion2.tarjetacredito.model.Cliente;
import edu.um.ar.programacion2.tarjetacredito.model.TarjetaCredito;
import edu.um.ar.programacion2.tarjetacredito.objeto.TarjetaCreditoObjeto;
import edu.um.ar.programacion2.tarjetacredito.repository.TarjetaCreditoRepository;

@Service
public class TarjetaCreditoService {

	@Autowired
	private TarjetaCreditoRepository tarjetacreditoRepository;

	@Autowired
	private ClienteService clienteService;

	public List<TarjetaCreditoObjeto> findAll() {
		List<TarjetaCredito> list = tarjetacreditoRepository.findAll();
		List<TarjetaCreditoObjeto> tarjetaList = new ArrayList<TarjetaCreditoObjeto>();
		for (TarjetaCredito tarjeta : list) {
			TarjetaCreditoObjeto tarjetaObj = new TarjetaCreditoObjeto(tarjeta.getId(), tarjeta.getTipo(),
					tarjeta.getNumero(), tarjeta.getCodseguridad(), tarjeta.getVencimiento(), tarjeta.getMontomaximo(),
					tarjeta.getCliente_id().getId());
			/*
			 * TarjetaCreditoObjeto tarjetaObj = new TarjetaCreditoObjeto(tarjeta.getId(),
			 * tarjeta.getTipo(), tarjeta.getNumero(), tarjeta.getCodseguridad(),
			 * tarjeta.getVencimiento(), tarjeta.getMontomaximo(), tarjeta.getToken(),
			 * tarjeta.getCliente_id().getId());
			 */
			tarjetaList.add(tarjetaObj);
		}
		return tarjetaList;
	}

	public ResponseEntity<String> findTokenByNumero(Integer numero) {
		Optional<TarjetaCredito> optionalTarjeta = tarjetacreditoRepository.findByNumero(numero);
		if (optionalTarjeta.isPresent()) {
			TarjetaCredito tar = optionalTarjeta.get();
			return new ResponseEntity<String>(tar.getToken(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("0", HttpStatus.BAD_REQUEST);
	}

	public TarjetaCreditoObjeto findById(Long id) {
		Optional<TarjetaCredito> optionalTarjeta = tarjetacreditoRepository.findById(id);
		TarjetaCreditoObjeto tarjetaObj = new TarjetaCreditoObjeto();
		if (optionalTarjeta.isPresent()) {
			TarjetaCredito tar = optionalTarjeta.get();
			tarjetaObj.setId(tar.getId());
			tarjetaObj.setTipo(tar.getTipo());
			tarjetaObj.setNumero(tar.getNumero());
			tarjetaObj.setCodseguridad(tar.getCodseguridad());
			tarjetaObj.setVencimiento(tar.getVencimiento());
			tarjetaObj.setMontomaximo(tar.getMontomaximo());
			// tarjetaObj.setToken(tar.getToken());
			tarjetaObj.setCliente_id(tar.getCliente_id().getId());
		}
		return tarjetaObj;
	}

	public boolean tarjeta_existente(Long id) {
		return tarjetacreditoRepository.existsById(id);
	}

	public ResponseEntity<String> createTarjetaCredito(TarjetaCreditoObjeto tarjetaObj) {
		if (this.tarjetaExistenteByNumero(tarjetaObj.getNumero())) {
			return new ResponseEntity<String>("Ya exixte una tarjeta con ese numero", HttpStatus.BAD_REQUEST);
		}
		
		Optional<Cliente> optionalCliente = clienteService.findById(tarjetaObj.getCliente_id());
		if (optionalCliente.isPresent()) {
			Cliente cliente = optionalCliente.get();
			Cliente cliente_encontrado = new Cliente(cliente.getId(), cliente.getNombre(), cliente.getApellido());

			String token = this.convertirSHA256(cliente_encontrado.getNombre());

			TarjetaCredito tarjetaCredito = new TarjetaCredito(tarjetaObj.getTipo(), tarjetaObj.getNumero(),
					tarjetaObj.getCodseguridad(), tarjetaObj.getVencimiento(), tarjetaObj.getMontomaximo(),
					cliente_encontrado, token);
			TarjetaCredito tarjeta_creada = tarjetacreditoRepository.save(tarjetaCredito);

			TarjetaCreditoObjeto tarjetaCredObj = new TarjetaCreditoObjeto(tarjeta_creada.getId(),
					tarjeta_creada.getTipo(), tarjeta_creada.getNumero(), tarjeta_creada.getCodseguridad(),
					tarjeta_creada.getVencimiento(), tarjeta_creada.getMontomaximo(),
					tarjeta_creada.getCliente_id().getId());

			return new ResponseEntity<String>(token, HttpStatus.OK);
		}
		return new ResponseEntity<String>("El cliente no esta registrado", HttpStatus.BAD_REQUEST);
	}
	
	public boolean tarjetaExistenteByNumero(Integer numero) {
		return tarjetacreditoRepository.existsByNumero(numero);
	}

	public String convertirSHA256(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

		byte[] hash = md.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();

		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

}