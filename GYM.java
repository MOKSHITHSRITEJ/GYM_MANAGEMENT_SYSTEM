import java.util.*;
class Member {
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
    public String getName() { return name; }
    public void display() {
        System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age +
                ", Contact: " + contact + ", Plan: " + plan);
    }
}
class Payment {
    private int memberId;
    private double amount;
    private String mode;
    public Payment(int memberId, double amount, String mode) {
        this.memberId = memberId;
        this.amount = amount;
        this.mode = mode;
    }
    public void display() {
        System.out.println("Member ID: " + memberId + ", Amount: " + amount + ", Mode: " + mode);
    }
}
class Attendance {
    private int memberId;
    private String date;
    public Attendance(int memberId, String date) {
        this.memberId = memberId;
        this.date = date;
    }
    public void display() {
        System.out.println("Member ID: " + memberId + ", Date: " + date);
    }
}
public class GYM {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Member> members = new ArrayList<>();
    static ArrayList<Payment> payments = new ArrayList<>();
    static ArrayList<Attendance> attendanceList = new ArrayList<>();
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== GYM MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Member");
            System.out.println("2. View Members");
            System.out.println("3. Record Payment");
            System.out.println("4. View Payments");
            System.out.println("5. Mark Attendance");
            System.out.println("6. View Attendance");
            System.out.println("7. Delete Member");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: addMember(); 
                        break;
                case 2: viewMembers(); 
                        break;
                case 3: addPayment();
                        break;
                case 4: viewPayments(); 
                        break;
                case 5: markAttendance(); 
                        break;
                case 6: viewAttendance(); 
                        break;
                case 7: deleteMember(); 
                        break;
                case 0:
                    System.out.println("Exiting");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    static void addMember() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Conact: ");
        String contact = sc.nextLine();
        System.out.print("Enter Plan (Monthly/Yearly): ");
        String plan = sc.nextLine();
        members.add(new Member(id, name, age, contact, plan));
        System.out.println("Member added successfully!");
    }
    static void viewMembers() {
        if (members.isEmpty()) {
            System.out.println("No members found!");
            return;
        }
        for (Member m : members) {
            m.display();
        }
    }
    static void addPayment() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Mode (Cash/Card/Online): ");
        String mode = sc.nextLine();
        payments.add(new Payment(id, amount, mode));
        System.out.println("Payment recorded!");
    }
    static void viewPayments() {
        if (payments.isEmpty()) {
            System.out.println("No payments found!");
            return;
        }
        for (Payment p : payments) {
            p.display();
        }
    }
    static void markAttendance() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Date (dd-mm-yyyy): ");
        String date = sc.nextLine();
        attendanceList.add(new Attendance(id, date));
        System.out.println("Attendance marked!");
    }
    static void viewAttendance() {
        if (attendanceList.isEmpty()) {
            System.out.println("No attendance records!");
            return;
        }
        for (Attendance a : attendanceList) {
            a.display();
        }
    }
    static void deleteMember() {
        System.out.print("Enter Member ID to delete: ");
        int id = sc.nextInt();
        members.removeIf(m -> m.getId() == id);
        System.out.println("Member deleted (if existed).");
    }
}