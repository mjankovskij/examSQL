package lt.codeacademy.service;

import lt.codeacademy.entity.User;
import lt.codeacademy.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public boolean validateName(String name) {
        return name.length() >= 2
                && name.length() <= 20
                && name.matches("^[a-zA-Z\\s]+")
                ;
    }

    public User getCreateUser(String name) {
        User user = userRepository.getUser(name);
        if (null == user) {
            user = new User();
            user.setName(name);
            userRepository.createUser(user);
        }
        return user;
    }
}