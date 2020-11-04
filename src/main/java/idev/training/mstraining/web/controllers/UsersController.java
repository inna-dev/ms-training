package idev.training.mstraining.web.controllers;

import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import idev.training.mstraining.web.model.UserRequestModel;
import idev.training.mstraining.web.model.UserResponseModel;
import idev.training.mstraining.web.service.UsersService;
import idev.training.mstraining.web.shared.UserDto;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping("/status/check")
    public String status() {
        return "Working";
    }

    @PostMapping
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                   .setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto savedUser = usersService.createUser(userDto);
        UserResponseModel responseModel = modelMapper.map(savedUser, UserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(responseModel);
    }
}
