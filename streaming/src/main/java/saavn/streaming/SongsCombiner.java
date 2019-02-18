package saavn.streaming;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

/*
 * Combiner class, used with the first mapper - SongsMapper1 to sum the streamingCount at Mapper level
 * Sums the StreamingCount of Songs based on StreamingDate (key) and SongId
 * 
 * Combiner is coded different from Reducer as some functionality is required differently than Reducer 
 */
public class SongsCombiner extends Reducer<Text, Song, Text, Song> {
	SimpleDateFormat dtFormat = new SimpleDateFormat ("yyyy-MM-dd");
	Logger logger = Logger.getLogger(SongsCombiner.class);
	
    public void reduce(Text key, Iterable<Song> values, Context context) throws IOException {
 	   
    	//Map for containing SongId and sum(StreamingCount)
	    HashMap<String, Integer> songs = new HashMap<String, Integer>();
        try {
	        for(Song song : values) {
	        	String songId = song.getSongId().toString();
	        	
	        	//If Map already contains SongId then update its StreamingCount
	        	if(songs.containsKey(songId)) 
	        		songs.put(songId, songs.get(songId) + song.getCount().get());
	        	else 
	        		//Add new record for SongId, with StreamingCount
	        		songs.put(songId, song.getCount().get());
	        }
	        
	        //Write all records for SongId to context
	        for(Map.Entry<String, Integer> e : songs.entrySet()) {
	            String songId = e.getKey();
	            Integer count = e.getValue();
	            Song song = new Song(songId, count, key.toString());
	            logger.debug(String.format("Writing to context: %s %s", key, song.toString()));
	            context.write(key, song);
	        }
        } catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }
}
