package buysell;

import java.util.*;

import api.*;

public class ProfitMaker {

	public double GetProfitValue(IApi api) {
		List<Date> dates= api.GetDates();
		 List<Double> buyHeap=new ArrayList<Double>();
         List<Double> sellHeap= new ArrayList<Double>();
         
         for(Date date : dates)
         {
        	 var price = api.GetPrice(date);
        	 if (buyHeap.size() == 0)
             {
                 buyHeap.add(price);
             }
        	 else if (price > buyHeap.get(buyHeap.size()-1))
             {
                 if (sellHeap.size() < buyHeap.size())
                 {
                     sellHeap.add(price);
                 }
                 else if (sellHeap.size() == buyHeap.size())
                 {
                     var root = sellHeap.get(sellHeap.size()-1);
                     if (root < price)
                     {
                         sellHeap.remove(sellHeap.size() - 1);
                         buyHeap.add(root);
                     }

                     else if (buyHeap.size() < (dates.size() / 2))
                     {
                         sellHeap.add(price);
                     }
                 }
                 
             }
        	 else
             {
                 buyHeap.remove(buyHeap.size() - 1);
                 buyHeap.add(price);
             }
         }
         return Sum(sellHeap)-Sum(buyHeap);
	}

	private double Sum(List<Double> heap) {
		
		return heap.stream().mapToDouble(Double::doubleValue).sum();
	}

}
