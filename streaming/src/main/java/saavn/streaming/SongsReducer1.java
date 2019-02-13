package saavn.streaming;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * This is a next step to SongsCombiner.
 * Does the sum(StreamingCount) for each SongId for passed on StreamingDate as Key. 
 * If any weights need to be applied on SongIds, based on StreamingDate, it can be done here.
 * It then sorts the data in descending order and  writes (SongId, Sum(StreamingCount) to context.
 */
public class SongsReducer1 extends Reducer<Text, Song, Text, IntWritable>{
	
	public static boolean ASC = true;
	public static boolean DESC = false;
	
    public void reduce(Text key, Iterable<Song> values, Context context) throws IOException {
	   
	    HashMap<String, Integer> songs = new HashMap<String, Integer>();
        try {
        	//Loop on all records, and do the sum(StreamingCount) using SongId as Key
	        for(Song song : values) {
	        	String songId = song.getSongId().toString();
	        	if(songs.containsKey(songId)) 
	        		songs.put(songId, songs.get(songId) + song.getCount().get());
	        	else 
	        		songs.put(songId, song.getCount().get());
	        }
	        
	        //Sort the StreamingSong based on StreamingCount, in descending order.
	        Map<String, Integer> sortedSongs = Util.sortByComparator(songs, DESC);
	        
	        //Write (SongId, Sum(StreamingCount)) to context
	        for(Map.Entry<String, Integer> e : sortedSongs.entrySet()) {
	            String songId = e.getKey();
	            Integer count = e.getValue();
            	context.write(new Text(songId), new IntWritable(count));
	        }
        } catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }
	   
}
