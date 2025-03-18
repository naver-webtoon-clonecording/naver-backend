package naver.webtoon.project.common.exception;

import jakarta.validation.GroupSequence;
import lombok.Builder;

@GroupSequence({Builder.Default.class, ValidationGroups.MinimumGroup.class})
public interface ValidationSequence {
}
