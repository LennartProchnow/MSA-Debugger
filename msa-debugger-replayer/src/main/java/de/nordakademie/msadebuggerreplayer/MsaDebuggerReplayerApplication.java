package de.nordakademie.msadebuggerreplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class MsaDebuggerReplayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaDebuggerReplayerApplication.class, args);
	}

}
