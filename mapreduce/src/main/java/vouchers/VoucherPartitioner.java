package vouchers;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class VoucherPartitioner extends Partitioner<IntWritable, Employee> implements Configurable {

	private Configuration configuration;
		
	public void setConf(Configuration conf) {
		this.configuration = conf;
	}

	public Configuration getConf() {
		return configuration;
	}

	@Override
	public int getPartition(IntWritable key, Employee value, int numPartitions) {
		int birthMonth = key.get();
		if (birthMonth < 0 || birthMonth > 12) return 1;
		return birthMonth;
	}

}
