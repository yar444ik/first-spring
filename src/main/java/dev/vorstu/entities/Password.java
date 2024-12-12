package dev.vorstu.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "passwords")
@Getter @Setter
@NoArgsConstructor
public class Password {
    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Password(String password) { this.password = passwordEncoder.encode(password); }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String password;

    @JsonIgnore
    private void setPasswordWithEncoding(String password) {
        this.password = passwordEncoder.encode(password);
    }
}
