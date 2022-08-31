package nl.duckstudios.pintandpillage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Village> villages;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Setter
    @Getter
    private String password;

    @Getter
    @Setter
    private boolean isFirstTimeLoggedIn = true;

    public User() {
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
