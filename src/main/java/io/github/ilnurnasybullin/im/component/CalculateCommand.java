package io.github.ilnurnasybullin.im.component;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import io.github.ilnurnasybullin.im.dto.Task;
import io.github.ilnurnasybullin.im.entity.AnswerParameters;
import io.github.ilnurnasybullin.im.entity.TaskParameters;
import io.github.ilnurnasybullin.im.service.CalculationService;
import io.github.ilnurnasybullin.im.service.TaskParametersService;
import io.github.ilnurnasybullin.im.service.TaskReaderService;
import org.springframework.stereotype.Component;

@Component
@Parameters(commandNames = CalculateCommand.commandName, commandDescription = "calculating")
public class CalculateCommand implements Command {

    public static final String commandName= "calculate";
    private final TaskReaderService taskReader;
    private final TaskParametersService taskParametersService;
    private final CalculationService calculationService;

    @Parameter(names = {"-i", "--input"}, description = "input file name")
    private String inputFilePathName;

    public CalculateCommand(TaskReaderService taskReader,
                            TaskParametersService taskParametersService,
                            CalculationService calculationService) {
        this.taskReader = taskReader;
        this.taskParametersService = taskParametersService;
        this.calculationService = calculationService;
    }

    @Override
    public boolean hasCommand(String commandName) {
        return CalculateCommand.commandName.equals(commandName);
    }

    @Override
    public void run() {
        try {
            Task task = taskReader.readTask(inputFilePathName);
            TaskParameters parameters = taskParametersService.taskParameters(task);
            AnswerParameters answer = calculationService.calculate(parameters);
            answer.variables().forEach(System.out::println);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
