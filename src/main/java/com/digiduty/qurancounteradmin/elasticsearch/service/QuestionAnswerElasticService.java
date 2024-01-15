package com.digiduty.qurancounteradmin.elasticsearch.service;

import com.digiduty.qurancounteradmin.elasticsearch.entity.QuestionAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuestionAnswerElasticService {
    QuestionAnswer save(QuestionAnswer questionAnswer);
    void deleteAll();
    void saveAll(List<QuestionAnswer> questionAnswerList);
    Page<QuestionAnswer> searchSimilar (QuestionAnswer questionAnswer, String[] fields, Pageable pageable);

    Page<QuestionAnswer> findByQuestion(String question, Pageable pageable);

    Optional<QuestionAnswer> findByQuestion(String question);

}
