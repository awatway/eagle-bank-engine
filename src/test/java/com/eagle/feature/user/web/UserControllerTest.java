package com.eagle.feature.user.web;

import com.eagle.feature.auth.JwtProvider;
import com.eagle.feature.user.service.UserService;
import com.eagle.feature.user.web.model.CreateUserRequest;
import com.eagle.feature.user.web.model.UpdateUserRequest;
import com.eagle.feature.user.web.model.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.eagle.feature.common.TestIds.USER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockitoBean
    private JwtProvider jwtProvider;
    @MockitoBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CreateUserRequest createUserRequest;
    private UpdateUserRequest updateUserRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        createUserRequest = CreateUserRequest.builder()
                .name("Aditi")
                .email("aditi@gmail.com")
                .phone("07444444444")
                .password("Test123456")
                .build();
        updateUserRequest = UpdateUserRequest.builder()
                .name("A")
                .phone("07444444444")
                .build();
        userResponse = UserResponse.builder()
                .build();
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(userResponse);
        mockMvc.perform(post("/v1/users")
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(userService).createUser(any(CreateUserRequest.class));
    }

    @Test
    void getUser() throws Exception {
        String token = "123456";
        when(jwtProvider.getUserId(token)).thenReturn(USER_ID);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/" + USER_ID)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        String token = "123456";
        when(jwtProvider.getUserId(token)).thenReturn(USER_ID);
        mockMvc.perform(put("/v1/users/" + USER_ID)
                        .content(objectMapper.writeValueAsString(updateUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        verify(userService).updateUser(USER_ID, updateUserRequest);
    }
}