package it.prova.gestionetratte.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.TrattaRepository;
import it.prova.gestionetratte.web.api.exception.DataTrattaBeforeDataInizioServizioException;
import it.prova.gestionetratte.web.api.exception.OraDecolloAfterOraAtterraggioException;
import it.prova.gestionetratte.web.api.exception.TrattaNonAnnullataException;
import it.prova.gestionetratte.web.api.exception.TrattaNotFoundException;

@Service
@Transactional(readOnly = true)
public class TrattaServiceImpl implements TrattaService {

	@Autowired
	private TrattaRepository repository;
	
	@Autowired
	private AirbusService airbusService;

	@Override
	public List<Tratta> listAllElements(boolean eager) {

		return (List<Tratta>) repository.findAll();
	}

	@Override
	public Tratta caricaSingoloElemento(Long id) {
		Tratta trattaDaCaricare = repository.findById(id).orElse(null);
		if (trattaDaCaricare == null) {
			throw new TrattaNotFoundException("Tratta not found con id: " + id);
		}
		return trattaDaCaricare;
	}

	@Override
	public Tratta caricaSingoloElementoEager(Long id) {
		Tratta trattaDaCaricare = repository.findSingleTrattaEager(id);
		if (trattaDaCaricare == null) {
			throw new TrattaNotFoundException("Tratta not found con id: " + id);
		}
		return trattaDaCaricare;
	}

	@Override
	@Transactional
	public Tratta aggiorna(Tratta trattaInstance) {
		Tratta trattaDaAggiornare = this.caricaSingoloElementoEager(trattaInstance.getId());
		if (trattaInstance.getData().isBefore(trattaDaAggiornare.getAirbus().getDataInizioServizio())) {
			throw new DataTrattaBeforeDataInizioServizioException("Attenzione! Hai inserito una data della tratta antecedente alla data inizio del servizio.");
		}
		if (trattaInstance.getOraDecollo().isAfter(trattaInstance.getOraAtterraggio())) {
			throw new OraDecolloAfterOraAtterraggioException("Attenzione! Hai inserito un'ora atterraggio antecedente all'ora decollo.");
		}
		return repository.save(trattaInstance);
	}

	@Override
	@Transactional
	public Tratta inserisciNuovo(Tratta trattaInstance) {
		Airbus airbus = airbusService.caricaSingoloElemento(trattaInstance.getAirbus().getId());
		if (trattaInstance.getData().isBefore(airbus.getDataInizioServizio())) {
			throw new DataTrattaBeforeDataInizioServizioException("Attenzione! Hai inserito una data della tratta antecedente alla data inizio del servizio.");
		}
		if (trattaInstance.getOraDecollo().isAfter(trattaInstance.getOraAtterraggio())) {
			throw new OraDecolloAfterOraAtterraggioException("Attenzione! Hai inserito un'ora atterraggio antecedente all'ora decollo.");
		}
		return repository.save(trattaInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idTratta) {
		Tratta trattaToBeRemoved = repository.findById(idTratta)
				.orElseThrow(() -> new TrattaNotFoundException("Tratta not found con id: " + idTratta));
		
		if (trattaToBeRemoved.getStato() != Stato.ANNULLATA) {
			throw new TrattaNonAnnullataException(
					"Attenzione! La tratta che stai cercando di eliminare non è stata annullata.");
		}
		repository.deleteById(idTratta);
	}

	@Override
	public List<Tratta> findByExample(Tratta example) {
		return repository.findByExample(example);
	}

	@Override
	public List<Tratta> findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}

	@Override
	@Transactional
	public List<Tratta> concludiTratte() {
		List<Tratta> tratteAttive = repository.findAllTratteAttive();
		for (Tratta trattaItem : tratteAttive) {
			LocalDateTime dateTimeAtterraggio = LocalDateTime.of(trattaItem.getData(), trattaItem.getOraAtterraggio());
			if (dateTimeAtterraggio.isBefore(LocalDateTime.now())) {
				trattaItem.setStato(Stato.CONCLUSA);
			}
		}
		repository.saveAll(tratteAttive);
		return tratteAttive;
	}

}
