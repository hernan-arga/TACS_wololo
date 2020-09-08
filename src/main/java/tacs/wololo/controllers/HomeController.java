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

    //GET   home/partidas?orderDate={fecha}
    @GetMapping(path = "/home/games", params = "orderDate")
    public ResponseEntity<?> orderGamesByDate(Date orderDate)
    {
        return ResponseEntity.ok(4);
    }

    //GET   home/partidas?orderState={estado}
    @GetMapping(path = "/home/games", params = "orderState")
    public ResponseEntity<?> orderGamesByState(GameState orderState)
    {
        return ResponseEntity.ok(4);
    }

    //GET   home/partidas?filterDate={fecha}
    @GetMapping(path = "/home/games", params = "filterDate")
    public ResponseEntity<?> filterGamesByDate(Date filterDate)
    {
        return ResponseEntity.ok(4);
    }

    //GET   home/partidas?filterState={estado}
    @GetMapping(path = "/home/games", params = "filterState")
    public ResponseEntity<?> filterGamesByState(GameState filterState)
    {
        return ResponseEntity.ok(4);
    }

}
