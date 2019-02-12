package partitionExample;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/* Counts the number of values associated with a key */

public class LogReducer extends Reducer<Text, Text, Text, IntWritable> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {


		int count = 0;

		for (@SuppressWarnings("unused")
		Text value : values) {
			count++;
		}

		context.write(key, new IntWritable(count));
	}
}
