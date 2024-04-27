import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class AgeCalculator extends JFrame {
    private JTextField birthDateField;
    private JButton calculateButton;
    private JLabel ageLabel;
    private JLabel feedbackLabel; // Label for real-time feedback

    public AgeCalculator() {
        setTitle("Age Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Enter your birthdate (yyyy-mm-dd):"));
        birthDateField = new JTextField(10);
        panel.add(birthDateField);

        feedbackLabel = new JLabel(" ");
        panel.add(feedbackLabel);

        calculateButton = new JButton("Calculate Age");
        panel.add(calculateButton);

        ageLabel = new JLabel("<html><body style='width: 200px;'>Your age will be shown here</body></html>");
        panel.add(ageLabel);

        add(panel, BorderLayout.CENTER);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAge();
                pack(); // Repack the frame to adjust to new content size
            }
        });

        birthDateField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateDate(); }
            public void removeUpdate(DocumentEvent e) { validateDate(); }
            public void changedUpdate(DocumentEvent e) { validateDate(); }

            private void validateDate() {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate.parse(birthDateField.getText(), formatter);
                    feedbackLabel.setText("Valid date format!");
                    feedbackLabel.setForeground(new Color(0, 128, 0)); // Set text color to green
                } catch (DateTimeParseException e) {
                    feedbackLabel.setText("<html><body style='width: 200px;'>Invalid date format. Use yyyy-mm-dd.</body></html>");
                    feedbackLabel.setForeground(Color.RED); // Set text color to red
                }
            }
        });

        birthDateField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculateAge();
                    pack(); // Repack the frame to adjust to new content size
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void calculateAge() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthDateField.getText(), formatter);
            LocalDate currentDate = LocalDate.now();
            long age = ChronoUnit.YEARS.between(birthDate, currentDate);
            ageLabel.setText("<html><body style='width: 200px;'>You are " + age + " years old.</body></html>");
        } catch (DateTimeParseException e) {
            ageLabel.setText("<html><body style='width: 200px;'>Invalid date format. Please use yyyy-mm-dd.</body></html>");
        }
    }

    public static void main(String[] args) {
        new AgeCalculator();
    }
}
