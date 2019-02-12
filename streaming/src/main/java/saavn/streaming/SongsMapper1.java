package saavn.streaming;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class SongsMapper1 extends Mapper<LongWritable, Text, Text, Song> {
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		Logger logger = Logger.getLogger(SongsMapper1.class);
		String cols [] = value.toString().split(",");
		//V-HvGNCt,477552b6e41394619569fbfb9a590d5b,1512143128,15,2017-12-01
		
		if(cols.length < 5) {
			logger.warn("Invalid streaming record, not all columns are available: " + value.toString());
			return;
		}
		
		String songId = cols[0];
		//Long unixDate = Long.parseLong(cols[2]);
		String playDate = cols[4];
		//Date date = Date.from(Instant.ofEpochSecond(playDate));
		
		if (songId.length() <= 0) {
			logger.warn("Invalid streaming record, songId is blank: " + value.toString());
			return;
		}
		else if (playDate.length() <= 8) {
			logger.warn("Invalid streaming record, invalid playdate: " + value.toString());
			return;
		}
		
		Song song = new Song(songId, 1);
		context.write(new Text(playDate), song); 
	}
}