package lt.codeacademy.service;

import lt.codeacademy.entity.User;

import java.util.Scanner;

public class AuthService {
    UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login() {
        Scanner sc = new Scanner(System.in);
        String name;
        while (true) {
            System.out.println("Iveskite varda:");
            name = sc.nextLine();
            if (userService.validateName(name)) {
            return userService.getUser(name);
            } else {
                System.out.println("Varda turi sudaryti tik raides, ilgis 2-20 simboliu.");
            }
        }
    }

}
