package br.com.siswbrasil.algafood;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Errors;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.siswbrasil.algafood.api.controller"))
				.paths(PathSelectors.ant("/**")).build().useDefaultResponseMessages(false)
				.ignoredParameterTypes(HttpServletRequest.class, HttpServletResponse.class, HttpSession.class,
						Pageable.class, Errors.class, Sort.class, Character.class)
				.apiInfo(this.apiInfo());
	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("Especialistas Spring API").description("ACurso Algaworks").version("1.0.0")
				.license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.contact(new Contact("Adriano", "", "adriano.faria@gmail.com")).build();
	}

}
