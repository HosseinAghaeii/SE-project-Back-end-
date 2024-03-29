package ir.segroup.unipoll.config.security;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.ws.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class AppAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppAuthenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        logger.log(Level.INFO,"username: {0}",userName);
        logger.log(Level.INFO,"password: {0}",password);
        return userRepository.findByUsername(userName)
                .map(userEntity -> {
                    if (passwordEncoder.matches(password, userEntity.getEncryptedPassword())) {
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(
                                new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().toUpperCase())
                        );
                        logger.log(Level.INFO,"authentication is successfully");
                        return new UsernamePasswordAuthenticationToken(userName, password, authorities);

                    } else {
                        throw new SystemServiceException(ExceptionMessages.CHECK_SECURITY.getMessage(),HttpStatus.FORBIDDEN);
                    }
                })
                .orElseThrow(() -> new SystemServiceException(ExceptionMessages.CHECK_SECURITY.getMessage(), HttpStatus.FORBIDDEN));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
