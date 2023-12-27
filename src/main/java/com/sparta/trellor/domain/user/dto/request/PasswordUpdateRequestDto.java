package com.sparta.trellor.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordUpdateRequestDto {
    private String currentPassword; // 현재 비밀번호

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[a-zA-Z0-9!@#$]{8,15}$",
            message = "비밀번호는 a-z, A-Z, 0-9, !@#$ 만 포함하고 8-15자이어야 합니다.")
    private String password; // 새로운 비밀번호
}
