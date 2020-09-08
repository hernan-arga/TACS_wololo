package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacs.wololo.model.GameState;
import tacs.wololo.model.Game;

@Slf4j
@RestController
@RequestMapping("/api")
public class GamesController
{
    @GetMapping(path = "/game")
    public ResponseEntity<?> game(){
        return ResponseEntity.ok(4);
    }

    @PostMapping(path = "/games")
    public ResponseEntity<?> newGame(@RequestBody Game game) {
        return ResponseEntity.ok(3);
    }

    //PUT   games/{game}?state={state}
    @PutMapping(path = "/games/{game}", params = "state")
    public ResponseEntity<?> actualizarEstadoDePartida(@PathVariable Game game, GameState state) {
        return ResponseEntity.ok(3);
    }
}
