package com.gadator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GadatorApplicationTest {

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp()
    {

        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .addFilters(this.springSecurityFilterChain)
                .build();
    }


    @Test
    public void testHome() throws Exception
    {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/");

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        assertEquals(200, result.getResponse().getStatus());

        System.out.println(result.getResponse().getContentAsString());

    }

}
