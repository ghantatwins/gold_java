package api;
import java.util.Date;
import java.util.List;

public interface IApi {
	double GetPrice(int index);
    double GetPrice(Date date);
    List<Date> GetDates();
}
