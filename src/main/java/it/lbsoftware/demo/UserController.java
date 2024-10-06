package it.lbsoftware.demo;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Same RESTful controller we wrote for the Spring Web MVC demo. */
@RestController
@RequestMapping("/api/users")
class UserController {

  /** The user repository is the User abstraction that handles the interactions with the DBMS. */
  private final UserRepo userRepo;

  /**
   * The IoC principle of Spring allows us to inject dependencies without having to instantiate them
   * directly. Here we need to interact with the database, so we want to use the repository
   * abstraction of Spring Data; we insert it in the class' constructor and Spring will inject it
   * for us.
   */
  public UserController(final UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  /**
   * Let's create a user with a certain name, if possible. Errors and handled by returning a 500
   * status code to the caller. If the creation of the user is successful, a CREATED status code is
   * returned instead.
   */
  @PostMapping
  public ResponseEntity<Void> createUser(@Valid @RequestBody final UserDto userDto) {
    var user = mapToEntity(userDto);
    try {
      userRepo.save(user);
    } catch (RuntimeException e) {
      System.err.println("Could not create user because of: " + e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
    // 201 = CREATED
    return ResponseEntity.status(201).build();
  }

  /**
   * Let's get the user corresponding to a certain name: we pass it via a path variable, like
   * /api/users/Luca and if the corresponding user exists, we get back its details. A 404 status
   * code is returned for errors and for users that cannot be found.
   */
  @GetMapping("/{name}")
  public ResponseEntity<UserDto> readUser(@PathVariable("name") String name) {
    try {
      var userOptional = userRepo.findByName(name);
      if (userOptional.isPresent()) {
        var user = userOptional.get();
        return ResponseEntity.ok(mapToDto(user));
      }
    } catch (RuntimeException e) {
      System.err.println("Could not read user because of: " + e.getMessage());
    }
    return ResponseEntity.notFound().build();
  }

  /** Converter from UserDto to User entity. */
  private User mapToEntity(final UserDto userDto) {
    var user = new User();
    user.setName(userDto.name());
    return user;
  }

  /** Converter from User entity to UserDto dto. */
  private UserDto mapToDto(final User user) {
    return new UserDto(user.getName());
  }
}
