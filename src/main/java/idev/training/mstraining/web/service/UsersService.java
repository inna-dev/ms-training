package idev.training.mstraining.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import idev.training.mstraining.shared.UserDto;

public interface UsersService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);

    UserDto getUserByEmail(String email);
}
