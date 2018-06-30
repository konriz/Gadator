package com.gadator.service;

import com.gadator.DTO.UserDTO;
import com.gadator.entity.User;
import com.gadator.repository.RoleRepository;
import com.gadator.repository.UserRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestConfiguration
    {
        @Bean
        public UserService userService()
        {
            return new UserServiceImpl();
        }

        @Bean
        public PasswordEncoder passwordEncoder()
        {
            return new BCryptPasswordEncoder();
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;


    private static String existingUserName = "testUser";
    private static String existingUserEmail = "testUser@mail.com";
    private static String existingUserPassword = "password";

    private static String newPassword = "newPassword";

    private static User testUser;

    private static UserDTO testUserDTO;

    @Before
    public void setUp()
    {
        testUser = new User();
        testUser.setName(existingUserName);
        testUser.setEmail(existingUserEmail);
        testUser.setPassword(passwordEncoder.encode(existingUserPassword));

        Mockito
                .when(userRepository.findOneByName(existingUserName))
                .thenReturn(testUser);

        testUserDTO = new UserDTO(testUser);
    }


    @Test
    public void whenFindExistingUserByName_thenReturnExistingUser()
    {
        User findUser = userService.findOneByName(existingUserName);

        assertEquals(findUser.getName(), existingUserName);
        assertEquals(findUser.getEmail(), existingUserEmail);

    }

    @Test
    public void givenCorrectPassword_whenCheckPassword_thenTrue()
    {
        assertTrue(userService.checkPassword(testUserDTO, existingUserPassword));
    }


}
