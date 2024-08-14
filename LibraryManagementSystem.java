import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryManagementSystem extends JFrame {
    private Library library;

    private JTextArea displayArea;
    private JTextField idField, titleField, authorField;

    public LibraryManagementSystem() {
        library = new Library();

        // Set up the main frame
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Book ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        JButton addButton = new JButton("Add Book");
        inputPanel.add(addButton);

        JButton viewButton = new JButton("View Available Books");
        inputPanel.add(viewButton);

        JButton borrowButton = new JButton("Borrow Book");
        inputPanel.add(borrowButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String title = titleField.getText();
                String author = authorField.getText();
                if (!id.isEmpty() && !title.isEmpty() && !author.isEmpty()) {
                    Book book = new Book(id, title, author);
                    library.addBook(book);
                    displayArea.append("Added: " + book + "\n");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("Available Books:\n");
                for (Book book : library.getAvailableBooks()) {
                    displayArea.append(book + "\n");
                }
            }
        });

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                if (!id.isEmpty()) {
                    Book book = library.findBookById(id);
                    if (book != null && book.isAvailable()) {
                        book.borrow();
                        displayArea.append("Borrowed: " + book + "\n");
                    } else if (book != null) {
                        JOptionPane.showMessageDialog(null, "Book is already borrowed.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter the Book ID.");
                }
            }
        });
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryManagementSystem().setVisible(true);
            }
        });
    }
}
