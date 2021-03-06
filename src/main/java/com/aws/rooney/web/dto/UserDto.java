package com.aws.rooney.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {

    private final String name;
    private final Integer age;
}
