package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.model.GameState;
import tacs.wololo.model.Game;
import tacs.wololo.security.payload.MessageResponse;
import tacs.wololo.services.implementations.GameService;
import tacs.wololo.services.implementations.ProvinceService;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class GamesController
{
    @Autowired
    GameService gameService;

    @GetMapping(path = "/game")
    public ResponseEntity<?> game(){
        return ResponseEntity.ok(4);
    }

    @PostMapping(path = "/games")
    public ResponseEntity<?> newGame(@RequestBody GameInfoDto game) {
        System.out.println("controller");
        gameService.createGame(game);
        return ResponseEntity.ok(new MessageResponse("Juego creado exitosamente!"));
    }

}
