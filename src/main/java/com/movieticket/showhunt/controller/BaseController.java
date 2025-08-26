package com.movieticket.showhunt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieticket.showhunt.dto.LoginDTO;
import com.movieticket.showhunt.dto.LoginResponseDTO;
import com.movieticket.showhunt.entity.Admin;
import com.movieticket.showhunt.entity.TheatreOwner;
import com.movieticket.showhunt.entity.User;
import com.movieticket.showhunt.service.AdminService;
import com.movieticket.showhunt.service.TheatreOwnerService;
import com.movieticket.showhunt.service.UserService;

@RestController
@RequestMapping("auth")
public class BaseController 
{
  @Autowired
  private TheatreOwnerService ownerService;
  @Autowired
  private UserService userService;
  @Autowired
  private AdminService adminService;
  @GetMapping("home")
  public String Welcome()
  {
    return "Welcome to ShowHunt";
  }
  
  @PostMapping("checklogin")
  public ResponseEntity<?> checkLogin(@RequestBody  LoginDTO dto) {
    try {
      Admin a=adminService.checkAdminLogin(dto.getUsername(), dto.getPassword());
      TheatreOwner theatreOwner=ownerService.checkLogin(dto.getUsername(), dto.getPassword());
      User u=userService.checkLogin(dto.getUsername(), dto.getPassword());
      if(a!=null)
      {
        LoginResponseDTO responseDTO = new LoginResponseDTO("ADMIN", a);
        return ResponseEntity.ok(responseDTO);
      }
      else if(theatreOwner!=null)
      {
        if(theatreOwner.isStatus()) {
        LoginResponseDTO responseDTO = new LoginResponseDTO("THEATREOWNER", theatreOwner);
        return ResponseEntity.ok(responseDTO);
        }
        else
        {
          return ResponseEntity.status(401).body("Please contact admin. Your are blocked");
        }
      }
      else if(u!=null)
      {
        LoginResponseDTO responseDTO = new LoginResponseDTO("USER", u);
        return ResponseEntity.ok(responseDTO);
      }
      else
      {
        return ResponseEntity.status(401).body("Invalid Email and Password");
      }
      
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Login Failed");
    }
  }}