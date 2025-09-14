import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame {

    // display for numbers and results
    private final JTextField screen;

    // not the cleanest way, but I'm keeping these as fields so I can reuse easily
    private double num1, num2, ans;
    private String currOp = "";

    public Calculator() {
        // Window basics
        setTitle("Simple Calculator"); // renamed for fun
        setSize(360, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // tried FlowLayout earlier, but BorderLayout gives more space control
        setLayout(new BorderLayout(10, 10));

        // main display (textfield at the top)
        screen = new JTextField();
        screen.setFont(new Font("Arial", Font.BOLD, 26));
        screen.setHorizontalAlignment(SwingConstants.RIGHT);
        screen.setEditable(false);  // don't want typing here, just button input
        add(screen, BorderLayout.NORTH);

        // making a grid panel for buttons (5 rows x 4 cols)
        JPanel gridPanel = new JPanel(new GridLayout(5, 4, 8, 8));

        // Number buttons 0-9
        JButton[] digits = new JButton[10];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = new JButton(String.valueOf(i));
            digits[i].setFont(new Font("Arial", Font.BOLD, 20));

            // each digit just adds itself to the textfield
            digits[i].addActionListener(e -> {
                JButton btn = (JButton) e.getSource();
                screen.setText(screen.getText() + btn.getText());
            });
        }

        // operator & function buttons
        JButton addBtn = new JButton("+");
        JButton minusBtn = new JButton("-");
        JButton timesBtn = new JButton("*");
        JButton divBtn = new JButton("/");
        JButton equalBtn = new JButton("=");
        JButton clearBtn = new JButton("C");
        JButton dotBtn = new JButton(".");
        JButton backBtn = new JButton("←");
        JButton modBtn = new JButton("%"); // acts as modulo (not percent really)

        JButton[] miscOps = { addBtn, minusBtn, timesBtn, divBtn, equalBtn, clearBtn, dotBtn, backBtn, modBtn };
        for (JButton b : miscOps) {
            b.setFont(new Font("Arial", Font.BOLD, 20));
        }

        // Handler for basic arithmetic ops (+ - * /)
        ActionListener simpleOpHandler = e -> {
            JButton btn = (JButton) e.getSource();
            try {
                num1 = Double.parseDouble(screen.getText());
            } catch (NumberFormatException ex) {
                num1 = 0; // fallback just in case
            }
            currOp = btn.getText(); // store symbol
            screen.setText("");     // clear display for second num
        };

        addBtn.addActionListener(simpleOpHandler);
        minusBtn.addActionListener(simpleOpHandler);
        timesBtn.addActionListener(simpleOpHandler);
        divBtn.addActionListener(simpleOpHandler);

        // Equals button logic
        equalBtn.addActionListener(e -> {
            try {
                num2 = Double.parseDouble(screen.getText());
            } catch (NumberFormatException ex) {
                num2 = 0;
            }

            switch (currOp) {
                case "+":
                    ans = num1 + num2;
                    break;
                case "-":
                    ans = num1 - num2;
                    break;
                case "*":
                    ans = num1 * num2;
                    break;
                case "/":
                    ans = (num2 == 0) ? Double.NaN : num1 / num2; // simple div-by-zero check
                    break;
                default:
                    ans = num2; // in case no operator was set
            }
            screen.setText(String.valueOf(ans));

            // carry result forward
            num1 = ans;
            currOp = "";
        });

        // Clear all button
        clearBtn.addActionListener(e -> {
            screen.setText("");
            num1 = num2 = ans = 0;
            currOp = "";
        });

        // Decimal point (avoiding multiple dots)
        dotBtn.addActionListener(e -> {
            if (!screen.getText().contains(".")) {
                screen.setText(screen.getText() + ".");
            }
        });

        // Backspace (just cuts last character)
        backBtn.addActionListener(e -> {
            String txt = screen.getText();
            if (txt.length() > 0) {
                screen.setText(txt.substring(0, txt.length() - 1));
            }
        });

        // Modulo operator (I keep calling this percent but it’s not really percent)
        modBtn.addActionListener(e -> {
            try{
                double current=Double.parseDouble(screen.getText());
                 double percentValue;
                 if(!currOp.isEmpty() && (currOp.equals("+")||currOp.equals("-"))){
                     percentValue=(num1*current)/100.0;
                 }
                 else{
                     percentValue=current/100.0;
                 }
                 screen.setText(String.valueOf(percentValue));
            } catch (NumberFormatException ex){

            }
        });

        // Layout in grid (not the cleanest way, but works fine)
        gridPanel.add(clearBtn); gridPanel.add(backBtn); gridPanel.add(modBtn); gridPanel.add(divBtn);
        gridPanel.add(digits[7]); gridPanel.add(digits[8]); gridPanel.add(digits[9]); gridPanel.add(timesBtn);
        gridPanel.add(digits[4]); gridPanel.add(digits[5]); gridPanel.add(digits[6]); gridPanel.add(minusBtn);
        gridPanel.add(digits[1]); gridPanel.add(digits[2]); gridPanel.add(digits[3]); gridPanel.add(addBtn);
        gridPanel.add(digits[0]); gridPanel.add(dotBtn); gridPanel.add(equalBtn);
        // note: grid is 5x4, last cell is left empty → fine for now

        add(gridPanel, BorderLayout.CENTER);

        // give operators a background color so they pop a bit
        Color lightBlue = new Color(200, 200, 255);
        JButton[] highlightOps = { addBtn, minusBtn, timesBtn, divBtn, equalBtn };
        for (JButton b : highlightOps) {
            b.setBackground(lightBlue);
            b.setOpaque(true);
            b.setBorderPainted(false);
        }

        // finally, show it
        setVisible(true);
    }

    public static void main(String[] args) {
        // launching the calculator
        new Calculator();
    }
}
