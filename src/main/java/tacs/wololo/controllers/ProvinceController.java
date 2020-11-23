package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacs.wololo.model.DTOs.ProvinceInfoDto;
import tacs.wololo.repositories.ProvinceRepository;
import tacs.wololo.services.implementations.ProvinceService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/provinces")
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    @GetMapping("")
    public ResponseEntity<?> getProvincesList() {
        return ResponseEntity.ok(provinceService.getProvincesList());
    }

}
