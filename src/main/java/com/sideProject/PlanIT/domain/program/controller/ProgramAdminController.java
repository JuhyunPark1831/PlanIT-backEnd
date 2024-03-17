package com.sideProject.PlanIT.domain.program.controller;

import com.sideProject.PlanIT.common.response.ApiResponse;
import com.sideProject.PlanIT.domain.program.dto.request.ApproveRequest;
import com.sideProject.PlanIT.domain.program.dto.request.ProgramModifyRequest;
import com.sideProject.PlanIT.domain.program.dto.response.ProgramResponse;
import com.sideProject.PlanIT.domain.program.dto.response.FindRegistrationResponse;
import com.sideProject.PlanIT.domain.program.entity.ENUM.ProgramSearchStatus;
import com.sideProject.PlanIT.domain.program.entity.ENUM.RegistrationSearchStatus;
import com.sideProject.PlanIT.domain.program.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/program")
public class ProgramAdminController {

    private final ProgramService programService;

    //어드민이 전부 검색
    @GetMapping("")
    public ApiResponse<List<ProgramResponse>> find(@RequestParam(value = "option", required = false, defaultValue = "VALID") ProgramSearchStatus option, Principal principal) {
        //todo : spring security 개발 후 토큰에서 userID를 전달해 줘야함.
        Long id = Long.parseLong(principal.getName());

        return ApiResponse.ok(
                programService.find(id,option)
        );
    }

    //어드민이 유저 id로 검색
    @GetMapping("/by-user/{id}")
    public ApiResponse<List<ProgramResponse>> find(
            @PathVariable("id") Long id,
            @RequestParam(value = "option", required = false, defaultValue = "VALID") ProgramSearchStatus option) {
        //todo : spring security 개발 후 토큰에서 userID를 전달해 줘야함.
        return ApiResponse.ok(
                programService.findByUser(id, option)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> refund(@PathVariable("id") Long id) {
        LocalDateTime now = LocalDateTime.now();
        return ApiResponse.ok(
                programService.refund(id,now)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<String> modify(
            @PathVariable("id") Long id,
            ProgramModifyRequest request) {
        return ApiResponse.ok(
                programService.modify(id, request)
        );
    }

    @PostMapping("/approve/{id}")
    public ApiResponse<Long> approve(@PathVariable("id") Long id, @RequestBody ApproveRequest request) {
        LocalDateTime now = LocalDateTime.now();
        return ApiResponse.ok(
                programService.approve(id, request.getTrainer(), now)
        );
    }

    @GetMapping("/registration")
    public ApiResponse<List<FindRegistrationResponse>> findRegistration(@RequestParam(value = "option", required = false, defaultValue = "READY") RegistrationSearchStatus option, Principal principal) {
        Long id = Long.parseLong(principal.getName());
        return ApiResponse.ok(
                programService.findRegistrations(id, option)
        );
    }


    @GetMapping("/registration/{id}")
    public ApiResponse<List<FindRegistrationResponse>> findRegistrationByUser(
            @PathVariable("id") Long id,
            @RequestParam(value = "option", required = false, defaultValue = "READY") RegistrationSearchStatus option) {
        //todo : spring security 개발 후 토큰에서 userID를 전달해 줘야함.
        return ApiResponse.ok(
                programService.findRegistrationsByUser(id,option)
        );
    }

    @PutMapping("/{id}/suspend")
    public ApiResponse<String> suspendProgram(@PathVariable(name = "id") Long id) {
        LocalDate today = LocalDate.now();
        Long result = programService.suspendProgram(id, today);
        return ApiResponse.ok(result + "번 회원권이 일시정지 처리되었습니다.");
    }

    @PutMapping("/{id}/resume")
    public ApiResponse<String> resumeProgram(@PathVariable(name = "id") Long id) {
        LocalDate today = LocalDate.now();
        Long result = programService.resumeProgram(id, today);
        return ApiResponse.ok(result + "번 회원권이 다시 활성화 되었습니다.");
    }
}