package inside.test.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "role", schema = "test_inside")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
}
