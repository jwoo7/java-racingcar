package racingcar;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        CarRacingGame carRacingGame = new CarRacingGame(new UserInput(), new Race());
        carRacingGame.start();
    }
}

class CarRacingGame {
    private final UserInput userInput;
    private final Race race;

    public CarRacingGame(UserInput userInput, Race race) {
        this.userInput = userInput;
        this.race = race;
    }

    public void start() {
        String[] names = userInput.getCarNames();
        int attempts = userInput.getAttempts();

        List<Car> cars = createCars(names);
        race.run(cars, attempts);
        race.printWinner(cars);
    }

    private List<Car> createCars(String[] names) {
        List<Car> cars = new ArrayList<>();
        for (String name : names) {
            cars.add(new Car(name));
        }
        return cars;
    }
}

class Car {
    private final String name;
    private int position = 0;

    public Car(String name) {
        this.name = name;
    }

    public void move() {
        int randomValue = Randoms.pickNumberInRange(0, 9);
        if (randomValue >= 4) {
            position++;
        }
    }

    public String getPositionDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < position; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}

class Race {

    public void run(List<Car> cars, int attempts) {
        for (int i = 0; i < attempts; i++) {
            moveAllCars(cars);
            printCarsPosition(cars);
        }
    }

    private void moveAllCars(List<Car> cars) {
        for (Car car : cars) {
            car.move();
        }
    }

    private void printCarsPosition(List<Car> cars) {
        for (Car car : cars) {
            System.out.println(car.getName() + " : " + car.getPositionDisplay());
        }
        System.out.println();
    }

    public void printWinner(List<Car> cars) {
        int maxPosition = getMaxPosition(cars);
        List<String> winners = getWinners(cars, maxPosition);
        System.out.println("최종 우승자 : " + String.join(", ", winners));
    }

    private int getMaxPosition(List<Car> cars) {
        int maxPosition = 0;
        for (Car car : cars) {
            if (car.getPosition() > maxPosition) {
                maxPosition = car.getPosition();
            }
        }
        return maxPosition;
    }

    private List<String> getWinners(List<Car> cars, int maxPosition) {
        List<String> winners = new ArrayList<>();
        for (Car car : cars) {
            if (car.getPosition() == maxPosition) {
                winners.add(car.getName());
            }
        }
        return winners;
    }
}

class UserInput {

    public String[] getCarNames() {
        String inputNames = promptCarNames();
        String[] names = inputNames.split(",");
        validateCarNames(names);
        return names;
    }

    private String promptCarNames() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        return Console.readLine();
    }

    private void validateCarNames(String[] names) {
        for (String name : names) {
            if (name.length() > 5) {
                throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다.");
            }
        }
    }

    public int getAttempts() {
        return promptAttempts();
    }

    private int promptAttempts() {
        System.out.println("시도할 회수는 몇회인가요?");
        int attempts = Integer.parseInt(Console.readLine());
        if (attempts <= 0) {
            throw new IllegalArgumentException("시도 횟수는 1회 이상이어야 합니다.");
        }
        return attempts;
    }
}
