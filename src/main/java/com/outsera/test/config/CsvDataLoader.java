package com.outsera.test.config;

import com.outsera.test.domain.Movie;
import com.outsera.test.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CsvDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CsvDataLoader.class);

    private final MovieRepository movieRepository;

    public CsvDataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movielist.csv");

        if (inputStream == null) {
            log.error("Arquivo movielist.csv não encontrado!");
            return;
        }
        log.info("Iniciando a importação do CSV...");

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isFirstLine = true;

            while((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] columns = line.split(";");

                if(columns.length < 4) continue;

                Integer year = Integer.parseInt(columns[0].trim());
                String title = columns[1].trim();

                List<String> studios = parseMultipleValues(columns[2]);
                List<String> producers = parseMultipleValues(columns[3]);

                boolean winner = columns.length > 4 && "yes".equalsIgnoreCase(columns[4].trim());

                Movie movie = new Movie(year, title, studios, producers, winner);
                movieRepository.save(movie);
            }
            log.info("Informações carregadas na base com sucesso!");
        }
    }

     List<String> parseMultipleValues(String text) {
        if(text == null || text.isEmpty()) return List.of();

        return Arrays.stream(text.split(",|\\s+and\\s+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
