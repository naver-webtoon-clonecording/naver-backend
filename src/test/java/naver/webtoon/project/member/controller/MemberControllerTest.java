package naver.webtoon.project.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import naver.webtoon.project.member.dto.request.MemberLoginRequest;
import naver.webtoon.project.member.dto.request.MemberSignUpRequest;
import naver.webtoon.project.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static naver.webtoon.project.member.fixture.MemberFixture.PASSWORD;
import static naver.webtoon.project.member.fixture.MemberFixture.USERNAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Mock
    MemberService memberService;
    @InjectMocks
    private MemberController memberController;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @DisplayName("회원 가입 정상 케이스")
    @Test
    public void signUp() throws Exception {
        MemberSignUpRequest request = new MemberSignUpRequest(USERNAME, PASSWORD);

        mockMvc.perform(post("/api/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("회원가입성공"));
    }

    @DisplayName("회원 가입 정상 케이스")
    @Test
    public void login() throws Exception {
        MemberLoginRequest request = new MemberLoginRequest(USERNAME, PASSWORD);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("로그인성공"));
    }
}
