package com.assessment.demo.usermanagement.controller;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.enumeration.Role;
import com.assessment.demo.usermanagement.enumeration.Status;
import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import com.assessment.demo.usermanagement.service.UserService;
import com.assessment.demo.usermanagement.transformer.UserModelTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith({SpringExtension.class})
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context).build();
    }

    UserRequestModel alanRequestModel = UserRequestModel.builder()
            .id(1L)
             .password("adsdsfsfs")
            .email("alan@gmail.com")
            .firstname("Alan")
            .lastname("Shearer")
            .mobile("+234889801676378")
            .title("Director")
            .role(Role.USER)
            .status(Status.REGISTERED)
            .dateRegistered(LocalDate.now())
            .build();

    @BeforeEach
    void mockUp() {

        User alan = UserModelTransformer.fromRequestModelToUserModel(alanRequestModel);
        UserResponseModel alanResponseModel = UserModelTransformer.fromUserModelToResponseModel(alan);

        UserRequestModel tomRequestModel = UserRequestModel.builder()
                .id(2L)
                .password("adadasdsd")
                .email("tom@gmail.com")
                .firstname("Tom")
                .lastname("Jones")
                .mobile("+234787378930445")
                .title("Lead")
                .role(Role.USER)
                .status(Status.REGISTERED)
                .dateRegistered(LocalDate.now())
                .build();
        User tom = UserModelTransformer.fromRequestModelToUserModel(tomRequestModel);
        UserResponseModel tomResponseModel = UserModelTransformer.fromUserModelToResponseModel(tom);

        var userList = List.of(alanResponseModel, tomResponseModel);
        var userPage = PageRequest.of(0, 2);
        var userPageImpl = new PageImpl(userList, userPage, 1);

        when(userService.register(alanRequestModel)).thenReturn(alanResponseModel);
        when(userService.getAll(any(Pageable.class))).thenReturn(userPageImpl);
        when(userService.update(1L, alanRequestModel)).thenReturn(alanResponseModel);
    }

    private String getRequestAsJsonString(Object obj) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateSerializer.INSTANCE);
        mapper.registerModule(module);
        return mapper.writeValueAsString(obj);
    }

    @Test
    void shouldReturnUsers() throws Exception {
        this.mockMvc
                .perform(get("/api/users"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldAddUser() throws Exception {
        this.mockMvc
                .perform(post("/api/user").contentType(APPLICATION_JSON)
                        .content(getRequestAsJsonString(alanRequestModel)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        String request = getRequestAsJsonString(alanRequestModel);
        this.mockMvc
                .perform(put("/api/user?id=1").contentType(APPLICATION_JSON).content(request))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        this.mockMvc.perform(delete("/api/user?id=1")).andExpect(status().isOk());
    }

}
