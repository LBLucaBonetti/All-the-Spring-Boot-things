package it.lbsoftware.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The @RestController annotation tells us the controller class is of type REST, so everything has
 * to be treated as such, for example, the output needs to be written to the response body.
 * The @RequestMapping annotation contains the definition of our actual endpoint, relative to the
 * main servlet context path; in production, supposing we will deploy the app to www.example.com, we
 * will reach it by pointing at www.example.com/api/greetings
 */
@RestController
@RequestMapping("/api/greetings")
class GreetingController {

  /**
   * This is a GET endpoint, hence the @GetMapping annotation. It returns a String, so that string
   * will be written to the response body. The method name is greetMe, but we won't be calling it
   * directly because Spring Boot will automatically bind the appropriate request to call it. The
   * method takes a single parameter, a String whose name is name, and that happens to also be the
   * name of the Java parameter we want to assign it to; no validation is made for us a part from
   * the conversion to String. The method determines if the name parameter is missing or contains
   * only whitespaces and returns an "error" message in that case. If the provided name is ok, then
   * a personalized greeting is returned instead.
   */
  @GetMapping
  public String greetMe(@RequestParam("name") final String name) {
    if (name == null || name.isBlank()) {
      return "No name provided, who are you?";
    }
    return "Hello, %s!".formatted(name);
  }
}
