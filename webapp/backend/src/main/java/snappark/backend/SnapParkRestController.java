import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SnapParkRestController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello, World!";
    }

    @GetMapping("/greet/{name}")
    public String greetWithName(@RequestParam String name) {
        return "Hello, " + name + "!";
    }
}