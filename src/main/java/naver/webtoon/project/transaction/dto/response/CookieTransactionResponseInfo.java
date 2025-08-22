package naver.webtoon.project.transaction.dto.response;

import lombok.Builder;
import lombok.Getter;
import naver.webtoon.project.common.time.TimeConverter;
import naver.webtoon.project.transaction.entity.CookieTransaction;
import naver.webtoon.project.transaction.entity.Type;

import java.util.Optional;

@Getter
public class CookieTransactionResponseInfo {

    private Long cookieTransactionId;
    private String type;
    private Integer amount;
    private String createdAt;

    @Builder
    public CookieTransactionResponseInfo(Long cookieTransactionId, String type, Integer amount, String createdAt){
        this.cookieTransactionId = cookieTransactionId;
        this.type = type;
        this.amount = amount;
        this.createdAt = cr가eatedAt;
    }

    public static CookieTransactionResponseInfo toResponse(CookieTransaction CookieTransaction){
        String koreanType = Type.toKoreanName(CookieTransaction.getType());
        String convertedCreatedAt = Optional.ofNullable(CookieTransaction.getCreatedAt())
                .map(TimeConverter::toStringFormat)
                .orElse(null);

        return CookieTransactionResponseInfo.builder()
                .cookieTransactionId(CookieTransaction.getId())
                .type(koreanType)
                .amount(CookieTransaction.getAmount())
                .createdAt(convertedCreatedAt)
                .build();
    }
}
