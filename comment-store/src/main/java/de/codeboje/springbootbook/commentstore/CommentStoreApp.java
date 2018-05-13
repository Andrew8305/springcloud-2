package de.codeboje.springbootbook.commentstore;

import javax.servlet.Filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.codeboje.springbootbook.commons.CommentstoreObjectMapper;
import de.codeboje.springbootbook.logging.RequestContextLoggingFilter;

/**
 * Application Configuration and Starter for the commentstore
 * 
 * @author Jens Boje
 *
 */
@SpringBootApplication
@RestController
@EnableTransactionManagement
@ComponentScan(basePackages = { "de.codeboje.springbootbook" })
// @EnableJpaRepositories(basePackages= {"de.codeboje.springbootbook"})
@EntityScan(basePackages = { "de.codeboje.springbootbook" })
@ImportResource(value = { "classpath*:legacy-context.xml" })
@EnableEurekaClient
public class CommentStoreApp {
	
	private static final Log log = LogFactory.getLog(CommentStoreApp.class);

	@Value("${user.role}")
	private String role;

	@Value("${msg:Hello world - Config Server is not working..pelase check}")
	private String msg;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(CommentStoreApp.class, args);
	}

	/**
	 * Maps the commons logging Filter to all requests; done by spring boot
	 * 
	 * @return
	 */
	@Bean
	public Filter initRequestContextLoggingFilter() {
		return new RequestContextLoggingFilter();
	}

	@Bean
	@Primary
	public ObjectMapper initObjectMapper() {
		return new CommentstoreObjectMapper();
	}

	@RequestMapping(value = "/whoami/{username}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String whoami(@PathVariable("username") String username) {
		log.info("whoami called with " + username);
		return String.format("Hello! You're %s and you'll become a(n) %s...\n", username, role);
	}

	@RequestMapping("/msg")
	public String getMsg() {
		log.info("get message called ");

		return this.msg;
	}

}
