package it.lbsoftware.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * The @DisplayName annotation gives us a human-readable name to display when running this test
 * suite. It can also be used for test cases (aka methods inside this class). We use the @Import
 * annotation to import a certain configuration class; in this case, we want to set the real
 * dependencies up to perform an integration test, and the TestcontainersConfiguration class allows
 * us to do so. Note that we did not write that class manually; it came with the other project setup
 * files when we downloaded the startup project from start.spring.io. That website knows the
 * defaults we may want to have and adjusts the generated code for that, according to the
 * dependencies we choose. The @SpringBootTest annotation spins up a whole application context with
 * all dependencies set. Abusing it may result in slower test execution time, but it is the closest
 * thing to testing in the real production environment, which in turn is something we should never
 * do.
 */
@DisplayName("User controller tests")
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class UserControllerTests {

  /**
   * We are no longer mocking the dependency: this is the real Spring Data JPA repository
   * abstraction that we can use to interact with the DBMS. With the imported
   * TestcontainersConfiguration, we are also be sure to spin up a container with the real database
   * itself. The @Autowired annotation tells Spring that we need this dependency when running this
   * test class.
   */
  @Autowired private UserRepo userRepo;

  /**
   * This is the class whose methods are tested in this class. We autowire it because we are no
   * longer instantiating ourselves: we want the real controller Spring Boot would spin up if we
   * were running the app in a (almost) real environment!
   */
  @Autowired private UserController userController;

  /**
   * The test case, a method that executes some logic. We use the @Test annotation from the test
   * runner (JUnit) to tell it this is something it should run. The test itself creates a DTO and
   * passes it as the only argument to the controller method that should save the user. If
   * everything works fine, we should have a 201 (CREATED) status code as a result. Since we now
   * have a real repository to talk to a real database, we want to make sure after the method is
   * called a real user with that name exists in it.
   */
  @DisplayName("Should save user")
  @Test
  void test1() {
    // Given
    var name = "Luca";
    var userDto = new UserDto(name);

    // When
    var res = userController.createUser(userDto);

    // Then
    assertEquals(201, res.getStatusCode().value());
    var users = userRepo.findAll();
    assertFalse(users.isEmpty());
    var user = users.stream().findFirst().get();
    assertEquals(name, user.getName());
  }
}
