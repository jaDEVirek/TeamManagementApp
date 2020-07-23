package src.main.java.com.devirek.dashboardapp.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import src.main.java.com.devirek.dashboardapp.security.repository.IUserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return UserDetailsImpl.build(userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with name: " + userName + " not found!")));
    }
}
