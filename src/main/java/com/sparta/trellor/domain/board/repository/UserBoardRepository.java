package com.sparta.trellor.domain.board.repository;


import com.sparta.trellor.domain.board.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
    List<UserBoard> findAllByInviteUserId(Long inviteUserId);
}
