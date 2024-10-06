package it.lbsoftware.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The @DisplayName annotation gives us a human-readable name to display when running this test
 * suite. It can also be used for test cases (aka methods inside this class). This class does not
 * deal with Spring directly, so no application context is created before running the cases here.
 */
@DisplayName("User controller tests")
class UserControllerTests {

  /**
   * A mock is a dependency we do not want to have for real in our test; mocking allows us to
   * determine the behavior the dependency should have when performing method calls with certain
   * arguments.
   */
  private final UserRepo userRepo = mock(UserRepo.class);

  /** This is the class whose methods are tested in this class. */
  private UserController userController;

  /**
   * We want to perform some initializations before each test method gets executed; this has nothing
   * to do with Spring, it's a test runner (JUnit) concern.
   */
  @BeforeEach
  void beforeEach() {
    userController = new UserController(userRepo);
  }

  /**
   * The test case, a method that executes some logic. We use the @Test annotation from the test
   * runner (JUnit) to tell it this is something it should run. The test itself creates a DTO and
   * passes it as the only argument to the controller method that should save the user. If
   * everything works fine, we should have a 201 (CREATED) status code as a result.
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
  }
}
