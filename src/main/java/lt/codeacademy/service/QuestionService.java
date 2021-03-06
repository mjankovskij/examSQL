package lt.codeacademy.service;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.Question;
import lt.codeacademy.repository.QuestionRepository;

import java.util.Scanner;

public class QuestionService {

    private final QuestionRepository questionRepository;
    private final Scanner sc;

    public QuestionService() {
        sc = new Scanner(System.in);
        questionRepository = new QuestionRepository();
    }

    // Egzamino kurimas arba atnaujinimas.
    // Kelias is ExamService.
    public boolean createUpdateQuestion(Exam exam, Question question) {
        String description;
        int minLength = 5;
        int maxLength = 50;
        while (true) {
            long qNum = questionRepository.countQuestions(exam) + 1;
            System.out.println("-----------------------------------");
            System.out.println("|   0 - Baigti                    |");
            if (null == question.getDescription()) {
                System.out.println("|   Uzduokite klausima nr. " + qNum + " :");
            } else {
                System.out.println("Klausimo ID " + question.getId() + ", naujas aprasymas:");
            }

            description = sc.nextLine();

            if (description.equals("0")) {
                System.out.println(
                        "Egzaminas " + exam.getName()
                                + " sekmingai sukurtas/atnaujintas su "
                                + (qNum - 1) + " klausimais(-u)."
                );
                return false;
            }

            if (validateLength(description, minLength, maxLength)) {
                question.setDescription(description);
                question.setAnswer1(enterAnswer('a'));
                question.setAnswer2(enterAnswer('b'));
                question.setAnswer3(enterAnswer('c'));
                question.setCorrect(selectCorrectAnswer());
                if (null == question.getExam()) {
                    question.setExam(exam);
                }
                questionRepository.createUpdate(question);
                sc.nextLine();
                System.out.println("Klausimas sekmingai sukurtas/atnaujintas.");
                return true;
            } else {
                System.out.println("Klausima turi sudaryti " + minLength + "-" + maxLength + " simboliu.");
            }
        }
    }

    // Egzamino klausimu redagavimas
    public void questionAddOrUpdate(Exam exam){
        while (true) {
            System.out.println(" --------------------------------------------");
            System.out.println("|   0 - Atgal                               |");
            System.out.println("|   1 - Redaguoti viena is esamu klausimu   |");
            System.out.println("|   2 - Kurti naujus klausimus              |");
            System.out.println(" --------------------------------------------");

            String select = sc.nextLine();
            switch (select) {
                case "0" -> {
                    return;
                }
                case "1" -> {
                    selectQuestionToUpdate(exam);
                }
                case "2" -> {
                    while (createUpdateQuestion(exam, new Question())) {
                        // vidaus nereikia, cia del update galimybes perkeltas while.
                    }
                }
                default -> {
                    System.out.println("Tokio pasirinkimo nera.");
                }
            }
        }
    }

    // Pasirenkamas egzamino klausimas kuri norima redaguoti.
    public void selectQuestionToUpdate(Exam exam) {
        long num;
        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("|   Issirinkite klausima redagavimui (nr):   |");
            System.out.println("----------------------------------------------");
            System.out.println("0 - Atgal");
            exam.getQuestions().forEach(e -> {
                System.out.println(e.getId() + " - " + e.getDescription());
            });
            num = sc.nextLong();

            if (0 == num) {
                return;
            }
            if (null != questionRepository.get(num)) {
                sc.nextLine();
                createUpdateQuestion(exam, questionRepository.get(num));
            } else {
                System.out.println("Tokio klausimo nera.");
            }
        }
    }

//    Ivedamas kausimo atsakymas pagal char a, b arba  c. Tiek kuriant nauja, tiek atnaujinant.
    public String enterAnswer(char qNum) {
        String answer;
        int minLength = 1;
        int maxLength = 20;
        while (true) {
            System.out.println("Iveskite atsakyma " + qNum + ":");
            answer = sc.nextLine();
            if (validateLength(answer, minLength, maxLength)) {
                return answer;
            } else {
                System.out.println("Atsakyma turi sudaryti " + minLength + "-" + maxLength + " simboliu.");
            }
        }
    }

    // Ivedama teisingo atsakymo raide. Tiek kuriant nauja, tiek atnaujinant.
    public char selectCorrectAnswer() {
        while (true) {
            System.out.println(" ----------------------------------------");
            System.out.println("|   Iveskite teisingo atsakymo raide:   |");
            System.out.println(" ----------------------------------------");
            char select = sc.next().charAt(0);

            switch (select) {
                case 'a', 'b', 'c' -> {
                    return select;
                }
                default -> {
                    System.out.println("Tokio atsakymo nera.");
                }
            }
        }
    }

    // Tikrinamas ilgis klausimo, atsakymo.
    public boolean validateLength(String str, int minLength, int maxLength) {
        return str.length() >= minLength
                && str.length() <= maxLength
                ;
    }

}
