package dataart.workshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {

    READ("perm:read"), WRITE("perm:write");

    private final String permission;
}