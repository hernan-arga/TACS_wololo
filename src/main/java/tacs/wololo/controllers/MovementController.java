package tacs.wololo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tacs.wololo.services.implementations.MovementService;

public class MovementController {
    @Autowired
    MovementService movementService;

    // en realidad no se si debería ser: /games/{id}/municipality/{idMunicipality}
    // TODO: ver cómo sería el path y si realmente es necesario este controller o
    // debería estar en otro controler

    @GetMapping(path = "/municipality/{idMunicipality}")
    public ResponseEntity<?> municipalities(@PathVariable(value = "idMunicipality") String municipalityId){
        // TODO: SEGUIR
        Long idGame = Long.valueOf(1); //TODO: está hardcodeado

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();

        return ResponseEntity.ok(movementService.getMovementsBy(idGame, userDetails.getUsername(), municipalityId));
    }
}
