package lt.codeacademy.service;

import lt.codeacademy.entity.*;
import lt.codeacademy.repository.ExamRepository;
import lt.codeacademy.repository.UserAnswerRepository;
import lt.codeacademy.repository.UserExamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExamService {

    private final ExamRepository examRepository;
    private final User user;
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


    public void createUpdate(Exam exam) {
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

    public boolean validateName(String name) {
        return name.length() >= 5
                && name.length() <= 50
                ;
    }


    public void select() {
        long num;
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("|   Issirinkite egzamina (nr):   |");
            System.out.println("----------------------------------");
            System.out.println("0 - Atgal");
            examRepository.getExams().forEach(e -> {
                System.out.println(e.getId() + " - " + e.getName());
            });
            num = sc.nextLong();
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
                    System.out.println("Pasirinkite veiksma.");
                }
            }
            sc.nextLine();
        }
    }

    private void deleteExam(Exam exam) {
        System.out.println("DEMESIO! Egzaminas ir visi susije klausimai bus pasalinti!");
        System.out.println("Norint istrinti egzamina iveskite '" + exam.getName() + "'");
        if (sc.nextLine().equals(exam.getName())) {
//            UserExamService userExamService = new UserExamService();
//            userExamService.delete(exam);
            examRepository.delete(exam);
            System.out.println("Egzaminas sekmingai istrintas");
        } else {
            System.out.println("Bloga ivestis.");
        }
    }

    private void examStart(UserExamService userExamService) {
        System.out.println("-----------------------------");
        System.out.println("|   Pradetas egzaminas " + exam.getName());
        System.out.println("-----------------------------");

        UserExam userExam = new UserExam();
        userExam.setUser(user);
        userExam.setExam(exam);
        List<UserAnswer> userAnswers = new ArrayList<>();

        exam.getQuestions().forEach(e -> {
            System.out.println(e.getId() + " - " + e.getDescription());
            System.out.println("a) " + e.getAnswer1());
            System.out.println("b) " + e.getAnswer2());
            System.out.println("c) " + e.getAnswer3());
            while (true) {
                System.out.println("Jusu atsakymas:");
                char ans = sc.next().charAt(0);
                if (ans == 'a' || ans == 'b' || ans == 'c') {
                    userAnswers.add(new UserAnswer(e.getId(), ans, userExam));
                } else {
                    System.out.println("Tokio atsakymo nera!");
                }
                return;
            }
        });

        if (null == userExamService.getByUserAndExamIds(user.getId(), exam.getId())) {
            System.out.println("ok");
            UserExamRepository userExamRepository = new UserExamRepository();
            userExamRepository.create(userExam);
            UserAnswerRepository userAnswerRepository = new UserAnswerRepository();
            userAnswers.forEach(userAnswerRepository::create);
            System.out.println("jook");
        } else {
            System.out.println("Sis egzaminas jau laikytas.");
        }
    }


//    public double getResult(UserExam userExam){
//    }

    public boolean validateExam(Long id) {
        return examRepository.getExam(id) != null;
    }
}
