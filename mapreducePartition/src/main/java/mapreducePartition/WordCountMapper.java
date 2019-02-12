package mapreducePartition;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.*;
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
		String st [] = value.toString().split("\\s+");
	    for(String st1:  st) {
	    	Text word = new Text(st1.replaceAll("[^a-zA-Z]","").toLowerCase());
            context.write(word, new IntWritable(1)); 
        }

	}
}
