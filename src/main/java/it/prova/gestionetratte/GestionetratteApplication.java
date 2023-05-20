package it.prova.gestionetratte;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.AirbusService;
import it.prova.gestionetratte.service.TrattaService;

@SpringBootApplication
public class GestionetratteApplication implements CommandLineRunner{

	
	@Autowired
	private AirbusService airbusService;
	
	@Autowired
	private TrattaService trattaService;
	
	public static void main(String[] args) {
		SpringApplication.run(GestionetratteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String codiceAirbus1 = "FE453";
		String descrizioneAirbus1 = "Ryanair";
		Airbus airbus1 = airbusService.findByCodiceAndDescrizione(codiceAirbus1, descrizioneAirbus1);
		if (airbus1 == null) {
			airbus1 = new Airbus(codiceAirbus1, descrizioneAirbus1, LocalDate.now().minusDays(1), 45);
			airbusService.inserisciNuovo(airbus1);
		}
		
		Tratta trattaMilanoNapoli = new Tratta("HJKGJ678", "Milano-Napoli", LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(10, 30), Stato.ATTIVA, airbus1);
		if (trattaService.findByCodiceAndDescrizione(trattaMilanoNapoli.getCodice(), trattaMilanoNapoli.getDescrizione()).isEmpty()) {
			trattaService.inserisciNuovo(trattaMilanoNapoli);
		}
		
		String codiceAirbus2 = "GH889";
		String descrizioneAirbus2 = "JapanAirLine";
		Airbus airbus2 = airbusService.findByCodiceAndDescrizione(codiceAirbus2, descrizioneAirbus2);
		if (airbus2 == null) {
			airbus2 = new Airbus(codiceAirbus2, descrizioneAirbus2, LocalDate.of(2020, 05, 19), 105);
			airbusService.inserisciNuovo(airbus2);
		}
		
		Tratta trattaMilanoTokyo = new Tratta("KKOOP67", "Milano-Tokyo", LocalDate.of(2022, 12, 1), LocalTime.of(10, 0), LocalTime.of(18, 30), Stato.CONCLUSA, airbus2);
		if (trattaService.findByCodiceAndDescrizione(trattaMilanoTokyo.getCodice(), trattaMilanoTokyo.getDescrizione()).isEmpty()) {
			trattaService.inserisciNuovo(trattaMilanoTokyo);
		}
	}

}
