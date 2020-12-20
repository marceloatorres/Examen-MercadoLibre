package repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{

}

