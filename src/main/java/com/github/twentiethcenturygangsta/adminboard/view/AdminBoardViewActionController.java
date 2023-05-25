package com.github.twentiethcenturygangsta.adminboard.view;

import com.github.twentiethcenturygangsta.adminboard.AdminBoardLoginService;
import com.github.twentiethcenturygangsta.adminboard.AdminBoardServiceFactory;
import com.github.twentiethcenturygangsta.adminboard.SessionConst;
import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import com.github.twentiethcenturygangsta.adminboard.user.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin-board/action")
public class AdminBoardViewActionController {

    private final AdminBoardServiceFactory adminBoardServiceFactory;
    private final AdminBoardLoginService adminBoardLoginService;

    public AdminBoardViewActionController(AdminBoardServiceFactory adminBoardServiceFactory, AdminBoardLoginService adminBoardLoginService) {
        this.adminBoardServiceFactory = adminBoardServiceFactory;
        this.adminBoardLoginService = adminBoardLoginService;
    }

    @PostMapping("/login")
    public String Login (Model model, @ModelAttribute("login") LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Optional<AdminBoardUser> adminBoardUser = adminBoardLoginService.loginAdminBoardUser(loginRequestDto);
        if(adminBoardUser.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, adminBoardUser);
            return "redirect:/admin-board/task";
        } else{
            model.addAttribute("error", "일치하는 대시보드 계정이 존재하지 않습니다.");
            return "login";
        }

    }
}
