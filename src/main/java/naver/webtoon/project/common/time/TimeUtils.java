package naver.webtoon.project.common.time;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtils {

    //24시간안에 업데이트가 되었는지 함수명 체크
    public static boolean isUpdatedWithin24Hours(LocalDateTime updatedAt) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(updatedAt, currentTime);
        long hoursDifference = duration.toHours();
        return hoursDifference < 24;
    }

    //30일안에 새로 등록이 되었는지 함수명 변경 필요.
    public static boolean isNewlyRegisteredWithin30Days(LocalDateTime updatedAt) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(updatedAt, currentTime);
        long hoursDifference = duration.toDays();
        return hoursDifference < 30;
    }
}
