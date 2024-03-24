package com.catale.backend.global.exception.member;

import com.catale.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class FollowingNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public FollowingNotFoundException() {
        this.errorCode = ErrorCode.FOLLOWING_NOT_FOUND;
    }
}