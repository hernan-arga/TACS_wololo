package tacs.wololo.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import tacs.wololo.model.EstadoPartida;

@Slf4j
@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping(path = "/home")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok(4);
    }

    //GET   home/partidas?orderDate={fecha}
    @GetMapping(path = "/home/partidas", params = "orderDate")
    public ResponseEntity<?> ordenarPartidasPorFecha(Date orderDate)
    {
        return ResponseEntity.ok(4);
    }

    //GET   home/partidas?orderState={estado}
    @GetMapping(path = "/home/partidas", params = "orderState")
    public ResponseEntity<?> ordenarPartidasPorEstado(EstadoPartida orderState)
    {
        return ResponseEntity.ok(4);
    }

    //GET   home/partidas?filterDate={fecha}
    @GetMapping(path = "/home/partidas", params = "filterDate")
    public ResponseEntity<?> filtrarPartidasPorFecha(Date filterDate)
    {
        return ResponseEntity.ok(4);
    }

    //GET   home/partidas?filterState={estado}
    @GetMapping(path = "/home/partidas", params = "filterState")
    public ResponseEntity<?> filtrarPartidasPorEstado(EstadoPartida filterState)
    {
        return ResponseEntity.ok(4);
    }

}
