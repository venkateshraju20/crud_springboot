package code.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import code.boot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
