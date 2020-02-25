package karangoel.codes.gchat.unit;

import karangoel.codes.gchat.controller.UserController;
import karangoel.codes.gchat.service.UserAccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @MockBean
    UserAccountServiceImpl userAccountService;

    @InjectMocks
    UserController userController;

    @Test
    public void whenSignUp_createNewUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

}
