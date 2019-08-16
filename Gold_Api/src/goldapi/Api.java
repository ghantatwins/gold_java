package goldapi;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.csvreader.CsvReader;

import api.IApi;


public class Api implements IApi {
	
	static List<Gold> records;
	 
	private static String _file;

	public Api(String fileName)	{
		 _file=fileName;
	}
	 private void Setup()
	 {
		 if(records==null)
		 {
			 records=GetRecords();
		 }
	 }
	@Override
	public double GetPrice(int index) {
		
		Setup();
		int actualRow = index;
        if (actualRow == records.size())
        {
            actualRow = index-1;
        }
        if (actualRow < 0)
        {
            throw new IndexOutOfBoundsException("Index Can't be negative");
        }
        if (actualRow >= records.size())
        {
            throw new IndexOutOfBoundsException("Index Can't be more than "+(records.size()-1));
        }
        return records.get(actualRow).Price;
	}

	@Override
	public double GetPrice(Date date) {
		Setup();
		
		List<Gold> gold = records.stream()
                .filter(x -> date.equals(x.Date))
                .collect(Collectors.toList());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
        if (gold.isEmpty())
        {
            throw new IndexOutOfBoundsException("No Date " + dateFormat.format(date) + " has been found");
        }
        
        if (gold.size()>1)
        {
            throw new IndexOutOfBoundsException("Duplicate Dates " + dateFormat.format(date) + " has been found");
        }
        return gold.get(0).Price;
	}

	@Override
	public List<Date> GetDates() {
		Setup();
		return records.stream().map(x->x.Date).collect(Collectors.toList());
	}
	
	
	private static List<Gold> GetRecords()
    {
		List<Gold> gold=new ArrayList<Gold>();
try {
			
			CsvReader goldData = new CsvReader(_file,';');
		
//			products.readHeaders();

			while (goldData.readRecord())
			{
				var date= goldData.get(0);
				var price= goldData.get(1);
				try {
					Gold g= new Gold();
					g.Date=GetFormattedDate(date);
					g.Price =GetFormattedPrice(price);
					gold.add(g);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
			}
	
			goldData.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return gold;
    }
	private static double GetFormattedPrice(String string) throws ParseException {
		 NumberFormat format = NumberFormat.getInstance();
		  double number= format.parse(string).doubleValue();
		return number;
	}
	private static Date GetFormattedDate(String str) throws ParseException {
		 String[] arrOfStr = str.split("-");
		 String month = GetMonth(arrOfStr[0]);
		 NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
			Number number= format.parse(arrOfStr[1]);
		 int year = GetYear(number.intValue());
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		 Date date = dateFormat.parse(year+"/"+month+"/1");
		 return date;
	}
	private static int GetYear(int chkYear) {
		Date date=new Date(); 
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		if(2000+chkYear<=year) return 2000+chkYear;
		
		return 1900+chkYear;
	}
	private static String GetMonth(String string) {
		switch(string)
		{
		case "jan": return 01+"";
		case "feb": return 02+"";
		case "mar": return 03+"";
		case "apr": return 04+"";
		case "maj": return 05+"";
		case "jun": return 06+"";
		case "jul": return 07+"";
		case "aug": return "08";
		case "sep": return "09";
		case "okt": return 10+"";
		case "nov": return 11+"";
		case "dec": return 12+"";
		}
		return "";
	}

}
