import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class HotelReservationSystem {

    enum RoomCategory {
        STANDARD, DELUXE, SUITE
    }

    static class Room implements Serializable {
        int roomNumber;
        RoomCategory category;
        double price;
        boolean isAvailable;

        Room(int roomNumber, RoomCategory category, double price) {
            this.roomNumber = roomNumber;
            this.category = category;
            this.price = price;
            this.isAvailable = true;
        }
    }

    static class Reservation implements Serializable {
        String guestName;
        int roomNumber;
        LocalDate checkInDate;
        LocalDate checkOutDate;
        boolean paid;

        Reservation(String guestName, int roomNumber, LocalDate in, LocalDate out) {
            this.guestName = guestName;
            this.roomNumber = roomNumber;
            this.checkInDate = in;
            this.checkOutDate = out;
            this.paid = false;
        }
    }

    static class Hotel {
        List<Room> rooms = new ArrayList<>();
        List<Reservation> reservations = new ArrayList<>();
        final String FILE_NAME = "reservations.ser";

        Hotel() {
            rooms.add(new Room(101, RoomCategory.STANDARD, 100));
            rooms.add(new Room(102, RoomCategory.DELUXE, 150));
            rooms.add(new Room(103, RoomCategory.SUITE, 200));
            rooms.add(new Room(104, RoomCategory.STANDARD, 90));
            loadReservations();
        }

        List<Room> searchAvailable(RoomCategory category) {
            List<Room> list = new ArrayList<>();
            for (Room r : rooms) {
                if (r.isAvailable && r.category == category) list.add(r);
            }
            return list;
        }

        Room getRoomByNumber(int number) {
            for (Room r : rooms) {
                if (r.roomNumber == number) return r;
            }
            return null;
        }

        Reservation bookRoom(String name, Room room, LocalDate in, LocalDate out) {
            room.isAvailable = false;
            Reservation res = new Reservation(name, room.roomNumber, in, out);
            reservations.add(res);
            return res;
        }

        boolean cancelReservation(String name) {
            Iterator<Reservation> it = reservations.iterator();
            while (it.hasNext()) {
                Reservation r = it.next();
                if (r.guestName.equalsIgnoreCase(name)) {
                    Room room = getRoomByNumber(r.roomNumber);
                    room.isAvailable = true;
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        Reservation findReservation(String name) {
            for (Reservation r : reservations) {
                if (r.guestName.equalsIgnoreCase(name)) return r;
            }
            return null;
        }

        void saveReservations() {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                out.writeObject(reservations);
            } catch (IOException e) {
                System.out.println("Erreur lors de la sauvegarde.");
            }
        }

        void loadReservations() {
            File f = new File(FILE_NAME);
            if (!f.exists()) return;
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                reservations = (List<Reservation>) in.readObject();
                for (Reservation r : reservations) {
                    Room room = getRoomByNumber(r.roomNumber);
                    if (room != null) room.isAvailable = false;
                }
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement des réservations.");
            }
        }
    }

    static class Payment {
        static boolean simulatePayment(double amount) {
            System.out.println("Traitement du paiement de " + amount + "€...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            System.out.println("Paiement réussi !");
            return true;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        System.out.println("=== Bienvenue dans le système de réservation d'hôtel ===");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Rechercher une chambre");
            System.out.println("2. Réserver une chambre");
            System.out.println("3. Annuler une réservation");
            System.out.println("4. Voir une réservation");
            System.out.println("5. Quitter");
            System.out.print("Choix : ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> {
                    System.out.print("Catégorie (STANDARD, DELUXE, SUITE) : ");
                    RoomCategory cat = RoomCategory.valueOf(sc.nextLine().toUpperCase());
                    List<Room> rooms = hotel.searchAvailable(cat);
                    if (rooms.isEmpty()) {
                        System.out.println("Aucune chambre disponible.");
                    } else {
                        System.out.println("Chambres disponibles :");
                        for (Room r : rooms) {
                            System.out.println("Chambre " + r.roomNumber + " - " + r.price + "€");
                        }
                    }
                }

                case 2 -> {
                    System.out.print("Nom du client : ");
                    String name = sc.nextLine();
                    System.out.print("Numéro de chambre : ");
                    int num = sc.nextInt();
                    Room room = hotel.getRoomByNumber(num);
                    if (room == null || !room.isAvailable) {
                        System.out.println("Chambre non disponible.");
                        break;
                    }
                    System.out.print("Date d'arrivée (YYYY-MM-DD) : ");
                    LocalDate in = LocalDate.parse(sc.next());
                    System.out.print("Date de départ (YYYY-MM-DD) : ");
                    LocalDate out = LocalDate.parse(sc.next());
                    if (Payment.simulatePayment(room.price)) {
                        Reservation res = hotel.bookRoom(name, room, in, out);
                        res.paid = true;
                        System.out.println("Réservation confirmée !");
                    }
                    hotel.saveReservations();
                }

                case 3 -> {
                    System.out.print("Nom du client à annuler : ");
                    String cancelName = sc.nextLine();
                    boolean result = hotel.cancelReservation(cancelName);
                    if (result) {
                        hotel.saveReservations();
                        System.out.println("Réservation annulée.");
                    } else {
                        System.out.println("Réservation introuvable.");
                    }
                }

                case 4 -> {
                    System.out.print("Nom du client : ");
                    String name = sc.nextLine();
                    Reservation r = hotel.findReservation(name);
                    if (r != null) {
                        System.out.println("Réservation pour " + r.guestName);
                        System.out.println("Chambre : " + r.roomNumber);
                        System.out.println("Du " + r.checkInDate + " au " + r.checkOutDate);
                        System.out.println("Payée : " + (r.paid ? "Oui" : "Non"));
                    } else {
                        System.out.println("Aucune réservation trouvée.");
                    }
                }

                case 5 -> {
                    System.out.println("Au revoir !");
                    hotel.saveReservations();
                    return;
                }

                default -> System.out.println("Choix invalide.");
            }
        }
    }
}
