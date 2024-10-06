package it.lbsoftware.demo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository is the main Spring Data abstraction to deal with entities. In this case, we want
 * to use the already-defined methods of the JpaRepository for our specific User that has its
 * primary key id field of type Long. We can also define some methods, if we need them, but the
 * JpaRepository offers us many useful ones for free: save, findAll, delete, ...
 */
public interface UserRepo extends JpaRepository<User, Long> {

  /**
   * This is a magic trick by Spring Data JPA. We do not need to define the SQL query for this
   * because it is automatically generated at compile-time by analyzing the entity and its fields.
   * There are certain cases where this automatic generation is not good either because the method
   * name becomes unreadable or too long or it produces a SQL output that is not what we want (i.e.:
   * it may be not using out indexes) BUT for such simple cases it's gold.
   */
  Optional<User> findByName(String name);
}
