package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.repository.AdminBoardUserRepository;
import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import com.github.twentiethcenturygangsta.adminboard.user.LoginRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminBoardLoginService {
    private final AdminBoardUserRepository adminBoardUserRepository;

    public AdminBoardLoginService(AdminBoardUserRepository adminBoardUserRepository) {
        this.adminBoardUserRepository = adminBoardUserRepository;
    }

    public Optional<AdminBoardUser> loginAdminBoardUser(LoginRequestDto loginRequestDto) {
        return adminBoardUserRepository.findByUserIdAndPassword(loginRequestDto.getMemberId(), loginRequestDto.getPassword());
    }
}
