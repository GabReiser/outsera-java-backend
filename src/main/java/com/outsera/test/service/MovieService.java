package com.outsera.test.service;

import com.outsera.test.domain.Movie;
import com.outsera.test.dto.MinMaxIntervalDTO;
import com.outsera.test.dto.ProducerIntervalDTO;
import com.outsera.test.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MinMaxIntervalDTO getProducerIntervals() {
        List<Movie> winners = movieRepository.findByWinnerTrue();
        Map<String, List<Integer>> producerWins = new HashMap<>();

        for (Movie movie : winners) {
            if(movie.getProducers() != null) {
                for (String producer : movie.getProducers()) {
                    producerWins.computeIfAbsent(producer, k -> new ArrayList<>()).add(movie.getYear());
                }
            }
        }

        List<ProducerIntervalDTO> allIntervals = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry: producerWins.entrySet()) {
            String producer = entry.getKey();
            List<Integer> years = entry.getValue();
            if(years.size() >= 2){
                Collections.sort(years);

                for(int i = 1; i < years.size(); i++){
                    int previousWin = years.get(i - 1);
                    int followingWin = years.get(i);
                    int interval = followingWin - previousWin;

                    allIntervals.add(ProducerIntervalDTO.builder()
                            .producer(producer)
                            .interval(interval)
                            .previousWin(previousWin)
                            .followingWin(followingWin)
                            .build());
                }
            }
        }
        if (allIntervals.isEmpty()) {
            return MinMaxIntervalDTO.builder()
                    .min(List.of())
                    .max(List.of())
                    .build();
        }
        int minInterval = allIntervals.stream()
                .mapToInt(ProducerIntervalDTO::getInterval)
                .min()
                .orElse(0);

        int maxInterval = allIntervals.stream()
                .mapToInt(ProducerIntervalDTO::getInterval)
                .max()
                .orElse(0);

        List<ProducerIntervalDTO> minList = allIntervals.stream()
                .filter(i -> i.getInterval() == minInterval)
                .toList();
        List<ProducerIntervalDTO> maxList = allIntervals.stream()
                .filter(i -> i.getInterval() == maxInterval)
                .toList();



        return MinMaxIntervalDTO.builder()
                .min(minList)
                .max(maxList)
                .build();
    }
}
