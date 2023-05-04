package hiber.service;

import hiber.model.Car;
import hiber.model.User;

import java.util.List;

public interface CarService {

    void addCar(Car car);
    List<Car> getCarsList();

}
