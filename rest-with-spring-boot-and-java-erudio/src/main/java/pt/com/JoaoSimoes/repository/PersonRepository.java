package pt.com.JoaoSimoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.com.JoaoSimoes.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
