package lt.codeacademy.service;

import lt.codeacademy.entity.*;
import lt.codeacademy.repository.ExamRepository;
import lt.codeacademy.repository.UserAnswerRepository;
import lt.codeacademy.repository.UserExamRepository;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ExamService {

    private final ExamRepository examRepository;
    private User user;
    private final Scanner sc;
    private Exam exam;

    public ExamService(User user) {
        examRepository = new ExamRepository();
        sc = new Scanner(System.in);
        this.user = user;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    // Issirinkimas egzamino, jei veliau norima laikyti arba redaguoti.
    // Kelias is MenuService.
    protected void select() {
        long num;

        while (true) {
            System.out.println("----------------------------------");
            System.out.println("|   Issirinkite egzamina (nr):   |");
            System.out.println("----------------------------------");
            System.out.println("0 - Atgal");

            examRepository.getExams().forEach(e -> {
                System.out.println(e.getId() + " - " + e.getName());
            });
            try {
                num = sc.nextLong();
            }catch(InputMismatchException e){
                System.out.println("Tokio egzamino nera.");
                sc.nextLine();
                continue;
            }
            setExam(examRepository.getExam(num));
            if (0 == num) {
                return;
            }
            if (null != exam) {
                examActions(exam);
            } else {
                System.out.println("Tokio egzamino nera.");
            }
        }
    }

    // Pasirinkus egzamina metode 'select()', veiksmu pasirinkimas.
    private void examActions(Exam exam) {
        UserExamService userExamService = new UserExamService();
        sc.nextLine();

        while (true) {
            System.out.println(" -----------------------------");
            System.out.println("|   0 - Atgal                |");
            System.out.println("|   1 - Laikyti egzamina     |");
            System.out.println("|   2 - Redaguoti egzamina   |");
            System.out.println("|   3 - Istrinti egzamina    |");
            System.out.println(" -----------------------------");
            String select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    if (null == userExamService.getByUserAndExamIds(user.getId(), exam.getId())) {
                        examStart(userExamService);
                    } else {
                        System.out.println("Sis egzaminas jau laikytas.");
                        return;
                    }
                }
                case "2" -> {
                    createUpdate(exam);
                    return;
                }
                case "3" -> {
                    deleteExam(exam);
                    return;
                }
                case "0" -> {
                    return;
                }
                default -> {
                    System.out.println("Pasirinkite teisinga veiksma.");
                }
            }
            sc.nextLine();
        }
    }

    // 1 Laikyti egzamina
    private void examStart(UserExamService userExamService) {
        UserAnswerService userAnswerService = new UserAnswerService();
        UserExam userExam = new UserExam(user, exam);
        userExam.setExam(exam);
        userExam.setUser(user);

        System.out.println("-----------------------------");
        System.out.println("|   Pradetas egzaminas " + exam.getName());
        System.out.println("-----------------------------");

        List<UserAnswer> userAnswers = answerQuestionsProcess(userExam);

        if (null == userExamService.getByUserAndExamIds(user.getId(), exam.getId())) {

            System.out.println(1);
            userExamService.create(userExam);
            System.out.println(2);
            userAnswers.forEach(userAnswerService::create);
            System.out.println(3);
            System.out.println("Ivertinimas: " +  userExamService.getResult(userExam));
        } else {
            System.out.println("Sis egzaminas jau laikytas.");
        }
    }

    // Pats procesas testo sprendimo.
    private List<UserAnswer> answerQuestionsProcess(UserExam userExam){
        List<UserAnswer> userAnswers = new ArrayList<>();
        exam.getQuestions().forEach(e -> {
            System.out.println(e.getId() + " - " + e.getDescription());
            System.out.println("a) " + e.getAnswer1());
            System.out.println("b) " + e.getAnswer2());
            System.out.println("c) " + e.getAnswer3());
            while (true) {
                System.out.println("Iveskite atsakymo raide:");
                char ans = sc.next().charAt(0);
                if (ans == 'a' || ans == 'b' || ans == 'c') {
                    userAnswers.add(new UserAnswer(e.getId(), ans, userExam));
                    return;
                } else {
                    System.out.println("Tokio atsakymo nera!");
                }
            }
        });
        return userAnswers;
    }

    // 2 Kurti arba redaguoti egzamina.
    // Protected nes gali but ne tik atnaujinimas, bet ir kuriamas is MenuService.
    protected void createUpdate(Exam exam) {
        if (null == exam) {
            exam = new Exam();
        }
        String newExamName;
        while (true) {
            System.out.println("--------------------------------------------");
            System.out.println("|   0 - Atgal                              |");
            if (null == exam.getName()) {
                System.out.println("|   Naujo egzamino pavadinimas:            |");
            } else {
                System.out.println("|   1 - Pereiti prie klausimu redagavimo   |");
                System.out.println("|   Egzamino '" + exam.getName() + "' naujas pavadinimas:");
            }
            System.out.println("--------------------------------------------");
            newExamName = sc.nextLine();
            if (newExamName.equals("0")) {
                return;
            }
            QuestionService questionService = new QuestionService();
            if (null != exam.getName() && newExamName.equals("1")) {
                questionService.selectQuestionToUpdate(exam);
                return;
            }
            if (validateName(newExamName)) {
                exam.setName(newExamName);
                examRepository.createUpdate(exam);
                System.out.println(newExamName + " - egzaminas sekmingai sukurtas/atnaujintas.");
                while (questionService.createUpdateQuestion(exam, new Question())) {
                    // vidaus nereikia, cia del update galimybes perkeltas while.
                }
                return;
            } else {
                System.out.println("Pavadinimo ilgis turi buti 5-50 simboliu.");
            }
        }
    }

    // Egzamino pavadinimo validacija.
    private boolean validateName(String name) {
        return name.length() >= 5
                && name.length() <= 50
                ;
    }

    // 3 Egzamino trinimas
    private void deleteExam(Exam exam) {
        System.out.println("DEMESIO! Egzaminas ir visa susijusi informacija bus pasalinta!");
        System.out.println("Norint istrinti egzamina iveskite '" + exam.getName() + "'");
        if (sc.nextLine().equals(exam.getName())) {
            examRepository.delete(exam);
            System.out.println("Egzaminas sekmingai istrintas");
        } else {
            System.out.println("Bloga ivestis.");
        }
    }

}
