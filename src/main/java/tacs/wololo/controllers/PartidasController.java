package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacs.wololo.model.EstadoPartida;
import tacs.wololo.model.Partida;
import tacs.wololo.model.User;

@Slf4j
@RestController
@RequestMapping("/api")
public class PartidasController {
    @GetMapping(path = "/partida")
    public ResponseEntity<?> partida(){
        return ResponseEntity.ok(4);
    }

    @GetMapping(path = "/partida/new")
    public ResponseEntity<?> nuevaPartida(){
        return ResponseEntity.ok(4);
    }

    @PostMapping(path = "/partidas")
    public ResponseEntity<?> nuevaPartidaAgregada(@RequestBody User user) {
        /*log.info("UsersController:  list users");
        Users resource = usersService.saveUser(user);*/
        return ResponseEntity.ok(3);
    }

    //PUT   partidas/{partida}?estado={estado}
    @PutMapping(path = "/partidas/{partida}", params = "estado")
    public ResponseEntity<?> actualizarEstadoDePartida(@PathVariable Partida partida,
                                                       EstadoPartida estado) {
        return ResponseEntity.ok(3);
    }
}
