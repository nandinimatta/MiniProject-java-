package projects;
import java.io.*;
import java.util.Scanner;
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    int marks;
    public Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Marks: " + marks;
    }
}
class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    Student data;
    Node next;
    public Node(Student data) {
        this.data = data;
        this.next = null;
    }
}
class StudentLinkedList implements Serializable {
    private static final long serialVersionUID = 1L;
    private Node head;
    private int size;
    public StudentLinkedList() {
        head = null;
        size = 0;
    }
    public boolean insert(Student student) {
        if (searchById(student.id) != null) {
            System.out.println("Student with ID " + student.id + " already exists.");
            return false;
        }
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        size++;
        return true;
    }
    public boolean deleteById(int id) {
        if (head == null) return false;
        if (head.data.id == id) {
            head = head.next;
            size--;
            return true;
        }
        Node prev = head;
        Node curr = head.next;
        while (curr != null) {
            if (curr.data.id == id) {
                prev.next = curr.next;
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }
    public Student searchById(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.id == id) {
                return temp.data;
            }
            temp = temp.next;
        }
        return null;
    }
    public Student[] toArray() {
        Student[] arr = new Student[size];
        Node temp = head;
        int i = 0;
        while (temp != null) {
            arr[i++] = temp.data;
            temp = temp.next;
        }
        return arr;
    }
    public void displayAll() {
        if (head == null) {
            System.out.println("No students in the system.");
            return;
        }
        Node temp = head;
        System.out.println("\n===== All Students =====");
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
        System.out.println("=======================\n");
    }
    public int getSize() {
        return size;
    }
}  
public class SmartStuManagementSYS {
	private StudentLinkedList list;
    private static final String FILE_NAME = "students.ser";

    public SmartStuManagementSYS() {
        list = new StudentLinkedList();
        loadFromFile();
    }
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(list);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No existing data file. Starting fresh.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            list = (StudentLinkedList) ois.readObject();
            System.out.println("Data loaded successfully. " + list.getSize() + " students found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    public void insertStudent(int id, String name, int marks) {
        Student s = new Student(id, name, marks);
        if (list.insert(s)) {
            System.out.println("Student added successfully.");
            saveToFile();
        }
    }
    public void deleteStudent(int id) {
        if (list.deleteById(id)) {
            System.out.println("Student with ID " + id + " deleted successfully.");
            saveToFile();
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }
    public void searchById(int id) {
        Student s = list.searchById(id);
        if (s != null) {
            System.out.println("Student found: " + s);
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }
    public void sortAndDisplayByMarks() {
        if (list.getSize() == 0) {
            System.out.println("No students in the system.");
            return;
        }
        Student[] students = list.toArray();
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - i - 1; j++) {
                if (students[j].marks < students[j + 1].marks) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
        System.out.println("\n===== Students Sorted by Marks (Highest first) =====");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("==================================================\n");
    }
    public void displayAll() {
        list.displayAll();
    }
    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Smart Student Management System (Linked List) =====");
            System.out.println("1. Insert Student");
            System.out.println("2. Delete Student (by ID)");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Sort and Display Students by Marks");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Enter Student ID: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter Marks: ");
                    int marks = Integer.parseInt(scanner.nextLine().trim());
                    insertStudent(id, name, marks);
                    break;
                case "2":
                    System.out.print("Enter Student ID to delete: ");
                    int delId = Integer.parseInt(scanner.nextLine().trim());
                    deleteStudent(delId);
                    break;
                case "3":
                    System.out.print("Enter Student ID to search: ");
                    int searchId = Integer.parseInt(scanner.nextLine().trim());
                    searchById(searchId);
                    break;
                case "4":
                    sortAndDisplayByMarks();
                    break;
                case "5":
                    displayAll();
                    break;
                case "6":
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-6.");
            }
        }
    }
	public static void main(String[] args) {
		SmartStuManagementSYS system = new SmartStuManagementSYS();
        system.runMenu();
	}
}