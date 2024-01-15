package com.digiduty.qurancounteradmin.elasticsearch.repository;


import com.digiduty.qurancounteradmin.elasticsearch.entity.QuestionAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface QuestionAnswerRepository extends ElasticsearchRepository<QuestionAnswer, String> {
    Page<QuestionAnswer> findByQuestion(String question, Pageable pageable);
    Optional<QuestionAnswer> findByQuestion(String question);
}
