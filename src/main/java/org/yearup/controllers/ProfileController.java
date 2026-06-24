package org.yearup.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProductService;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    private final ProfileService profileService;

    private final UserService userService;

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }


    @GetMapping
    public Profile viewProfile(Principal principal) {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        return profileService.getProfileService(userId);
    }

    @PutMapping
    public Profile updateProfile(Principal principal, @RequestBody Profile profile) {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        return profileService.updateProfileService(userId, profile);
    }
}
