package com.sparta.trellor.util;

import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.domain.user.entity.UserRoleEnum;

public interface CommonTest {

    String ANOTHER_PREFIX = "another";
    Long TEST_USER_ID = 1L;
    Long TEST_ANOTHER_USER_ID = 2L;
    String TEST_USER_NAME = "user999";
    String TEST_USER_EMAIL = "user999@email.com";
    String TEST_USER_PASSWORD = "asdf999";
    UserRoleEnum TEST_USER_ROLE = UserRoleEnum.USER;
    User TEST_USER =
        new User(
            TEST_USER_NAME,
            TEST_USER_EMAIL,
            TEST_USER_PASSWORD,
            TEST_USER_ROLE);
    User TEST_ANOTHER_USER =
        new User(
            ANOTHER_PREFIX + TEST_USER_NAME,
            ANOTHER_PREFIX + TEST_USER_EMAIL,
            TEST_USER_PASSWORD,
            TEST_USER_ROLE);

}
