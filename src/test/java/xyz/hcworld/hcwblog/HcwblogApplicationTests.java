package xyz.hcworld.hcwblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.hcworld.hcwblog.entity.User;
import xyz.hcworld.hcwblog.service.UserService;

@SpringBootTest
class HcwblogApplicationTests {

    @Autowired
    UserService userService;
    @Test
    public void contextLoads() {
        User user = userService.getById(1L);
        System.out.println(user.toString());
    }
}
