package saavn.streaming;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * 1. Reads (SongId, Sum(StreamingCount)) from input record 
 * 		and store information (Sum(StreamingCount), SongId) in a TreeMap
 * 2. TreeMap is used for storing records in 2D type of array, e.g. -
 * 			-200: abc,def,why
 * 			-170: kdk,wiw
 * 			-150: kwj,k3k,dj2,kjd
 * 			-120: kdj
 * 2. If TreeMap size is increasing more then size 100 or total number of elements 
 * 		in tree are more then 100, then remove one SongId from last key.
 * 3. Once the Mapper is finished, flush TreepMap data into context while converting
 * 		Sum(StreamingCount) into Ranking. Max gets 1, and so on.
 */
public class SongsMapper3 extends Mapper<LongWritable, Text, IntWritable, Text> {
	private TreeMap<Integer, Text> sortedMaxStreamed = new TreeMap<Integer, Text>();
	private Integer totalItems = 0;
	        
    @Override
    protected void map(LongWritable key, Text value, Context context) throws InterruptedException, IOException {
    	
    	//Parse the songId and listenedCount from given record
    	String[] cols = value.toString().split("\\t");
        Text songId = new Text(cols[0]);
        int listenedCount = -1 * Integer.parseInt(cols[1]);
        
        //Store the count and songId in TreeMap, to keep records in sorted order
        if (sortedMaxStreamed.containsKey(listenedCount))
        {
        	Text existing = sortedMaxStreamed.get(listenedCount);
        	sortedMaxStreamed.put(listenedCount, new Text(String.format("%s,%s",existing.toString(), songId)));
        }
        else
        {
        	sortedMaxStreamed.put(listenedCount, new Text(songId));
        }
        
        totalItems++;
        //If number of records are more then required then remove the least listened song
        if (totalItems > 100 || sortedMaxStreamed.size() > 100) {
        	removeOneSongFromLastElement();
        }
    }

    //Removes one SongId from last element of the treeMap. 
    //If last element contains only one element, then that element itself is removed.
	private void removeOneSongFromLastElement() {
		Integer lastKey = sortedMaxStreamed.lastKey();
		String[] lastItems = sortedMaxStreamed.get(lastKey).toString().split(",");
		if (lastItems.length <= 1) {
			//Remove last element from TreeMap
			sortedMaxStreamed.remove(sortedMaxStreamed.lastKey());
		}
		else {
			//Remove last SongId from last element stored in TreeMap
			String songIds = "";
			for(int i=0;i<lastItems.length-1;i++) {
				songIds = songIds + lastItems[i] + ",";
			}
			songIds = songIds.substring(0, songIds.length() -1);
			sortedMaxStreamed.put(lastKey, new Text(songIds));
		}
	}
    
	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		//Write the maximum listened songs, Mapper would itself write them in sorted manner
		int count = 1;
		for (Map.Entry<Integer, Text> t : sortedMaxStreamed.entrySet()) {
			//Write the Rank of SongId, instead of streamingCount
			String[] songIds = t.getValue().toString().split(",");
			for(String songId: songIds) {
				context.write(new IntWritable(count), new Text(songId));
				count++;
			}
			if (count > 100)
				break;
		}
	}
}
