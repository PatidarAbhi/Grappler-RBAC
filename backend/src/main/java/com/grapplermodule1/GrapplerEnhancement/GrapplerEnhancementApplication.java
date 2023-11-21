package com.grapplermodule1.GrapplerEnhancement;
import com.grapplermodule1.GrapplerEnhancement.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info= @Info ( title = "Grappler Enhancement - Module 1",
				version="1.0.0",
				description="The project is to define the structure of APIs in Role Based Access Controll and Hierarchy",
				termsOfService="https://grappler.innogent.in/dashboard"  ,
				contact = @Contact ( name = "Dishika Jain , Abhishek Patidar, Rishi Raghuvanshi" , email ="dishika.jain@innogent.in ,abhishek.patidar@innogent.in , rishi.raghuvanshi@innogent.in " ) ,
				license = @License ( name = "licence",url="https://github.com/rishiraghuvanshi08/Grappler-Enhancement")

		)
)
public class GrapplerEnhancementApplication {
	public static void main(String[] args) {
		SpringApplication.run(GrapplerEnhancementApplication.class, args);
	}

}
