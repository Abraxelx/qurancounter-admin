package com.digiduty.qurancounteradmin.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "question_answer")
public class QuestionAnswerModel {
    @Id
    @SequenceGenerator(allocationSize = 1, name = "seq_questionAnswer", sequenceName = "seq_questionAnswer")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_questionAnswer")
    private Long id;
    @Column(columnDefinition = "TEXT", name = "question")
    private String question;
    @Column(columnDefinition = "TEXT", name = "answer")
    private String answer;
    @Column(length = 150, nullable = false, unique = false, name = "seo_url")
    private String seoUrl;
    @Column(columnDefinition = "TEXT", name = "summary")
    private String summary;

    private List<String> sources = new ArrayList<>();

    @Column(nullable = true, unique = false, name = "read_count")
    private Integer readCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = true, unique = false, name = "created_date")
    @Basic(optional = false)
    private Date createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = true, unique = false, name = "update_date")
    @Basic(optional = false)
    private Date updateDate;
}
