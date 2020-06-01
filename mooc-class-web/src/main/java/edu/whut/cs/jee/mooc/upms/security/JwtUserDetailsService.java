package edu.whut.cs.jee.mooc.upms.security;

import com.alibaba.dubbo.config.annotation.Reference;
import edu.whut.cs.jee.mooc.upms.dto.UserDto;
import edu.whut.cs.jee.mooc.upms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {

    @Reference      //单体应用时使用 @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDto> userDtos = userService.getUserByUsername(username);
        UserDto userDto = userDtos.size() > 0 ? userDtos.get(0) : null;

        if (userDto == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(userDto);
        }
    }
}
