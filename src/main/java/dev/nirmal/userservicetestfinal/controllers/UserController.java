package dev.nirmal.userservicetestfinal.controllers;

import dev.nirmal.userservicetestfinal.dtos.SetUserRolesRequestDto;
import dev.nirmal.userservicetestfinal.dtos.UserDto;
import dev.nirmal.userservicetestfinal.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId) {
/*
  NOTE: 123 UP :
    for call comming from the Product service this code hass been commented

    UserDto userDto = userService.getUserDetails(userId);
 */
    System.out.println("Call reached from product service call");
    UserDto userDto = new UserDto();
    return new ResponseEntity<>(userDto, HttpStatus.OK);
  }

  @PostMapping("/{id}/roles")
  public ResponseEntity<UserDto> setUserRoles(
      @PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto request) {

    UserDto userDto = userService.setUserRoles(userId, request.getRoleIds());

    return new ResponseEntity<>(userDto, HttpStatus.OK);
  }
}
