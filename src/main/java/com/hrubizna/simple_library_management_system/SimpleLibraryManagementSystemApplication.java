package com.hrubizna.simple_library_management_system;

import lombok.extern.java.Log;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;


@SpringBootApplication
@Log
public class SimpleLibraryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleLibraryManagementSystemApplication.class, args);
    }
}

