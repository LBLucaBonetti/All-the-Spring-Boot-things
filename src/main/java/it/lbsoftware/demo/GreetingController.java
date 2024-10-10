package it.lbsoftware.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
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
   * The ChatClient is the main Spring AI interface that deals with different AI service providers
   * with a single abstraction.
   */
  private final ChatClient chatClient;

  /**
   * Using constructor injection and Spring IoC implementation, we can simply say "we need a chat
   * client builder" and it will be instantiated for us. We simply have to say we want to build it,
   * after having its builder available.
   */
  GreetingController(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  /**
   * This is a GET endpoint, hence the @GetMapping annotation. It returns a ResponseEntity of type
   * MessageOutput; that string will be written to the response body as JSON, and the structure of
   * that JSON will be composed of a key "message" (the String field name of the type MessageOutput)
   * and the message we will associate to it. The method name is greetMe, but we won't be calling it
   * directly because Spring Boot will automatically bind the appropriate request to call it. The
   * method takes a single parameter, a String whose name is name, and that happens to also be the
   * name of the Java parameter we want to assign it to; no validation is made for us a part from
   * the conversion to String. The method determines if the name parameter is missing or contains
   * only whitespaces and returns an "error" message in that case. If the provided name is ok, then
   * a personalized greeting is returned instead.
   */
  @GetMapping
  public ResponseEntity<MessageOutput> greetMe(@RequestParam("name") final String name) {
    if (name == null || name.isBlank()) {
      return ResponseEntity.badRequest().body(new MessageOutput("No name provided, who are you?"));
    }

    return ResponseEntity.ok().body(new MessageOutput(getGreetingFromLlm(name)));
  }

  /**
   * This method simply calls the locally running Ollama instance to get a response from a prompt.
   * The prompt is personalized to give the LLM an indication of the name the user provided when
   * calling the greeting API. To install ollama, visit <a href="https://ollama.com/">the Ollama
   * website</a> and follow the instructions. <code>ollama run llama3.2</code> will run a local LLM
   * model (llama 3.2) on your local machine. You need to download it and run it for the whole
   * system to properly run.
   */
  private String getGreetingFromLlm(String name) {
    return chatClient
        .prompt(
            "You are a greeter machine. Please provide a peaceful greeting for a person whose name is %s. Only provide that greeting with no quotes"
                .formatted(name))
        .call()
        .content();
  }
}
