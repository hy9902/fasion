package com.hydt.app;


import com.hydt.app.config.TcScopeConfig;
import com.hydt.app.service.SampleService;
import com.hydt.app.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource()
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//@PropertySource(value={"file:D:/zTest/config/config1.properties"})
public class FasionApplication implements CommandLineRunner{

	private static Logger logger = LoggerFactory.getLogger(FasionApplication.class);

	@Autowired
	TcScopeConfig tcScopeConfig;

	@Autowired
	private SampleService sampleService;

	@Autowired
	private RabbitListenerContainerFactory rabbitListenerContainerFactory;


	public static void main(String[] args) {

		SpringApplication.run(FasionApplication.class, args);
	}


	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}


	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {
		//testSampleService();
		testTcScopeConfig();
	}

	private void testTcScopeConfig(){
		if(tcScopeConfig == null){
			logger.error("tcScopeConfig is null");
		} else {
			logger.error("tcScopeConfig is OK");
			logger.error("isEnable :" + tcScopeConfig.isEnable());
			Map<String, String> map = tcScopeConfig.getEir();
			if(map == null) return;
			logger.error("getEir size :" + map.size());
			for(Map.Entry<String,String> entry: map.entrySet()){
				logger.error(entry.getKey() + ":" + entry.getValue());
			}
			List<String> pc = tcScopeConfig.getPc();
			if(pc == null) return;
			logger.error("getPc size :" + pc.size());
			for(String item: pc){
				logger.error(item);
			}
		}
	}

	private void testSampleService(){
		System.out.println("------1231-------------"+Thread.currentThread().getUncaughtExceptionHandler().getClass().getName()+"-----------------------");
		Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
			System.out.println(t.getName()+"---------------------" + e.getLocalizedMessage());
		});

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("user", "N/A",
						AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")));
		try {
			System.out.println(this.sampleService.secure());
			System.out.println(this.sampleService.authorized());
			//System.out.println(this.sampleService.denied());
		}
		finally {
			SecurityContextHolder.clearContext();
		}
	}
}
