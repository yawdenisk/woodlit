package org.yawdenisk.woodlit.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yawdenisk.woodlit.Entites.User;
import org.yawdenisk.woodlit.Repositories.UserRepository;

import java.util.Optional;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(org.yawdenisk.woodlit.Entites.UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(email + "not found"));
    }
    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
