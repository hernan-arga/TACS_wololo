package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class EstadisticasController
{
    @GetMapping(path = "/estadisticas/municipios")
    public ResponseEntity<?> municipios(){
        return ResponseEntity.ok(4);
    }

    @GetMapping(path = "/estadisticas/partidas")
    public ResponseEntity<?> partidas(){
        return ResponseEntity.ok(4);
    }

    @GetMapping(path = "/estadisticas/usuarios")
    public ResponseEntity<?> usuarios(){
        return ResponseEntity.ok(4);
    }

    // GET   estadisticas/usuarios?id=1
    @GetMapping(path = "/estadisticas/usuarios/{usuarioId}")
    public ResponseEntity<?> usuario(@PathVariable String usuarioId){
        return ResponseEntity.ok(4);
    }
}
