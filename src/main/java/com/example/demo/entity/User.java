package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@JsonAutoDetect
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле не должно быть пустым!")
    @Size(min = 1, max = 10, message = "Поле должно содержать от 1 до 10 знаков!")
    @NotBlank(message = "Поле не может содержать только пробелы!")
    @Column
    private String username;
    @NotEmpty(message = "Поле не должно быть пустым!")
    @Size(min = 1, max = 15, message = "Поле должно содержать от 1 до 15 знаков")
    @NotBlank(message = "Поле не может содержать только пробелы!")
    @Column
    private String password;
    @Column
    private boolean active;
    @Column
    private BigDecimal money;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("id ASC")
    private List<Message> message = new ArrayList<>();

    public User(String username, String password, boolean active, BigDecimal money, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.money = money;
        this.roles = roles;
    }

    @JsonIgnore
    public BigDecimal getM() {
        return getMoney() != null ? getMoney() : new BigDecimal(0);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
