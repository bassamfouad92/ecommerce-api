package com.bsm.ecommerce_api.ecommerce_api.mappers;
import com.bsm.ecommerce_api.ecommerce_api.dtos.RegisterUserRequest;
import com.bsm.ecommerce_api.ecommerce_api.dtos.UserDto;
import com.bsm.ecommerce_api.ecommerce_api.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest registerUserRequest);
}
