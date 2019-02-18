package saavn.streaming;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	
	public void reduce(Text key, Iterable<Song> values, Context context) 
			throws IOException, InterruptedException {
		
		Map<Text, Song> validSongs = removeDataAnomalies(key, values);
		
		//Sum the streaming count for given key(songId)
		int sum = 0;
		for(Map.Entry<Text, Song> entry: validSongs.entrySet()) {
			sum += entry.getValue().getCount().get();
		}
		context.write(key, new IntWritable(sum));
	}

	private Map<Text, Song> removeDataAnomalies(Text key, Iterable<Song> values) {
		Logger logger = Logger.getLogger(SongsMapper1.class);
		
        Song prev = null;
        Map<Text, Song> validSongs = new HashMap<Text, Song>();
		for(Song current : values) {
			if (prev != null) {
				//Ignore record if streaming count has gone higher/fallen by 1000 times
				if (current.getCount().get() * 1000 >= prev.getCount().get() ||
					current.getCount().get() <= prev.getCount().get() * 1000) {
					logger.error("Seems anomoly in streaming data, ignoring record -" + current.toString());
					prev = current;
					continue;
				}
			}
			prev = current;
			validSongs.put(key, current);
        }
		return validSongs;
	}
}
