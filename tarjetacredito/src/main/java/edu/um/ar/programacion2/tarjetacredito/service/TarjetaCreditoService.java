package edu.um.ar.programacion2.tarjetacredito.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.transaction.annotation.Transactional;

import edu.um.ar.programacion2.tarjetacredito.dto.TarjetaCreditoDto;
import edu.um.ar.programacion2.tarjetacredito.model.Cliente;
import edu.um.ar.programacion2.tarjetacredito.model.TarjetaCredito;
import edu.um.ar.programacion2.tarjetacredito.repository.TarjetaCreditoRepository;

@Service
public class TarjetaCreditoService {

	@Autowired
	private TarjetaCreditoRepository tarjetacreditoRepository;

	@Autowired
	private ClienteService clienteService;

	public List<TarjetaCreditoDto> findAll() {
		List<TarjetaCredito> list = tarjetacreditoRepository.findAll();
		List<TarjetaCreditoDto> tarjetaList = new ArrayList<TarjetaCreditoDto>();
		for (TarjetaCredito tarjeta : list) {
			TarjetaCreditoDto tarjetaObj = new TarjetaCreditoDto(tarjeta.getId(), tarjeta.getTipo(),
					tarjeta.getNumero(), tarjeta.getCodseguridad(), tarjeta.getVencimiento(), tarjeta.getMontomaximo(),
					tarjeta.getCliente_id().getId(), tarjeta.getActivo());
			tarjetaList.add(tarjetaObj);
		}
		return tarjetaList;
	}

	public TarjetaCredito findTarjetaCreditoByToken(String token) {
		Optional<TarjetaCredito> findTarjeta = tarjetacreditoRepository.findByToken(token);
		if (findTarjeta.isPresent()) {
			TarjetaCredito tarjetaEncontrada = findTarjeta.get();
			return tarjetaEncontrada;
		}
		return null;
	}

	public ResponseEntity verificarTarjetaHabilitada(String token) {
		Optional<TarjetaCredito> findTarjeta = tarjetacreditoRepository.findByToken(token);
		TarjetaCredito tarjetaEncontrada = findTarjeta.get();
		if (tarjetaEncontrada.getActivo()) {
			return new ResponseEntity<String>("La tarjeta se encuentra habilitada", HttpStatus.OK);
		}
		return new ResponseEntity<String>("La tarjeta no se encuentra habilitada", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity verificarPertenenciaTarjeta(String token, Long clienteId) {
		Optional<TarjetaCredito> findTarjeta = tarjetacreditoRepository.findByToken(token);
		TarjetaCredito tarjetaEncontrada = findTarjeta.get();
		if (tarjetaEncontrada.getCliente_id().getId().equals(clienteId)) {
			return new ResponseEntity<String>("La tarjeta le pertenece al cliente", HttpStatus.OK);
		}
		return new ResponseEntity<String>("La tarjeta no le pertenece al cliente", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity findTokenByNumero(Long numero) {
		Optional<TarjetaCredito> optionalTarjeta = tarjetacreditoRepository.findByNumero(numero);
		if (optionalTarjeta.isPresent()) {
			TarjetaCredito tar = optionalTarjeta.get();
			return new ResponseEntity<>(tar.getToken(), HttpStatus.OK);
		}
		return new ResponseEntity<>("0", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> verificarTarjeta(String token) {
		if (existsTarjetaByToken(token)) {
			return new ResponseEntity<String>("La tarjeta es valida", HttpStatus.OK);
		}
		return new ResponseEntity<String>("La tarjeta no se encuentra registrada", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> verificarVencimiento(String token) {
		Optional<TarjetaCredito> optionalTarjeta = findTarjetaByToken(token);
		TarjetaCredito tarjeta_encontrada = optionalTarjeta.get();

		Date fecha = this.TodayDate();

		if (fecha.equals(tarjeta_encontrada.getVencimiento()) || fecha.before(tarjeta_encontrada.getVencimiento())) {
			return new ResponseEntity<String>("La tarjeta es valida", HttpStatus.OK);
		}
		return new ResponseEntity<String>("La tarjeta se encuentra expirada", HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<String> verificarMontoTarjeta(Float monto, String token) {
		Optional<TarjetaCredito> optionalTarjeta = findTarjetaByToken(token);
		if (optionalTarjeta.isPresent()) {
			TarjetaCredito tarjeta_encontrada = optionalTarjeta.get();
			if (tarjeta_encontrada.getMontomaximo() < monto) {
				return new ResponseEntity<String>("El valor maximo de la venta ha sido superado",
						HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>("Monto valido, se puede continuar el proceso de venta", HttpStatus.OK);
	}

	public ResponseEntity createTarjetaCredito(TarjetaCreditoDto tarjetaDto) {
		if (this.tarjetaExistenteByNumero(tarjetaDto.getNumero())) {
			return new ResponseEntity<>("Ya exixte una tarjeta con ese numero", HttpStatus.BAD_REQUEST);
		}

		Optional<Cliente> optionalCliente = clienteService.findById(tarjetaDto.getCliente_id());
		if (optionalCliente.isPresent()) {
			Cliente cliente = optionalCliente.get();
			Cliente cliente_encontrado = new Cliente(cliente.getId(), cliente.getNombre(), cliente.getApellido());

			String token = encriptarToken(tarjetaDto);
	        //String token = this.convertirSHA256(cliente_encontrado.getNombre(),1);

			TarjetaCredito tarjetaCredito = new TarjetaCredito(tarjetaDto.getTipo(), tarjetaDto.getNumero(),
					tarjetaDto.getCodseguridad(), tarjetaDto.getVencimiento(), tarjetaDto.getMontomaximo(),
					cliente_encontrado, token, true);
			tarjetacreditoRepository.save(tarjetaCredito);
			
			return new ResponseEntity<>(token, HttpStatus.OK);
		}
		return new ResponseEntity<>("El cliente no esta registrado", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> deleteTarjetaCredito(String token) {
		Optional<TarjetaCredito> buscarTarjeta = tarjetacreditoRepository.findByToken(token);
		if (buscarTarjeta.isPresent()) {
			TarjetaCredito datosTarjetaCredito = buscarTarjeta.get();
			if (datosTarjetaCredito.getActivo()) {
				TarjetaCredito deshabilitarTarjeta = new TarjetaCredito(datosTarjetaCredito.getId(),
						datosTarjetaCredito.getTipo(), datosTarjetaCredito.getNumero(),
						datosTarjetaCredito.getCodseguridad(), datosTarjetaCredito.getVencimiento(),
						datosTarjetaCredito.getMontomaximo(), datosTarjetaCredito.getCliente_id(),
						datosTarjetaCredito.getToken(), false);
				tarjetacreditoRepository.save(deshabilitarTarjeta);
				return new ResponseEntity<String>("La tarjeta ha sido deshabilitada", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("La tarjeta ya se encuentra deshabilitada", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>("La tarjeta no se encuentra registrada", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> updateTarjetaCredito(String token) {
		Optional<TarjetaCredito> optionalTarjetaCredito = tarjetacreditoRepository.findByToken(token);
		if (optionalTarjetaCredito.isPresent()) {
			TarjetaCredito datosTarjeta = optionalTarjetaCredito.get();
			if (!datosTarjeta.getActivo()) {
				TarjetaCredito actualizarTarjeta = new TarjetaCredito(datosTarjeta.getId(), datosTarjeta.getTipo(),
						datosTarjeta.getNumero(), datosTarjeta.getCodseguridad(), datosTarjeta.getVencimiento(),
						datosTarjeta.getMontomaximo(), datosTarjeta.getCliente_id(), datosTarjeta.getToken(), true);
				tarjetacreditoRepository.save(actualizarTarjeta);
				return new ResponseEntity<String>("La tarjeta ha sido habilitada", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("La tarjeta ya esta habilitada", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("No se encontro ninguna tarjeta", HttpStatus.BAD_REQUEST);
		}
	}

	public boolean tarjetaExistenteByNumero(Long numero) {
		return tarjetacreditoRepository.existsByNumero(numero);
	}

	public boolean existsTarjetaByToken(String token) {
		return tarjetacreditoRepository.existsByToken(token);
	}

	public Optional<TarjetaCredito> findTarjetaByToken(String token) {
		return tarjetacreditoRepository.findByToken(token);
	}

	public Date TodayDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		// dateFormat.format(date);
		return date;
	}
	
	public String encriptarToken(TarjetaCreditoDto tarjetaDto) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String vencimiento = dateFormat.format(tarjetaDto.getVencimiento());
		String token = vencimiento + tarjetaDto.getNumero() + tarjetaDto.getCodseguridad() + System.currentTimeMillis();
		// token = Hashing.sha256().hashString(token,
		// StandardCharsets.UTF_8).toString();
		String tokenEncriptado = convertirSHA256(token);
		return tokenEncriptado;
	}
	
	// public String convertirSHA256(String nombre, int numberOfWords) {
	public String convertirSHA256(String token) {
		//String[] randomWord = generateRandomWords(numberOfWords);
		//String wordToCrypt = nombre + randomWord;

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

		byte[] hash = md.digest(token.getBytes());
		StringBuffer sb = new StringBuffer();

		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}
/*
	public static String[] generateRandomWords(int numberOfWords)
	{
	    String[] randomStrings = new String[numberOfWords];
	    Random random = new Random();
	    for(int i = 0; i < numberOfWords; i++)
	    {
	        char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
	        for(int j = 0; j < word.length; j++)
	        {
	            word[j] = (char)('a' + random.nextInt(26));
	        }
	        randomStrings[i] = new String(word);
	    }
	    return randomStrings;
	}
*/
	
}