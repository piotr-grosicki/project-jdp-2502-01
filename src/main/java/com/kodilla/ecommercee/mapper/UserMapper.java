package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMapper {

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getAddress(),
                userDto.isBlocked(),
                userDto.getToken(),
                userDto.getExpiresAt(),
                new ArrayList<>(),
                null
        );
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getAddress(),
                user.isBlocked(),
                user.getToken(),
                user.getExpiresAt()
        );
    }

    public List<UserDto> mapToUserDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .toList();
    }
}
