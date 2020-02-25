package karangoel.codes.gchat.integration.controller;

import karangoel.codes.gchat.Main;
import karangoel.codes.gchat.util.GroupJSON;
import karangoel.codes.gchat.util.UserJSON;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Main.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
public class GroupControllerTest {
    @Autowired
    private MockMvc mvc;

    private Cookie authCookie;

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
    public void whenCreateAGroup_newGroup() throws Exception {
        assertThat(authCookie).isNotNull();

        JSONObject newGroup = GroupJSON.createGroup("group1");

        mvc.perform(
                MockMvcRequestBuilders
                .post("/api/v1/user/group/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newGroup.toString())
                .cookie(authCookie)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body", Matchers.is("group1 created")));
    }

    @Test
    public void joinAGroup_returnGroup() throws Exception {
        assertThat(authCookie).isNotNull();

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/user/group/join/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(authCookie)
        )
                .andExpect(status().isOk())
                .andDo(mvcResult -> log.info(mvcResult.getResponse().getContentAsString()));
    }


    @Test
    public void getGroups_Groups() throws Exception {
        assertThat(authCookie).isNotNull();

        mvc.perform(
                MockMvcRequestBuilders
                .get("/api/v1/user/group/")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(authCookie)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.[*].name").isArray())
                .andExpect(jsonPath("$.body.[*].name", Matchers.contains("group1")));
    }

}
