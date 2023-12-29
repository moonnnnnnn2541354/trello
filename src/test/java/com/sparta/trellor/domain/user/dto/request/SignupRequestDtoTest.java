package com.sparta.trellor.domain.user.dto.request;

import com.sparta.trellor.test.CommonTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DisplayName("회원가입 요청 DTO 유효성 검사")
class SignupRequestDtoTest implements CommonTest {

    @DisplayName("회원가입 요청 DTO 생성")
    @Nested
    class signupRequestDTO {
        ValidatorFactory factory;
        Validator validator;

        @BeforeEach
        void setUp() {
            factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @DisplayName("회원가입 요청 DTO 생성 성공")
        @Test
        void createSignupRequestDTO_success() {
            // given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername(TEST_USER_NAME);
            signupRequestDto.setPassword(TEST_USER_PASSWORD);
            signupRequestDto.setEmail(TEST_USER_EMAIL);

            // when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            // then
            assertThat(violations).isEmpty();
        }

        @DisplayName("회원가입 요청 DTO 생성 실패 - 잘못된 username")
        @Test
        void createSignupRequestDTO_wrongUsername() {
            // given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername("Invalid username");
            signupRequestDto.setPassword(TEST_USER_PASSWORD);
            signupRequestDto.setEmail(TEST_USER_EMAIL);

            // when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                    .extracting("message")
                    .contains("사용자이름은 a-z, 0-9 만 포함하고 2-10자이어야 합니다.");
        }

        @DisplayName("회원가입 요청 DTO 생성 실패 - 잘못된 password")
        @Test
        void createSignupRequestDTO_wrongPassword() {
            // given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername(TEST_USER_NAME);
            signupRequestDto.setPassword("Invalid password");
            signupRequestDto.setEmail(TEST_USER_EMAIL);

            // when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                    .extracting("message")
                    .contains("비밀번호는 a-z, A-Z, 0-9, !@#$ 만 포함하고 8-15자이어야 합니다.");
        }

        @DisplayName("회원가입 요청 DTO 생성 실패 - 잘못된 email")
        @Test
        void createSignupRequestDTO_wrongEmail() {
            // given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername(TEST_USER_NAME);
            signupRequestDto.setPassword(TEST_USER_PASSWORD);
            signupRequestDto.setEmail("Invalid email");

            // when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                    .extracting("message")
                    .contains("email 형식만 입력 가능합니다.");
        }
    }
}