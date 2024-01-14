package com.digiduty.qurancounteradmin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StateItem {
    private Long id;
    private String text;
    private String slug;
}
