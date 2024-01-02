package com.sparta.trellor.domain.board.entity;

import com.sparta.trellor.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import javax.sql.RowSet;

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

    /**
     * user : userBoard = 1 : n
     * userBoard : board = n : 1
     */

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
