package saavn.streaming;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * Combiner class, used with the first mapper to sum the streamingCount at Mapper level
 */
public class SongsCombiner extends Reducer<Text, Song, Text, Song> {
	
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
	        
	        for(Map.Entry<String, Integer> e : songs.entrySet()) {
	            String songId = e.getKey();
	            Integer count = e.getValue();
	            context.write(key, new Song(songId, count));
	        }
        } catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }
}
