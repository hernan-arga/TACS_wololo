package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tacs.wololo.model.DTOs.AttackDto;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.model.DTOs.GameStatusDto;
import tacs.wololo.model.Game;
import tacs.wololo.security.payload.MessageResponse;
import tacs.wololo.services.implementations.GameService;

import java.util.List;

@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin
@RequestMapping("/api")
public class GamesController
{
    @Autowired
    GameService gameService;

    @PostMapping(path = "/games")
    public ResponseEntity<?> newGame(@RequestBody GameInfoDto game) {
        gameService.createGame(game);
        return ResponseEntity.ok(new MessageResponse("Juego creado exitosamente!"));
    }

    @GetMapping(path = "/games")
    public List<GameInfoDto> getGames()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();

        return gameService.getGames(userDetails.getUsername());
    }

    @GetMapping(path = "/games/{id}")
    public ResponseEntity<GameStatusDto> getGame(@PathVariable(value = "id") Long gameID)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();

        GameStatusDto game = gameService.getGame(gameID, userDetails.getUsername());

        if(game == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(game);
    }
    
    @PatchMapping(path = "/games/{id}/municipalities")
    public ResponseEntity<?> processAttack(@PathVariable(value = "id") Long gameID,
                                           @RequestBody AttackDto attackInfo) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();

        int result = gameService.processAttack(userDetails.getUsername(), gameID,
                attackInfo.getAttackMun(), attackInfo.getDefenceMun());

        switch (result) {
            case 1:
                return ResponseEntity.ok(true);
            case 0:
                return ResponseEntity.ok(false);
            case -1:
                return ResponseEntity.badRequest().build();
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
