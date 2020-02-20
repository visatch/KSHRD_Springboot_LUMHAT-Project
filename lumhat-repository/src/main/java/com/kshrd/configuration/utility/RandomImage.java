package com.kshrd.configuration.utility;

import java.util.Random;

public class RandomImage {
    public static String getRandomImage(){
        String images[] = {"class-card-1.png","class-card-2.png","class-card-3.png","class-card-4.png",
                            "class-card-5.png","class-card-6.png","class-card-7.png","class-card-8.png",
                            "class-card-9.png","class-card-10.png","class-card-11.png","class-card-12.png"};
        Random random = new Random();
        int index = random.nextInt(images.length);
        return images[index];
    }
}
