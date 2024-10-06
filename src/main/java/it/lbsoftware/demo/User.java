package it.lbsoftware.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * The User entity is the logical link between our database table called "app_user" and the
 * corresponding application world. The @Table annotation is used to define where the JPA
 * implementor should look to find and manage data for this class. The @Entity tells Hibernate that
 * this is an entity, so state transitions and talking to the DBMS should be mediated by it.
 */
@Table(name = "app_user")
@Entity
public class User {

  /**
   * We have an identifier, the primary key of our table. We don't want to handle it manually, so we
   * will rely on a database sequence that needs to be initialized with default values (start with 1
   * and increment by 50). The @GeneratedValue tells Hibernate to use that sequence. If the sequence
   * is not found on the db, an error is thrown and the app does not start.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  /**
   * The name is the logically unique identifier of our users. The @NotBlank validation annotation
   * tells the persistence provider to throw whenever an entity with a blank name is persisted, so
   * that we do not end up having a blank name for our user on the db. The @Column annotation is
   * used to assert that we want it to be unique, never NULL and non-updatable by our choice and
   * convenience. These indication are followed by the persistence provider. Ideally, the database
   * migrations should have the corresponding SQL constraints as well.
   */
  @NotBlank
  @Column(unique = true, nullable = false, updatable = false)
  private String name;

  public User() {
    /* Empty constructor needed by the framework */
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
