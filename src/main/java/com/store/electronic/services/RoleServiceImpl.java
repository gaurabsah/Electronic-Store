package com.store.electronic.services;

import com.store.electronic.dtos.RoleDto;
import com.store.electronic.entities.Role;
import com.store.electronic.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        Role role = Role.builder().roleId(UUID.randomUUID().toString()).roleName(roleDto.getRoleName()).build();
        Role savedRole = roleRepository.save(role);
        RoleDto roleDto1 = mapper.map(savedRole, RoleDto.class);
        logger.info("Role saved successfully {}", roleDto1.getRoleName());
        return roleDto1;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDto = roles.stream().map(role -> mapper.map(role, RoleDto.class)).collect(Collectors.toList());
        logger.info("Roles fetched successfully");
        return roleDto;
    }
}
