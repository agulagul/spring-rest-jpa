package com.bca.opentemplate.springrestjpa.service.maintenance;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bca.opentemplate.springrestjpa.model.dao.maintenance.User;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.UserRequestDto;
import com.bca.opentemplate.springrestjpa.model.dto.maintenance.UserResponseDto;
import com.bca.opentemplate.springrestjpa.repository.maintenance.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;

  public List<UserResponseDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(e -> modelMapper.map(e, UserResponseDto.class)).collect(Collectors.toList());
  }
  
  public UserResponseDto getUserById(UUID id) {
    User user = userRepository.findById(id).get();
    return modelMapper.map(user, UserResponseDto.class);
  }

  public List<UserResponseDto> getAllUsersByUserName(UserRequestDto userRequestDto) {
    List<User> users = userRepository.findAllByUserName(userRequestDto.getUserName());
    return users.stream().map(e -> modelMapper.map(e, UserResponseDto.class)).collect(Collectors.toList());
  }

  public UserResponseDto createUser(UserRequestDto userRequestDto){
    User newUser = new User();
    newUser.setUserName(userRequestDto.getUserName());
    User createdUser = userRepository.save(newUser);
    return modelMapper.map(createdUser, UserResponseDto.class);
  }
  
  public UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto){
    User user = userRepository.findById(id).get();
    modelMapper.map(userRequestDto, user);
    userRepository.save(user);
    return modelMapper.map(user, UserResponseDto.class);
  }
  
  public UserResponseDto deleteUser(UUID id){
    User user = userRepository.findById(id).get();
    userRepository.delete(user);
    return modelMapper.map(user, UserResponseDto.class);
  }

}
