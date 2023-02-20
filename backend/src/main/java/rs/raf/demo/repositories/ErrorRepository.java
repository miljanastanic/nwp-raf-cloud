package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.ErrorMessage;

@Repository
public interface ErrorRepository extends JpaRepository<ErrorMessage, Long> {
}
