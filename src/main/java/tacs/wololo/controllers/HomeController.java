package tacs.wololo.controllers;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import tacs.wololo.model.GameState;

@Slf4j
@RestController
@RequestMapping("/api")
public class HomeController
{
    @GetMapping(path = "/home")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok(4);
    }
    
}
