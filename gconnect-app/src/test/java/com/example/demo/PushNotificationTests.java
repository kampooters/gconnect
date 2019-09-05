//package com.example.demo;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tb.gconnect.NotificationGateway;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import javax.ws.rs.core.MediaType;
//import java.sql.Timestamp;
//import java.util.Arrays;
//
//import static com.tb.gconnect.common.constants.BASERoute.BASE_ROUTE;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@ActiveProfiles("dev")
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {PushNotificationController.class, NotificationGateway.class})
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class PushNotificationTests extends AuthBase {
//    private static NotificationResponseDTO notificationResponseDTO;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void notifitcationTestGetAll() throws Exception {
//        this.mockMvc
//                .perform(get(BASE_ROUTE + PushNotificationRoute.TEST)
//                        /*.accept(MediaType.APPLICATION_JSON)*/)
//                .andDo(print())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void addNotificationTest() throws Exception {
//
//        ContextualNotification contextualNotification = new ContextualNotification();
//        contextualNotification.setApp_id("TEST");
//        contextualNotification.setCompaign_id("TEST");
//        contextualNotification.setEnd_window(23);
//        contextualNotification.setPriority(2);
//        contextualNotification.setSchedule_date(new Timestamp(System.currentTimeMillis()));
//        contextualNotification.setStart_window(1);
//        NotificationMessage notificationMessage = new NotificationMessage();
//        notificationMessage.setMessage("TEST MESSAGE");
//        notificationMessage.setMssidn(Arrays.asList(111111111111L));
//        contextualNotification.setMessage(Arrays.asList(notificationMessage));
//        MvcResult result = this.mockMvc
//                .perform(MockMvcRequestBuilders.post(BASE_ROUTE + PushNotificationRoute.SEND)
//                        .header("Authorization", this.getAuthResponseDTO().getAccessToken()).header("ClientId", this.getAuthResponseDTO().getClientId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(contextualNotification)))
//                .andDo(print())
//                .andExpect(status().isOk()).andReturn();
//        String contentAsString = result.getResponse().getContentAsString();
//        notificationResponseDTO = objectMapper.readValue(contentAsString, NotificationResponseDTO.class);
//
//    }
//
//    @Test
//    public void getByIdTest() throws Exception {
//        String path = BASE_ROUTE + PushNotificationRoute.GET_STATS;
//        path = path.replaceAll("\\{id\\}", notificationResponseDTO.getMessage_id());
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.get(path)
//                        .header("Authorization", this.getAuthResponseDTO().getAccessToken())
//                        .header("ClientId", this.getAuthResponseDTO().getClientId())
//                )
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//
//}
