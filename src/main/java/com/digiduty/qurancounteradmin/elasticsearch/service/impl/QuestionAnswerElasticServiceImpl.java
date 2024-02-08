package com.digiduty.qurancounteradmin.elasticsearch.service.impl;


import com.digiduty.qurancounteradmin.elasticsearch.entity.QuestionAnswer;
import com.digiduty.qurancounteradmin.elasticsearch.repository.QuestionAnswerRepository;
import com.digiduty.qurancounteradmin.elasticsearch.service.QuestionAnswerElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionAnswerElasticServiceImpl implements QuestionAnswerElasticService {

    private final
    QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    public QuestionAnswerElasticServiceImpl(QuestionAnswerRepository questionAnswerRepository) {
        this.questionAnswerRepository = questionAnswerRepository;
    }


    @Override
    public QuestionAnswer save(QuestionAnswer questionAnswer) {
        return questionAnswerRepository.save(questionAnswer);
    }

    @Override
    public void deleteAll() {
         questionAnswerRepository.deleteAll();
    }

    @Override
    public void saveAll(List<QuestionAnswer> questionAnswerList) {
        questionAnswerRepository.saveAll(questionAnswerList);

    }

    @Override
    public Page<QuestionAnswer> searchSimilar (QuestionAnswer questionAnswer,String[] fields,Pageable pageable) {
        return questionAnswerRepository.searchSimilar(questionAnswer,fields,pageable);
    }

    @Override
    public Page<QuestionAnswer> findByQuestion(String question, Pageable pageable) {
        return questionAnswerRepository.findByQuestion(question,pageable);
    }

    @Override
    public Optional<QuestionAnswer> findByQuestion(String question) {
        return questionAnswerRepository.findByQuestion(question);
    }



}
