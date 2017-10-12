package com.hydt.app;

import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FasionApplication.class)
public class FasionApplicationTests {

	@Value("${jetty.keyStore}")
	private String keyStore;

	@Test
	public void contextLoads() throws IOException {
		System.out.println(keyStore);
		ClassPathResource resource = new ClassPathResource("keyStore");
		URL url = resource.getURL();
		System.out.println(url);

		String path = ResourceUtils.getFile(keyStore).getPath();
		System.out.println(path);
	}

}
