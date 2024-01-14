package com.digiduty.qurancounteradmin.dao;

import com.digiduty.qurancounteradmin.model.QuestionAnswerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerDao extends CustomGenericDao<QuestionAnswerModel, Long> {
    Page<QuestionAnswerModel> findAllBySeoUrlContaining(String seoUrl, Pageable pageable);

    Page<QuestionAnswerModel> findAll(Pageable pageable);
}
