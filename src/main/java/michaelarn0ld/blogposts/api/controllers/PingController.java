package michaelarn0ld.blogposts.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ping")
public class PingController {

    @GetMapping
    public ResponseEntity<?> getPing() {
        Map<String, Boolean> success = new HashMap<>();
        success.put("success", true);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

}
