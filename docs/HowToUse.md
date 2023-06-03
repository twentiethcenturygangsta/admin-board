# How To Use
There are two ways to use the library.
One is using `mavenLocal` and the other is `using modules distributed with Jitpack`.

## Basic Step

### Config
Create `AdminBoardClient` bean

- UserCredentials : Settings for accounts that can use the dashboard.
```java
@Configuration
public class AdminBoardConfig {

    @Bean
    public AdminBoardClient adminBoardClient() {
        UserCredentials userCredentials = UserCredentials.builder().memberId("aaa").password("1234").build();
        AdminBoardInfo adminBoardInfo = AdminBoardInfo.builder().description("상품 데이터를 확인하는 대시보드 서비스").title("ADMIN BOARD").version("1.0.0").build();

        return AdminBoardClient.builder().adminBoardInfo(adminBoardInfo).userCredentials(userCredentials).build();
    }
}
```

