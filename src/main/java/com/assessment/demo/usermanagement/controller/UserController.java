package com.assessment.demo.usermanagement.controller;

import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import com.assessment.demo.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application.json", schema = @Schema(implementation = UserRequestModel.class))
            })
    })
    @PostMapping("/user")
    public ResponseEntity<UserResponseModel> register(@RequestBody UserRequestModel requestModel) {
        return new ResponseEntity<>(userService.register(requestModel), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application.json", schema = @Schema(implementation = UserResponseModel.class))
            })
    })
    @GetMapping("/users")
    public Page<UserResponseModel> get(Pageable pageable) {
        return userService.getAll(pageable);
    }


    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application.json", schema = @Schema(implementation = UserResponseModel.class))
            })
    })
    @PutMapping("/user")
    public ResponseEntity<UserResponseModel> update(@RequestParam(name = "id") Long id, @RequestBody UserRequestModel requestModel) {
        return ResponseEntity.ok(userService.update(id, requestModel));
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application.json", schema = @Schema(implementation = UserRequestModel.class))
            })
    })
    @DeleteMapping("/user")
    public void delete(@RequestParam Long id) {
        userService.delete(id);
    }
}
