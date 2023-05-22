package it.prova.gestionetratte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionetratte.model.Airbus;

public interface AirbusRepository extends CrudRepository<Airbus, Long>,CustomAirbusRepository {

	Airbus findByCodiceAndDescrizione(String codice,String descrizione);
	
	@Query("select distinct a from Airbus a left join fetch a.tratte")
	List<Airbus> findAllEager();
	
	@Query("from Airbus a left join fetch a.tratte where a.id=?1")
	Airbus findByIdEager(Long idAirbus);
	
	List<Airbus> findByCodiceIgnoreCaseContainingOrDescrizioneIgnoreCaseContainingOrderByCodiceAsc(String codice,
			String descrizione);
	@Query(value = "select * from airbus a where a.id in (\r\n"
			+ " select t1.airbus_id \r\n"
			+ " from Tratta t1, Tratta t2 \r\n"
			+ "     where t1.airbus_id = t2.airbus_id\r\n"
			+ " and t1.id <> t2.id \r\n"
			+ " and t1.data = t2.data \r\n"
			+ " and (t1.oradecollo between t2.oradecollo and t2.oraatterraggio or t1.oraatterraggio between t2.oradecollo and t2.oraatterraggio)\r\n"
			+ " )\r\n"
			+ "", nativeQuery = true)
	List<Airbus> airbusConSovrapposizioni();
}
