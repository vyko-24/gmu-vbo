package sgu.vbo.server.modules.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgu.vbo.server.modules.user.User;

@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name="fullname", nullable = false)
    private String fullname;

    @Column(name="matricula", nullable = false)
    private String matricula;

    @OneToOne
    @JoinColumn(name="id_user", nullable = false)
    private User user;

}
