package pt.com.JoaoSimoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.com.JoaoSimoes.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
