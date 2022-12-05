import java.text.SimpleDateFormat;
import java.util.Date;
public class date {

    public date(){}

    public String getCurrentDate()
    {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
        return formatter.format(date);
    }
}


