package karangoel.codes.gchat.controller;

import karangoel.codes.gchat.model.UserAccount;
import karangoel.codes.gchat.service.UserAccountService;
import karangoel.codes.gchat.util.Mappings;
import karangoel.codes.gchat.util.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static karangoel.codes.gchat.util.Mappings.USER;
import static karangoel.codes.gchat.util.Mappings.VERSION;

@RestController
@RequestMapping(VERSION + USER)
@Validated
public class UserController {

    // == fields ==
    private final UserAccountService userAccountService;

    // == constructors ==
    @Autowired
    public UserController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    // == request methods ==
    @PostMapping(Mappings.SIGN_UP)
    public ResponseEntity<ResponseDTO<Object>> signUpUser(@RequestBody @Valid UserAccount newUserAccount) {
        UserAccount saveUser = userAccountService.saveUser(newUserAccount);

        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .status(HttpStatus.CREATED.toString())
                .body(saveUser.getEmail() + " registered")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    //== authorized access ==
    @GetMapping
    public ResponseEntity<ResponseDTO<Object>> getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.toString();

        UserAccount  userAccount = userAccountService.getUser(email);

        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .status(HttpStatus.OK.toString())
                .body(userAccount)
                .build();

        return  ResponseEntity.ok(responseDTO);
    }

}
