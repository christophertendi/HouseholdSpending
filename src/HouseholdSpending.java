//imports
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//methods used
public class HouseholdSpending {
    private JTextField tfweek1;
    private JTextField tfweek3;
    private JTextField tfweek4;
    private JTextField tftotal;
    private JTextField tfavg;
    private JButton calculateButton;
    private JTextField tfweek2;
    private JPanel main;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JComboBox<String> comboBox1;
    private JButton loadButton;

    public static void main(String[] args) {
        SpendingDatabase.init();
        JFrame frame = new JFrame("HouseholdSpending");
        frame.setContentPane(new HouseholdSpending().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    //main function of program
    public HouseholdSpending() {
        comboBox1.addItem("January");
        comboBox1.addItem("February");
        comboBox1.addItem("March");
        comboBox1.addItem("April");
        comboBox1.addItem("May");
        comboBox1.addItem("June");
        comboBox1.addItem("July");
        comboBox1.addItem("August");
        comboBox1.addItem("September");
        comboBox1.addItem("October");
        comboBox1.addItem("November");
        comboBox1.addItem("December");

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //variables used in the program
                double week1,week2,week3,week4,total;
                double average;

                //getting the user input and incorporating it into the formula
                week1 = Double.parseDouble(tfweek1.getText());
                week2 = Double.parseDouble(tfweek2.getText());
                week3 = Double.parseDouble(tfweek3.getText());
                week4 = Double.parseDouble(tfweek4.getText());
                //formula to find total spending of the 4 weeks
                total = week1 + week2 + week3 + week4;
                tftotal.setText(String.valueOf(total));
                //formula to find the average spending of the 4 weeks
                average = total/4;
                tfavg.setText(String.valueOf(average));

            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                double week1,week2,week3,week4;
                String month = (String) comboBox1.getSelectedItem();
                //getting user input to fill database
                week1 = Double.parseDouble(tfweek1.getText());
                week2 = Double.parseDouble(tfweek2.getText());
                week3 = Double.parseDouble(tfweek3.getText());
                week4 = Double.parseDouble(tfweek4.getText());
                SpendingDatabase.storeMonthData(month, week1, week2, week3, week4);

            }
        });
        loadButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String month = (String) comboBox1.getSelectedItem();
                    Connection conn = SpendingDatabase.getDatabase();
                    String spend = "SELECT * FROM spending_table WHERE month=?";
                    PreparedStatement ps = conn.prepareStatement(spend);
                    ps.setString(1, month);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    tfweek1.setText(String.valueOf(rs.getDouble("week1")));
                    tfweek2.setText(String.valueOf(rs.getDouble("week2")));
                    tfweek3.setText(String.valueOf(rs.getDouble("week3")));
                    tfweek4.setText(String.valueOf(rs.getDouble("week4")));

                }catch(SQLException ee)
                {
                    System.out.println(ee.getMessage());

                }

            }
        });
    }
}
