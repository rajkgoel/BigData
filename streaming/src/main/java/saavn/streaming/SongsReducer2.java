package saavn.streaming;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

/*
 * Do the summation of streaming count for each songId
 * and write songId, sum(stream_count) to context
 *
 * PS:: This class is also used as a Combiner for SongsMapper2
 */
public class SongsReducer2 extends Reducer<Text, Song, Text, IntWritable>{
	
	Logger logger = Logger.getLogger(SongsReducer2.class);
	
	public void reduce(Text key, Iterable<Song> values, Context context) 
			throws IOException, InterruptedException {
		
        List<Song> validSongs = removeDataAnomalies(values);
		
		//Sum the streaming count for given key(songId)
		int sum = 0;
		for(Song entry: validSongs) {
			sum += entry.getCount().get();
		}
		context.write(key, new IntWritable(sum));
	}

	private List<Song> removeDataAnomalies(Iterable<Song> values) {
		Song prev = null;
        List<Song> validSongs = new ArrayList<Song>();
		for(Song current : values) {
			if (prev != null) {
				//Ignore record if streaming count has gone higher/fallen by 1000 times
				if ((current.getCount().get() >= prev.getCount().get() * 1000) ||
					(current.getCount().get() * 1000 <= prev.getCount().get() )) {
					logger.error("Seems anomoly in streaming data, ignoring record -" + current.toString());
					prev = current;
					continue;
				}
			}
			prev = current;
			validSongs.add(current);
        }
		return validSongs;
	}
}
