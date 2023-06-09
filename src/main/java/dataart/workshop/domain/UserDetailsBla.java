package dataart.workshop.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserDetailsBla(Collection<? extends GrantedAuthority> authorities, String username) {
}
