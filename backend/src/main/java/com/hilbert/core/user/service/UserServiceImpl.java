package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.dto.UserMapper;
import com.hilbert.core.user.model.User;
import com.hilbert.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDataDto getByUsername(String username) {
        Optional<User> userOpt = this.userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            // TODO: Throw exception
        }
        User foundUser = userOpt.get();

        return this.mapUserToUserDataDto(foundUser);
    }

    
    private UserDataDto mapUserToUserDataDto(User user) {
        return UserMapper.INSTANCE.userToUserDataDto(user);
    }
}
