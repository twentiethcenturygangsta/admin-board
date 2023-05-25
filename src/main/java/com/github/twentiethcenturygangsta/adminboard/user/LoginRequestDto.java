package com.github.twentiethcenturygangsta.adminboard.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String memberId;
    private String password;

    @Builder
    public LoginRequestDto(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }
}
