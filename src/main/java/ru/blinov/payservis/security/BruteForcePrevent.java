package ru.blinov.payservis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blinov.payservis.dao.UserRepository;
import ru.blinov.payservis.exception.CustomException;
import ru.blinov.payservis.model.User;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BruteForcePrevent {
    private final static long BLOCKEDUNTIL = 300000;
    private final static long MAXATTEMPT = 2;

    private Map<User,Integer> map = new ConcurrentHashMap<>();
    @Autowired
    private UserRepository userRepository;


public void failAttempt(User user) {
    if (map.get(user)==null||map.get(user)<MAXATTEMPT) {
        map.putIfAbsent(user,0);
        map.put(user,map.get(user)+1);
    }else {
        user.setBlockedUntil(new Date(System.currentTimeMillis() + BLOCKEDUNTIL));
        map.put(user,0);
        user.setBlocked(true);
        userRepository.save(user);
        throw new CustomException("user is blocked");
    }
}


    public void successAttempt(User user) {
        user.setBlocked(false);
        user.setBlockedUntil(new Date(0));
        map.put(user,0);
        userRepository.save(user);
    }

    public boolean check(User user){
        return user.getBlockedUntil().getTime()>new Date(System.currentTimeMillis()).getTime();
    }


}
