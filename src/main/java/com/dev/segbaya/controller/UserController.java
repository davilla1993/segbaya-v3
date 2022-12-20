package com.dev.segbaya.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dev.segbaya.domain.Role;
import com.dev.segbaya.domain.User;
import com.dev.segbaya.message.ResponseMessage;
import com.dev.segbaya.repo.RoleRepo;
import com.dev.segbaya.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleRepo roleRepo;

    @Operation(description = "")
    @PostMapping("/login")
    public ResponseEntity<String> login(@Parameter(description = "email password") @RequestBody String s) {
        return ResponseEntity.ok("");
    }


    @GetMapping("/user/find/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> saveUser(@RequestParam String firstName ,
                                      @RequestParam String lastName ,
                                      @RequestParam String email ,
                                      @RequestParam String password ,
                                      @RequestParam MultipartFile filePhoto) {
        String message = "";
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                    path("/api/user/register").toUriString());

            final Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$" , Pattern.CASE_INSENSITIVE);

            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

            final Pattern VALID_PASSWORD_ADDRESS_REGEX =
                    Pattern.compile("^[a-zA-Z0-9]{6,12}$" , Pattern.CASE_INSENSITIVE);

            Matcher matcher2 = VALID_PASSWORD_ADDRESS_REGEX.matcher(password);

            if (matcher.find()) {
                if (matcher2.find()) {
                    userService.saveUser(firstName , lastName , email , password , filePhoto);
                    return ResponseEntity.created(uri).body("Created successfully !");
                } else {
                    return ResponseEntity.created(uri).body("Password must contain between 6 and 12 characters.");
                }
            } else {
                return ResponseEntity.created(uri).body("Invalid email.");
            }
        } catch (Exception e) {
            message = "Could not upload the photo !\n" + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/user/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId ,
                                        @RequestBody User user) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$" , Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());

        if (matcher.find()) {
            userService.updateUser(userId , user);
            return ResponseEntity.ok().body("Updated successfully !");
        } else {
            return ResponseEntity.ok().body("Invalid email.");
        }

    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("Deleted successfully !");
    }


    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/add-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getEmail() , form.getRoleName());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("segbaya-secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                User user = userService.getUserByEmail(email);
                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles" , user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token" , access_token);
                tokens.put("refresh_token" , refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream() , tokens);
            } catch (Exception e) {
                response.setHeader("errror" , e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                //response.sendError(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message" , e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream() , error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}

@Data
class RoleToUserForm {
    private String email;
    private String roleName;
}