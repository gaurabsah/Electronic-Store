package com.store.electronic.controllers;

import com.store.electronic.dtos.RoleDto;
import com.store.electronic.services.RoleService;
import com.store.electronic.services.RoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto) {
        RoleDto roleDto1 = roleService.saveRole(roleDto);
        logger.info("Role created: {}", roleDto1.getRoleId());
        return new ResponseEntity<>(roleDto1, HttpStatus.CREATED);
    }

    public ResponseEntity<List<RoleDto>> fetchAllRoles() {
        List<RoleDto> allRoles = roleService.getAllRoles();
        logger.info("Fetched all roles: {}", allRoles.size());
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }
}
