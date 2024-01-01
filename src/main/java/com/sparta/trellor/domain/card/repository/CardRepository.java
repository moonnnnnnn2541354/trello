package com.sparta.trellor.domain.card.repository;

import com.sparta.trellor.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

}
