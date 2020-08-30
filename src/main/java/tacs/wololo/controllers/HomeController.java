package tacs.wololo.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping(path = "/home")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok(4);
    }

    //GET   home/partidas?orderBy={fecha}
    @GetMapping(path = "/home/partidas")
    public ResponseEntity<?> ordenarPartidasPor(@RequestParam("orderBy") Date fecha)
    {
        return ResponseEntity.ok(4);
    }




}
