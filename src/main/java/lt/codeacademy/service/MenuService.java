package lt.codeacademy.service;

import java.util.Scanner;

public class MenuService {
    private Scanner sc;

    public MenuService() {
         sc = new Scanner(System.in);
    }

    public void selectExam(ExamService examService) {
        while (true) {
            System.out.println(" ---------------------------------");
            System.out.println("|   0 - Baigti darba             |");
            System.out.println("|   1 - Pasirinkti egzamina      |");
            System.out.println("|   2 - Sukurti nauja egzamina   |");
            System.out.println(" ---------------------------------");

            String select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    examService.select();
                }
                case "2" -> {
                    examService.createUpdate(null);
                }
                case "0" -> {
                    return;
                }
                default -> {
                    System.out.println("Pasirinkite veiksma.");
                }
            }
        }
    }
}
