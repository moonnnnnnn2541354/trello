package com.sparta.trellor.domain.board.entity;

import com.sparta.trellor.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_board_id")
    private Long userBoardId;

    @Column(name = "user_id")
    private Long inviteUserId;
    @Column(name = "board_id")
    private Long inviteBoardId;

    public UserBoard(Long inviteUserId, Long inviteBoardId) {
        this.inviteUserId = inviteUserId;
        this.inviteBoardId = inviteBoardId;
    }

    public UserBoard(User user, Board board) {
        user.addUserBoardList(this);
        board.addUserBoardList(this);
    }
}
