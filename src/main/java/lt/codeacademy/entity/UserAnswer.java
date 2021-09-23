package lt.codeacademy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private Character answer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userExam_id")
    private UserExam userExam;

    public UserAnswer(Long questionId, Character answer, UserExam userExam) {
        this.questionId = questionId;
        this.answer = answer;
        this.userExam = userExam;
    }
}