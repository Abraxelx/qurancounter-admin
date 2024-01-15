package com.digiduty.qurancounteradmin.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Document(indexName = "questionanswer")
@Data
public class QuestionAnswer {

    @Id
    private String id;
    private String question;
    private String answer;
    private String summary;
    private String seoUrl;
    private List<String> sources = new ArrayList<>();
    private Integer readCount;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant createdDate;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant updateDate;

}
