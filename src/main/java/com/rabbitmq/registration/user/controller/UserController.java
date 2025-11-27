package com.rabbitmq.registration.user.controller;

import com.rabbitmq.registration.user.dto.UserDTO;
import com.rabbitmq.registration.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Users related operations")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "List all users", description = "Returns a users list of all registered ")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    @GetMapping
    public List<UserDTO> findAll(){

        return userService.findAll();
    }

    @Operation(
            summary = "Search user by ID",
            description = "Returns ID for a specific user ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){

        UserDTO userDTO = userService.findById(id);
        return ResponseEntity.ok(userDTO);
    }

    @Operation(
            summary = "Create a new user",
            description = "Create a new user with name",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User name",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserDTO.class),
                            examples = @ExampleObject(value = "{ \"name\": \"User created\" }")

                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {

        UserDTO userCreated = userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @Operation(
            summary = "Update an existing user",
            description = "Update user data by ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserDTO.class),
                            examples = @ExampleObject(value = "{ \"name\": \"User math\", \"id\": 30 }")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update (@PathVariable Long id , @RequestBody UserDTO userDTO){

        UserDTO userUpdated = userService.update(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @Operation(
            summary = "Delete user by ID",
            description = "Remove a user from the system",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
