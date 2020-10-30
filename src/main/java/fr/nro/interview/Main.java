package fr.nro.interview;

import java.util.Random;

public class Main {

  public static void main(String ... args) {
    Random r = new Random();
    for(int i=0; i<500;i++) {
      System.out.println(r.nextInt(20));
    }
  }
}
