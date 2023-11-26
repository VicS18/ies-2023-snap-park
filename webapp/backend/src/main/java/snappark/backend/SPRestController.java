package snappark.backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class SPRestController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello, World!";
    }

    @GetMapping("/greet/{name}")
    public String greetWithName(@RequestParam String name) {
        return "Hello, " + name + "!";
    }
}