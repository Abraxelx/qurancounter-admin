package com.digiduty.qurancounteradmin.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        QuestionAnswerModel that = (QuestionAnswerModel) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
