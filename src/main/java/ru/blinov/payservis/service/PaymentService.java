package ru.blinov.payservis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.blinov.payservis.dao.TokenRepository;
import ru.blinov.payservis.dao.UserRepository;
import ru.blinov.payservis.dto.LoginRequestDto;
import ru.blinov.payservis.exception.CustomException;
import ru.blinov.payservis.model.Token;
import ru.blinov.payservis.model.Transaction;
import ru.blinov.payservis.model.User;
import ru.blinov.payservis.security.BruteForcePrevent;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private BruteForcePrevent bruteForcePrevent;

    @Autowired
    public PaymentService(UserRepository userRepository,
                          TokenRepository tokenRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          BruteForcePrevent bruteForcePrevent) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.bruteForcePrevent = bruteForcePrevent;
    }

    public String login(LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User user = null;
        user = userRepository.findByUserName(loginRequestDto.getName());
//        System.out.println(user.getPassword());
        if (user != null && bruteForcePrevent.check(user) &&
                bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException("user is blocked");
        } else if (user != null && !bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            bruteForcePrevent.failAttempt(user);
            throw new CustomException("invalid username or password");
        }
        if (user == null) {
            user = new User();
            user.setUserName(loginRequestDto.getName());
            user.setPassword(bCryptPasswordEncoder.encode(loginRequestDto.getPassword()));
            user.setTotalBalance(new BigDecimal("8"));
        }
        bruteForcePrevent.successAttempt(user);
        user.setSessionId(request.getSession().getId());
        userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getName());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getName(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Token token = new Token();
        token.setUser(user);
        token.setGenerateToken(UUID.randomUUID().toString());
        token.setSessionId(request.getSession().getId());
        tokenRepository.save(token);

        return "user is authenticated";

    }

    public String payment() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(username);
        BigDecimal totalBalance = user.getTotalBalance();
        if (totalBalance.compareTo(new BigDecimal("1.1")) < 0) {
            throw new CustomException("no money");
        } else {
            List<Transaction> set = user.getSetOfTransactions();
            totalBalance = totalBalance.subtract(new BigDecimal("1.1"));
            Transaction transaction = new Transaction(totalBalance, new BigDecimal("1.1"));
            set.add(transaction);
            user.setTotalBalance(totalBalance);
            userRepository.save(user);
            return "payment is passed";
        }
    }


}