package lt.codeacademy;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.User;
import lt.codeacademy.entity.UserExam;
import lt.codeacademy.repository.ExamRepository;
import lt.codeacademy.repository.UserExamRepository;
import lt.codeacademy.service.AuthService;
import lt.codeacademy.service.ExamService;
import lt.codeacademy.service.MenuService;
import lt.codeacademy.service.UserService;


public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------------------------------");
        System.out.println("|   EGZAMINAVIMU PROGRAMA / Sveiki avtyke   |");
        System.out.println("---------------------------------------------");

        UserService userService = new UserService();
        AuthService authService = new AuthService(userService);

        // Prisijungimas (ivedam user varda, jei vardas ivestas ok, randam DB, jei ten nera, irasom).
        User user = authService.login();
        if (null != user) {
            MenuService menuService = new MenuService();
            menuService.selectExam(user);
        }

        System.out.println("---------------------------------------------------");
        System.out.println("|   EGZAMINAVIMU PROGRAMA / Lauksime sugriztant   |");
        System.out.println("---------------------------------------------------");
    }
}