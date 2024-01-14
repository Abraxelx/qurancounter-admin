package com.digiduty.qurancounteradmin.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StateData {
    private long total_count;
    private List<StateItem> items;
}
