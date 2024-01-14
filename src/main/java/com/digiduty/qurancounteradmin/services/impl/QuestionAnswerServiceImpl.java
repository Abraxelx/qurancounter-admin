package com.digiduty.qurancounteradmin.services.impl;

import com.digiduty.qurancounteradmin.dao.QuestionAnswerDao;
import com.digiduty.qurancounteradmin.model.QuestionAnswerModel;
import com.digiduty.qurancounteradmin.services.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionAnswerServiceImpl extends AbstractServiceImpl<QuestionAnswerModel, Long> implements QuestionAnswerService {
    QuestionAnswerDao questionAnswerDao;

    @Autowired
    public QuestionAnswerServiceImpl(QuestionAnswerDao repository) {
        super(repository);
        this.questionAnswerDao = repository;
    }

    @Override
    public Page<QuestionAnswerModel> findAllBySeoUrlContaining(String seoUrl, Pageable pageable) {
        return questionAnswerDao.findAllBySeoUrlContaining(seoUrl, pageable);
    }

    @Override
    public Page<QuestionAnswerModel> findAll(Pageable pageable) {
        return questionAnswerDao.findAll(pageable);
    }
}
