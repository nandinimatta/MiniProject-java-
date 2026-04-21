package projects;
import java.io.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String title;
    String author;
    boolean isIssued;
    String issuedTo;
    String issueDate;
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
        this.issuedTo = null;
        this.issueDate = null;
    }
    @Override
    public String toString() {
        String status = isIssued ? "Issued to " + issuedTo + " on " + issueDate : "Available";
        return "ID: " + id + " | " + title + " by " + author + " | " + status;
    }
}    
public class LibraryManagementSYS {
	private static final int MAX_BOOKS = 100;
    private Book[] books;         
    private int bookCount;        
    private static final String FILE_NAME = "books.dat";
    public LibraryManagementSYS() {
        books = new Book[MAX_BOOKS];
        bookCount = 0;
        loadFromFile();           
    }
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            Book[] savedBooks = new Book[bookCount];
            System.arraycopy(books, 0, savedBooks, 0, bookCount);
            oos.writeObject(savedBooks);
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
            Book[] loadedBooks = (Book[]) ois.readObject();
            bookCount = loadedBooks.length;
            System.arraycopy(loadedBooks, 0, books, 0, bookCount);
            System.out.println("Data loaded successfully. " + bookCount + " books found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    public void addBook(int id, String title, String author) {
        if (bookCount >= MAX_BOOKS) {
            System.out.println("Library is full! Cannot add more books.");
            return;
        }
        for (int i = 0; i < bookCount; i++) {
            if (books[i].id == id) {
                System.out.println("A book with ID " + id + " already exists.");
                return;
            }
        }
        books[bookCount] = new Book(id, title, author);
        bookCount++;
        System.out.println("Book added successfully.");
        saveToFile();  
    }
    public void issueBook(int id, String studentName) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].id == id) {
                if (books[i].isIssued) {
                    System.out.println("Book is already issued to " + books[i].issuedTo);
                    return;
                }
                books[i].isIssued = true;
                books[i].issuedTo = studentName;
                books[i].issueDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
                System.out.println("Book issued to " + studentName + " on " + books[i].issueDate);
                saveToFile();
                return;
            }
        }
        System.out.println("Book with ID " + id + " not found.");
    }
    public void returnBook(int id) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].id == id) {
                if (!books[i].isIssued) {
                    System.out.println("Book is not issued. Cannot return.");
                    return;
                }
                System.out.println("Book returned by " + books[i].issuedTo);
                books[i].isIssued = false;
                books[i].issuedTo = null;
                books[i].issueDate = null;
                saveToFile();
                return;
            }
        }
        System.out.println("Book with ID " + id + " not found.");
    }
    public void displayAllBooks() {
        if (bookCount == 0) {
            System.out.println("No books in the library.");
            return;
        }
        System.out.println("\n===== Library Catalog =====");
        for (int i = 0; i < bookCount; i++) {
            System.out.println(books[i]);
        }
        System.out.println("==========================\n");
    }
    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Display All Books");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Enter Book ID: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine().trim();
                    addBook(id, title, author);
                    break;
                case "2":
                    System.out.print("Enter Book ID to issue: ");
                    int issueId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter Student Name: ");
                    String student = scanner.nextLine().trim();
                    issueBook(issueId, student);
                    break;
                case "3":
                    System.out.print("Enter Book ID to return: ");
                    int returnId = Integer.parseInt(scanner.nextLine().trim());
                    returnBook(returnId);
                    break;
                case "4":
                    displayAllBooks();
                    break;
                case "5":
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-5.");
            }
        }
    }
	public static void main(String[] args) {
		LibraryManagementSYS  library = new LibraryManagementSYS();
        library.runMenu();
	}
}