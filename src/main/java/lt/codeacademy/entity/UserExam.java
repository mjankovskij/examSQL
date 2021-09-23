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
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @OneToMany(mappedBy = "userExam", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserAnswer> userAnswers;

//    public UserExam(User user) {
//        this.user = user;
//    }
//    private Exam exam;
//    @Column(nullable = false)

//    public UserExam(String name)
//    {
//        this.id = id;
//    }
}