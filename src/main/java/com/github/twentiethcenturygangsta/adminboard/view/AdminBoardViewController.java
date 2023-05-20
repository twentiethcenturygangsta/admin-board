package com.github.twentiethcenturygangsta.adminboard.view;

import com.github.twentiethcenturygangsta.adminboard.AdminBoardFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        log.info("entities = {}", adminBoardFactory.getEntities());
        log.info("data = {}", adminBoardFactory.getEntitiesByGroup());

        return "home";
    }

    @GetMapping("/user")
    public String ProfileView(Model model) {
        getSideBarModel(model);
        model.addAttribute("data", adminBoardFactory.getEntities());
        return "user";
    }

    @GetMapping("/admin-user")
    public String AdminUserView(Model model) {
        getSideBarModel(model);
        model.addAttribute("data", adminBoardFactory.getEntities());
        model.addAttribute("entityName", "AdminUser");
        return "adminUser";
    }

    @GetMapping("/{entityName}")
    public String EntityView(Model model, @PathVariable("entityName") String entityName, @PageableDefault Pageable pageable) {
        getSideBarModel(model);
        model.addAttribute("data", adminBoardFactory.getObjects(entityName, pageable));
        model.addAttribute("entity", adminBoardFactory.getEntity(entityName));
        model.addAttribute("entityName", entityName);

        return "entity";
    }

    @GetMapping("/table")
    public String TableView(Model model) {
        getSideBarModel(model);
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

    @GetMapping("/tasks")
    public String TaskView(Model model) {
        getSideBarModel(model);
        model.addAttribute("entityName", "tasks");
        return "tasks";
    }

    private void getSideBarModel(Model model) {
        model.addAttribute("adminBoardInformation", adminBoardFactory.getAdminBoardInfo());
        model.addAttribute("entitiesByGroup", adminBoardFactory.getEntitiesByGroup());
        model.addAttribute("entities", adminBoardFactory.getEntities());
    }
}
