package it.prova.gestionetratte.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrattaDTO {

	private Long id;

	@NotBlank(message = "{codiceTratta.notBlank}")
	private String codice;

	@NotBlank(message = "{descrizioneTratta.notBlank}")
	private String descrizione;

	@NotNull(message = "{data.notnull}")
	private LocalDate data;

	@NotNull(message = "{oraDecollo.notnull}")
	private LocalTime oraDecollo;

	@NotNull(message = "{oraAtterraggio.notnull}")
	private LocalTime oraAtterraggio;

	@NotNull(message = "{stato.notnull}")
	private Stato stato;

	@JsonIgnoreProperties(value = { "tratte" })
	@NotNull(message = "tratte.notnull")
	private AirbusDTO airbus;

	public TrattaDTO() {
		super();
	}

	public TrattaDTO(Long id, @NotBlank(message = "{codiceTratta.notBlank}") String codice,
			@NotBlank(message = "{descrizioneTratta.notBlank}") String descrizione,
			@NotNull(message = "{data.notnull}") LocalDate data,
			@NotNull(message = "{oraDecollo.notnull}") LocalTime oraDecollo,
			@NotNull(message = "{oraAtterraggio.notnull}") LocalTime oraAtterraggio,
			@NotNull(message = "{stato.notnull}") Stato stato, @NotNull(message = "tratte.notnull") AirbusDTO airbus) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.data = data;
		this.oraDecollo = oraDecollo;
		this.oraAtterraggio = oraAtterraggio;
		this.stato = stato;
		this.airbus = airbus;
	}

	public TrattaDTO(@NotBlank(message = "{codiceTratta.notBlank}") String codice,
			@NotBlank(message = "{descrizioneTratta.notBlank}") String descrizione) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public TrattaDTO(Long id, @NotBlank(message = "{codiceTratta.notBlank}") String codice,
			@NotBlank(message = "{descrizioneTratta.notBlank}") String descrizione,
			@NotNull(message = "{data.notnull}") LocalDate data) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.data = data;
	}

	public TrattaDTO(Long id, @NotBlank(message = "{codiceTratta.notBlank}") String codice,
			@NotBlank(message = "{descrizioneTratta.notBlank}") String descrizione,
			@NotNull(message = "{data.notnull}") LocalDate data,
			@NotNull(message = "{oraDecollo.notnull}") LocalTime oraDecollo,
			@NotNull(message = "{oraAtterraggio.notnull}") LocalTime oraAtterraggio,
			@NotNull(message = "{stato.notnull}") Stato stato) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.data = data;
		this.oraDecollo = oraDecollo;
		this.oraAtterraggio = oraAtterraggio;
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOraDecollo() {
		return oraDecollo;
	}

	public void setOraDecollo(LocalTime oraDecollo) {
		this.oraDecollo = oraDecollo;
	}

	public LocalTime getOraAtterraggio() {
		return oraAtterraggio;
	}

	public void setOraAtterraggio(LocalTime oraAtterraggio) {
		this.oraAtterraggio = oraAtterraggio;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public AirbusDTO getAirbus() {
		return airbus;
	}

	public void setAirbus(AirbusDTO airbus) {
		this.airbus = airbus;
	}

	public Tratta buildTrattaModel() {
		Tratta result = new Tratta(this.id, this.codice, this.descrizione, this.data, this.oraDecollo,
				this.oraAtterraggio, this.stato);
		if (this.airbus != null) {
			result.setAirbus(this.airbus.buildAirbusModel());
		}
		return result;
	}

	public static TrattaDTO buildTrattaDTOFromModel(Tratta trattaModel, boolean includeAirbus) {
		TrattaDTO result = new TrattaDTO(trattaModel.getId(), trattaModel.getCodice(), trattaModel.getDescrizione(),
				trattaModel.getData(), trattaModel.getOraDecollo(), trattaModel.getOraAtterraggio(),
				trattaModel.getStato());
		if (includeAirbus) {
			result.setAirbus(AirbusDTO.buildAirbusDTOFromModel(trattaModel.getAirbus(), false));
		}
		return result;
	}

	public static List<TrattaDTO> createTrattaDTOListFromModelList(List<Tratta> modelListInput, boolean includeAirbus) {
		return modelListInput.stream().map(trattaEntity -> {
			return TrattaDTO.buildTrattaDTOFromModel(trattaEntity, includeAirbus);
		}).collect(Collectors.toList());

	}

	public static Set<TrattaDTO> createTrattaDTOSetFromModelSet(Set<Tratta> modelSetInput, boolean includeAirbus) {
		return modelSetInput.stream().map(trattaEntity -> {
			return TrattaDTO.buildTrattaDTOFromModel(trattaEntity, includeAirbus);
		}).collect(Collectors.toSet());

	}
}
