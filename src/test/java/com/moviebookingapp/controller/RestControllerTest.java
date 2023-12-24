package com.moviebookingapp.controller;

import com.moviebookingapp.models.Role;
import com.moviebookingapp.serviceImpl.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import java.util.*;

public class RestControllerTest {
    @Mock
    Role role;
    @InjectMocks
    private RestController restController;

    @Mock
    private RoleService roleService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(restController).build();
    }


    @Test
    void addRoleTest() {

        when(roleService.createNewRole(role)).thenReturn(role);
        assertEquals(new ResponseEntity<>(role,HttpStatus.OK), restController.createNewRole(role));
    }

    @Test
    void testGetAllRole() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("admin", "Role1"));
        roles.add(new Role("user", "Role2"));

        when(roleService.getRoles()).thenReturn(roles);
        assertEquals(new ResponseEntity<List<Role>>(roles,HttpStatus.OK), restController.getAllRole(roles));

    }


}
