import java.util.ArrayList;

public class analyseExpense {

    private ArrayList<ExpenseModel> expenseList = new ArrayList<>();
     private float gross = 0 ;
    public analyseExpense(ArrayList<ExpenseModel> expenseList) {
        this.expenseList = expenseList;
    }

    public String totalExp()
    {

        for (int i = 0; i < expenseList.size(); i++) {
            gross = gross + expenseList.get(i).getExpenditure();
        }

        return String.valueOf(gross);
    }

    public String avgExp()
    {
        return String.valueOf(gross/expenseList.size());
    }
}
