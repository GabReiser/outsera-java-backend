package com.outsera.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MinMaxIntervalDTO {
    private List<ProducerIntervalDTO> min;
    private List<ProducerIntervalDTO> max;
}
