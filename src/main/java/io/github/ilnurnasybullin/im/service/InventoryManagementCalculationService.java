package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.AnswerParameters;
import io.github.ilnurnasybullin.im.entity.TaskParameters;

public interface InventoryManagementCalculationService {
    AnswerParameters answer(TaskParameters parameters);
}
