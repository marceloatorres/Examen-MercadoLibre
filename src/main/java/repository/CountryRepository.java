package repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String>{
	@Query(value= "SELECT * FROM countries WHERE distance_to_other_place = (SELECT MAX(distance_to_other_place) FROM countries) limit 1;", nativeQuery = true)
	Country findMax();
	
	@Query(value= "SELECT * FROM countries WHERE distance_to_other_place = (SELECT MIN(distance_to_other_place) FROM countries) limit 1;", nativeQuery = true)
	Country findMin();
	
	@Query(value ="select sum((distance_to_other_place * request_count))/ sum(request_count) from countries;", nativeQuery = true)
	long distanceAverage();

}

