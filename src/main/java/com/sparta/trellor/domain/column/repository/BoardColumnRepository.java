package com.sparta.trellor.domain.column.repository;

import com.sparta.trellor.domain.column.entity.BoardColumn;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {

    Optional<BoardColumn> findByColumnName(String name);

}
