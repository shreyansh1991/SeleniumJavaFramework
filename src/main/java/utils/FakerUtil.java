package utils;

import com.github.javafaker.Faker;

import java.util.Random;

public class FakerUtil {
    public Long generateRandomNumber() {
        Faker faker = new Faker();
        return faker.number().randomNumber(10,true);
    }
}
