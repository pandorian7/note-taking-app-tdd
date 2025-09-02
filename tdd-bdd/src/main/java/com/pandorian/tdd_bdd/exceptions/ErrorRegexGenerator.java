package com.pandorian.tdd_bdd.exceptions;

import org.reflections.Reflections;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class ErrorRegexGenerator {
    public static void main(String[] args) throws IOException {
        Reflections reflections = new Reflections("com.pandorian.tdd_bdd.exceptions");
        Set<Class<? extends ApplicationException>> errors = reflections.getSubTypesOf(ApplicationException.class);

        String regex = errors.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.joining("|"));

        String content = "package com.pandorian.tdd_bdd.exceptions;\n\n" +
                "public class GeneratedConstants {\n" +
                "    public static final String ERROR_REGEX = \"" + regex + "\";\n" +
                "}";
        try (FileWriter writer = new FileWriter("src/main/java/com/pandorian/tdd_bdd/exceptions/GeneratedConstants.java")) {
            writer.write(content);
        }
    }
}