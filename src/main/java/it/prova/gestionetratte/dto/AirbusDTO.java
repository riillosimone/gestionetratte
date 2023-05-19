package it.prova.gestionetratte.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.gestionetratte.model.Airbus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirbusDTO {

	private Long id;

	@NotBlank(message = "{codiceAirbus.notblank}")
	private String codice;
	@NotBlank(message = "{descrizioneAirbus.notblank}")
	private String descrizione;
	@NotNull(message = "{dataInizioServizio.notnull}")
	private LocalDate dataInizioServizio;
	@NotNull(message = "{numeroPasseggeri.notnull}")
	private Integer numeroPassegeri;

	@JsonIgnoreProperties(value = { "airbus" })
	private Set<TrattaDTO> tratte = new HashSet<>();

	public AirbusDTO() {
		super();
	}

	public AirbusDTO(Long id) {
		super();
		this.id = id;
	}

	public AirbusDTO(Long id, @NotBlank(message = "{codiceAirbus.notblank}") String codice,
			@NotBlank(message = "{descrizioneAirbus.notblank}") String descrizione,
			@NotNull(message = "{dataInizioServizio.notnull}") LocalDate dataInizioServizio,
			@NotNull(message = "{numeroPasseggeri.notnull}") Integer numeroPassegeri) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizioServizio = dataInizioServizio;
		this.numeroPassegeri = numeroPassegeri;
	}

	public AirbusDTO(@NotBlank(message = "{codiceAirbus.notblank}") String codice,
			@NotBlank(message = "{descrizioneAirbus.notblank}") String descrizione,
			@NotNull(message = "{numeroPasseggeri.notnull}") Integer numeroPassegeri) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
		this.numeroPassegeri = numeroPassegeri;
	}

	public AirbusDTO(Long id, @NotBlank(message = "{codiceAirbus.notblank}") String codice,
			@NotBlank(message = "{descrizioneAirbus.notblank}") String descrizione,
			@NotNull(message = "{dataInizioServizio.notnull}") LocalDate dataInizioServizio,
			@NotNull(message = "{numeroPasseggeri.notnull}") Integer numeroPassegeri, Set<TrattaDTO> tratte) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizioServizio = dataInizioServizio;
		this.numeroPassegeri = numeroPassegeri;
		this.tratte = tratte;
	}

	public AirbusDTO(@NotBlank(message = "{codiceAirbus.notblank}") String codice,
			@NotBlank(message = "{descrizioneAirbus.notblank}") String descrizione,
			@NotNull(message = "{dataInizioServizio.notnull}") LocalDate dataInizioServizio,
			@NotNull(message = "{numeroPasseggeri.notnull}") Integer numeroPassegeri) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizioServizio = dataInizioServizio;
		this.numeroPassegeri = numeroPassegeri;
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

	public LocalDate getDataInizioServizio() {
		return dataInizioServizio;
	}

	public void setDataInizioServizio(LocalDate dataInizioServizio) {
		this.dataInizioServizio = dataInizioServizio;
	}

	public Integer getNumeroPassegeri() {
		return numeroPassegeri;
	}

	public void setNumeroPassegeri(Integer numeroPassegeri) {
		this.numeroPassegeri = numeroPassegeri;
	}

	public Set<TrattaDTO> getTratte() {
		return tratte;
	}

	public void setTratte(Set<TrattaDTO> tratte) {
		this.tratte = tratte;
	}
	
	public Airbus buildAirbusModel() {
		return new Airbus(this.id, this.codice, this.descrizione, this.dataInizioServizio, this.numeroPassegeri);
	}

	public static AirbusDTO buildAirbusDTOFromModel(Airbus airbusModel,boolean includeTratte) {
		AirbusDTO result = new AirbusDTO(airbusModel.getId(), airbusModel.getCodice(), airbusModel.getDescrizione(), airbusModel.getDataInizioServizio(), airbusModel.getNumeroPasseggeri());
		if (includeTratte) {
			result.setTratte(null);
		}
		return result;
	}
	
	public static List<AirbusDTO> createAirbusDTOListFromModelList(List<Airbus> modelListInput, boolean includeTratte) {
		return modelListInput.stream().map(airbusEntity -> {
			AirbusDTO result = AirbusDTO.buildAirbusDTOFromModel(airbusEntity, includeTratte);
			if (includeTratte) {
				result.setTratte(null);
			}
			return result;
		}).collect(Collectors.toList());
	} 
}
