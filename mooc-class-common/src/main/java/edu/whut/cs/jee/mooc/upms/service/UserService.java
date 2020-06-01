package edu.whut.cs.jee.mooc.upms.service;


import edu.whut.cs.jee.mooc.upms.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Long saveUser(UserDto userDto);

    UserDto getUser(Long id);

    List<UserDto> getAllUsers();

    void removeUser(Long userId);

    Page<UserDto> getUsersByPage(UserDto userDto, Pageable pageable);

    List<UserDto> getUserByUsername(String username);

    Long register(UserDto userToAdd);
}
