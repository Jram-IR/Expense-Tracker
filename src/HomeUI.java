import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HomeUI extends JFrame{
    private JPanel parentPanel,homePanel,dataPanel;
    private JButton btn_addExp,btn_review,btn_search,btn_back,btn_analyse,btn_clear,btn_deleteAll;

    private JTable expenseTable;
    private JTextField txt_search,txt_expType,txt_expense;

    private  ExpenseModel expenseModel;
    private static final ArrayList<ExpenseModel> expenseList = new ArrayList<>();
    private final DbManager dbManager = new DbManager();

    private Filter filter;

    public HomeUI()
    {
        setUpViews();

        JTableHeader tableHeader = expenseTable.getTableHeader();
        tableHeader.setFont(new Font("Courier",Font.BOLD,18));
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
                btn_clear.setVisible(true);
            }
        });

        btn_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetExpenseTable();
                btn_clear.setVisible(false);
            }
        });

        btn_analyse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyseExpense analyseExpense = new analyseExpense(expenseList);
                JOptionPane.showMessageDialog(null,"Total Expenditure is : "+analyseExpense.totalExp()
                +"\n"+"Average Expenditure : " + analyseExpense.avgExp(),"Analysis",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btn_deleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    int response = JOptionPane.showConfirmDialog(null,
                            "this action will clear all your current records",
                            "Clear All",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
                    if(response == JOptionPane.YES_OPTION) {
                        dbManager.deleteAll();
                        expenseList.clear();
                        addToExpenseTable();
                        expenseTable.repaint();

                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    System.out.println("Error deleting all records");
                }
            }
        });
    }

    private void search() {
        filter = new Filter();
        expenseList.clear();
        expenseList.addAll(filter.search(txt_search.getText()));
        if(expenseList.size() == 0)
        {
            JOptionPane.showMessageDialog(null, "Expense Not found", "Oops !", JOptionPane.ERROR_MESSAGE);
            resetExpenseTable();
        }
        addToExpenseTable();
        expenseTable.repaint();
    }

    private void resetExpenseTable() {

        txt_search.setText("");
        expenseList.clear();
        expenseList.addAll(filter.search(txt_search.getText()));
        addToExpenseTable();
        expenseTable.repaint();
    }

    private  void setUpViews()
    {
        add(parentPanel);
        btn_clear.setVisible(false);
        btn_addExp.setOpaque(false);

        parentPanel.removeAll();
        parentPanel.add(homePanel);
        parentPanel.repaint();
        parentPanel.revalidate();
        setTitle("Expense Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750,550);
        setLocationRelativeTo(null);
        setVisible(true);
        populateExpenseTable();
        wireNavigation();
    }

    private void populateExpenseTable() {
        try {
            ResultSet result = dbManager.displayData();
            while (result.next()) {
              ExpenseModel expModel = new ExpenseModel(
                      result.getString("exp_Type") ,
                      Float.parseFloat(result.getString("expenditure")) ,
                      result.getString("date"));
              expenseList.add(expModel);
              addToExpenseTable();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("An error occurred in fetching stored data");
        }
    }

    private void wireNavigation()

    {
        //forward navigation review
        btn_review.addActionListener(e -> {
            addToExpenseTable();
            txt_search.setText("");
            parentPanel.removeAll();
            parentPanel.add(dataPanel);
            parentPanel.repaint();
            parentPanel.revalidate();

        });


        // backward navigation
        btn_back.addActionListener(e -> {
            parentPanel.removeAll();
            parentPanel.add(homePanel);
            parentPanel.repaint();
            parentPanel.revalidate();
        });

        // forward navigation add expense
        addExpenseData();

    }

    public  void  addExpenseData()
    {
        btn_addExp.addActionListener(e -> {
            saveData();
            txt_expense.setText("");
            txt_expType.setText("");
        });

    }

    private void saveData() {
        try {
            date mdate = new date();
            String currentDate = mdate.getCurrentDate();
            if(!txt_expType.getText().equals("")&&!txt_expense.getText().equals("")) {
                expenseModel = new ExpenseModel(txt_expType.getText(), Float.parseFloat(txt_expense.getText()), currentDate);
                dbManager.saveAddedExpense(expenseModel);
                expenseList.add(expenseModel);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Please enter required data","Message",JOptionPane.ERROR_MESSAGE);
            }
            }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error Occurred in adding ");
        }
    }

    public void addToExpenseTable()
    {
        Object[][] data = new Object[1000][1000];
        for (int i = 0; i <expenseList.size() ; i++) {
            for (int j = 0; j < 3; j++) {

                data[i][j]=expenseList.get(i).getExpType();
                j++;
                data[i][j]=expenseList.get(i).getExpenditure();
                j++;
                data[i][j]=expenseList.get(i).getDate();
                j++;

            }
        }
        expenseTable.setModel(new DefaultTableModel(data,
                new String[] {"Expense Type","Expenditure","Date"}));

    }



}


