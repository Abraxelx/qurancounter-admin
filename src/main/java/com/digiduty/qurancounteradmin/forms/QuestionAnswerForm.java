package com.digiduty.qurancounteradmin.forms;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class QuestionAnswerForm extends ItemForm {
    @NotEmpty(message = "Question cannot be empty.")
    private String question;
    @NotEmpty(message = "Answer cannot be empty.")
    private String answer;
    @NotEmpty(message = "SEO Url cannot be empty.")
    @Size(min = 3, max = 150)
    private String seoUrl;
    private String summary;
    private List<String> sources = new ArrayList<>();
    private Integer readCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;
}
