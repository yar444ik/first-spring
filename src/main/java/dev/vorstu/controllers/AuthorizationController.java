package dev.vorstu.controllers;

import dev.vorstu.dto.UserDTO;
import dev.vorstu.entities.UserEntity;
import dev.vorstu.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthorizationController {

    @Autowired
    UserService userService;

    @PostMapping(value = "login")
    public void apiLogin(Principal user) {
        log.info("Login user");
        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
        log.info("Hello {} with role {}", token.getName(), token.getAuthorities());
    }

    @PostMapping(value = "registration")
    public UserDTO addUser(@RequestBody UserDTO userShow) {
        if (!userService.checkAvailableUsername(userShow.getUsername())) {
            return userService.saveUser(userShow);
        } else {
            throw new IllegalArgumentException("Username is already taken");
        }
    }


    @PostMapping(path = "logout", produces = "application/json")
    @ResponseBody
    public void logout(Principal user, HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(
                AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY
        );
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
    }

}