package ru.qmbo.warranty.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Fill in the name")
    @Size(max = 255, message = "Name too long (more than 255 characters)")
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Fill in the username")
    @Size(max = 255, message = "Username too long (more than 255 characters)")
    private String username;

    @Column(nullable = false)
    @Size(max = 255, message = "Password too long (more than 255 characters)")
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(this.getRole());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isAdmin() {
        return this.getRole().equals(Role.ADMIN);
    }



    public enum Role implements GrantedAuthority {

        ADMIN("Administrator"),
        USER("User"),
        MODER("Moderator");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String getAuthority() {
            return name();
        }

    }

}
