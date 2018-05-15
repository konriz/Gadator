package com.gadator.gadator;

import com.gadator.gadator.entity.User;
import com.gadator.gadator.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserServiceTest {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Before
    public void setupMockMvc()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    public void createUser() throws Exception
    {
        mockMvc.perform(post("/user/registration").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "NAME")
                .param("email", "EMAIL")
                .param("password", "letmein")
                .param("matchingPassword", "letmein"))
                .andExpect(status().isOk());

        User expectedUser = new User();
        expectedUser.setName("NAME");
        expectedUser.setEmail("EMAIL");

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(1)));
    }


}
