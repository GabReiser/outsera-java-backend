package com.outsera.test.controller;

import com.outsera.test.dto.MinMaxIntervalDTO;
import com.outsera.test.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/intervals")
    public ResponseEntity<MinMaxIntervalDTO> getProducerIntervals(){
        MinMaxIntervalDTO response = movieService.getProducerIntervals();
        return ResponseEntity.ok(response);
    }
}
