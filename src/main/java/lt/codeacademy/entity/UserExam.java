package lt.codeacademy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_exams")
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "exam_id"})}

public class UserExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 2)
    private Integer result;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @OneToMany(mappedBy = "userExam", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserAnswer> userAnswers;

    public UserExam(User user, Exam exam) {
        this.user = user;
        this.exam = exam;
    }
}