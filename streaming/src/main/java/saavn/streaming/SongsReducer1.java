package saavn.streaming;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * 
 */
public class SongsReducer1 extends Reducer<Text, Song, Text, IntWritable>{
	
	public static boolean ASC = true;
	public static boolean DESC = false;
	
    public void reduce(Text key, Iterable<Song> values, Context context) throws IOException {
	   
	    HashMap<String, Integer> songs = new HashMap<String, Integer>();
        try {
	        for(Song song : values) {
	        	String songId = song.getSongId().toString();
	        	if(songs.containsKey(songId)) 
	        		songs.put(songId, songs.get(songId) + song.getCount().get());
	        	else 
	        		songs.put(songId, song.getCount().get());
	        }
	        
	        Map<String, Integer> sortedSongs = Util.sortByComparator(songs, DESC);
	        
//	        int index = 0;
	        for(Map.Entry<String, Integer> e : sortedSongs.entrySet()) {
	            String songId = e.getKey();
	            Integer count = e.getValue();
            	context.write(new Text(songId), new IntWritable(count));
//	            index++;
//	            if(index > 20) break;
	        }
        } catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }
	   
}
