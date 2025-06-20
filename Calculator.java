import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    private final JTextField display;
    private String currentNumber = "";
    private double storedValue = 0;
    private String pendingOp = "=";   // last operation pressed

    public Calculator() {
        super("Swing Calculator");

        // Display field
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(display.getFont().deriveFont(24f));

        // Panel for buttons
        JPanel buttons = new JPanel(new GridLayout(5, 4, 6, 5));

        String[] keys = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };

        for (String key : keys) {
            addButton(buttons, key);
        }

        // Layout
        setLayout(new BorderLayout(5, 5));
        add(display, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);

        // Window settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /** Utility to add a button with an action listener */
    private void addButton(JPanel panel, String key) {
        JButton btn = new JButton(key);
        btn.setFont(btn.getFont().deriveFont(18f));
        btn.addActionListener(this);
        panel.add(btn);
    }

    /** Core event handler */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789.".contains(cmd)) {
            // Build the current number string
            if (cmd.equals(".") && currentNumber.contains(".")) return;
            currentNumber += cmd;
            display.setText(currentNumber);
        } else if ("+-*/".contains(cmd)) {
            computePending();
            pendingOp = cmd;
        } else if ("=".equals(cmd)) {
            computePending();
            pendingOp = "=";
        } else if ("C".equals(cmd)) {
            currentNumber = "";
            storedValue = 0;
            pendingOp = "=";
            display.setText("0");
        }
    }

    /** Compute any pending arithmetic operation */
    private void computePending() {
        double num = currentNumber.isEmpty() ? 0 : Double.parseDouble(currentNumber);

        switch (pendingOp) {
            case "+": storedValue += num; break;
            case "-": storedValue -= num; break;
            case "*": storedValue *= num; break;
            case "/": storedValue = num == 0 ? 0 : storedValue / num; break;
            case "=": storedValue = num; break;
        }

        display.setText(Double.toString(storedValue));
        currentNumber = ""; // reset for next entry
    }

    public static void main(String[] args) {
        // Ensure GUI uses native OS styling where possible
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(Calculator::new);
    }
}
