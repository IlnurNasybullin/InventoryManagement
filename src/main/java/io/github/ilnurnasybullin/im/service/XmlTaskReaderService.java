package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.dto.Task;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class XmlTaskReaderService implements TaskReaderService {

    private final Jaxb2Marshaller jaxb2Marshaller;

    public XmlTaskReaderService(Jaxb2Marshaller jaxb2Marshaller) {
        this.jaxb2Marshaller = jaxb2Marshaller;
    }

    @Override
    public Task readTask(String xmlFile) throws IOException, JAXBException {
        Unmarshaller unmarshaller = jaxb2Marshaller.createUnmarshaller();
        return (Task) unmarshaller.unmarshal(new FileReader(xmlFile, StandardCharsets.UTF_8));
    }
}
