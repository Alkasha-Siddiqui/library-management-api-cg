package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class LibraryManagementApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void testMain() {
		assertDoesNotThrow(() -> LibraryManagementApplication.main(new String[] {}));
	}

	@Test
	void contextLoads() {
		// Test that the application context loads successfully
		assertNotNull(applicationContext);
	}

	@Test
	void testModelMapperBean() {
		// Test that the ModelMapper bean is created
		ModelMapper modelMapper = applicationContext.getBean(ModelMapper.class);
		assertNotNull(modelMapper);
	}
}
