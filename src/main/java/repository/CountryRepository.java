package repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String>{
	@Query(value= "SELECT * FROM countries WHERE distance_to_argentina = (SELECT MAX(distance_to_argentina) FROM countries);", nativeQuery = true)
	List<Country> findMax();
	
	@Query(value= "SELECT * FROM countries WHERE distance_to_argentina = (SELECT MIN(distance_to_argentina) FROM countries);", nativeQuery = true)
	List<Country> findMin();
	
	@Query(value ="select sum((distance_to_argentina * request_count))/ sum(request_count) from countries;", nativeQuery = true)
	long distanceAverage();

}

