import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;

public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "Hritik9929";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        reserveRoom(statement, scanner);
                        break;

                    case 2:
                        viewReservation(statement);
                        break;

                    case 3:
                        getRoomNum(statement, scanner);
                        break;

                    case 4:
                        updateReservation(statement, scanner);
                        break;

                    case 5:
                        deleteReservation(statement, scanner);
                        break;

                    case 0:
                        exit();
                        scanner.close();
                        connection.close();
                        statement.close();
                        return;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void reserveRoom(Statement statement, Scanner scanner) {//Connection connection,
        try {
            System.out.println("Enter Guest Name : ");
            String gName = scanner.nextLine();
            System.out.println("Enter Room No : ");
            int gRoomNo = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter Guest Conatct Number : ");
            int gConNo = scanner.nextInt();
            String query = "INSERT INTO reservation(guest_Name,room_number,contact_number)" +
                    "VALUES ('" + gName + "', " + gRoomNo + ", '" + gConNo + "')";
            //connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Reservation Successful!");
            } else {
                System.out.println("Reservation Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewReservation(Statement statement) {
        String sqlQuery = "SELECT * FROM reservation";
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_Name");
                int roomNumber = resultSet.getInt("room_number");
                int contactNumber = resultSet.getInt("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getRoomNum(Statement statement, Scanner scanner) {
        System.out.println("Enter Reservation ID : ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Guest Name");
        String gName = scanner.nextLine();
        String sqlQuery = "SELECT room_number FROM reservation " +
                "WHERE reservation_id = " + reservationId +
                " OR guest_Name = '" + gName + "'";
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                System.out.println("Room Number for Reservation ID " + reservationId + " and Guest " + gName + " is : " + resultSet.getInt("room_number"));
            } else {
                System.out.println("Reservation Not Found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateReservation(Statement statement, Scanner scanner) {
        System.out.println("Enter the guest reservation id : ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();
        if (!reservationExists(statement, reservationId)) {
            System.out.println("Reservation ID Not Exists");
            return;
        }
        System.out.println("Enter Updated Guest Name : ");
        String newGName = scanner.nextLine();
        System.out.println("Enter Updated Guest Room No : ");
        int newGRoomNo = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Updated Contact No : ");
        int newConNo = scanner.nextInt();
        String sqlQuery = "UPDATE reservation SET guest_Name = '" + newGName +
                "', room_number = " + newGRoomNo +
                ", contact_number = " + newConNo +
                " WHERE reservation_id = " + reservationId;
        try {
            int affectedRow = statement.executeUpdate(sqlQuery);
            if (affectedRow > 0) {
                System.out.println("Reservation updated successfully!");
            } else {
                System.out.println("Reservation update Failed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteReservation(Statement statement, Scanner scanner) {
        System.out.println("Enter the Reservation to Delete : ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();
        if (!reservationExists(statement, reservationId)) {
            System.out.println("Reservation ID Not Exists");
            return;
        }
        String sqlQuery = "DELETE FROM reservation WHERE reservation_id = " + reservationId;
        try {
            int affectedRow = statement.executeUpdate(sqlQuery);
            if (affectedRow > 0) {
                System.out.println("Reservation deleted successfully!");
            } else {
                System.out.println("Reservation deletion failed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean reservationExists(Statement statement, int reservationId) {
        String sqlQuery = "Select reservation_id FROM reservation where reservation_id = " + reservationId;
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }

}
