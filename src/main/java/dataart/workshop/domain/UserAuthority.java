package dataart.workshop.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserAuthority(String username, Collection<? extends GrantedAuthority> authorities) {
}
