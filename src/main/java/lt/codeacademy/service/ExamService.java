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
    private UserExamService userExamService;
    private UserAnswerService userAnswerService;
    private Scanner sc;
    private User user;
    private Exam exam;

    public ExamService() {
        examRepository = new ExamRepository();
    }

    public ExamService(User user) {
        examRepository = new ExamRepository();
        sc = new Scanner(System.in);
        userExamService = new UserExamService();
        userAnswerService = new UserAnswerService();
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
            } catch (InputMismatchException e) {
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
        sc.nextLine();

        while (true) {
            System.out.println(" -----------------------------");
            System.out.println("|   0 - Atgal                |");
            System.out.println("|   1 - Laikyti egzamina     |");
            System.out.println("|   2 - Redaguoti egzamina   |");
            System.out.println("|   3 - Istrinti egzamina    |");
            System.out.println("|   4 - Statistika           |");
            System.out.println(" -----------------------------");
            String select = sc.nextLine();
            switch (select) {
                case "1" -> {
// PALIEKAM KAS UZKOMENTUOTA, JEI NORESIM LEISTI VIENAM USERIUI, VIENA EGZAMINA LAIKYTI TIK 1 KARTA
//                    if (null == userExamService.getByUserAndExamIds(user.getId(), exam.getId())) {
                    examStart(userExamService);
                    sc.nextLine();
//                    } else {
//                        System.out.println("Sis egzaminas jau laikytas.");
//                        return;
//                    }
                }
                case "2" -> {
                    createUpdate(exam);
                }
                case "3" -> {
                    deleteExam(exam);
                }
                case "4" -> {
                    stats(exam);
                }
                case "0" -> {
                    return;
                }
                default -> {
                    System.out.println("Pasirinkite teisinga veiksma.");
                }
            }
        }
    }

    // 1 Laikyti egzamina
    private void examStart(UserExamService userExamService) {
        UserExam userExam = new UserExam(user, exam);
        userExam.setExam(exam);
        userExam.setUser(user);

        System.out.println("-----------------------------");
        System.out.println("|   Pradetas egzaminas " + exam.getName());
        System.out.println("-----------------------------");

        List<UserAnswer> userAnswers = answerQuestionsProcess(userExam);
// PALIEKAM KAS UZKOMENTUOTA, JEI NORESIM LEISTI VIENAM USERIUI, VIENA EGZAMINA LAIKYTI TIK 1 KARTA
//        if (null == userExamService.getByUserAndExamIds(user.getId(), exam.getId())) {
        int result = userExamService.validate(userExam, userAnswers);
        userExam.setResult(result);
        userExamService.create(userExam);
        userAnswers.forEach(userAnswerService::create);

        System.out.println("Ivertinimas: " + result);
//        } else {
//            System.out.println("Sis egzaminas jau laikytas.");
//        }
    }

    // Pats procesas testo sprendimo.
    private List<UserAnswer> answerQuestionsProcess(UserExam userExam) {
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
                    userAnswers.add(new UserAnswer(e, ans, userExam));
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
                questionService.questionAddOrUpdate(exam);
                return;
            }
            if (validateName(newExamName)) {
                exam.setName(newExamName);
                examRepository.createUpdate(exam);
                System.out.println(newExamName + " - egzaminas sekmingai sukurtas/atnaujintas.");
                while (questionService.createUpdateQuestion(exam, new Question())) {
                    // Vidaus nereikia.
                    // Tiesiog kvieciant tik update nebutu while, o cia grazina iskart false kai reikia sustot.
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
        System.out.println("Norint istrinti egzamina iveskite '" + exam.getName() + "', arba rasykite bet ka, kad grizti.");
        if (sc.nextLine().equals(exam.getName())) {
            examRepository.delete(exam);
            System.out.println("Egzaminas ir visa susijusi informacija sekmingai istrinta.");
        } else {
            System.out.println("Bloga ivestis.");
        }
    }

    // 4 Statistika
    private void stats(Exam exam) {
        System.out.println(exam.getName() + " statistika:");

        System.out.println("Egzamians sprestas kartu: " + exam.getUserExams().size());

        System.out.println(
                "Atsakyta i klausimu viso/siame egzamine: "
                        + userAnswerService.totalAnswers()
                        + " / "
                        + userAnswerService.totalAnswersInExam(exam)
        );

        System.out.println(
                "Teisingu atsakymu viso/siame egzamine: "
                        + userAnswerService.totalCorrectAnswers()
                        + " / "
                        + userAnswerService.totalCorrectAnswersInExam(exam)
        );

        System.out.println(
                "Vidutiniskai teisingu atsakymu per egzamina: "
                        + userAnswerService.averageCorrectAnswers()
        );

        System.out.println("Pasirinkta atsakymo variantu (viso/egzamine):");
        System.out.println(
                "a: "
                        + userAnswerService.answerSelectedTimes('a')
                        + " / "
                        + userAnswerService.answerSelectedTimesInExam(exam, 'a')
        );
        System.out.println(
                "b: "
                        + userAnswerService.answerSelectedTimes('b')
                        + " / "
                        + userAnswerService.answerSelectedTimesInExam(exam, 'b')
        );
        System.out.println(
                "c: "
                        + userAnswerService.answerSelectedTimes('c')
                        + " / "
                        + userAnswerService.answerSelectedTimesInExam(exam, 'c')
        );

        List<UserExam> userExamList = userExamService.getAllSameUserExamsByExam(user, exam);
        System.out.println("Jus si egzamina laikete " + userExamList.size() + " kart.");
        userExamList.forEach(e -> {
            System.out.println("Rezultatas: " +  e.getResult());
        });
    }

    public List<Exam> getExams() {
        return examRepository.getExams();
    }
}
