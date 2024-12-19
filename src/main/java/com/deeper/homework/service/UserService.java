package com.deeper.homework.service;

import com.deeper.homework.entity.Role;
import com.deeper.homework.entity.User;
import com.deeper.homework.exception.UsernameAlreadyExistsException;
import com.deeper.homework.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public User registerUser(String username, String password) {
    if (userRepository.existsByUsername(username)) {
      throw new UsernameAlreadyExistsException("Username '" + username + "' is already taken.");
    }
    User user = User.builder()
        .username(username)
        .password(password)
        .authorities(Set.of(Role.ROLE_BASIC))
        .build();
    return userRepository.save(user);
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }
}
