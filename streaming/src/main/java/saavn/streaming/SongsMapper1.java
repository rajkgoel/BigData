package saavn.streaming;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class SongsMapper1 extends Mapper<LongWritable, Text, Text, Song> {
	
	String pattern = "yyyy-MM-dd";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//hadoop fs -copyToLocal output/part* Downloads/windowsDir/saavn/
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		Logger logger = Logger.getLogger(SongsMapper1.class);
		String cols [] = value.toString().split(",");
		
		String[] beginEndDays = context.getConfiguration().get("BeginEndInDays").split(":");
//		logger.info(String.format("Received Params - Begin: %s, End: %s", beginEndDays[0], beginEndDays[1]));
		
		int beginDateInDays = Integer.parseInt(beginEndDays[0]);
		int endDateInDays = Integer.parseInt(beginEndDays[1]);
		
		Calendar startDate = Util.getStartDate(beginDateInDays);
		Calendar endDate = Util.getEndDate(endDateInDays);
		
//		logger.info(String.format("Records should be between: %s - %s", 
//				timeDateFormat.format(startDate.getTime()),
//				timeDateFormat.format(endDate.getTime())));
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
		
		try {
			Date streamingDate = simpleDateFormat.parse(playDate);
			if(streamingDate.compareTo(startDate.getTime()) < 0 || streamingDate.compareTo(endDate.getTime()) > 0) {
				logger.debug(String.format("Ignoring record for out of bound date, StartDate: %s, EndDate: %s, StreamingDate: %s",
						timeDateFormat.format(startDate.getTime()), 
						timeDateFormat.format(endDate.getTime()),
						timeDateFormat.format(streamingDate)));
				return;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Exception while finding streaming date.");
		}
		//dt.parse(playDate);
		//LocalDate streamingDate = LocalDate.parse(playDate);

		
		Song song = new Song(songId, 1);
		//logger.info("Processing record: " + value.toString());
		context.write(new Text(playDate), song); 
	}

}