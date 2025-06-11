package com.eagle.feature.account.web;

import com.eagle.feature.account.service.BankAccountService;
import com.eagle.feature.account.web.model.BankAccountResponse;
import com.eagle.feature.account.web.model.CreateBankAccountRequest;
import com.eagle.feature.account.web.model.UpdateBankAccountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.eagle.feature.common.TestIds.ACCOUNT_ID;
import static com.eagle.feature.common.TestIds.USER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {
    @MockitoBean
    private BankAccountService bankAccountService;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CreateBankAccountRequest createBankAccountRequest;
    private UpdateBankAccountRequest updateBankAccountRequest;
    private BankAccountResponse bankAccountResponse;

    @BeforeEach
    void setUp() {
        createBankAccountRequest = CreateBankAccountRequest.builder().name("my account").build();
        updateBankAccountRequest = UpdateBankAccountRequest.builder().name("updated account").build();
        bankAccountResponse = BankAccountResponse.builder().build();
    }

    @Test
    void createAccount() throws Exception {
        when(bankAccountService.createAccount(eq(USER_ID), any(CreateBankAccountRequest.class))).thenReturn(bankAccountResponse);
        mockMvc.perform(post("/v1/accounts/user/" + USER_ID)
                        .content(objectMapper.writeValueAsString(createBankAccountRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bankAccountService).createAccount(eq(USER_ID), any(CreateBankAccountRequest.class));
    }

    @Test
    void getAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/" + ACCOUNT_ID))
                .andExpect(status().isOk());
    }

    @Test
    void updateAccount() throws Exception {
        mockMvc.perform(patch("/v1/accounts/" + ACCOUNT_ID)
                        .content(objectMapper.writeValueAsString(updateBankAccountRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bankAccountService).updateAccount(eq(ACCOUNT_ID), any(UpdateBankAccountRequest.class));
    }

    @Test
    void deleteAccount() throws Exception {
        mockMvc.perform(delete("/v1/accounts/" + ACCOUNT_ID))
                .andExpect(status().isOk());
        verify(bankAccountService).deleteAccount(ACCOUNT_ID);
    }
}