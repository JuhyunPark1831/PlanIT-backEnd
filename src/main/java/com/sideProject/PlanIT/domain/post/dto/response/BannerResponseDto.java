package com.sideProject.PlanIT.domain.post.dto.response;

import com.sideProject.PlanIT.domain.post.entity.Banner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerResponseDto {
    private Long id;
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String imagePath;

    @Builder
    public BannerResponseDto(Long id, String title, LocalDateTime startAt, LocalDateTime endAt, String imagePath) {
        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.imagePath = imagePath;
    }

    public static BannerResponseDto of(Banner banner) {
        return BannerResponseDto.builder()
                .id(banner.getId())
                .title(banner.getTitle())
                .startAt(banner.getStartAt())
                .endAt(banner.getEndAt())
                .imagePath(banner.getImagePath())
                .build();
    }
}
