package karangoel.codes.gchat.controller;

import karangoel.codes.gchat.model.UserGroup;
import karangoel.codes.gchat.service.UserGroupService;
import karangoel.codes.gchat.util.ResponseDTO;
import karangoel.codes.gchat.validator.group.GroupIDExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static karangoel.codes.gchat.util.Mappings.*;

@RestController
@RequestMapping(VERSION + GROUP)
@Validated
public class GroupController {

    // == fields ==
    private UserGroupService userGroupService;

    // == constructors ==
    @Autowired
    public GroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    // == request methods ==
    @PostMapping
    public ResponseEntity<Object> createGroup(@RequestBody @Valid UserGroup newGroup) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.toString();

        UserGroup saveGroup = userGroupService.createGroup(newGroup,email);

        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .status(HttpStatus.CREATED.toString())
                .body(saveGroup.getName() + " created")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getGroups () {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.toString();

        Set<UserGroup> groups = userGroupService.getGroups(email);

        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .status(HttpStatus.OK.toString())
                .body(groups)
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(GROUP_JOIN + "{id}")
    public ResponseEntity<Object> joinGroup(@PathVariable @GroupIDExist long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.toString();

        UserGroup userGroup = userGroupService.addMember(email,id);

        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .status(HttpStatus.OK.toString())
                .body(String.format("%s added to group %s",email,userGroup.getName()))
                .build();

        return ResponseEntity.ok(responseDTO);

    }

}
