package com.sparta.trellor.domain.comment.entity;

import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.comment.dto.request.CommentRequestDto;
import com.sparta.trellor.domain.utils.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comment_info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card", nullable = false)
    private Card card;

    @Builder
    public Comment(String username, String comment_info, Card card) {
        this.username = username;
        this.comment_info = comment_info;
        this.card = card;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment_info = requestDto.getInfo();
    }


}
