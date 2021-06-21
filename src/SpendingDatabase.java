import java.sql.*;

public class SpendingDatabase
{
    public static void init ()
    {
        try
        {   //making sql table to store data
            Connection conn = DriverManager.getConnection("jdbc:sqlite:spending.db");
            String createTable = "CREATE TABLE IF NOT EXISTS spending_table(month VARCHAR(15) PRIMARY KEY, week1 DOUBLE, week2 DOUBLE, week3 DOUBLE, week4 DOUBLE)";
            conn.createStatement().execute(createTable);
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static Connection getDatabase ()
    {
        try
        {
            return DriverManager.getConnection("jdbc:sqlite:spending.db");
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

    }
    public static void storeMonthData (String month, Double week1,Double week2,Double week3, Double week4)
    {
        try
        {
            Connection conn = getDatabase();
            Statement stmt = conn.createStatement();
            //inserting data into the table using sql code
            String insertData = "INSERT INTO spending_table (month, week1, week2, week3, week4) VALUES(?, ?, ?, ?, ?) ON CONFLICT(month) DO UPDATE SET week1=?, week2=?, week3=?, week4=?";
            PreparedStatement ps = conn.prepareStatement(insertData);
            ps.setString(1, month);
            ps.setDouble(2, week1);
            ps.setDouble(3, week2);
            ps.setDouble(4, week3);
            ps.setDouble(5, week4);
            ps.setDouble(6, week1);
            ps.setDouble(7, week2);
            ps.setDouble(8, week3);
            ps.setDouble(9, week4);
            ps.executeUpdate();

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
