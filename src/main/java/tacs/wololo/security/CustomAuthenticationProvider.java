package tacs.wololo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.stereotype.Component;
import tacs.wololo.model.User;
import tacs.wololo.repositories.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository = new UserRepository();

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException
    {
        final String username = auth.getName();
        final String password = auth.getCredentials().toString();

        User user = userRepository.getUserbyUsername(username);
        
        if (user.getPassword().equals(password))
        {
            return new UsernamePasswordAuthenticationToken(username, password
                    , AuthorityUtils.createAuthorityList(user.getRole()));
        } else {
            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
