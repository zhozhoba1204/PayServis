package ru.blinov.payservis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.blinov.payservis.dao.UserRepository;
import ru.blinov.payservis.model.User;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("unable to find user " + username);
        }
        UserDetails authorisedUser = org.springframework.security.core.userdetails.User.withUsername(
                user.getUserName()).password(user.getPassword()).authorities("USER").build();
        return authorisedUser;
    }
}


