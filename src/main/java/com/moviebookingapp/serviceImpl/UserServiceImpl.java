package com.moviebookingapp.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import com.moviebookingapp.dto.ResetPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moviebookingapp.Repository.RoleDao;
import com.moviebookingapp.Repository.UserRepo;
import com.moviebookingapp.Service.MovieService;
import com.moviebookingapp.Service.UserService;
import com.moviebookingapp.dto.ForgotPassword;
import com.moviebookingapp.exception.PasswordMismatchException;
import com.moviebookingapp.exception.UserAlredyExistException;
import com.moviebookingapp.exception.UserNotExistException;
import com.moviebookingapp.models.Role;
import com.moviebookingapp.models.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private ResetPassword resetPassword;


    public void initRolesAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDesc("Admin Role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDesc("Default Role for user");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setFirstName("admin");
        adminUser.setLastName("admin");
        adminUser.setUserName("admin123");
        adminUser.setEmail("admin@gmail.com");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setConfirmPassword(getEncodedPassword("admin@pass"));
        adminUser.setContactNumber("9534212345");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepo.save(adminUser);

//		User user=new User();
//		user.setUserFirstName("mohit");
//		user.setUserLastName("sahu");
//		user.setUserName("mohit123");
//		user.setUserPassword(getEncodedPassword("mohit@123"));
//		Set<Role> userRoles=new HashSet<>();
//		userRoles.add(userRole);
//		user.setRole(userRoles);
//		userdao.save(user);
        //


    }

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        user.setConfirmPassword(getEncodedPassword(user.getConfirmPassword()));

        return userRepo.save(user);
    }

    @Override
    public Boolean resetPassword(String userName, ResetPassword resetPassword) throws UserNotExistException {

        boolean returnValue = false;


        logger.info("Finding user");
        User byUserName = userRepo.findByUserName(userName);


        if (byUserName != null) {

            byUserName.setUserPassword(getEncodedPassword(resetPassword.getUserPassword()));

            byUserName.setConfirmPassword(getEncodedPassword(resetPassword.getConfirmPassword()));

            userRepo.save(byUserName);
            returnValue = true;
        }


        return returnValue;
    }


    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    @Override
    public User registerUser(User user) throws ConstraintViolationException, UserAlredyExistException, PasswordMismatchException {
        logger.info("finding user");
        if (userRepo.findByUserName(user.getUserName()) == null) {
            if (user.getUserPassword().equals(user.getConfirmPassword())) {
                logger.info("registring user");
                User user1 = userRepo.save(user);
                return user1;
            } else {
                logger.error("rules are not fullfilled");
                throw new PasswordMismatchException("Password is not matching");
            }
        } else {
            logger.error("user is already registered");
            throw new UserAlredyExistException("LoginId Already exist");
        }

    }

    @Override
    public boolean authenticate(String userName, String userPassword) {
        logger.info("finding user");
        logger.info(userName);
        User user = userRepo.findByUserName(userName);
        User user1 = userRepo.findByUserName(userName);
        logger.info(" user" + user1);
        if (user == null) {
            logger.error("user not found");
            return false;
        }
        if (user.getUserPassword().equals(userPassword)) {
            logger.info("user logged in");
            return true;
        } else return false;
    }

    @Override
    public String updatePassword(String userPassword, ForgotPassword forgotPassword) throws PasswordMismatchException, UserNotExistException {
        logger.info("finding user");
        User user = userRepo.findByUserName(userPassword);
        User user1 = userRepo.findByUserName(userPassword);
        if (user == null) {
            logger.error("user not found");
            throw new UserNotExistException("This login id doesnot exist");
        } else {
            if (forgotPassword.getUserPassword().equals(forgotPassword.getConfirmPassword())) {
                logger.info("updating password");
                user.setUserPassword(getEncodedPassword(user.getUserPassword()));
                user.setConfirmPassword(getEncodedPassword(user.getConfirmPassword()));

                userRepo.save(user);
                logger.info("password updated successfully");
                return "password changed successfully";
            } else {
                logger.error("rules are not fullfilled");
                throw new PasswordMismatchException("Password is not matching");
            }
        }
    }

}
