import java.sql.*;

public class DbManager {

    private static Connection con;
    private static boolean hasData = false;


    public ResultSet displayData() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement statement = con.createStatement();
        return statement.executeQuery("SELECT * FROM expense");
    }

    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:Expense.db");
        initialize();
    }

    private void initialize() throws SQLException {
        if (!hasData) {
            hasData = true;
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name='expense'");
            if (!resultSet.next()) {
                System.out.println("Building the expense table ");
                Statement statement1 = con.createStatement();
                String sql = "CREATE TABLE expense " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " exp_Type TEXT NOT NULL, " +
                        " expenditure FLOAT, " +
                        " date TEXT NOT NULL) " ;
                statement1.execute(sql);



            }

        }

    }

    public void saveAddedExpense(ExpenseModel exp) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("INSERT INTO expense values(?,?,?,?);");
        prep.setString(2, exp.getExpType());
        prep.setFloat(3, exp.getExpenditure());
        prep.setString(4,exp.getDate());
        prep.execute();
    }


    public void deleteAll() throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("DELETE  FROM expense");
        prep.execute();
        System.out.println("DELETED");
    }
}