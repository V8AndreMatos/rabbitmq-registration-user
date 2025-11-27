package com.rabbitmq.registration.user.service;

import com.rabbitmq.registration.user.dto.UserDTO;
import com.rabbitmq.registration.user.entity.User;
import com.rabbitmq.registration.user.exceptions.ResourceNotFoundException;
import com.rabbitmq.registration.user.messaging.UserProducer;
import com.rabbitmq.registration.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    // Find All Users
    public List<UserDTO> findAll(){
        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    // Find User By ID
    public UserDTO findById(Long id) {
        User material = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID " + id + " Not Found"));
        return new UserDTO(material);
    }

    // Create a new User
    @Transactional
    public UserDTO create(UserDTO userDTO){
        User entity = userDTO.toEntity();
        entity = userRepository.save(entity);

        // Send a message to line after to save
        userProducer.sendUser(new UserDTO(entity));

        return new UserDTO(entity);
    }

    // Update User
    public UserDTO update(Long id , UserDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " Not Found"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    // Delete User By ID
    public void deleteById(Long id){

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " Not found");
        }
        userRepository.deleteById(id);
    }
}
