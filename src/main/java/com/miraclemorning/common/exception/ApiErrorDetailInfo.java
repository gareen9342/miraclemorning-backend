package com.miraclemorning.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiErrorDetailInfo {
    private String target;
    private String message;
}
