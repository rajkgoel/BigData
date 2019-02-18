package saavn.streaming;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * Simply reverts the Key, Value as Value, Key, and writes them to context
 * This is done only to write output data in the format of "SongId	Rank"
 * to match output with Saavn trending songs list
 */
public class SongsReducer3  extends Reducer<IntWritable, Text, Text, IntWritable>{

	public void reduce(IntWritable key, Iterable<Text> values, Context context) 
			throws IOException, InterruptedException {
		//Simply writes K,V as V,K
		for(Text value: values) {
			context.write(value, key);
        }
	}
}
