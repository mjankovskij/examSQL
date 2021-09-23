package lt.codeacademy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<Question> questions;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserExam> userExams;

//    public Exam(String name, Set<UserExam> userExams, Set<Question> questions)
//    {
//        this.name = name;
//        this.questions = questions;
//    }
//
//    public Exam(String name)
//    {
//        this.name = name;
//    }
}