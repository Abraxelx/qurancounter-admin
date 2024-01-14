package com.digiduty.qurancounteradmin.services;

import com.digiduty.qurancounteradmin.model.QuestionAnswerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionAnswerService extends AbstractService<QuestionAnswerModel, Long> {
    Page<QuestionAnswerModel> findAllBySeoUrlContaining(String seoUrl, Pageable pageable);

    Page<QuestionAnswerModel> findAll(Pageable pageable);
}
