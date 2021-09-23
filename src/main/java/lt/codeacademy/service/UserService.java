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

    public User getUser(String name) {
        User user = userRepository.getUser(name);
        if (null == user) {
            user = new User();
            user.setName(name);
            userRepository.createUser(user);
        }
        return user;
    }

//    public List<User> getUsers() {
//        return userRepository.getUsers();
//    }

//    public void getUsers() {
//        userRepository.getUsers().forEach(System.out::println);
//    }

//    public void update() {
//        User user = userRepository.getUser(1L);
//
//        if (user == null) {
//            System.out.println("User does not exist");
//            return;
//        }
//
//        user.setName("Naujas vardas");
////        user.setSurname("Nauja pavarde");
//
//        userRepository.updateUser(user);
//    }

//    public void updateUserNameById() {
//        userRepository.updateUserNameById(2L, "Petras");
//    }
//
//    public void delete() {
//        User user = userRepository.getUser(2L);
//        userRepository.delete(user);
//    }


}