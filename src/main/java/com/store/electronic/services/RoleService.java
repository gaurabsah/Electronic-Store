package com.store.electronic.services;

import com.store.electronic.dtos.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto saveRole(RoleDto role);

    List<RoleDto> getAllRoles();
}
