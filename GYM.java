import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

// ================= INTERFACE =================
interface Displayable {
    void display();
}

// ================= ABSTRACT CLASS =================
abstract class Person implements Serializable, Displayable {
    protected int id;
    protected String name;
    protected int age;
    protected String contact;

    public Person(int id, String name, int age, String contact) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.contact = contact;
    }

    public int getId() { return id; }

    public abstract void display();
}

// ================= MEMBER =================
class Member extends Person {
    private String plan;
    private LocalDate startDate;
    private int durationDays;

    public Member(int id, String name, int age, String contact, String plan, int durationDays) {
        super(id, name, age, contact);
        this.plan = plan;
        this.durationDays = durationDays;
        this.startDate = LocalDate.now();
    }

    public long getDaysLeft() {
        LocalDate expiry = startDate.plusDays(durationDays);
        return ChronoUnit.DAYS.between(LocalDate.now(), expiry);
    }

    @Override
    public void display() {
        long days = getDaysLeft();
        String status = (days > 0) ? days + " days left" : "Expired";

        System.out.println("ID: " + id + ", Name: " + name +
                ", Plan: " + plan +
                ", Membership: " + status);
    }
}

// ================= PAYMENT =================
class Payment implements Serializable, Displayable {
    private int memberId;
    private double amount;
    private String mode;

    public Payment(int memberId, double amount, String mode) {
        this.memberId = memberId;
        this.amount = amount;
        this.mode = mode;
    }

    public int getMemberId() { return memberId; }

    @Override
    public void display() {
        System.out.println("Member ID: " + memberId +
                ", Amount: ₹" + amount +
                ", Mode: " + mode);
    }
}

// ================= ATTENDANCE =================
class Attendance implements Serializable, Displayable {
    private int memberId;
    private String date;

    public Attendance(int memberId, String date) {
        this.memberId = memberId;
        this.date = date;
    }

    @Override
    public void display() {
        System.out.println("Member ID: " + memberId + ", Date: " + date);
    }
}

// ================= WORKOUT =================
class Workout implements Serializable, Displayable {
    private int memberId;
    private String schedule;

    public Workout(int memberId, String schedule) {
        this.memberId = memberId;
        this.schedule = schedule;
    }

    public int getMemberId() { return memberId; }

    @Override
    public void display() {
        System.out.println("Workout Plan:\n" + schedule);
    }
}

// ================= STORAGE =================
class GymData implements Serializable {
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Payment> payments = new ArrayList<>();
    private ArrayList<Attendance> attendanceList = new ArrayList<>();
    private ArrayList<Workout> workouts = new ArrayList<>();
    private int idCounter = 1000;

    // Getters (Encapsulation)
    public ArrayList<Member> getMembers() { return members; }
    public ArrayList<Payment> getPayments() { return payments; }
    public ArrayList<Attendance> getAttendanceList() { return attendanceList; }
    public ArrayList<Workout> getWorkouts() { return workouts; }

    public int generateId() { return idCounter++; }
}

// ================= MAIN =================
public class GYM {

    static Scanner sc = new Scanner(System.in);
    static GymData data = new GymData();

    public static void main(String[] args) {
        loadData();

        while (true) {
            System.out.println("\n===== GYM MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Member");
            System.out.println("2. View Members");
            System.out.println("3. View Payments");
            System.out.println("4. Mark Attendance");
            System.out.println("5. View Attendance");
            System.out.println("6. Delete Member");
            System.out.println("7. View Workout");
            System.out.println("0. Exit");

            int ch = sc.nextInt();

            switch (ch) {
                case 1: addMember(); break;
                case 2: viewList(data.getMembers()); break;
                case 3: viewList(data.getPayments()); break;
                case 4: markAttendance(); break;
                case 5: viewList(data.getAttendanceList()); break;
                case 6: deleteMember(); break;
                case 7: viewList(data.getWorkouts()); break;
                case 0:
                    saveData();
                    System.out.println("Saved & Exiting...");
                    return;
            }
        }
    }

    // ================= GENERIC POLYMORPHISM =================
    static <T extends Displayable> void viewList(ArrayList<T> list) {
        for (T item : list) {
            item.display();
            System.out.println("---------------");
        }
    }

    // ================= FILE =================
    static void saveData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gym.dat"));
            oos.writeObject(data);
            oos.close();
        } catch (Exception e) {
            System.out.println("Save error");
        }
    }

    static void loadData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("gym.dat"));
            data = (GymData) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Fresh start");
        }
    }

    // ================= ADD MEMBER =================
    static void addMember() {
        int id = data.generateId();
        System.out.println("Generated ID: " + id);

        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Age: ");
        int age = sc.nextInt(); sc.nextLine();

        System.out.print("Contact: ");
        String contact = sc.nextLine();

        System.out.println("\n1. 1 Month (1000)");
        System.out.println("2. 3 Months (2500)");
        System.out.println("3. 6 Months (4500)");
        System.out.println("4. 1 Year (8500)");

        int p = sc.nextInt(); sc.nextLine();

        String plan = "";
        double amount = 0;
        int duration = 0;

        switch (p) {
            case 1: plan="1 Month"; amount=1000; duration=30; break;
            case 2: plan="3 Months"; amount=2500; duration=90; break;
            case 3: plan="6 Months"; amount=4500; duration=180; break;
            case 4: plan="1 Year"; amount=8500; duration=365; break;
            default: System.out.println("Invalid"); return;
        }

        System.out.print("Payment Mode: ");
        String mode = sc.nextLine();

        data.getMembers().add(new Member(id,name,age,contact,plan,duration));
        data.getPayments().add(new Payment(id,amount,mode));

        System.out.println("\n1. Custom Workout\n2. Default Workout");
        int w = sc.nextInt(); sc.nextLine();

        String schedule="";
        if(w==1){
            String[] days={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
            for(String d:days){
                System.out.print(d+": ");
                schedule+=d+" - "+sc.nextLine()+"\n";
            }
        } else {
            schedule="Mon-Chest\nTue-Back\nWed-Shoulders\nThu-Arms\nFri-Legs\nSat-Circuit\nSun-Rest";
        }

        data.getWorkouts().add(new Workout(id,schedule));

        saveData();
        System.out.println("Member added!");
    }

    // ================= ATTENDANCE =================
    static void markAttendance() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt(); sc.nextLine();

        Member found = null;
        for(Member m : data.getMembers()){
            if(m.getId() == id){
                found = m;
                break;
            }
        }

        if(found == null){
            System.out.println("Member not found");
            return;
        }

        long days = found.getDaysLeft();

        if(days <= 0)
            System.out.println("❌ Expired");
        else
            System.out.println("✅ " + days + " days left");

        System.out.print("Date: ");
        String date = sc.nextLine();

        data.getAttendanceList().add(new Attendance(id,date));
        saveData();

        System.out.println("Attendance done");
    }

    // ================= DELETE =================
    static void deleteMember() {
        int id = sc.nextInt();

        data.getMembers().removeIf(m -> m.getId() == id);
        data.getPayments().removeIf(p -> p.getMemberId() == id);
        data.getWorkouts().removeIf(w -> w.getMemberId() == id);

        saveData();
        System.out.println("Deleted");
    }
}