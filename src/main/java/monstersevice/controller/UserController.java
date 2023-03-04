package monstersevice.controller;

import monstersevice.model.user.User;
import monstersevice.service.external.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<User> getUser() {
        try {
            return new ResponseEntity<>(
                    userService.getUser(), HttpStatus.OK
            );
        } catch (Exception ex) {
            return new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST);
        }
    }
}
