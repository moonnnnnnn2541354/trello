package com.sparta.trellor.domain.board.repository;

import com.sparta.trellor.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByBoardId(Long boardId);
}
