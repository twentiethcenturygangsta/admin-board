package com.github.twentiethcenturygangsta.adminboard.user;

import com.github.twentiethcenturygangsta.adminboard.annotation.AdminBoardColumn;
import com.github.twentiethcenturygangsta.adminboard.annotation.AdminBoardEntity;
import com.github.twentiethcenturygangsta.adminboard.task.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AdminBoardEntity(group = "AdminBoard", description = "대시보드 사용 권한이 있는 계정 테이블입니다.")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminBoardUser {

    @Id
    @GeneratedValue
    @Column(name = "admin_board_user_id")
    private Long id;
    @AdminBoardColumn(description = "아이디")
    private String userId;

    @AdminBoardColumn(isExposed = false)
    private String password;

    @AdminBoardColumn(description = "테이블 데이터를 생성할 수 있는 권한")
    private Boolean hasCreateObjectAuthority;
    @AdminBoardColumn(description = "대시보드 사용 권한을 가진 계정을 생성할 수 있는 권한")
    private Boolean hasCreateAdminBoardUserAuthority;
    @AdminBoardColumn(description = "테이블 데이터를 수정할 수 있는 권한")
    private Boolean hasUpdateObjectAuthority;
    @AdminBoardColumn(description = "테이블 데이터를 삭제할 수 있는 권한")
    private Boolean hasDeleteObjectAuthority;

    @OneToMany(mappedBy = "adminBoardUser", cascade = CascadeType.ALL)
    private final List<Task> tasks = new ArrayList<>();

    @Builder
    public AdminBoardUser(
            String userId,
            String password,
            Boolean hasCreateObjectAuthority,
            Boolean hasCreateAdminBoardUserAuthority,
            Boolean hasUpdateObjectAuthority,
            Boolean hasDeleteObjectAuthority
    ) {
        this.userId = userId;
        this.password = password;
        this.hasCreateObjectAuthority = hasCreateObjectAuthority;
        this.hasCreateAdminBoardUserAuthority = hasCreateAdminBoardUserAuthority;
        this.hasUpdateObjectAuthority = hasUpdateObjectAuthority;
        this.hasDeleteObjectAuthority = hasDeleteObjectAuthority;
    }

    public Boolean getHasCreateObjectAuthority() {
        return hasCreateObjectAuthority;
    }
    public Boolean getHasCreateAdminBoardUserAuthority() {
        return hasCreateAdminBoardUserAuthority;
    }

    public Boolean getHasDeleteObjectAuthority() {
        return hasDeleteObjectAuthority;
    }

    public Boolean getHasUpdateObjectAuthority() {
        return hasUpdateObjectAuthority;
    }
}
