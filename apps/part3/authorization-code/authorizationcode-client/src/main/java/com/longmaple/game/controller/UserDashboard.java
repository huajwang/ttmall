package com.longmaple.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.longmaple.game.data.ClientUser;
import com.longmaple.game.data.ClientUserDetails;
import com.longmaple.game.data.UserProfile;

@Controller
public class UserDashboard {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/callback")
    public ModelAndView callback() {
        return new ModelAndView("forward:/dashboard");
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(Authentication auth) {
        ClientUserDetails userDetails = (ClientUserDetails) auth.getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();

        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("user", clientUser);

        getUserProfile(mv);

        return mv;
    }
    
    private void getUserProfile(ModelAndView mv) {
        String endpoint = "http://localhost:9001/user/getProfile";
        try {
            UserProfile userProfile = restTemplate.getForObject(endpoint, UserProfile.class);
            mv.addObject("profile", userProfile);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("it was not possible to retrieve user profile");
        }
    }


    private void updateProfile(ModelAndView mv) {
        String endpoint = "http://localhost:9001/user/updateProfile";
        try {
        	UserProfile userProfile = new UserProfile();
        	userProfile.setEmail("test@longmaple.com");
        	userProfile.setName("TakeOut");
        	restTemplate.put(endpoint, null, userProfile);
            mv.addObject("profile", userProfile);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("it was not possible to retrieve user profile");
        }
    }
}
