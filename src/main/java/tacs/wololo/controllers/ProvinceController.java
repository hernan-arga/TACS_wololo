package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    @GetMapping("")
    public ResponseEntity<?> getProvincesList() {
        return ResponseEntity.ok(3);
    }
}
