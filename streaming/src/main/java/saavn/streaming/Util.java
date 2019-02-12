package saavn.streaming;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Util {

	public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
	
	public static Calendar getStartDate(int numOfDays) {
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_MONTH, -numOfDays);
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		return startDate;
	}
	
	public static Calendar getEndDate(int numOfDays) {
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_MONTH, -numOfDays);
		startDate.set(Calendar.HOUR_OF_DAY, 23);
		startDate.set(Calendar.MINUTE, 59);
		startDate.set(Calendar.SECOND, 59);
		startDate.set(Calendar.MILLISECOND, 999);
		return startDate;
	}
}
