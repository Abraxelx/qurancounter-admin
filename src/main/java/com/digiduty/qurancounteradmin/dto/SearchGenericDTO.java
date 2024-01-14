package com.digiduty.qurancounteradmin.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchGenericDTO {
    private List<String> selectedAttributes;
    private List<String> attributeValues;
    private List<String> filterTypes;
    private String logicalOperator;

    public SearchGenericDTO(List<String> selectedAttributes, List<String> attributeValues, List<String> filterTypes, String logicalOperator) {
        this.selectedAttributes = selectedAttributes;
        this.attributeValues = attributeValues;
        this.filterTypes = filterTypes;
        this.logicalOperator = logicalOperator;
    }
}
