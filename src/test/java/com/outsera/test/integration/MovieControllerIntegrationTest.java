package com.outsera.test.integration;

import com.outsera.test.domain.Movie;
import com.outsera.test.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("Deve retornar corretamente o produtor com maior e menor intervalo")
    void shouldReturnMinAndMaxIntervals() throws Exception {
        List<Movie> allMovies = movieRepository.findAll();
        List<Integer> matthewWins = allMovies.stream()
                        .filter(m -> m.getProducers().contains("Matthew Vaughn") && m.isWinner())
                        .map(Movie::getYear)
                        .sorted()
                        .collect(Collectors.toList());

        int intervalEsperado = 0;
        int previousWinEsperado = 0;
        int followingWinEsperado = 0;

        for(int i = 0; i < matthewWins.size() -1; i++){
            int currentWin = matthewWins.get(i);
            int nextWin = matthewWins.get(i + 1);
            int diff = nextWin - currentWin;

            if(diff > intervalEsperado){
                intervalEsperado = diff;
                previousWinEsperado = currentWin;
                followingWinEsperado = nextWin;
            }
        }

        mockMvc.perform(get("/api/movies/intervals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.max[0].producer", is("Matthew Vaughn")))
                .andExpect(jsonPath("$.max[0].interval", is(intervalEsperado)))
                .andExpect(jsonPath("$.max[0].previousWin", is(previousWinEsperado)))
                .andExpect(jsonPath("$.max[0].followingWin", is(followingWinEsperado)));
    }
}
