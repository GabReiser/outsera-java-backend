package com.outsera.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProducerIntervalDTO {
    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}
