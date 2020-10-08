package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tacs.wololo.model.ElementScoreBoard;
import tacs.wololo.services.implementations.StatisticsService;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class StatisticsController
{
    @Autowired
    StatisticsService statisticsService;

    @GetMapping(path = "/scoreboard/{id}")
    public ResponseEntity<?> scoreboard(@PathVariable(value = "id") Long gameID)
    {
        try
        {
            List<ElementScoreBoard> scoreBoard = statisticsService.getScoreBoard(gameID);
            return ResponseEntity.ok(scoreBoard);

        }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/statistics/games")
    public ResponseEntity<?> gamesByDate
            (@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
             @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to)
    {
        try
        {
            return ResponseEntity.ok(statisticsService.getStatisticsByDates(from, to));

        }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(path = "/statistics/users/{userPlayer}")
    public ResponseEntity<?> user(@PathVariable String userPlayer){
        try
        {
            return ResponseEntity.ok(statisticsService.getIndividualStatistics(userPlayer));

        }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }
}
