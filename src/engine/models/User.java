package engine.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Pattern(regexp = ".+@.+\\..+")
    String email;

    @Column
    @Length(min = 5)
    String password;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
