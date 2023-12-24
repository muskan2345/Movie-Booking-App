package com.moviebookingapp.Service;


import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moviebookingapp.Repository.UserRepo;
import com.moviebookingapp.controller.MovieController;
import com.moviebookingapp.models.JwtRequest;
import com.moviebookingapp.models.JwtResponse;
import com.moviebookingapp.models.User;
import com.moviebookingapp.util.JwtUtil;





@Service
public class JwtService implements UserDetailsService {
     
	@Lazy
	 @Autowired
	    private JwtUtil jwtUtil;
       
	@Lazy
	    @Autowired
	    private UserRepo userDao;
	
	 Logger logger = LoggerFactory.getLogger(JwtService.class);
        
	@Lazy
	    @Autowired
	    private AuthenticationManager authenticationManager;

	    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
	        String userName = jwtRequest.getUserName();
	        String userPassword = jwtRequest.getUserPassword();
	        logger.info(userName);
	        authenticate(userName, userPassword);
	        logger.info("authentication passed");
	        

	        UserDetails userDetails = loadUserByUsername(userName);
	        logger.info("userDetails"+userDetails);
	        String newGeneratedToken = jwtUtil.generateToken(userDetails);
	        logger.info(newGeneratedToken);

	        User user = userDao.findById(userName).get();
	        logger.info("response user"+user);
	        return new JwtResponse(user, newGeneratedToken);
	    }

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userDao.findById(username).get();

	        if (user != null) {
	            return new org.springframework.security.core.userdetails.User(
	                    user.getUserName(),
	                    user.getUserPassword(),
	                    getAuthority(user)
	            );
	        } else {
	            throw new UsernameNotFoundException("User not found with username: " + username);
	        }
	    }

	    private Set<SimpleGrantedAuthority> getAuthority(User user) {
	        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	        user.getRole().forEach(role -> {
	            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
	        });
	        return authorities;
	    }

	    private void authenticate(String userName, String userPassword) throws Exception {
	        try {
	        	logger.info("inside authenticate token");
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
	        } catch (DisabledException e) {
	            throw new Exception("USER_DISABLED", e);
	        } catch (BadCredentialsException e) {
	            throw new Exception("INVALID_CREDENTIALS", e);
	        }
	    }

}
