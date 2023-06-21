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
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
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
        adminBoardFactory.createObject(entityName, object);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/tasks/object")
    public ResponseEntity<Object> createTask(
            @RequestBody HashMap<String, Object> object
    ) {
        log.info("task = {} {} ", (String) object.get("taskContent"), (String) object.get("adminBoardUserName"));
        adminBoardFactory.createTask((String) object.get("taskContent"), (String) object.get("adminBoardUserName"));
        return ResponseEntity.ok("success");
    }
}
