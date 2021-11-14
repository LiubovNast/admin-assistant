package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import com.alevel.nix7.adminassistant.model.user.User;
import com.alevel.nix7.adminassistant.service.AdminService;
import com.alevel.nix7.adminassistant.service.SpecialistService;
import com.alevel.nix7.adminassistant.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RootPath.ROOT)
public class AuthenticationController {

    private final UserService userService;
    private final SpecialistService specialistService;
    private final AdminService adminService;

    public AuthenticationController(UserService userService, SpecialistService specialistService, AdminService adminService) {
        this.userService = userService;
        this.specialistService = specialistService;
        this.adminService = adminService;
    }

    @GetMapping
    public User getUserByPhone(@RequestBody String phone) {
        return userService.getByPhone(phone);
    }

    @GetMapping
    public Specialist getSpecialistToken(@RequestBody String login, String password) {
        return null;
    }

    @GetMapping
    public AdminResponse getAdminToken(@RequestBody String login, String password) {
        return null;
    }
}
