package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.AnswerParameters;
import io.github.ilnurnasybullin.im.entity.TaskParameters;

public interface CalculationService {
    AnswerParameters calculate(TaskParameters parameters);
}
