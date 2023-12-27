package com.sparta.trellor.domain.column.controller;

import com.sparta.trellor.domain.column.service.BoardColumnService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/column")
@RequiredArgsConstructor
public class BoardColumnController {
    private final BoardColumnService columnService;

    @PostMapping
    public
}
