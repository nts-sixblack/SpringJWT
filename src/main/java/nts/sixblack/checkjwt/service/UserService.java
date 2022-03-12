package nts.sixblack.checkjwt.service;

import nts.sixblack.checkjwt.entity.User;
import nts.sixblack.checkjwt.repository.UserRepository;
import nts.sixblack.checkjwt.util.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetail(user);
    }

    public UserDetails loadUserById(long userId){
        User user = userRepository.findByUserId(userId);
        return new CustomUserDetail(user);
    }
}