package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.dto.Task;
import io.github.ilnurnasybullin.im.entity.TaskParameters;

public interface TaskParametersService {
    TaskParameters taskParameters(Task task);
}
