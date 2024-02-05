package dev.nirmal.userservicetestfinal.controllers;

import dev.nirmal.userservicetestfinal.dtos.CreateRoleRequestDto;
import dev.nirmal.userservicetestfinal.models.Role;
import dev.nirmal.userservicetestfinal.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
  private RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @PostMapping
  public ResponseEntity<Role> createRole(CreateRoleRequestDto request) {
    Role role = roleService.createRole(request.getName());
    return new ResponseEntity<>(role, HttpStatus.OK);
  }
}
