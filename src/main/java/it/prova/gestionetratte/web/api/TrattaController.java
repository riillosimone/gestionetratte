package it.prova.gestionetratte.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.TrattaService;
import it.prova.gestionetratte.web.api.exception.IdNotNullForInsertException;
import it.prova.gestionetratte.web.api.exception.TrattaNotFoundException;

@RestController
@RequestMapping("api/tratta")
public class TrattaController {
	
	@Autowired
	private TrattaService trattaService;
	
	@GetMapping
	public List<TrattaDTO> getAll() {
		return TrattaDTO.createTrattaDTOListFromModelList(trattaService.listAllElements(true),true);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TrattaDTO createNew (@Valid @RequestBody TrattaDTO trattainput) {
		if (trattainput.getId()!=null) {
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		}
		Tratta trattaInserita = trattaService.inserisciNuovo(trattainput.buildTrattaModel());
		return TrattaDTO.buildTrattaDTOFromModel(trattaInserita, true);
	}
	
	@GetMapping("/{id}")
	public TrattaDTO findById(@PathVariable(value = "id", required = true) long id) {
		Tratta tratta = trattaService.caricaSingoloElementoEager(id);
		if (tratta == null) {
			throw new TrattaNotFoundException("Tratta not found con id: "+id);
		}
		return TrattaDTO.buildTrattaDTOFromModel(tratta, false);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete (@PathVariable(value = "id", required = true) Long id) {
		trattaService.rimuovi(id);
	}
	
	@PutMapping("/{id}")
	public TrattaDTO update(@Valid @RequestBody TrattaDTO trattainput, @PathVariable(value = "id", required = true) Long id) {
		Tratta tratta = trattaService.caricaSingoloElemento(id);
		if (tratta == null) {
			throw new TrattaNotFoundException("Tratta not found con id: "+id);
		}
		trattainput.setId(id);
		Tratta trattaAggiornata= trattaService.aggiorna(trattainput.buildTrattaModel());
		return TrattaDTO.buildTrattaDTOFromModel(trattaAggiornata, true);
	}
}
