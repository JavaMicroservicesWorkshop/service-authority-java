package dataart.workshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedUserDto {

    private List<UserDto> data;
    private int page;
    private int totalPages;
    private int size;
}
