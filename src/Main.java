import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "qwe123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/oris2dz";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        UsersRepositoryJdbcImpl userRepository = new UsersRepositoryJdbcImpl(connection);

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить пользователей");
            System.out.println("2. Найти всех пользователей");
            System.out.println("3. Найти пользователя по ID");
            System.out.println("4. Обновить данные пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("6. Найти пользователей по возрасту");
            System.out.println("7. Найти пользователей по email");
            System.out.println("8. Найти пользователей по номеру телефона");
            System.out.println("9. Найти пользователей по городу");
            System.out.println("0. Выход");

            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.println("Сколько пользователей вы хотите добавить?");
                        int count = Integer.parseInt(scanner.nextLine());
                        addUsers(userRepository, count);
                        break;
                    case 2:
                        findAllUsers(userRepository);
                        break;
                    case 3:
                        System.out.println("Введите ID пользователя:");
                        Long id = Long.parseLong(scanner.nextLine());
                        findUserById(userRepository, id);
                        break;
                    case 4:
                        System.out.println("Введите ID пользователя для обновления:");
                        Long updateId = Long.parseLong(scanner.nextLine());
                        updateUser(userRepository, updateId);
                        break;
                    case 5:
                        System.out.println("Введите ID пользователя для удаления:");
                        Long deleteId = Long.parseLong(scanner.nextLine());
                        removeUser(userRepository, deleteId);
                        break;
                    case 6:
                        System.out.println("Введите возраст:");
                        int age = Integer.parseInt(scanner.nextLine());
                        findUsersByAge(userRepository, age);
                        break;
                    case 7:
                        System.out.println("Введите email:");
                        String email = scanner.nextLine();
                        findUsersByEmail(userRepository, email);
                        break;
                    case 8:
                        System.out.println("Введите номер телефона:");
                        String phoneNumber = scanner.nextLine();
                        findUsersByPhoneNumber(userRepository, phoneNumber);
                        break;
                    case 9:
                        System.out.println("Введите город:");
                        String city = scanner.nextLine();
                        findUsersByCity(userRepository, city);
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        scanner.close();
                        connection.close();
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }
    }

    public static void addUsers(UsersRepositoryJdbcImpl userRepository, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("Введите имя: ");
            String firstName = scanner.nextLine();
            System.out.println("Введите фамилию: ");
            String lastName = scanner.nextLine();
            System.out.println("Введите возраст: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.println("Введите email: ");
            String email = scanner.nextLine();
            System.out.println("Введите номер телефона: ");
            String phoneNumber = scanner.nextLine();
            System.out.println("Введите город: ");
            String city = scanner.nextLine();

            User user = new User(null, firstName, lastName, age, email, phoneNumber, city);
            userRepository.save(user);
            System.out.println("Пользователь успешно добавлен.");
        }
    }

    public static void findAllUsers(UsersRepositoryJdbcImpl userRepository) {
        try {
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                System.out.println("Пользователи не найдены.");
            } else {
                users.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске пользователей: " + e.getMessage());
        }
    }

    public static void findUserById(UsersRepositoryJdbcImpl userRepository, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            System.out.println(user.get());
        } else {
            System.out.println("Пользователь с ID " + id + " не найден.");
        }
    }

    public static void updateUser(UsersRepositoryJdbcImpl userRepository, Long id) {
        System.out.println("Введите новое имя:");
        String firstName = scanner.nextLine();
        System.out.println("Введите новую фамилию:");
        String lastName = scanner.nextLine();
        System.out.println("Введите новый возраст:");
        int age = Integer.parseInt(scanner.nextLine());

        User user = new User(id, firstName, lastName, age, null, null, null);
        userRepository.update(user);
        System.out.println("Пользователь обновлен.");
    }

    public static void removeUser(UsersRepositoryJdbcImpl userRepository, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.remove(user.get());
            System.out.println("Пользователь с ID " + id + " удален.");
        } else {
            System.out.println("Пользователь с ID " + id + " не найден.");
        }
    }

    public static void findUsersByAge(UsersRepositoryJdbcImpl userRepository, int age) {
        List<User> users = userRepository.findAllByAge(age);
        if (users.isEmpty()) {
            System.out.println("Пользователи с возрастом " + age + " не найдены.");
        } else {
            users.forEach(System.out::println);
        }
    }

    public static void findUsersByEmail(UsersRepositoryJdbcImpl userRepository, String email) {
        List<User> users = userRepository.findAllByEmail(email);
        if (users.isEmpty()) {
            System.out.println("Пользователи с email " + email + " не найдены.");
        } else {
            users.forEach(System.out::println);
        }
    }

    public static void findUsersByPhoneNumber(UsersRepositoryJdbcImpl userRepository, String phoneNumber) {
        List<User> users = userRepository.findAllByPhoneNumber(phoneNumber);
        if (users.isEmpty()) {
            System.out.println("Пользователи с номером телефона " + phoneNumber + " не найдены.");
        } else {
            users.forEach(System.out::println);
        }
    }

    public static void findUsersByCity(UsersRepositoryJdbcImpl userRepository, String city) {
        List<User> users = userRepository.findAllByCity(city);
        if (users.isEmpty()) {
            System.out.println("Пользователи из города " + city + " не найдены.");
        } else {
            users.forEach(System.out::println);
        }
    }
}
