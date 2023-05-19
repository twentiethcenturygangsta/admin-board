package com.github.twentiethcenturygangsta.adminboard.view;

import com.github.twentiethcenturygangsta.adminboard.AdminBoardFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin-board")
public class AdminBoardViewController {
    private final AdminBoardFactory adminBoardFactory;

    public AdminBoardViewController(final AdminBoardFactory adminBoardFactory) {
        this.adminBoardFactory = adminBoardFactory;
    }

    @GetMapping("/home")
    public String HomeView(Model model) {
        model.addAttribute("adminBoardInformation", adminBoardFactory.getAdminBoardInfo());
        model.addAttribute("data", adminBoardFactory.getEntitiesByGroup());
        model.addAttribute("entities", adminBoardFactory.getEntities());
        log.info("entities = {}", adminBoardFactory.getEntities());
        log.info("data = {}", adminBoardFactory.getEntitiesByGroup());

        return "home";
    }

    @GetMapping("/user")
    public String ProfileView(Model model) {
        model.addAttribute("data", adminBoardFactory.getEntities());
        return "user";
    }

    @GetMapping("/table")
    public String TableView(Model model) {
        model.addAttribute("data", adminBoardFactory.getEntities());
        return "table";
    }

    @GetMapping("/notifications")
    public String NotificationView(Model model) {
        model.addAttribute("data", adminBoardFactory.getEntities());
        return "notifications";
    }

    @GetMapping("/icons")
    public String IconsView(Model model) {
        model.addAttribute("data", adminBoardFactory.getEntities());
        return "icons";
    }

    @GetMapping("/maps")
    public String MapsView(Model model) {
        model.addAttribute("data", adminBoardFactory.getEntities());
        return "maps";
    }

    @GetMapping("/typography")
    public String TypographyView(Model model) {
        model.addAttribute("data", adminBoardFactory.getEntities());
        return "typography";
    }

    @GetMapping("/upgrade")
    public String UpgradeView(Model model) {
        model.addAttribute("data", adminBoardFactory.getEntities());
        return "upgrade";
    }


    @GetMapping("/login")
    public String LoginView(Model model) {
        return "login";
    }
}
