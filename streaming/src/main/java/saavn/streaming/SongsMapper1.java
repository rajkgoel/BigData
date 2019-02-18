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

/*
 * Does following tasks -
 * a. Read input parameters from command line and validate them
 * b. Read input record from file, validate the number of columns in record.
 * c. Parse required columns from input record, and validate parsed columns
 * d. Ignore the record if it's streaming date is falling out off input date criteria
 * e. Write the following information in context: 
 * 		StreamingDate, (Instance of Song object, containing SongId & StreamingCount)
 */
public class SongsMapper1 extends Mapper<LongWritable, Text, Text, Song> {
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		Logger logger = Logger.getLogger(SongsMapper1.class);
		
		//Parse begin/end days from input parameters
		String[] beginEndDays = context.getConfiguration().get("BeginEndInDays").split(":");
		logger.debug(String.format("Received Params - Begin: %s, End: %s", beginEndDays[0], beginEndDays[1]));
		
		int beginDateInDays = Integer.parseInt(beginEndDays[0]);
		int endDateInDays = Integer.parseInt(beginEndDays[1]);
		
		if(beginDateInDays < endDateInDays) 
			throw new IOException("Begin From should be earlier then End till days. E.g. 7:1");
		
		//Calculate Start/End Date for streaming data filtering
		Calendar startDate = Util.getStartDate(beginDateInDays);
		Calendar endDate = Util.getEndDate(endDateInDays);
		
		logger.debug(String.format("Records should be between: %s - %s", 
				timeDateFormat.format(startDate.getTime()),
				timeDateFormat.format(endDate.getTime())));
		
		//Parse record and do required validation
		//V-HvGNCt,477552b6e41394619569fbfb9a590d5b,1512143128,15,2017-12-01
		String cols [] = value.toString().split(",");
		if(cols.length < 5) {
			logger.warn("Invalid streaming record, not all columns are available: " + value.toString());
			return;
		}
		
		System.out.println(String.format("Read line: %s", value));
		String songId = cols[0];
		String playDate = cols[4];
		
		if (songId.length() <= 0) {
			logger.warn("Invalid streaming record, songId is blank: " + value.toString());
			return;
		}
		else if (playDate.length() <= 8) {
			logger.warn("Invalid streaming record, invalid playdate: " + value.toString());
			return;
		}
		
		try {
			//Return if streaming dates are not matching with input dates
			Date streamingDate = simpleDateFormat.parse(playDate);
			if(streamingDate.compareTo(startDate.getTime()) < 0 || streamingDate.compareTo(endDate.getTime()) > 0) {
				logger.debug(String.format("Ignoring record for out of bound date, StartDate: %s, EndDate: %s, StreamingDate: %s",
						timeDateFormat.format(startDate.getTime()), 
						timeDateFormat.format(endDate.getTime()),
						timeDateFormat.format(streamingDate)));
				return;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("Exception while finding streaming date.");
		}
	
		//Write parsed and filtered record for further processing 
		Song song = new Song(songId, 1, playDate);
		logger.debug("Processing record: " + value.toString());
		context.write(new Text(playDate), song); 
	}

}