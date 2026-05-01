package com.java.poc.java_basics.oops;

import java.util.Scanner;

abstract class Car {
  protected Boolean isSedan;
  protected String seats;

  public Car(Boolean isSedan, String seats) {
    this.isSedan = isSedan;
    this.seats = seats;
  }

  public Boolean getIsSedan() {
    return this.isSedan;
  }

  public String getSeats() {
    return this.seats;
  }

  public abstract String getMileage();
}

class WagonR extends Car {
  private String mileage;

  public WagonR(Integer mileage) {
    super(false, "4");
    this.mileage = mileage + " kmpl";
    System.out.println("A WagonR is a sedan=" + getIsSedan() + ",no of seats=" + getSeats() + ",mileage=" + getMileage());
  }

  public String getMileage() {
    return this.mileage;
  }
}

class HondaCity extends Car {
  private String mileage;

  public HondaCity(Integer mileage) {
    super(true, "4");
    this.mileage = mileage + " kmpl";
    System.out.println("A HondaCity is a sedan=" + getIsSedan() + ",no of seats=" + getSeats() + ",mileage=" + getMileage());
  }

  public String getMileage() {
    return this.mileage;
  }
}

class InnovaCrysta extends Car {
  private String mileage;

  public InnovaCrysta(Integer mileage) {
    super(false, "6");
    this.mileage = mileage + " kmpl";
    System.out.println("A InnovaCrysta is a sedan=" + getIsSedan() + ",no of seats=" + getSeats() + ",mileage=" + getMileage());
  }

  public String getMileage() {
    return this.mileage;
  }
}

class CarFactory {
  public Car getCar(int type, int mileage) {
    if (type == 0) {
      return new WagonR(mileage);
    } else if (type == 1) {
      return new HondaCity(mileage);
    } else if (type == 2) {
      return new InnovaCrysta(mileage);
    }
    return null;
  }
}

public class CarInheritance {
  public static void main(String[] args) {
    CarFactory carFactory = new CarFactory();
    Scanner scanner = new Scanner(System.in);
    int type = scanner.nextInt();
    int mileage = scanner.nextInt();
    carFactory.getCar(type, mileage);
  }
}