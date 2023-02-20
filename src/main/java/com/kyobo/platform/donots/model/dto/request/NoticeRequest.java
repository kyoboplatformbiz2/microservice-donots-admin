package com.kyobo.platform.donots.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Getter
// @Setter가 없으면 MultipartFile를 제외한 모든 Form Data가 RequestBody가 DTO로 담아지지 않는다. (모두 null 값으로 넘어옴)
// @NoArgsConstructor, @AllArgsConstructor, @Builder 모두 있어도 @Setter가 없으면 해당 문제가 해결되지 않는다.
@Setter
public class NoticeRequest {

    @Schema(description = "제목")
    @NotBlank
    private String title;

    @Schema(description = "본문")
    @NotBlank
    private String body;

    @Schema(description = "게시 시작")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardStartDate;

    @Schema(description = "게시 종료")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardEndDate;

    // Lombok의 Getter, Setter의 is 자동탈락규칙으로 인한 문제가 발생하지 않고 정상적으로 필드를 받는다.
    // JSON으로 받을 때만 발생하는 이슈로 보인다.
    @Schema(description = "첨부이미지파일 변경여부")
    private Boolean isAttachedImageFileChanged;
}
