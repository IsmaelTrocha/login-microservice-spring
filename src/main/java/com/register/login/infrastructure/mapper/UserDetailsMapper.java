package com.register.login.infrastructure.mapper;

import com.register.login.config.security.UserRegistrationDetails;
import com.register.login.domain.entites.User;
import com.register.login.shared.mapper.EntityToDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserDetailsMapper extends EntityToDto<User, UserRegistrationDetails> {

}
