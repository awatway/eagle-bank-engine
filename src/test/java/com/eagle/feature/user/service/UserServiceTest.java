package com.eagle.feature.user.service;

import com.eagle.feature.user.repository.UserRepository;
import com.eagle.feature.user.web.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.eagle.feature.common.TestIds.USER_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().build();
    }

    @Test
    void createUser() {
        when(userRepository.createUser(user)).thenReturn(user);
        var response = userService.createUser(user);
        verify(userRepository).createUser(user);
    }

    @Test
    void getUser() {
        var response = userService.getUser(USER_ID);
        verify(userRepository).getUser(USER_ID);
    }

    @Test
    void updateUser() {
        userService.updateUser(USER_ID, user);
        verify(userRepository).updateUser(USER_ID, user);
    }
}