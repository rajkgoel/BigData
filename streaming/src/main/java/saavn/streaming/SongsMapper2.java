package saavn.streaming;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * Read record, parse songId and its streaming count, and write it to context
 */
public class SongsMapper2 extends Mapper<Object, Text, Text, IntWritable>  {

	@Override
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		
		//Parse songId and streamingCount and write them to Context
		String[] cols = value.toString().split("\\t");
		Text songId = new Text(cols[0]);
		IntWritable streamingCount = new IntWritable(Integer.parseInt(cols[1]));
		context.write(songId, streamingCount);
	}
}
