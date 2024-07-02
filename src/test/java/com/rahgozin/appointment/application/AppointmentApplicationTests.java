package com.rahgozin.appointment.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahgozin.appointment.application.config.JwtAuthenticationFilter;
import com.rahgozin.appointment.application.controller.DoctorRestService;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.model.AddAppointmentRequest;
import com.rahgozin.appointment.application.repository.AppointmentRepository;
import com.rahgozin.appointment.application.repository.DoctorRepository;
import com.rahgozin.appointment.application.service.AppointmentService;
import com.rahgozin.appointment.application.service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.http.HttpRequest;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private DoctorRepository doctorRepository;
	@MockBean
	private JwtAuthenticationFilter filter;

	@MockBean
	private DoctorService doctorService;


	@Test
	void test(){

		DoctorEntity doctor = new DoctorEntity();
		doctor.setUsername("doc");
		doctor.setPassword("doc");
		doctor.setName("doc");
		doctor.setAddress("doc");
	}

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
							header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZXphZWkiLCJpYXQiOjE3MTk5Mjg0OTYsImV4cCI6MTcxOTkyOTkzNn0.sGUfUrP1j-_MyqMzLhWMKAXHhSKKz4nLmnBhPSjeO80")).
					andExpect(status().is4xxClientError()).
					andExpect(content().string("START_TIME_SOONER_THAN_END_TIME"));}catch (Exception e){

			e.printStackTrace();
			System.out.println(e.getMessage());
			throw e
					;}

//		appointmentService.addAppointments(mockDoctor.getUsername(), request);

	}

}
