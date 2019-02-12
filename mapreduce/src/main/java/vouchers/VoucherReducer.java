package vouchers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class VoucherReducer extends Reducer<IntWritable, Employee, IntWritable, Employee> {
	
   public void reduce(IntWritable key, Iterable<Employee> values, Context context) throws IOException {
                
        try {
	        for(Employee emp : values) {
	        	context.write(key, emp);
	        }
        } catch (InterruptedException e) {
    		
			e.printStackTrace();
		} 
    }
}
