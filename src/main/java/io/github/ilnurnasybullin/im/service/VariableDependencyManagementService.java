package io.github.ilnurnasybullin.im.service;

import java.util.Collection;

public interface VariableDependencyManagementService {

    void createPatterns(Collection<String> names);
    Collection<String> dependencies(String expression);
    void clearPatterns();

}
