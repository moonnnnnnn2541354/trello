package com.sparta.trellor.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardFindColumnReponseDto extends BoardColumn{
    private Long columnId;
    private String columnName;
    public BoardFindColumnReponseDto(BoardColumn boardColumn){
        this.columnId = boardColumn.getColumnId();
        this.columnName = boardColumn.getColumnName();
    }


}
