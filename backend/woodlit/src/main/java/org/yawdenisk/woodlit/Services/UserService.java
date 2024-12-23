package org.yawdenisk.woodlit.Services;

import lombok.Setter;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(org.yawdenisk.woodlit.Entites.UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
    }
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        User u = user.get();
        return u;
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
