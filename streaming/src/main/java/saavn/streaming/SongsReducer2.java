package saavn.streaming;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * Do the summation of streaming count for each songId
 * and write songId, sum(stream_count) to context
 */
public class SongsReducer2 extends Reducer<Text, IntWritable, Text, IntWritable>{

	public void reduce(Text key, Iterable<IntWritable> values, Context context) 
			throws IOException, InterruptedException {
		
		//Sum the streaming count for given key(songId)
        int sum = 0;
		for(IntWritable cnt : values) {
        	sum += cnt.get();
        }
		context.write(key, new IntWritable(sum));
	}
}
