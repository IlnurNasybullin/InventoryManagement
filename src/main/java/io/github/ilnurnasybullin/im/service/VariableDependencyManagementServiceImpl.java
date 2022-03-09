package io.github.ilnurnasybullin.im.service;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class VariableDependencyManagementServiceImpl implements VariableDependencyManagementService {

    private final Map<String, Predicate<String>> patterns;

    public VariableDependencyManagementServiceImpl() {
        patterns = new HashMap<>();
    }

    @Override
    public void createPatterns(Collection<String> names) {
        names.forEach(name -> patterns.put(name, Pattern.compile("[(\\s,+\\-*/%^](" + name + ")[)\\s,+\\-*/%^]").asPredicate()));
    }

    @Override
    public Collection<String> dependencies(String expression) {
        return patterns.entrySet().stream()
                .filter(entry -> entry.getValue().test(expression))
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public void clearPatterns() {
        patterns.clear();
    }
}
