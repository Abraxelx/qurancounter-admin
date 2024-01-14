package com.digiduty.qurancounteradmin.populators;

import com.digiduty.qurancounteradmin.forms.QuestionAnswerForm;
import com.digiduty.qurancounteradmin.model.QuestionAnswerModel;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionAnswerPopulator<S extends QuestionAnswerModel, T extends QuestionAnswerForm> implements Populator<S, T> {
    @Override
    public void populate(QuestionAnswerModel source, QuestionAnswerForm target) throws ConversionException {
        target.setId(source.getId());
        target.setQuestion(source.getQuestion());
        target.setAnswer(source.getAnswer());
        target.setSeoUrl(source.getSeoUrl());
        target.setSummary(source.getSummary());
        target.setSources(source.getSources());
        target.setReadCount(source.getReadCount());
        target.setCreatedDate(source.getCreatedDate());
        target.setUpdateDate(source.getUpdateDate());
    }

    @Override
    public void reversePopulate(QuestionAnswerForm source, QuestionAnswerModel target) throws ConversionException {
        target.setId(source.getId());
        target.setQuestion(source.getQuestion());
        target.setAnswer(source.getAnswer());
        target.setSeoUrl(source.getSeoUrl());
        target.setSummary(source.getSummary());
        target.setSources(source.getSources());
        target.setReadCount(source.getReadCount());
        target.setCreatedDate(source.getCreatedDate());
        target.setUpdateDate(source.getUpdateDate());
    }

    @Override
    public void populateAll(List<S> sourceList, List<T> targetList) throws ConversionException {
        for (QuestionAnswerModel source : sourceList) {
            final QuestionAnswerForm target = new QuestionAnswerForm();
            this.populate(source, target);
            targetList.add((T) target);
        }
    }

    @Override
    public void reversePopulateAll(List<T> sourceList, List<S> targetList) throws ConversionException {
        for (QuestionAnswerForm source : sourceList) {
            final QuestionAnswerModel target = new QuestionAnswerModel();
            this.reversePopulate(source, target);
            targetList.add((S) target);
        }
    }
}
