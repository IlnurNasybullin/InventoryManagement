package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.dto.Task;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface TaskReaderService {
    Task readTask(String xmlFile) throws IOException, JAXBException;
}
