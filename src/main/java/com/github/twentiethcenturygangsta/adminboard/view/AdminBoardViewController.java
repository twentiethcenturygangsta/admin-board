package com.github.twentiethcenturygangsta.adminboard.view;

import com.github.twentiethcenturygangsta.adminboard.AdminBoardFactory;
import com.github.twentiethcenturygangsta.adminboard.AdminBoardLoginService;
import com.github.twentiethcenturygangsta.adminboard.AdminBoardServiceFactory;
import com.github.twentiethcenturygangsta.adminboard.SessionConst;
import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import com.github.twentiethcenturygangsta.adminboard.user.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/AdminBoardUser")
    public String AdminUserView(Model model, HttpServletRequest request, @PageableDefault Pageable pageable) {
        getSideBarModel(model);
        getNavBarModel(model, request);
        model.addAttribute("data", adminBoardServiceFactory.getAdminBoardUsers(pageable));
        model.addAttribute("entity", adminBoardFactory.getEntity("AdminBoardUser"));
        model.addAttribute("groupName", "AdminBoard");
        model.addAttribute("entityName", "AdminBoardUser");
        return "adminUser";
    }

    @PostMapping("/login")
    public String Login (Model model, @ModelAttribute("login") LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Optional<AdminBoardUser> adminBoardUser = adminBoardLoginService.loginAdminBoardUser(loginRequestDto);
        if(adminBoardUser.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, adminBoardUser.get().getUserId());
            session.setAttribute("createAuthority", adminBoardServiceFactory.getAdminBoardUser(adminBoardUser.get().getUserId()).getHasCreateObjectAuthority());
            session.setAttribute("createAdminAuthority", adminBoardServiceFactory.getAdminBoardUser(adminBoardUser.get().getUserId()).getHasCreateAdminBoardUserAuthority());
            session.setAttribute("updateAuthority", adminBoardServiceFactory.getAdminBoardUser(adminBoardUser.get().getUserId()).getHasUpdateObjectAuthority());
            session.setAttribute("deleteAuthority", adminBoardServiceFactory.getAdminBoardUser(adminBoardUser.get().getUserId()).getHasDeleteObjectAuthority());
            return "redirect:/admin-board/task";
        } else{
            model.addAttribute("error", "일치하는 대시보드 계정이 존재하지 않습니다.");
            return "login";
        }
    }

    @GetMapping("/{entityName}")
    public String EntityView(
            Model model,
            HttpServletRequest request,
            @PathVariable("entityName") String entityName,
            String keyword,
            String type,
            @PageableDefault Pageable pageable) {
        getSideBarModel(model);
        getNavBarModel(model, request);

        if ("ALL".equals(keyword) && "ALL".equals(type)) {
            model.addAttribute("keyword", "ALL");
            model.addAttribute("type", "ALL");
        } else {
            model.addAttribute("keyword", keyword);
            model.addAttribute("type", type);
        }
        model.addAttribute("data", adminBoardFactory.getObjects(entityName, keyword, type, pageable));
        model.addAttribute("entity", adminBoardFactory.getEntity(entityName));
        model.addAttribute("entityName", entityName);

        return "entity";
    }

    @GetMapping("/{entityName}/{id}")
    public String EntityDetailView(
            Model model,
            HttpServletRequest request,
            @PathVariable("entityName") String entityName,
            @PathVariable("id") Long id) {
        getSideBarModel(model);
        getNavBarModel(model, request);
        model.addAttribute("entity", adminBoardFactory.getEntity(entityName));
        Object returnObject = null;
        Optional<Object> object = adminBoardFactory.getObject(entityName, id);
        if (object.isPresent()) {
            returnObject = object.get();
        }
        model.addAttribute("object", returnObject);
        model.addAttribute("entityName", entityName);
        model.addAttribute("entities", adminBoardFactory.getEntities());
        model.addAttribute("objectId", id);

        return "objectDetail";
    }

    @GetMapping("/login")
    public String LoginView(@ModelAttribute("login") LoginRequestDto loginRequestDto) {
        return "login";
    }

    @GetMapping("/task")
    public String TaskView( Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        getSideBarModel(model);
        getNavBarModel(model, request);
        String adminBoardUser = (String) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("data", adminBoardFactory.getTasks(adminBoardUser));
        model.addAttribute("entityName", "Task");
        return "tasks";
    }

    @GetMapping("/{entityName}/object")
    public String createObjectView(Model model, HttpServletRequest request, @PathVariable("entityName") String entityName) {
        getSideBarModel(model);
        getNavBarModel(model, request);
        HttpSession session = request.getSession();
        Boolean createAuthority = (Boolean) session.getAttribute("createAuthority");
        if(!createAuthority) {
            return "redirect:/admin-board/" + entityName;
        }
        model.addAttribute("entity", adminBoardFactory.getEntity(entityName));
        model.addAttribute("entityName", entityName);
        model.addAttribute("entities", adminBoardFactory.getEntities());
        model.addAttribute("enums", adminBoardFactory.getEnumClass());
        return "createObject";
    }

    @GetMapping("/{entityName}/object/{id}/edit")
    public String updateObjectView(
            Model model,
            HttpServletRequest request,
            @PathVariable("entityName") String entityName,
            @PathVariable("id") Long id
    ) {
        getSideBarModel(model);
        getNavBarModel(model, request);
        HttpSession session = request.getSession();
        Boolean updateAuthority = (Boolean) session.getAttribute("updateAuthority");
        if(!updateAuthority) {
            return "redirect:/admin-board/" + entityName +"/object/" + id;
        }
        model.addAttribute("entity", adminBoardFactory.getEntity(entityName));
        model.addAttribute("entityName", entityName);
        model.addAttribute("entities", adminBoardFactory.getEntities());
        model.addAttribute("enums", adminBoardFactory.getEnumClass());
        model.addAttribute("objectId", id);
        Optional<Object> object = adminBoardFactory.getObject(entityName, id);
        Object returnObject = null;
        if (object.isPresent()) {
            returnObject = object.get();
        }
        model.addAttribute("object", returnObject);
        return "updateObject";
    }

    private void getSideBarModel(Model model) {
        model.addAttribute("adminBoardInformation", adminBoardFactory.getAdminBoardInfo());
        model.addAttribute("entitiesByGroup", adminBoardFactory.getGroupEntities());
        model.addAttribute("entities", adminBoardFactory.getEntities());
    }

    private void getNavBarModel(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(SessionConst.LOGIN_MEMBER);
        AdminBoardUser adminBoardUser = adminBoardServiceFactory.getAdminBoardUser(userId);
        model.addAttribute("userName", userId);
        model.addAttribute("createAuthority", adminBoardUser.getHasCreateObjectAuthority());
        model.addAttribute("createAdminAuthority", adminBoardUser.getHasCreateAdminBoardUserAuthority());
        model.addAttribute("updateAuthority", adminBoardUser.getHasUpdateObjectAuthority());
        model.addAttribute("deleteAuthority", adminBoardUser.getHasDeleteObjectAuthority());
        session.setAttribute("createAuthority", adminBoardUser.getHasCreateObjectAuthority());
        session.setAttribute("createAdminAuthority", adminBoardUser.getHasCreateAdminBoardUserAuthority());
        session.setAttribute("updateAuthority", adminBoardUser.getHasUpdateObjectAuthority());
        session.setAttribute("deleteAuthority", adminBoardUser.getHasDeleteObjectAuthority());
    }
}
