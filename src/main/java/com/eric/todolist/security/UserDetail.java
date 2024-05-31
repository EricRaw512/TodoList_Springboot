package com.eric.todolist.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eric.todolist.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail implements UserDetails{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1502039293417485174L;
	
	private int id;
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetail createUserDetail(User user) {
        Collection<? extends GrantedAuthority> authorities  = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        return new UserDetail(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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

    public boolean hasRole(String role) {
        return authorities.stream()
            .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}