package com.sparta.trellor.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.entity.UserBoard;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    public User(String username, String email, String password, UserRoleEnum role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void passwordUpdate(String encodingNewPassword) {
        this.password = encodingNewPassword;
    }

    public void emailUpdate(String email) {
        this.email = email;
    }

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private List<Board> boards = new ArrayList<>();

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private List<UserBoard> userBoards = new ArrayList<>();

    public void addBoardList(Board board) {
        this.boards.add(board);
    }

    public void addUserBoardList(UserBoard userBoard) {
        this.userBoards.add(userBoard);
    }
}

