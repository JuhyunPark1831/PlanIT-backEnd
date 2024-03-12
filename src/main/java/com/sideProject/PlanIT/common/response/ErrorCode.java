package com.sideProject.PlanIT.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),
    ALREADY_REFUNDED_PROGRAM(400, "이미 환불된 프로그램입니다."),
    ALREADY_APPROVE_PROGRAM(400, "이미 등록된 프로그램입니다."),
    ALREADY_EXIST_EMAIL(400, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(400, "비밀번호가 틀렸습니다."),


    //404 Error Not found.
    RESOURCE_NOT_FOUND(404, "리소스를 찾을 수 없습니다."),
    PROGRAM_NOT_FOUND(404,"프로그램을 찾을 수 없습니다"),
    REGISTRATION_NOT_FOUND(404,"등록을 찾을 수 없습니다"),
    MEMBER_NOT_FOUND(404,"회원을 찾을 수 없습니다"),
    EMPLOYEE_NOT_FOUND(404,"직원을 찾을 수 없습니다");


    private final int status;
    private final String message;
}