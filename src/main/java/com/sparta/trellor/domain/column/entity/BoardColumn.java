package com.sparta.trellor.domain.column.entity;

import com.sparta.trellor.domain.utils.BaseTime;
import jakarta.persistence.*;
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
public class BoardColumn extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    private Long columnId;

    @Column(name = "column_name", nullable = false)
    private String columnName;

    @Column(name = "column_no")
    private Long columnNo;
}
