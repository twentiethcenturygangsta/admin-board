package com.github.twentiethcenturygangsta.adminboard.view;

import com.github.twentiethcenturygangsta.adminboard.AdminBoardFactory;
import com.github.twentiethcenturygangsta.adminboard.AdminBoardLoginService;
import com.github.twentiethcenturygangsta.adminboard.AdminBoardServiceFactory;
import com.github.twentiethcenturygangsta.adminboard.user.LoginRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin-board")
public class AdminBoardViewController {
    private final AdminBoardFactory adminBoardFactory;
    private final AdminBoardServiceFactory adminBoardServiceFactory;
    private final AdminBoardLoginService adminBoardLoginService;

    public AdminBoardViewController(
            final AdminBoardFactory adminBoardFactory,
            final AdminBoardServiceFactory adminBoardServiceFactory,
            final AdminBoardLoginService adminBoardLoginService
    ) {
        this.adminBoardFactory = adminBoardFactory;
        this.adminBoardServiceFactory = adminBoardServiceFactory;
        this.adminBoardLoginService = adminBoardLoginService;
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
    public String AdminUserView(Model model, @PageableDefault Pageable pageable) {
        getSideBarModel(model);
        model.addAttribute("data", adminBoardServiceFactory.getAdminBoardUsers(pageable));
        model.addAttribute("entity", adminBoardFactory.getEntity("AdminBoardUser"));
        model.addAttribute("groupName", "AdminBoard");
        model.addAttribute("entityName", "AdminBoardUser");
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

    @GetMapping("/{entityName}/{id}")
    public String EntityDetailView(
            Model model,
            @PathVariable("entityName") String entityName,
            @PathVariable("id") Long id) {
        getSideBarModel(model);
        model.addAttribute("entity", adminBoardFactory.getEntity(entityName));
        Object returnObject = null;
        Optional<Object> object = adminBoardFactory.getObject(entityName, id);
        if (object.isPresent()) {
            returnObject = object.get();
        }
        model.addAttribute("object", returnObject);
        model.addAttribute("entityName", entityName);
        model.addAttribute("entities", adminBoardFactory.getEntities());

        return "objectDetail";
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
    public String LoginView(@ModelAttribute("login") LoginRequestDto loginRequestDto) {
        return "login";
    }

    @GetMapping("/task")
    public String TaskView(Model model, @PageableDefault Pageable pageable) {
        getSideBarModel(model);
        model.addAttribute("data", adminBoardFactory.getObjects("Task", pageable));
        model.addAttribute("entityName", "Task");
        return "tasks";
    }

    private void getSideBarModel(Model model) {
        model.addAttribute("adminBoardInformation", adminBoardFactory.getAdminBoardInfo());
        model.addAttribute("entitiesByGroup", adminBoardFactory.getEntitiesByGroup());
        model.addAttribute("entities", adminBoardFactory.getEntities());
    }
}
