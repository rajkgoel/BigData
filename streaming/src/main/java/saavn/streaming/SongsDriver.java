package saavn.streaming;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SongsDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
        int returnStatus = 0;
		try {
			returnStatus = ToolRunner.run(new Configuration(), new SongsDriver(), args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.exit(returnStatus);

	}

	public int run(String[] args) throws Exception {
		 
		Configuration conf = this.getConf();
		conf.set("BeginEndInDays", "7:1"); //Default values for Begin/End in Days
		  	
		try {
			Job job = initializeFirstJob(args, conf);
			job.waitForCompletion(true); // this is for the first job to complete
			
			job = initializeSecondJob(args, conf);
			job.waitForCompletion(true); // this is for the second job to complete
			
			job = initializeThirdJob(args, conf);

			return job.waitForCompletion(true)? 0:1;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
   	
	}

	private Job initializeThirdJob(String[] args, Configuration conf) throws IOException {
		Job job;
		job = Job.getInstance(conf, "StreamingSongs_3");
		job.setJarByClass(SongsDriver.class);
				
		job.setMapperClass(SongsMapper3.class);
		job.setReducerClass(SongsReducer3.class); //No Combiner is required here
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[1] + "Temp2"));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		return job;
	}

	private Job initializeSecondJob(String[] args, Configuration conf) throws IOException {
		Job job;
		job = Job.getInstance(conf, "StreamingSongs_2");
		job.setJarByClass(SongsDriver.class);
				
		job.setMapperClass(SongsMapper2.class);
		job.setCombinerClass(SongsReducer2.class);
		job.setReducerClass(SongsReducer2.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[1] + "Temp1"));
		FileOutputFormat.setOutputPath(job, new Path(args[1] + "Temp2"));
		return job;
	}

	private Job initializeFirstJob(String[] args, Configuration conf) throws IOException {
		Job job = Job.getInstance(conf, "StreamingSongs_1");
		job.setJarByClass(SongsDriver.class);
			
		job.setMapperClass(SongsMapper1.class);
		job.setCombinerClass(SongsCombiner.class);
		job.setReducerClass(SongsReducer1.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Song.class);
			
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1] + "Temp1"));
		return job;
	}

}
