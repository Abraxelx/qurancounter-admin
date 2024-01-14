package com.digiduty.qurancounteradmin.populators;

import org.springframework.core.convert.ConversionException;

import java.util.List;

public interface Populator<SOURCE, TARGET> {
    void populate(SOURCE source, TARGET target) throws ConversionException;

    void populateAll(List<SOURCE> source, List<TARGET> target) throws ConversionException;

    void reversePopulate(TARGET source, SOURCE target) throws ConversionException;

    void reversePopulateAll(List<TARGET> source, List<SOURCE> target) throws ConversionException;
}
