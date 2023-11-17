package com.holisticbabe.holisticbabemarketplace.Models;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class _User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_user;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @Column(unique = true)
    @Size(min = 8, max = 100)
    private String username;

    @NonNull
    @Column(unique = true)
    // @Email(message = "Please enter a valid email!")
    private String email;

    @Column(unique = true)
    private BigInteger phoneNumber;

    @Size(min = 8, max = 200)
    private String password;

    @Column(length = 10000)
    private String description;

    private LocalDate birthDate;

    private String profession;

    private LocalDate memberSince;

    private boolean enabled;

    @OneToOne(mappedBy = "user")
    private Token token;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_role")
    private _Role role;

    @OneToOne
    @JoinColumn(name = "id_multi")
    private Multimedia image;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.getRoleName()));
    }

    // ROLE_ADMIN

    // to verify we use only ADMIN

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
        return enabled;
    }
}
