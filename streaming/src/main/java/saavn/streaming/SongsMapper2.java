package saavn.streaming;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * Read record, parse songId and its streaming count, and write it to context
 */
public class SongsMapper2 extends Mapper<LongWritable, Text, Text, Song>  {

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
		//Parse songId and streamingCount and write them to Context
		String[] cols = value.toString().split("\\t");
		Text songId = new Text(cols[0]);
		String[] values = cols[1].split(",");
		IntWritable streamingCount = new IntWritable(Integer.parseInt(values[1].trim()));
		Text streamingDate = new Text(values[2].trim());
		context.write(songId, new Song(songId, streamingCount, streamingDate));
	}
}
