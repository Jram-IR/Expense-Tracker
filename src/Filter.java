import java.sql.ResultSet;
import java.util.ArrayList;

public class Filter {
    private  ArrayList<ExpenseModel>expenseList = new ArrayList<>();
    private  ArrayList<ExpenseModel>filterList = new ArrayList<>();

    private  DbManager dbManager = new DbManager();

    public Filter()
    {
        try {
            ResultSet result = dbManager.displayData();
            while (result.next()) {

                ExpenseModel expModel = new ExpenseModel(
                        result.getString("exp_Type") ,
                        Float.parseFloat(result.getString("expenditure")) ,
                        result.getString("date"));
                expenseList.add(expModel);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("An error occurred in fetching stored data");
        }

    }



    public  ArrayList<ExpenseModel> search(String query) {

        String q = query.toLowerCase().trim();
        if(!query.equals(""))
        {
            for (int i = 0; i < expenseList.size(); i++) {
                ExpenseModel expModel = expenseList.get(i);
                if(expModel.getExpType().toLowerCase().trim().contains(q)
                        || String.valueOf(expModel.getExpenditure()).toLowerCase().trim().contains(q)
                        || expModel.getDate().toLowerCase().trim().contains(q))
                {
                    filterList.add(expModel);
                }
            }
            return  filterList;
        }
        else  return  expenseList;


    }


}
