package it.lbsoftware.demo;

/**
 * This Java record is a simple immutable container for a single message String. It is used to make
 * the endpoint we defined a RESTful one. When this object is used as the return type from a REST
 * controller with a ResponseEntity, then an automatic conversion is made by Spring Web MVC and the
 * output will be a JSON object!
 */
public record MessageOutput(String message) {}
