package com.sparta.trellor.domain.user.dto.request;

import com.sparta.trellor.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[a-zA-Z0-9]{2,10}$",
            message = "사용자이름은 a-z, A-Z, 0-9 만 포함하고 2-10자이어야 합니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "email형식만 입력 가능합니다.")
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[a-zA-Z0-9!@#$]{8,15}$",
            message = "비밀번호는 a-z, A-Z, 0-9, !@#$ 만 포함하고 8-15자이어야 합니다.")
    private String password;

    private boolean admin = false;
}
