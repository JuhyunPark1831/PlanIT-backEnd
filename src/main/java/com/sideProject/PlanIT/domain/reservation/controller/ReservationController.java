package com.sideProject.PlanIT.domain.reservation.controller;

import com.sideProject.PlanIT.common.response.ApiResponse;
import com.sideProject.PlanIT.domain.reservation.dto.reqeust.ChangeReservationRequest;
import com.sideProject.PlanIT.domain.reservation.dto.reqeust.ReservationRequest;
import com.sideProject.PlanIT.domain.reservation.dto.response.ReservationResponse;
import com.sideProject.PlanIT.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PutMapping("/change/{employeeId}")
    public ApiResponse<String> changeAvailability(
            Principal principal,
            @RequestBody ChangeReservationRequest request,
            @PathVariable("employeeId") Long employeeId
    ) {
        return ApiResponse.ok(
                reservationService.changeAvailability(
                        request.getReservedTimes(),
                        employeeId,
                        Long.valueOf(principal.getName())
                )
        );
    }

    @PostMapping("/{reservationId}")
    public ApiResponse<String> reservation(
            Principal principal,
            @PathVariable("reservationId") Long reservationId,
            @RequestBody ReservationRequest request
    ) {
        LocalDateTime now = LocalDateTime.now();
        return ApiResponse.ok(
                reservationService.reservation(
                        reservationId,
                        Long.valueOf(principal.getName()),
                        request.getProgramId()
                )
        );
    }

    @GetMapping("")
    public ApiResponse<Map<LocalDate, List<ReservationResponse>>> findReservation(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Principal principal
    ) {
        if (date == null) {
            date = LocalDate.now(); // 파라미터가 없을 경우 기본값으로 오늘 날짜를 사용
        }
        System.out.println(principal.getName());
        return ApiResponse.ok(
                reservationService.findReservationForWeekByMember(
                        date,
                        Long.valueOf(principal.getName())
                )
        );
    }

    @DeleteMapping("/{reservationId}")
    public ApiResponse<String> cancelReservation(
            @PathVariable("reservationId") Long reservationId,
            Principal principal
    ) {
        return ApiResponse.ok(
                reservationService.cancel(
                        Long.valueOf(principal.getName()),
                        reservationId
                )
        );
    }
}
