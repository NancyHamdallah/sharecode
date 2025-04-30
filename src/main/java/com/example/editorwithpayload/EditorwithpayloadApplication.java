package com.example.editorwithpayload;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EditorwithpayloadApplication {

	public static void main(String[] args) {

		if (System.getenv("RAILWAY_STATIC_URL") == null) {
			Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

			// Set required environment variables
			System.setProperty("MONGODB_URI", dotenv.get("MONGODB_URI"));
			System.setProperty("GITHUB_CLIENT_ID", dotenv.get("GITHUB_CLIENT_ID"));
			System.setProperty("GITHUB_CLIENT_SECRET", dotenv.get("GITHUB_CLIENT_SECRET"));
			System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
			System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
			System.setProperty("SUPABASE_URL", dotenv.get("SUPABASE_URL"));
			System.setProperty("SUPABASE_API_KEY", dotenv.get("SUPABASE_API_KEY"));
			System.setProperty("SUPABASE_BUCKET_NAME", dotenv.get("SUPABASE_BUCKET_NAME"));
			System.setProperty("JDOODLE_CLIENT_ID", dotenv.get("JDOODLE_CLIENT_ID"));
			System.setProperty("JDOODLE_CLIENT_SECRET", dotenv.get("JDOODLE_CLIENT_SECRET"));
		}


		SpringApplication.run(EditorwithpayloadApplication.class, args);


	}
	@PostConstruct
	public void printMongoUri() {
		System.out.println("MONGODB_URI = " + System.getenv("MONGODB_URI"));
	}


}
