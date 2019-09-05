package com.example.demo;

import com.tb.gconnect.GConnect;
import com.tb.gconnect.common.constants.AuthRoute;
import com.tb.gconnect.controller.AuthController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AuthController.class, GConnect.class})
@AutoConfigureMockMvc
public class AuthTests extends AuthBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {

        this.mockMvc
                .perform(get(AuthRoute.BASE_ROUTE + AuthRoute.TEST))
                .andDo(print())
                .andExpect(status().isOk());
    }

   /* @Test
    @Order(2)
    public void registerClientTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(AuthRoute.BASE_ROUTE + AuthRoute.REGISTER)
                                .header("client_id", clientId).header("secret", secret)

                        *//*.content(jsonString)*//*)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200)
                );
    }*/

    @Test
    public void getAccessTokenTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(AuthRoute.BASE_ROUTE + AuthRoute.ACCESS_TOKEN)
                        .header("clientId", clientId).header("secret", secret))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200)
                ).andReturn();
    }

    @Test
    public void refreshTokenTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(AuthRoute.BASE_ROUTE + AuthRoute.REFRESH_TOKEN)
                                .header("clientId", clientId).header("refreshToken", authResponseDTO.getRefreshToken())

                        /*.content(jsonString)*/)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200)
                );
    }

}