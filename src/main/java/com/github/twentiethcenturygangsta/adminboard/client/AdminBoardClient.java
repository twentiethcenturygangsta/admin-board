package com.github.twentiethcenturygangsta.adminboard.client;

import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class AdminBoardClient {

    private final UserCredentials userCredentials;
    private final AdminBoardInfo adminBoardInfo;

    @Builder
    public AdminBoardClient(UserCredentials userCredentials, AdminBoardInfo adminBoardInfo) {
        this.userCredentials = userCredentials;
        this.adminBoardInfo = adminBoardInfo;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public AdminBoardInfo getAdminBoardInfo() {
        return adminBoardInfo;
    }

    public AdminBoardUser createAdminBoardUser() {
        return AdminBoardUser.builder()
                .userId(userCredentials.getMemberId())
                .password(userCredentials.getPassword())
                .hasCreateAdminBoardUserAuthority(true)
                .hasCreateObjectAuthority(true)
                .hasUpdateObjectAuthority(true)
                .hasDeleteObjectAuthority(true)
                .build();
    }
}
