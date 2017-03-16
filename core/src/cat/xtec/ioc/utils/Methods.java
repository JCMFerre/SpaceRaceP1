package cat.xtec.ioc.utils;

import java.util.Random;

public class Methods {

    // Mètode que torna un float aleatòri entre un mínim i un màxim
    public static float randomFloat(float min, float max) {
        return new Random().nextFloat() * (max - min) + min;
    }
}