package com.ciklum.rockpaperscissor.service;

import static com.ciklum.rockpaperscissor.persistence.model.ShapeEnum.PAPER;
import static com.ciklum.rockpaperscissor.persistence.model.ShapeEnum.ROCK;
import static com.ciklum.rockpaperscissor.persistence.model.ShapeEnum.SCISSOR;

import com.ciklum.rockpaperscissor.persistence.model.ShapeEnum;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class ShapeServiceImpl implements ShapeService {

    private static final Map<ShapeEnum, ShapeEnum> SHAPE_BEATS_MAP;

    static {
        SHAPE_BEATS_MAP = new EnumMap<>(ShapeEnum.class);
        SHAPE_BEATS_MAP.put(PAPER, ROCK);
        SHAPE_BEATS_MAP.put(ROCK, SCISSOR);
        SHAPE_BEATS_MAP.put(SCISSOR, PAPER);
    }

    private final Random randomGenerator;

    public ShapeServiceImpl() {
        this.randomGenerator = new Random();
    }

    @Override
    public ShapeEnum getRandomShape() {
        int shapesLength = ShapeEnum.values().length;
        return ShapeEnum.values()[this.randomGenerator.nextInt(shapesLength)];
    }

    @Override
    public boolean shapeBeatsTo(ShapeEnum shape, ShapeEnum shapeToBeat) {
        return SHAPE_BEATS_MAP.get(shape).equals(shapeToBeat);
    }
}
