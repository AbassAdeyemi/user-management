package com.assessment.demo.usermanagement.controller;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.enumeration.Role;
import com.assessment.demo.usermanagement.enumeration.Status;
import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import com.assessment.demo.usermanagement.service.UserService;
import com.assessment.demo.usermanagement.transformer.UserModelTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@EnableSpringDataWebSupport
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserControllerTests.class})
public class UserControllerTests {

    @MockBean
    private UserService userService;

    @MockBean
    private UserModelTransformer transformer;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    UserRequestModel alanRequestModel = UserRequestModel.builder()
            .id(1L)
            .email("alan@gmail.com")
            .firstname("Alan")
            .lastname("Shearer")
            .mobile("+234889801676378")
            .title("Director")
            .role(Role.USER)
            .status(Status.REGISTERED)
            .dateRegistered(LocalDate.now())
            .build();

    User alan = transformer.fromRequestModelToUserModel(alanRequestModel);
    UserResponseModel alanResponseModel = transformer.fromUserModelToResponseModel(alan);

    UserRequestModel tomRequestModel = UserRequestModel.builder()
            .id(2L)
            .email("tom@gmail.com")
            .firstname("Tom")
            .lastname("Jones")
            .mobile("+234787378930445")
            .title("Lead")
            .role(Role.USER)
            .status(Status.REGISTERED)
            .dateRegistered(LocalDate.now())
            .build();
    User tom = transformer.fromRequestModelToUserModel(tomRequestModel);
    UserResponseModel tomResponseModel = transformer.fromUserModelToResponseModel(tom);

    @BeforeEach
    void mockUp() {
        var userList = List.of(alanResponseModel, tomResponseModel);
        var userPage = PageRequest.of(0, 2);
        var userPageImpl = new PageImpl(userList, userPage, 1);

        when(userService.register(alanRequestModel)).thenReturn(alanResponseModel);
        when(userService.getAll(any(Pageable.class))).thenReturn(userPageImpl);
        when(userService.update(1L, alanRequestModel)).thenReturn(alanResponseModel);
    }

    @Test
    void shouldReturnUsers() throws Exception {
        this.mockMvc
                .perform(get("/api/users"))
                .andExpect(status().isOk());

    }

}
