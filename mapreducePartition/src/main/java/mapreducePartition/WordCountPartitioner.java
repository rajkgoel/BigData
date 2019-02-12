package mapreducePartition;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class WordCountPartitioner extends Partitioner<Text, IntWritable> implements Configurable{

	private Configuration configuration;
	HashMap<String, Integer> startingChars = new HashMap<String, Integer>();
	
	public void setConf(Configuration conf) {
		this.configuration = conf;
	    startingChars.put("a", 0);
	    startingChars.put("bc", 1);
	    startingChars.put("def", 2);
	    startingChars.put("ghj", 3);
	    startingChars.put("hkin", 4);
	    startingChars.put("mpq", 5);
	    startingChars.put("or", 6);
	    startingChars.put("suv", 7);
	    startingChars.put("t", 8);
	    startingChars.put("wxyz", 9);
	}

	public Configuration getConf() {
		return configuration;
	}

	@Override
	public int getPartition(Text key, IntWritable value, int numReduceTasks) {
		if (key.getLength() < 1) return 0;
		String firstChar = key.toString().substring(0, 1);
		for (Entry<String, Integer> entry : startingChars.entrySet())
		{
			if (entry.getKey().toString().contains(firstChar))
				return (int)entry.getValue();
		}
		return 0;
	}

}
