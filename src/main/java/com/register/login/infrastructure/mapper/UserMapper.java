package com.register.login.infrastructure.mapper;


import com.register.login.domain.entites.User;
import com.register.login.domain.registration.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {


    User toEntity(RegistrationRequest registrationRequest);

    RegistrationRequest toDto(User user);

}
