public class ExpenseModel {
    String expType;
    float expenditure;
    String date;

    public ExpenseModel(){}
    public ExpenseModel(String expType, float expenditure, String date) {
        this.expType = expType;
        this.expenditure = expenditure;
        this.date = date;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public float getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(int expenditure) {
        this.expenditure = expenditure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}



