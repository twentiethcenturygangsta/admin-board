package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.client.AdminBoardClient;
import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import com.github.twentiethcenturygangsta.adminboard.repository.AdminBoardUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminBoardServiceFactory {
    private final AdminBoardUserRepository adminBoardUserRepository;
    private final AdminBoardClient adminBoardClient;

    public void createSuperAdminBoardUser() {
        AdminBoardUser adminBoardUser = adminBoardClient.createAdminBoardUser();
        Optional<AdminBoardUser> user = adminBoardUserRepository.findByUserIdAndPassword(adminBoardUser.getUserId(), adminBoardUser.getPassword());
        if (user.isEmpty()) {
            adminBoardUserRepository.save(adminBoardUser);
        }
    }

    public Page<AdminBoardUser> getAdminBoardUsers(Pageable pageable) {
        return adminBoardUserRepository.findAll(pageable);
    }
}
