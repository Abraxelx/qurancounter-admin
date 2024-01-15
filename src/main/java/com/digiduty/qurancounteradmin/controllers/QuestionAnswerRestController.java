package com.digiduty.qurancounteradmin.controllers;

import com.digiduty.qurancounteradmin.elasticsearch.entity.QuestionAnswer;
import com.digiduty.qurancounteradmin.elasticsearch.service.impl.QuestionAnswerElasticServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/questionAnswer")
public class QuestionAnswerRestController {

    private final QuestionAnswerElasticServiceImpl questionAnswerService;

    @Autowired
    public QuestionAnswerRestController(QuestionAnswerElasticServiceImpl questionAnswerService) {
        this.questionAnswerService = questionAnswerService;
    }



    @GetMapping("/findByQuestion")
    public Page<QuestionAnswer> searchTaxes(@RequestParam String question,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size) {
        return questionAnswerService.findByQuestion(question, PageRequest.of(page, size));
    }

}
