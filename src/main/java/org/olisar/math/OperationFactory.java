package org.olisar.math;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class OperationFactory {

    private final static List<Function<Integer, IOperation>> FACTORY_METHODS = List.of(
            Addition::new,
            Subtraction::new
    );

    public static IOperation create(int result) {
        return FACTORY_METHODS.get(
                ThreadLocalRandom.current().nextInt(0, FACTORY_METHODS.size())
        ).apply(result);
    }

}
