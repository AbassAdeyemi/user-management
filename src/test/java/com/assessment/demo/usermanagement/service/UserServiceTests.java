package com.assessment.demo.usermanagement.service;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.enumeration.Role;
import com.assessment.demo.usermanagement.enumeration.Status;
import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import com.assessment.demo.usermanagement.repository.UserRepository;
import com.assessment.demo.usermanagement.service.impl.EmailServiceImpl;
import com.assessment.demo.usermanagement.service.impl.UserServiceImpl;
import com.assessment.demo.usermanagement.transformer.UserModelTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailServiceImpl emailService;

    UserRequestModel userRequestModel = UserRequestModel.builder()
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

    User user = UserModelTransformer.fromRequestModelToUserModel(userRequestModel);
    UserResponseModel userResponseModel = UserModelTransformer.fromUserModelToResponseModel(user);

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

    @Test
    void shouldAddUser() throws MessagingException, UnsupportedEncodingException {

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseModel savedUser = userService.register(userRequestModel);

        assertThat(savedUser).isNotNull();

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldUpdateUser() {

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        UserResponseModel expected = userService.update(1L, userRequestModel);
        assertThat(expected).isNotNull();

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldReturnAll() {

        List<User> users = List.of(user);
        var pageable = PageRequest.of(0, 1);
        var pageImpl = new PageImpl<>(users, pageable, 1);
        Page<UserResponseModel> responseModelPage = pageImpl.map(UserModelTransformer::fromUserModelToResponseModel);
        when(userRepository.findAll(pageable)).thenReturn(pageImpl);

        Page<UserResponseModel> expected = userService.getAll(pageable);

        assertEquals(expected.getTotalElements(), responseModelPage.getTotalElements());
    }

}
