package lt.codeacademy.service;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.Question;
import lt.codeacademy.entity.User;
import lt.codeacademy.repository.ExamRepository;
import lt.codeacademy.repository.QuestionRepository;

import java.util.Scanner;

public class QuestionService {

    private final QuestionRepository questionRepository;
    private final Scanner sc;

    public QuestionService() {
        sc = new Scanner(System.in);
        questionRepository = new QuestionRepository();
    }

    public boolean createUpdateQuestion(Exam exam, Question question) {
        String description;
        int minLength = 5;
        int maxLength = 50;
        while (true) {
            long qNum = questionRepository.countQuestions(exam.getId()) + 1;
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
                return true;
            } else {
                System.out.println("Klausima turi sudaryti " + minLength + "-" + maxLength + " simboliu.");
            }
        }
    }

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
//            setExam(examRepository.getExam(num));
            if (0 == num) {
                return;
            }
            if (null != questionRepository.get(num)) {
                sc.nextLine();
                createUpdateQuestion(exam, questionRepository.get(num));
                System.out.println("Klausimas sekmingai atnaujintas.");
            } else {
                System.out.println("Tokio klausimo nera.");
            }
        }
    }


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

    public boolean validateLength(String str, int minLength, int maxLength) {
        return str.length() >= minLength
                && str.length() <= maxLength
                ;
    }

}
