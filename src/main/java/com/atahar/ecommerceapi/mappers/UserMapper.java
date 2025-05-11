package com.atahar.ecommerceapi.mappers;


import com.atahar.ecommerceapi.dtos.request.RegisterUserRequest;
import com.atahar.ecommerceapi.dtos.request.UpdateUserRequest;
import com.atahar.ecommerceapi.dtos.UserDto;
import com.atahar.ecommerceapi.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
