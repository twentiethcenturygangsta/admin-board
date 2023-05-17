package com.github.twentiethcenturygangsta.adminboard.view;

import com.github.twentiethcenturygangsta.adminboard.AdminBoardFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin-board")
public class AdminBoardViewController {
    private final AdminBoardFactory adminBoardFactory;

    public AdminBoardViewController(final AdminBoardFactory adminBoardFactory) {
        this.adminBoardFactory = adminBoardFactory;
    }

    @GetMapping("/home")
    public String HomeView(Model model) {
        model.addAttribute("data", adminBoardFactory.getEntities());
        return "home";
    }

    @GetMapping("/login")
    public String LoginView(Model model) {
        return "login";
    }
}
