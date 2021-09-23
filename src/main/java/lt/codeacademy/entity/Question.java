package lt.codeacademy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String description;

    @Column(nullable = false, length = 30)
    private String answer1, answer2, answer3;

    @Column(nullable = false)
    private Character correct;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    public Question(String description, String answer1, String answer2, String answer3, Character correct, Exam exam) {
        this.description = description;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.correct = correct;
        this.exam = exam;
    }
}