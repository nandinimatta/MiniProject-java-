package projects;
import java.util.*;
import java.io.*;
class TextEditor {
    private StringBuilder currentText;
    private Deque<String> undoStack;
    private Deque<String> redoStack;
    public TextEditor() {
        currentText = new StringBuilder();
        undoStack = new ArrayDeque<>();
        redoStack = new ArrayDeque<>();
    }
    private void saveForUndo() {
        undoStack.push(currentText.toString());
        redoStack.clear(); 
    }
    public void append(String text) {
        saveForUndo();
        currentText.append(text);
        System.out.println("Appended: \"" + text + "\"");
    }
    public void setText(String newText) {
        saveForUndo();
        currentText = new StringBuilder(newText);
        System.out.println("Text replaced.");
    }
    public boolean undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return false;
        }
        redoStack.push(currentText.toString());
        String previous = undoStack.pop();
        currentText = new StringBuilder(previous);
        System.out.println("Undo successful.");
        return true;
    }
    public boolean redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return false;
        }
        undoStack.push(currentText.toString());
        String next = redoStack.pop();
        currentText = new StringBuilder(next);
        System.out.println("Redo successful.");
        return true;
    }
    public void show() {
        System.out.println("\n--- Current Text ---");
        System.out.println(currentText.length() == 0 ? "(empty)" : currentText.toString());
        System.out.println("--------------------\n");
    }
    public String getText() {
        return currentText.toString();
    }
}
public class UnRedoTextEditor {
	private static final String SAVE_FILE = "texteditor.sav";
	public static void main(String[] args) {
		TextEditor editor = new TextEditor();
        Scanner scanner = new Scanner(System.in);
        loadFromFile(editor);
        while (true) {
            System.out.println("\n===== Text Editor with Undo/Redo =====");
            System.out.println("1. Append text");
            System.out.println("2. Replace full text");
            System.out.println("3. Undo");
            System.out.println("4. Redo");
            System.out.println("5. Show current text");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice (1-6): ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Enter text to append: ");
                    String toAppend = scanner.nextLine();
                    editor.append(toAppend);
                    break;
                case "2":
                    System.out.print("Enter new full text: ");
                    String newText = scanner.nextLine();
                    editor.setText(newText);
                    break;
                case "3":
                    editor.undo();
                    break;
                case "4":
                    editor.redo();
                    break;
                case "5":
                    editor.show();
                    break;
                case "6":
                    saveToFile(editor);
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-6.");
            }
        }
    }
    private static void saveToFile(TextEditor editor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(editor.getText());
            System.out.println("Text saved to " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
    private static void loadFromFile(TextEditor editor) {
        File file = new File(SAVE_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            if (content.length() > 0) {
                if (content.charAt(content.length() - 1) == '\n')
                    content.setLength(content.length() - 1);
                editor.setText(content.toString());
                System.out.println("Previous session loaded.");
            }
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
	}
}