package com.eagle.feature.user.web;

import com.eagle.feature.user.service.UserService;
import com.eagle.feature.user.web.model.User;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockitoBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().build();
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/v1/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).createUser(any(User.class));
    }

    @Test
    void getUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/" + USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        mockMvc.perform(put("/v1/users/" + USER_ID)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).updateUser(eq(USER_ID), any(User.class));
    }
}