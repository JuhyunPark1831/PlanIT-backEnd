package com.sideProject.PlanIT.domain.program.entity;

import com.sideProject.PlanIT.domain.product.entity.Product;
import com.sideProject.PlanIT.domain.program.entity.ENUM.RegistrationStatus;
import com.sideProject.PlanIT.domain.user.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private LocalDateTime registrationAt;

    @Column
    private LocalDateTime paymentAt;

    @Column
    private LocalDateTime refundAt;

    @Enumerated(EnumType.STRING)
    RegistrationStatus status;

    @Column
    private int discount;

    @Column
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Registration(LocalDateTime registrationAt, LocalDateTime paymentAt, LocalDateTime refundAt, RegistrationStatus status, int discount, int totalPrice, Member member, Product product) {
        this.registrationAt = registrationAt;
        this.paymentAt = paymentAt;
        this.refundAt = refundAt;
        this.status = status;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.member = member;
        this.product = product;
    }

    //환불 상태로 변경
    public void changeToRefund(LocalDateTime now) {
        this.refundAt = now;
        this.status = RegistrationStatus.REFUND;
    }

    //승인 상태로 변경
    public void changeToAccepted() {
        this.status = RegistrationStatus.ACCEPTED;
    }

    //취소 상태로 변경
    public void changeToDeclined() {
        this.status = RegistrationStatus.DECLINED;
    }
}
