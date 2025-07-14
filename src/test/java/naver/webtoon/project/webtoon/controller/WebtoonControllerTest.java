package naver.webtoon.project.webtoon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import naver.webtoon.project.webtoon.dto.request.WebtoonRegisterRequest;
import naver.webtoon.project.webtoon.dto.request.WebtoonUpdateRequest;
import naver.webtoon.project.webtoon.dto.response.WebtoonInfo;
import naver.webtoon.project.webtoon.dto.response.WebtoonInfoListResponse;
import naver.webtoon.project.webtoon.service.WebtoonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static naver.webtoon.project.webtoon.fixture.WebtoonFixture.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WebtoonControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Mock
    WebtoonService webtoonService;
    @InjectMocks
    private WebtoonController webtoonController;
    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(webtoonController).build();
    }

    @DisplayName("웹툰 생성 정상 케이스")
    @Test
    public void registerWebtoon() throws Exception{
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        mockMvc.perform(post("/api/webtoon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("웹툰등록성공"));
    }

    @DisplayName("웹툰 제목 공백 생성 실패 케이스")
    @Test
    public void 웹툰_제목_공백_생성_실패() throws Exception{
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(null, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        mockMvc.perform(post("/api/webtoon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("웹툰 작가 공백 생성 실패 케이스")
    @Test
    public void 웹툰_작가_공백_생성_실패() throws Exception{
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, null, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        mockMvc.perform(post("/api/webtoon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("웹툰 썸네일 공백 생성 실패 케이스")
    @Test
    public void 웹툰_썸네일_공백_생성_실패() throws Exception{
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, null, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        mockMvc.perform(post("/api/webtoon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("웹툰 내용 공백 생성 실패 케이스")
    @Test
    public void 웹툰_내용_공백_생성_실패() throws Exception{
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, null, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        mockMvc.perform(post("/api/webtoon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("웹툰 연재 상태 공백 생성 실패 케이스")
    @Test
    public void 웹툰_연재_상태_공백_생성_실패() throws Exception{
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, null,
                List.of("MON"),
                List.of("로맨스")
        );

        mockMvc.perform(post("/api/webtoon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("웹툰 연재일 공백 생성 실패 케이스")
    @Test
    public void 웹툰_연재일_공백_생성_실패() throws Exception{
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                null,
                List.of("로맨스")
        );

        mockMvc.perform(post("/api/webtoon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("웹툰 수정 정상 케이스")
    @Test
    public void updateWebtoon() throws Exception{
        WebtoonUpdateRequest request = new WebtoonUpdateRequest(
                "수정된 제목", "수정된 작가명","수정된 설명", "수정된 썸네일", "BREAK",
                List.of("WED"),
                List.of("코믹")
        );

        mockMvc.perform(put("/api/webtoon/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("웹툰수정성공"));
    }

    @DisplayName("등록된 웹툰 삭제 성공 케이스")
    @Test
    public void 등록된_웹툰_삭제_성공() throws Exception{

        mockMvc.perform(delete("/api/webtoon/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("웹툰삭제성공"));
    }

    @DisplayName("요일별 인기순 웹툰 조회 케이스")
    @Test
    public void 요일별_인기순_웹툰_조회_성공() throws Exception{

        String publishingDay = "MON";

        List<WebtoonInfo> result = List.of();
        WebtoonInfoListResponse mockResponse = new WebtoonInfoListResponse(result);

        when(webtoonService.getPopularWebtoonsByDayOfWeekAndWithin30Days(publishingDay)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/webtoon/MON/popular")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("요일별인기순웹툰조회성공"));
    }

    @DisplayName("요일별 업데이트순 웹툰 조회 케이스")
    @Test
    public void 요일별_업데이트순_웹툰_조회_성공() throws Exception{

        String publishingDay = "MON";

        List<WebtoonInfo> result = List.of();
        WebtoonInfoListResponse mockResponse = new WebtoonInfoListResponse(result);

        when(webtoonService.getLastUpdateWebtoonsByDayOfWeek(publishingDay)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/webtoon/MON/latest-update")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("요일별업데이트순웹툰조회성공"));
    }

    @DisplayName("요일별 조회수 웹툰 조회 케이스")
    @Test
    public void 요일별_조회수_웹툰_조회_성공() throws Exception{

        String publishingDay = "MON";

        List<WebtoonInfo> result = List.of();
        WebtoonInfoListResponse mockResponse = new WebtoonInfoListResponse(result);

        when(webtoonService.getTotalViewsWebtoonsByDayOfWeek(publishingDay)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/webtoon/MON/total-views")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("요일별조회순웹툰조회"));
    }

    @DisplayName("완결 웹툰 인기순 조회 케이스")
    @Test
    public void 완결_웹툰_인기순_조회_성공() throws Exception{

        List<WebtoonInfo> result = List.of();
        WebtoonInfoListResponse mockResponse = new WebtoonInfoListResponse(result);

        when(webtoonService.getPopularWebtoonsByComplete()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/webtoon/finished/popular")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("완결웹툰인기순조회성공"));
    }

    @DisplayName("최근 완결순 조회 케이스")
    @Test
    public void 최근_완결순_조회_성공() throws Exception{

        List<WebtoonInfo> result = List.of();
        WebtoonInfoListResponse mockResponse = new WebtoonInfoListResponse(result);

        when(webtoonService.getLatestCompletedWebtoons()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/webtoon/finished/latest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("최근완결순조회성공"));
    }

    @DisplayName("해시태그별 모든 웹툰수 조회 케이스")
    @Test
    public void 해시태그별_모든_웹툰수_조회_성공() throws Exception{

        String hashtag = "액션";
        int mockCount = 12;

        when(webtoonService.getWebtoonCountByHashtag(hashtag)).thenReturn(mockCount);

        mockMvc.perform(get("/api/webtoon/액션/count")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("해시태그별모든웹툰수조회성공"));
    }
}
