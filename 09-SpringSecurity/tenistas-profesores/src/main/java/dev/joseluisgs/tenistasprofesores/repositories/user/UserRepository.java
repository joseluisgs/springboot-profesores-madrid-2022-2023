package dev.joseluisgs.tenistasprofesores.repositories.user;

import dev.joseluisgs.tenistasprofesores.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
