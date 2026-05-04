import java.io.*;
import java.util.*;

// =================== MODELS ===================
class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int age;
    private String contact;
    private String plan;

    public Member(int id, String name, int age, String contact, String plan) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.plan = plan;
    }

    public int getId() { return id; }

    public void display() {
        System.out.println("ID: " + id + ", Name: " + name +
                ", Age: " + age + ", Contact: " + contact +
                ", Plan: " + plan);
    }
}

class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private int memberId;
    private double amount;
    private String mode;

    public Payment(int memberId, double amount, String mode) {
        this.memberId = memberId;
        this.amount = amount;
        this.mode = mode;
    }

    public int getMemberId() { return memberId; }

    public void display() {
        System.out.println("Member ID: " + memberId +
                ", Amount: " + amount +
                ", Mode: " + mode);
    }
}

class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;

    private int memberId;
    private String date;

    public Attendance(int memberId, String date) {
        this.memberId = memberId;
        this.date = date;
    }

    public void display() {
        System.out.println("Member ID: " + memberId +
                ", Date: " + date);
    }
}

class Workout implements Serializable {
    private static final long serialVersionUID = 1L;

    private int memberId;
    private String schedule;

    public Workout(int memberId, String schedule) {
        this.memberId = memberId;
        this.schedule = schedule;
    }

    public int getMemberId() { return memberId; }

    public void display() {
        System.out.println("Workout Plan:\n" + schedule);
    }
}

// Wrapper to store everything together (including idCounter)
class GymData implements Serializable {
    private static final long serialVersionUID = 1L;

    ArrayList<Member> members = new ArrayList<>();
    ArrayList<Payment> payments = new ArrayList<>();
    ArrayList<Attendance> attendanceList = new ArrayList<>();
    ArrayList<Workout> workouts = new ArrayList<>();
    int idCounter = 1000;
}

// =================== MAIN APP ===================
public class GYM {

    static Scanner sc = new Scanner(System.in);
    static GymData data = new GymData();

    public static void main(String[] args) {
        loadData();

        while (true) {
            System.out.println("\n===== GYM MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Member (with Payment & Workout)");
            System.out.println("2. View Members");
            System.out.println("3. View Payments");
            System.out.println("4. Mark Attendance");
            System.out.println("5. View Attendance");
            System.out.println("6. Delete Member");
            System.out.println("7. View Workout Schedule");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            int choice = safeInt();

            switch (choice) {
                case 1: addMember(); break;
                case 2: viewMembers(); break;
                case 3: viewPayments(); break;
                case 4: markAttendance(); break;
                case 5: viewAttendance(); break;
                case 6: deleteMember(); break;
                case 7: viewWorkout(); break;
                case 0:
                    saveData();
                    System.out.println("Data saved. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // =================== STORAGE ===================
    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gym.dat"))) {
            oos.writeObject(data);
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    static void loadData() {
        File f = new File("gym.dat");
        if (!f.exists()) {
            System.out.println("No previous data found. Starting fresh.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            data = (GymData) ois.readObject();
            System.out.println("Data loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading data. Starting fresh.");
            data = new GymData();
        }
    }

    // =================== HELPERS ===================
    static int safeInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    // =================== MEMBER + PAYMENT + WORKOUT ===================
    static void addMember() {
        int id = data.idCounter++;
        System.out.println("Generated Member ID: " + id);

        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = safeInt();
        sc.nextLine();

        System.out.print("Enter Contact: ");
        String contact = sc.nextLine();

        // PLAN + PRICE
        System.out.println("\nChoose Subscription Plan:");
        System.out.println("1. 1 Month - 1000");
        System.out.println("2. 3 Months - 2500");
        System.out.println("3. 6 Months - 4500");
        System.out.println("4. 1 Year - 8500");

        int p = safeInt();
        sc.nextLine();

        String plan;
        double amount;

        switch (p) {
            case 1: plan = "1 Month"; amount = 1000; break;
            case 2: plan = "3 Months"; amount = 2500; break;
            case 3: plan = "6 Months"; amount = 4500; break;
            case 4: plan = "1 Year"; amount = 8500; break;
            default:
                System.out.println("Invalid plan!");
                return;
        }

        System.out.print("Enter Payment Mode (Cash/Card/Online): ");
        String mode = sc.nextLine();

        data.members.add(new Member(id, name, age, contact, plan));
        data.payments.add(new Payment(id, amount, mode));
        System.out.println("Payment of ₹" + amount + " recorded!");

        // WORKOUT
        System.out.println("\nChoose Workout Plan:");
        System.out.println("1. Your Own Workout");
        System.out.println("2. Our Workout");

        int w = safeInt();
        sc.nextLine();

        String schedule;

        if (w == 1) {
            String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
            StringBuilder sb = new StringBuilder();
            for (String day : days) {
                System.out.print(day + ": ");
                sb.append(day).append(" - ").append(sc.nextLine()).append("\n");
            }
            schedule = sb.toString();
        } else {
            schedule =
                "Monday - Chest + Tricep\n" +
                "Tuesday - Back + Bicep\n" +
                "Wednesday - Shoulders + Abs\n" +
                "Thursday - Bicep + Tricep\n" +
                "Friday - Legs\n" +
                "Saturday - Circuit\n" +
                "Sunday - Rest";
        }

        data.workouts.add(new Workout(id, schedule));

        saveData();
        System.out.println("Member added successfully!");
    }

    static void viewMembers() {
        if (data.members.isEmpty()) {
            System.out.println("No members found!");
            return;
        }
        for (Member m : data.members) {
            m.display();
            for (Workout w : data.workouts) {
                if (w.getMemberId() == m.getId()) {
                    w.display();
                }
            }
            System.out.println("----------------------");
        }
    }

    static void deleteMember() {
        System.out.print("Enter Member ID to delete: ");
        int id = safeInt();

        boolean removed = data.members.removeIf(m -> m.getId() == id);
        data.payments.removeIf(p -> p.getMemberId() == id);
        data.workouts.removeIf(w -> w.getMemberId() == id);

        saveData();

        if (removed) System.out.println("Member deleted!");
        else System.out.println("Member not found!");
    }

    // =================== PAYMENT ===================
    static void viewPayments() {
        if (data.payments.isEmpty()) {
            System.out.println("No payments found!");
            return;
        }
        for (Payment p : data.payments) p.display();
    }

    // =================== ATTENDANCE ===================
    static void markAttendance() {
        System.out.print("Enter Member ID: ");
        int id = safeInt();
        sc.nextLine();

        System.out.print("Enter Date (dd-mm-yyyy): ");
        String date = sc.nextLine();

        data.attendanceList.add(new Attendance(id, date));
        saveData();
        System.out.println("Attendance marked!");
    }

    static void viewAttendance() {
        if (data.attendanceList.isEmpty()) {
            System.out.println("No attendance records!");
            return;
        }
        for (Attendance a : data.attendanceList) a.display();
    }

    // =================== WORKOUT ===================
    static void viewWorkout() {
        if (data.workouts.isEmpty()) {
            System.out.println("No workout schedules found!");
            return;
        }
        for (Workout w : data.workouts) {
            System.out.println("Member ID: " + w.getMemberId());
            w.display();
            System.out.println("----------------------");
        }
    }
}