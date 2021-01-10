package repository;

import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String>{
	@Query(value= "SELECT * FROM countries WHERE distance_to_other_place = (SELECT MAX(distance_to_other_place) FROM countries) limit 1;", nativeQuery = true)
	Country findMax();
	
	@Query(value= "SELECT * FROM countries WHERE distance_to_other_place = (SELECT MIN(distance_to_other_place) FROM countries) limit 1;", nativeQuery = true)
	Country findMin();
	
	@Query(value ="SELECT SUM((distance_to_other_place * request_count))/ SUM(request_count) FROM countries;", nativeQuery = true)
	long distanceAverage();

	@Modifying
	@Transactional
	@Query(value="UPDATE countries c SET c.request_count = (request_count+1) WHERE c.alpha3Code =:alphaCode", nativeQuery = true)
	int updateRequestCount(@Param(value = "alphaCode") String alpha3Code);
	
}

