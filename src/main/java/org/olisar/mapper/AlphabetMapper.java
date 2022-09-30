package org.olisar.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class AlphabetMapper implements IMapper {

    private final Map<String, Integer> map = new HashMap<>();

    private final Set<Integer> usedNumbers = new HashSet<>();

    public AlphabetMapper(String[] allowedLetters) {
        for (String letter : allowedLetters) {
            map.put(letter, generateUniqueNumber());
        }
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    @Override
    public int assignNumber(String letter) {
        return map.get(letter);
    }

    private Integer generateUniqueNumber()
    {
        while (true)
        {
            Integer number = ThreadLocalRandom.current().nextInt(0, 100 + 1);
            if (!usedNumbers.contains(number))
            {
                usedNumbers.add(number);
                return number;
            }
        }
    }

}
