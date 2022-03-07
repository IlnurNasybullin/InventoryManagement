package io.github.ilnurnasybullin.im;

import com.beust.jcommander.JCommander;
import io.github.ilnurnasybullin.im.component.Command;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    private final List<Command> commands;

    public App(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void run(String... args) {
        JCommander.Builder builder = JCommander.newBuilder()
                .programName("spring-boot-cli");
        commands.forEach(builder::addCommand);

        JCommander commander = builder.build();
        commander.parse(args);
        commands.stream().filter(command -> command.hasCommand(commander.getParsedCommand()))
                .forEach(Runnable::run);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
