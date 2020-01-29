package edu.um.ar.programacion2.tarjetacredito.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
					tarjeta.getToken(), tarjeta.getCliente_id().getId());
			tarjetaList.add(tarjetaObj);
		}
		return tarjetaList;
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
			tarjetaObj.setToken(tar.getToken());
			tarjetaObj.setCliente_id(tar.getCliente_id().getId());
		}
		return tarjetaObj;
	}
    public boolean tarjeta_existente(Long id) {
    	return tarjetacreditoRepository.existsById(id);
    }
    
    public ResponseEntity<TarjetaCreditoObjeto> createTarjetaCredito(TarjetaCreditoObjeto tarjetaObj) {
    	System.out.println("emntra funcions");
    	System.out.println("el cliente id es: "+tarjetaObj.getCliente_id());
		Optional<Cliente> optionalCliente = clienteService.findById(tarjetaObj.getCliente_id());
		System.out.println("Despues de buscar cliente");
		if (optionalCliente.isPresent()) {
			System.out.println("se encontro cliente");
			Cliente cliente = optionalCliente.get();
			Cliente cliente_encontrado = new Cliente(cliente.getId(), cliente.getNombre(), cliente.getApellido());
			System.out.println("encontramos cliente con: ");
			System.out.println(cliente_encontrado.getNombre());
			System.out.println(cliente_encontrado.getApellido());
			System.out.println(cliente_encontrado.getId());

			TarjetaCredito tarjetaCredito = new TarjetaCredito(tarjetaObj.getTipo(), tarjetaObj.getNumero(),
					tarjetaObj.getCodseguridad(), tarjetaObj.getVencimiento(), tarjetaObj.getMontomaximo(),
					cliente_encontrado, tarjetaObj.getToken());
			System.out.println("ANTES DE CREARSE TARJETA");
			TarjetaCredito tarjeta_creada = tarjetacreditoRepository.save(tarjetaCredito);
			System.out.println("dESPUES DE CREARSE TARJETA");

			TarjetaCreditoObjeto tarjetaCredObj = new TarjetaCreditoObjeto(tarjeta_creada.getId(),
					tarjeta_creada.getTipo(), tarjeta_creada.getNumero(), tarjeta_creada.getCodseguridad(),
					tarjeta_creada.getVencimiento(), tarjeta_creada.getMontomaximo(), tarjeta_creada.getToken(),
					tarjeta_creada.getCliente_id().getId());

			//return tarjetaCredObj;
			return new ResponseEntity<TarjetaCreditoObjeto>(HttpStatus.OK);
		}
		return null;
	}
    
 
}