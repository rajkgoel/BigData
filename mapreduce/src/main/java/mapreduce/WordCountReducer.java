package mapreduce;

import java.io.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	
	enum InfoCounter { COUNT, LENTGH }
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException {
        int count = 0;
        
        Configuration conf = context.getConfiguration();
        String checkChar = conf.get("checkChar");
        
        for(IntWritable val : values) {
            count = count + val.get();
            
            if(key.getLength() > 0 && key.toString().startsWith("p")) {
            	context.getCounter(InfoCounter.COUNT).increment(1);
            	context.getCounter(InfoCounter.LENTGH).increment(key.getLength());
            }
        }
        
        try {
			context.write(key, new IntWritable(count));
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}     
	
    }
}