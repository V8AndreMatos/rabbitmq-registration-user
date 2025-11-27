package com.rabbitmq.registration.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rabbitmq.registration.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Schema(description = "DTO representing a user register")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @Schema(description = "ID of the user", example = "1")
    private Long id;

    @NotBlank(message = " username is required")
    @Schema(description = "Name of the user", example = "Andre")
    private String name;

    @NotBlank(message = "email is required")
    @Schema(description = "Email of the user", example = "andre@email.com")
    @Email(message = "invalid email format")
    private String email;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }


    // Converts Entity to DTO
    public static UserDTO fromEntity(User entity) {
        return new UserDTO(entity.getId(), entity.getName(), entity.getEmail());
    }

    // Converts DTO to Entity
    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setEmail(this.email);
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
