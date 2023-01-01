package com.momo2x.mbdn.members.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Avatar not found")
public class AvatarNotFoundException extends MemberServiceException {
}
