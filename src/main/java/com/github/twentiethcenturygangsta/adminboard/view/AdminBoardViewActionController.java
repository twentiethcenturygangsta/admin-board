package com.github.twentiethcenturygangsta.adminboard.view;

import com.github.twentiethcenturygangsta.adminboard.AdminBoardFactory;
import com.github.twentiethcenturygangsta.adminboard.AdminBoardLoginService;
import com.github.twentiethcenturygangsta.adminboard.AdminBoardServiceFactory;
import com.github.twentiethcenturygangsta.adminboard.SessionConst;
import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import com.github.twentiethcenturygangsta.adminboard.user.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/admin-board/action")
public class AdminBoardViewActionController {

    private final AdminBoardServiceFactory adminBoardServiceFactory;
    private final AdminBoardLoginService adminBoardLoginService;
    private final AdminBoardFactory adminBoardFactory;

    public AdminBoardViewActionController(AdminBoardServiceFactory adminBoardServiceFactory, AdminBoardLoginService adminBoardLoginService, AdminBoardFactory adminBoardFactory) {
        this.adminBoardServiceFactory = adminBoardServiceFactory;
        this.adminBoardLoginService = adminBoardLoginService;
        this.adminBoardFactory = adminBoardFactory;
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

    @GetMapping("/{entityName}/search")
    public ResponseEntity<Object> searchObjects(
            @PathVariable("entityName") String entityName,
            @RequestParam("keyword") String keyword,
            @RequestParam("searchType") String searchType,
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize) {
        return ResponseEntity.ok(adminBoardFactory.getSearchObjects(entityName, searchType, keyword, pageIndex, pageSize));
    }

    @PostMapping("/{entityName}/object")
    public ResponseEntity<Object> createObject(
            @PathVariable("entityName") String entityName,
            @RequestBody HashMap<String, Object> object
    ) {
        return ResponseEntity.ok(adminBoardFactory.createObject(entityName, object));
    }
}
