package ru.blinov.payservis.security;


import ru.blinov.payservis.dao.TokenRepository;
import ru.blinov.payservis.dao.UserRepository;
import ru.blinov.payservis.model.Token;
import ru.blinov.payservis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        User user = userRepository.findByUserName(authentication.getName());
        String sessionId = user.getSessionId();
        Token token = tokenRepository.findBySessionId(sessionId);
        tokenRepository.deleteById(token.getId());
        SecurityContextHolder.clearContext();
        System.out.println("token is deleted");
    }
}


