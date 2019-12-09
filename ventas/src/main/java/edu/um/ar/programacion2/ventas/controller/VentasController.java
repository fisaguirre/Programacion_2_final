package edu.um.ar.programacion2.ventas.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.service.VentasService;
/*
@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class VentasController {
 
    @Autowired
    private VentasService ventasService;

    @GetMapping("/ventas")
	public ResponseEntity<List<Ventas>> findAll( ) {
		return new ResponseEntity<List<Ventas>>(ventasService.findAll(), HttpStatus.OK);
	}   
}
*/
@RestController 
public class VentasController {
	@Autowired
    private VentasService ventasService;
 
    @RequestMapping(value = "/ventas", method = RequestMethod.GET)
    public ResponseEntity<List<Ventas>> findAll() 
    {
    	return new ResponseEntity<List<Ventas>>(ventasService.findAll(), HttpStatus.OK);
    }
   
    @RequestMapping(value = "/ventas/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<Ventas>> getVentasById(@PathVariable("id") Long id) 
    {
    	//Optional<Ventas> venta;
    	//return new ResponseEntity<Ventas>(venta.get(id), HttpStatus.OK);
    	//return new ResponseEntity<Ventas>(ventasService.findById(id), HttpStatus.OK);
    	return ResponseEntity.ok(ventasService.findById(id));
    }
    /*
    @GetMapping(value = "{productId}") // /products/{productId} -> /products/1
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long id) {
		Optional<Product> product = this.productDAO.findById(id);
		if(product.isPresent()) {
			return ResponseEntity.ok(product.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	*/
    @PostMapping
	public ResponseEntity<Ventas> createVentas(@RequestBody Ventas venta) {
		// newProduct has same properties but also has ID
    	return ResponseEntity.ok(ventasService.createVentas(venta));
		//Ventas newVentas = this.productDAO.save(product);
		//return ResponseEntity.ok(newVentas);
	}
    
    @DeleteMapping(value = "{idToDelete}")
	public ResponseEntity<Ventas> deleteVentas(@PathVariable("idToDelete") Long id) {
    	return ResponseEntity.ok(ventasService.deleteVentas(id));
		// newProduct has same properties but also has ID
		//this.productDAO.deleteById(id);
		//return ResponseEntity.ok(null);
	}
    
    @PutMapping
	public ResponseEntity<Ventas> updateVentas(@RequestBody Ventas venta) {
    	return new ResponseEntity(ventasService.updateVentas(venta), HttpStatus.OK);
	}
}
 