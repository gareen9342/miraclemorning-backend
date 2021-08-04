package com.miraclemorning.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ApiErrorInfo {
    private String message;

    private final List<ApiErrorDetailInfo> details = new ArrayList<>();

    public void addDetailInfo(String target, String message){
        details.add(new ApiErrorDetailInfo(target,message));
    }

    public List<ApiErrorDetailInfo> getDetails(){
        return details;
    }
}
