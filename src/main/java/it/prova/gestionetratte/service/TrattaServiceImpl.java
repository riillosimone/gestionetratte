package it.prova.gestionetratte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.TrattaRepository;
import it.prova.gestionetratte.web.api.exception.TrattaNotFoundException;

@Service
@Transactional(readOnly = true)
public class TrattaServiceImpl implements TrattaService {

	@Autowired
	private TrattaRepository repository;
	
	@Override
	public List<Tratta> listAllElements(boolean eager) {
		
		return (List<Tratta>) repository.findAll();
	}

	@Override
	public Tratta caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Tratta caricaSingoloElementoEager(Long id) {
		return repository.findSingleTrattaEager(id);
	}

	@Override
	@Transactional
	public Tratta aggiorna(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}

	@Override
	@Transactional
	public Tratta inserisciNuovo(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idTratta) {
		repository.findById(idTratta).orElseThrow(()->new TrattaNotFoundException("Tratta not found con id: "+idTratta));
		repository.deleteById(idTratta);
	}

	@Override
	public List<Tratta> findByExample(Tratta example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tratta> findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}

}
