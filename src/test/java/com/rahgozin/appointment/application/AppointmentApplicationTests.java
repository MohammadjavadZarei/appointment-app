package com.rahgozin.appointment.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahgozin.appointment.application.config.JwtAuthenticationFilter;
import com.rahgozin.appointment.application.controller.DoctorRestService;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.entity.Role;
import com.rahgozin.appointment.application.exception.ErrorsModel;
import com.rahgozin.appointment.application.exception.ExceptionEnum;
import com.rahgozin.appointment.application.model.AddAppointmentRequest;
import com.rahgozin.appointment.application.model.CancelAppointmentRequest;
import com.rahgozin.appointment.application.model.LoginRequest;
import com.rahgozin.appointment.application.model.ReserveAppointmentRequest;
import com.rahgozin.appointment.application.repository.AppointmentRepository;
import com.rahgozin.appointment.application.repository.DoctorRepository;
import com.rahgozin.appointment.application.service.AppointmentService;
import com.rahgozin.appointment.application.service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WithMockUser(username = "rezaei", password = "12345")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

class AppointmentApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    String token;
    String patientToken;
    final Object object = new Object();



    @BeforeEach
    void test2() throws Exception {
        //patient login

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest request2 = new LoginRequest();
        request2.setUsername("mohammadi");
        request2.setPassword("12345");
        MvcResult resultActions2 = mockMvc.
                perform(post("/v1/auth/login").
                        content(objectMapper.writeValueAsString(request2)).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andReturn();
        String responseBody2 = resultActions2.getResponse().getContentAsString();
        Map<String, String> responseMap2 = objectMapper.readValue(responseBody2, Map.class);
        this.patientToken = responseMap2.get("jwtToken");
    }
    @BeforeEach
    void test() throws Exception {


        //doctor login

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest request = new LoginRequest();
        request.setUsername("rezaei");
        request.setPassword("12345");
        MvcResult resultActions = mockMvc.
                perform(post("/v1/auth/login").
                        content(objectMapper.writeValueAsString(request)).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andReturn();
        String responseBody = resultActions.getResponse().getContentAsString();
        Map<String, String> responseMap = objectMapper.readValue(responseBody, Map.class);
        this.token = responseMap.get("jwtToken");

    }

    @Test
    void addAppointmentTest() throws Exception {
        try {
            AddAppointmentRequest request = new AddAppointmentRequest("07:00", "06:00", 20240702);
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorsModel errorsModel = new ErrorsModel();
            errorsModel.setMessage(ExceptionEnum.START_TIME_SOONER_THAN_END_TIME.name());
            errorsModel.setCode(String.valueOf(400));
            mockMvc.perform(post("/doctor/addAppointment").
                            content(objectMapper.writeValueAsString(request)).
                            contentType(MediaType.APPLICATION_JSON).
                            accept("application/json").
                            header("Authorization", "Bearer " + token)).
                    andExpect(status().is4xxClientError()).
                    andExpect(content().string(objectMapper.writeValueAsString(errorsModel)));
        } catch (Exception e) {

            e.printStackTrace();
            throw e
                    ;
        }
    }

    @Test
    void contextLoads() throws Exception {
        try {
            String result = mockMvc.perform(get("/doctor/appointments").
                            contentType(MediaType.APPLICATION_JSON).
                            accept("application/json").
                            header("Authorization", "Bearer " + token)).
                    andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString();
        } catch (Exception e) {

            e.printStackTrace();
            throw e
                    ;
        }
    }

    @Test
    void cc() throws Exception {
        int threadCount =2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);


            executorService.submit(() -> {
                try {
                    checkConcurrency();
                    latch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        executorService.submit(() -> {
            try {
                checkConcurrencyPatient();
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

            latch.countDown();
            latch.countDown();

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    void checkConcurrency() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Long> ids = new ArrayList<>();
            ids.add(553L);
            CancelAppointmentRequest request = new CancelAppointmentRequest(ids);
                String result = mockMvc.perform(post("/doctor/cancelAppointment").content(objectMapper.writeValueAsString(request)).header("Authorization","Bearer " + token).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

        } catch (Exception e) {
            throw e;
        }
    }



    void checkConcurrencyPatient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ReserveAppointmentRequest reserveAppointmentRequest = new ReserveAppointmentRequest();
            reserveAppointmentRequest.setAppointmentId(553L);

            String result2 = mockMvc.perform(post("/patient/reserve").content(objectMapper.writeValueAsString(reserveAppointmentRequest)).header("Authorization","Bearer " + patientToken).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

        } catch (Exception e) {
            throw e;
        }
    }

}
