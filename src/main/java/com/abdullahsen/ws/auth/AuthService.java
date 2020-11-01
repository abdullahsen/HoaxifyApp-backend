package com.abdullahsen.ws.auth;

import com.abdullahsen.ws.user.User;
import com.abdullahsen.ws.user.UserRepository;
import com.abdullahsen.ws.user.vm.UserVM;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {


    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticate(Credentials credentials) {
        User inDB = userRepository.findByUsername(credentials.getUsername());
        if (inDB == null) {
            throw new AuthException();
        }

        boolean matches = passwordEncoder.matches(credentials.getPassword(), inDB.getPassword());
        if (!matches) {
            throw new AuthException();
        }

        UserVM userVM = new UserVM(inDB);
        String token = Jwts.builder().setSubject("" + inDB.getId()).signWith(SignatureAlgorithm.HS512, "my-app-secret").compact();
        AuthResponse response = new AuthResponse();
        response.setUser(userVM);
        response.setToken(token);
        return response;
    }

    @Transactional
    public UserDetails getUserDetails(String token) {
        JwtParser parser = Jwts.parser().setSigningKey("my-app-secret");
        try{
            parser.parse(token);
            Claims claims = parser.parseClaimsJws(token).getBody();
            long userId = Long.parseLong(claims.getSubject());
            User user = userRepository.getOne(userId);
            User actualUser = (User)((HibernateProxy)user).getHibernateLazyInitializer().getImplementation();
            return actualUser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}