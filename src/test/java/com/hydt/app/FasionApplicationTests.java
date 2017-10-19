package com.hydt.app;

import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FasionApplication.class)
public class FasionApplicationTests {

	@Value("${jetty.keyStore}")
	private String keyStore;

	@Autowired
	WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}



	@Test
	public void contextLoads() throws IOException {
		System.out.println(keyStore);
		ClassPathResource resource = new ClassPathResource("keyStore");
		URL url = resource.getURL();
		System.out.println(url);

		String path = ResourceUtils.getFile(keyStore).getPath();
		System.out.println(path);
	}

	@Test
	public void testController() throws Exception {
		mockMvc.perform(post("/")).andExpect(status().isOk());
	}

}
