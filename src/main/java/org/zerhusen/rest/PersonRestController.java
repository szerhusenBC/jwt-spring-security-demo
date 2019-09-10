package org.zerhusen.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PersonRestController {

   @GetMapping("/person")
   public ResponseEntity<Person> getPerson() {
      return ResponseEntity.ok(new Person("John Doe", "john.doe@test.org"));
   }

   private static class Person {

      private final String name;
      private final String email;

      public Person(String name, String email) {
         this.name = name;
         this.email = email;
      }

      public String getName() {
         return name;
      }

      public String getEmail() {
         return email;
      }
   }
}
