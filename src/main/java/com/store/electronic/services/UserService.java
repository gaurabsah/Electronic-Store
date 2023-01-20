package com.store.electronic.services;


import com.store.electronic.dtos.UserDto;

import java.util.List;

public interface UserService {

    //    create
    UserDto createUser(UserDto userDto);

    //    update user
    UserDto updateUser(UserDto userDto, String userId);

    //    get All
    List<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortOrder);

    //    delete
    void deleteUser(String userId);

    //    get single user by id
    UserDto getUserById(String userId);

    //    get single user by email
    UserDto getUserByEmail(String email);

    //    search user
    List<UserDto> searchUser(String keyword);

}
