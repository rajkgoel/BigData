package vouchers;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import vouchers.VoucherMapper.InfoCounter;

public class VoucherDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
        int returnStatus = ToolRunner.run(new Configuration(), new VoucherDriver(), args);
        System.exit(returnStatus);
    }

	public int run(String[] args) throws IOException{

 	 Job job = Job.getInstance(getConf());
	
	 job.setJobName("Gift Voucher");
	 job.setJarByClass(VoucherDriver.class);
	 
	 job.setOutputKeyClass(IntWritable.class);
	 job.setOutputValueClass(Employee.class);
	 job.setMapOutputKeyClass(IntWritable.class);
	 job.setMapOutputValueClass(Employee.class);
	 
	 job.setMapperClass(VoucherMapper.class);
	 job.setCombinerClass(VoucherReducer.class);
	 job.setReducerClass(VoucherReducer.class);
	 job.setPartitionerClass(VoucherPartitioner.class);
	 
	//Setting path to the input files
	FileInputFormat.addInputPath(job, new Path(args[0]));
	//Setting path to the output files
	FileOutputFormat.setOutputPath(job,new Path(args[1]));
	
	//setting number of reducers
	job.setNumReduceTasks(13);
	
	//Since default InputFormatClass is TextInputFormat, we need not include this line in our code
	//job.setInputFormatClass(TextInputFormat.class);
	   	
	
	try {
		
	    boolean success = job.waitForCompletion(true);
	    if (success) {
	      /*
	       * Print out the counters that the mappers have been incrementing.
	       */
	      long married = job.getCounters().findCounter(InfoCounter.MARRIED).getValue();
	      long males = job.getCounters().findCounter(InfoCounter.MALE).getValue();
	      long females = job.getCounters().findCounter(InfoCounter.FEMALE).getValue();
	 
	      System.out.println("Total Married   = " + married);
	      System.out.println("Total Males   = " + males);
	      System.out.println("Total Females   = " + females);
	      return 0;
	    } else
	      return 1;
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 0;
}

}
