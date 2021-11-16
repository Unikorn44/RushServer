package fr.rushserver.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RushController {
    @GetMapping("/rushs")
    public String[] getRushs() {
        return new String[] { "Rush 1", "Rush 2", "Rush 3" };
    }
}
