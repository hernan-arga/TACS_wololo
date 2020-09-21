package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class StatisticsController
{
    @GetMapping(path = "/statistics/games")
    public ResponseEntity<?> games(){
        return ResponseEntity.ok(4);
    }

    @GetMapping(path = "/statistics/users")
    public ResponseEntity<?> users(){
        return ResponseEntity.ok(4);
    }

    // GET   estadisticas/usuarios?id=1
    @GetMapping(path = "/statistics/users/{userId}")
    public ResponseEntity<?> user(@PathVariable String userId){
        return ResponseEntity.ok(4);
    }
}
