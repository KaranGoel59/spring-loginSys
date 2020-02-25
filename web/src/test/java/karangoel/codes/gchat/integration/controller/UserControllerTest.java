package karangoel.codes.gchat.integration.controller;

import karangoel.codes.gchat.Main;
import karangoel.codes.gchat.util.UserJSON;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Main.class,
       webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    // == tests ==
    private Cookie authCookie = null;

    @Before
    public void signInUser() throws Exception {
        JSONObject cred = UserJSON.createUserSignIn("usertest@test.com","12345678Kk");

        mvc.perform (
                MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cred.toString())
        )
                .andExpect(status().isOk())
                .andExpect(cookie().exists("Authorization"))
                .andDo(mvcResult -> authCookie = mvcResult.getResponse().getCookie("Authorization"));

        assertThat(authCookie).isNotNull();
    }


    @Test
    public  void checkUserJWT() throws Exception {
        assertThat(authCookie).isNotNull();

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/user/")
                        .cookie(authCookie)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.email", is("usertest@test.com")))
                .andExpect(jsonPath("$.body.password").doesNotExist());
    }

}
