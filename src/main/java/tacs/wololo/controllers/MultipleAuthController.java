package tacs.wololo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultipleAuthController {
    @GetMapping("/api/ping")
    public String getPing() {
        return "OK";
    }

    @GetMapping("/api/admin/ping")
    public String getAdminPing() {return "ADMIN";}
}
