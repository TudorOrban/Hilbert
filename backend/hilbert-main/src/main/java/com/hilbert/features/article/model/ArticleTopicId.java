package com.hilbert.features.article.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@Embeddable
public class ArticleTopicId implements Serializable {

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "topic_id")
    private Long topicId;
}
