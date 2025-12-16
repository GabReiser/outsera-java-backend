package com.outsera.test.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"year\"")
    private Integer year;
    private String title;
    private boolean winner;

    @ElementCollection
    private List<String> studios;
    @ElementCollection
    private List<String> producers;

    public Movie(Integer year, String title, List<String> studios, List<String> producers, boolean winner) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }
}
