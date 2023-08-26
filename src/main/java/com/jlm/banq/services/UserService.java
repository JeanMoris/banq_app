package com.jlm.banq.services;

import com.jlm.banq.dto.AuthenticationRequest;
import com.jlm.banq.dto.AuthenticationResponse;
import com.jlm.banq.dto.LightUserDto;
import com.jlm.banq.dto.UserDto;

public interface UserService extends AbstractService<UserDto>{

    Integer validateAccount(Integer id);

    Integer invalidateAccount(Integer id);

    AuthenticationResponse register(UserDto user);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Integer update(LightUserDto userDto);
}
