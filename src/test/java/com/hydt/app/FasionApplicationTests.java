package com.hydt.app;

import com.hydt.app.config.dataSource.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FasionApplication.class)
public class FasionApplicationTests {

	@Value("${jetty.keyStore}")
	private String keyStore;

	@Autowired
	private DataSource dataSource;

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
	public void testSendEmail(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	}


	@Test
	public void testdb(){

	}
}
