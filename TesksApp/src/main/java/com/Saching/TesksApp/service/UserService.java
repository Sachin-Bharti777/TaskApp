package com.Saching.TesksApp.service;

import com.Saching.TesksApp.dto.Response;
import com.Saching.TesksApp.dto.UserRequest;
import com.Saching.TesksApp.entity.User;
import org.apache.coyote.BadRequestException;

public interface UserService {
    Response<?> signUp(UserRequest userRequest) throws BadRequestException;
    Response<?> login(UserRequest userRequest);
    User getCurrentLoggedUser();


}
