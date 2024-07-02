package com.rahgozin.appointment.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahgozin.appointment.application.config.JwtAuthenticationFilter;
import com.rahgozin.appointment.application.controller.DoctorRestService;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.entity.Role;
import com.rahgozin.appointment.application.model.AddAppointmentRequest;
import com.rahgozin.appointment.application.model.LoginRequest;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.http.HttpRequest;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WithMockUser(username = "rezaei", password = "12345")
class AppointmentApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private AppointmentService appointmentService;

	@MockBean
	private DoctorRepository doctorRepository;
	@MockBean
	private JwtAuthenticationFilter filter;

	@MockBean
	private DoctorService doctorService;

	@MockBean
	private PasswordEncoder passwordEncoder;


//	@BeforeEach
//	void test() throws Exception {
////		DoctorEntity doctor = new DoctorEntity();
////		doctor.setUsername("rezaei");
////		doctor.setPassword(passwordEncoder.encode("12345"));
////		doctor.setName("doc");
////		doctor.setAddress("doc");
////		try{
////			doctorRepository.save(doctor);
////			System.out.println("Doctor saved");
////
////		}catch (Exception e){
////			e.fillInStackTrace();
////		}
//		ObjectMapper objectMapper = new ObjectMapper();
//		LoginRequest request = new LoginRequest();
//		request.setUsername("rezaei");
//		request.setPassword("12345");
//		MvcResult resultActions = mockMvc.perform(post("/v1/auth/login").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)).andReturn();
//		String token = String.valueOf(resultActions.getClass().getField("jwtToken"));
//		System.out.println(token);
//	}

	@Test
	void contextLoads() throws Exception {
		try{
			Long doctorId = 1L;
			DoctorEntity mockDoctor = new DoctorEntity();
			Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(mockDoctor));
			AddAppointmentRequest request = new AddAppointmentRequest("07:00","06:00",20240702);
			ObjectMapper objectMapper = new ObjectMapper();
			mockMvc.perform(post("/doctor/addAppointment").
							content(objectMapper.writeValueAsString(request)).
							contentType(MediaType.APPLICATION_JSON).
							accept("application/json").
							header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZXphZWkiLCJpYXQiOjE3MTk5NDE0MjUsImV4cCI6MTcxOTk0Mjg2NX0.KK8i6m3bjVPUVJ81CB0K9FAZDy96Ymd1f-YaY6trZww")).
					andExpect(status().is4xxClientError()).
					andExpect(content().string("START_TIME_SOONER_THAN_END_TIME"));}catch (Exception e){

			e.printStackTrace();
			System.out.println(e.getMessage());
			throw e
					;}

//		appointmentService.addAppointments(mockDoctor.getUsername(), request);

	}

}
