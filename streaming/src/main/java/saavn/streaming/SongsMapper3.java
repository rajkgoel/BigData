package saavn.streaming;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * Does sorting of records based on sum of the number of times any
 * song has been listened, in the complete duration
 */
public class SongsMapper3 extends Mapper<LongWritable, Text, IntWritable, Text> {
	private TreeMap<Integer, Text> sortedMaxStreamed = new TreeMap<Integer, Text>();
	        
    @Override
    protected void map(LongWritable key, Text value, Context context) throws InterruptedException, IOException {
    	
    	//Parse the songId and listenedCount from given record
    	String[] cols = value.toString().split("\\t");
        Text songId = new Text(cols[0]);
        int listenedCount = -1 * Integer.parseInt(cols[1]);
        
        //Store the count and songId in TreeMap, to keep records in sorted order
        sortedMaxStreamed.put(listenedCount, new Text(songId));

        //If number of records are more then required then remove the least listened song
		if (sortedMaxStreamed.size() > 100) {
			sortedMaxStreamed.remove(sortedMaxStreamed.lastKey());
		}
    }
    
	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		//Write the maximum listened songs, Mapper would itself write them in sorted manner
		int count = 1;
		for (Map.Entry<Integer, Text> t : sortedMaxStreamed.entrySet()) {
			//Write the Rank of SongId, instead of streamingCount
			context.write(new IntWritable(count), t.getValue());
			count++;
		}
	}
}
