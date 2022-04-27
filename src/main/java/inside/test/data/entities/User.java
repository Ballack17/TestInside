package inside.test.data.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user",schema = "test_inside")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name="id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Length(max = 32)
    private String name;

    @Column(name = "password")
    @Length(max = 64)
    private String password;

    @OneToMany(mappedBy = "user")
    @Column
    private List<Message> messages;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles", schema = "test_inside",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

}
