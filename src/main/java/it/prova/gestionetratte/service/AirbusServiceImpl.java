package it.prova.gestionetratte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.repository.AirbusRepository;
import it.prova.gestionetratte.web.api.exception.AirbusConTratteException;
import it.prova.gestionetratte.web.api.exception.AirbusNotFoundException;

@Service
@Transactional(readOnly = true)
public class AirbusServiceImpl implements AirbusService {

	@Autowired
	private AirbusRepository repository;

	@Override
	public List<Airbus> listAllElements() {
		return (List<Airbus>) repository.findAll();
	}

	@Override
	public List<Airbus> listAllElementsEager() {
		return (List<Airbus>) repository.findAllEager();
	}

	@Override
	public Airbus caricaSingoloElemento(Long id) {
		Airbus airbusDaCaricare = repository.findById(id).orElse(null);
		if (airbusDaCaricare == null) {
			throw new AirbusNotFoundException("Airbus not found con id: " + id);
		}
		return airbusDaCaricare;
	}

	@Override
	public Airbus caricaSingoloElementoConTratte(Long id) {
		Airbus airbusDaCaricare = repository.findByIdEager(id);
		if (airbusDaCaricare == null) {
			throw new AirbusNotFoundException("Airbus not found con id: " + id);
		}
		return airbusDaCaricare;
	}

	@Override
	@Transactional
	public Airbus aggiorna(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Override
	@Transactional
	public Airbus inserisciNuovo(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		Airbus airbusToDelete = repository.findById(idToRemove)
				.orElseThrow(() -> new AirbusNotFoundException("Airbus not found con id: " + idToRemove));
		if (airbusToDelete == null) {
			throw new AirbusNotFoundException("Airbus not found con id: "+ idToRemove);
		}
		if (!airbusToDelete.getTratte().isEmpty()) {
			throw new AirbusConTratteException("Attenzione! Elimina le tratte collegate prima di eliminare l'airbus.");
		}
		repository.deleteById(idToRemove);
	}

	@Override
	public List<Airbus> findByExample(Airbus example) {
		return repository.findByExample(example);
	}

	@Override
	public Airbus findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}

	@Override
	public List<Airbus> cercaByCodiceEDescrizioneLike(String term) {
		return repository.findByCodiceIgnoreCaseContainingOrDescrizioneIgnoreCaseContainingOrderByCodiceAsc(term, term);
	}

}
