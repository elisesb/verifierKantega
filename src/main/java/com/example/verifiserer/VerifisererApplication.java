package com.example.verifiserer;

import com.example.verifiserer.service.ApplicantService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class VerifisererApplication {
	private final ApplicantService applicantService;

	public VerifisererApplication(ApplicantService applicantService) {
		this.applicantService = applicantService;
	}

	public static void main(String[] args) {
		SpringApplication.run(VerifisererApplication.class, args);
	}


	// Automatically triggered when the application is fully ready
	@EventListener(ApplicationReadyEvent.class)
	public void cleanUpOldCvs() {
		applicantService.deleteOldCvRecords();  // Trigger cleanup when the application starts
	}
}



