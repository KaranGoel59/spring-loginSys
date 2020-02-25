package karangoel.codes.gchat.integration.controller;

import karangoel.codes.gchat.Main;
import karangoel.codes.gchat.util.UserJSON;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Main.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
public class CreateUserTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenSignUp_createNewUser() throws Exception{
        JSONObject user =
                UserJSON.createUserSignUp("test@test.com", "test_first","test_last","12345678Kk");

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/sign-up/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user.toString())
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body", is("test@test.com registered")));
    }
}
