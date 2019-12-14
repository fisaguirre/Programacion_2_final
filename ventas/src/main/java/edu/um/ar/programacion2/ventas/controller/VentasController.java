package edu.um.ar.programacion2.ventas.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.galleryservice.model.Gallery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.service.VentasService;
import org.springframework.http.HttpHeaders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/ventas")
public class VentasController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private VentasService ventasService;
	
	@GetMapping("/")
	public ResponseEntity<List<Ventas>> getAllVentas(Pageable pageable) {
		/*
		 * Page<Ventas> page = ventasService.findAll(pageable); HttpHeaders headers =
		 * PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.
		 * fromCurrentRequest(), page); return
		 * ResponseEntity.ok().headers(headers).body(page.getContent());
		 */
        return new ResponseEntity<List<Ventas>>(ventasService.findAll(), HttpStatus.OK);
    }
	/**
     * {@code GET  /ventas/:id} : get the "id" ventas.
     *
     * @param id the id of the ventas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ventas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Ventas>> getVentas(@PathVariable Long id) {
        //log.debug("REST request to get Ventas : {}", id);
        Optional<Ventas> ventas = ventasService.findById(id);
        //return ResponseUtil.wrapOrNotFound(ventas);
        return ResponseEntity.ok(ventas);
    }
/*
    @PostMapping("/ventas")
    public ResponseEntity<Ventas> createVentas(@Valid @RequestBody Ventas ventas) throws URISyntaxException {
        log.debug("REST request to save Ventas : {}", ventas);
        if (ventas.getId() != null) {
            throw new BadRequestAlertException("A new ventas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ventas result = ventasService.save(ventas);
        return ResponseEntity.created(new URI("/api/ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    */
    @PostMapping
	public ResponseEntity<Ventas> createVentas(@RequestBody Ventas venta) {
		// newProduct has same properties but also has ID
    	return ResponseEntity.ok(ventasService.createVentas(venta));
		//Ventas newVentas = this.productDAO.save(product);
		//return ResponseEntity.ok(newVentas);
	}
    @PostMapping
	public ResponseEntity<Ventas> createVentas(@RequestBody Ventas venta) {
		// newProduct has same properties but also has ID
    	return ResponseEntity.ok(ventasService.createVentas(venta));
		//Ventas newVentas = this.productDAO.save(product);
		//return ResponseEntity.ok(newVentas);
	}
    @RequestMapping("/{id}")
	public Gallery getGallery(@PathVariable final int id) {
		// create gallery object
		Gallery gallery = new Gallery();
		gallery.setId(id);
		
		// get list of available images 
		List<Object> images = restTemplate.getForObject("http://image.service/images/", List.class);
		gallery.setImages(images);
	
		return gallery;
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
 