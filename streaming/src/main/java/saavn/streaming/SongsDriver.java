package saavn.streaming;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * This job is divided across three jobs, each with a combination of Mapper, Reducer and a Combiner (where applicable)
 * Details of each Mapper, Reducer and Combiners are provided in their respective classes. 
 * 
 * Driver class expects three types of parameters - 
 * a. BeginEndInDays: Starting from back, begin and end number of days, separated by colon.
 * 					  E.g. to find streaming songs for last 7 days, provide 7:1
 * b. Input file: input file path on hdfs
 * c. Output directory: Output path where results would be written.
 * 
 * Each Mapper and Reducer jobs, configured in Driver class, runs in sequential i.e. 
 * 	Mapper1(+Combiner) -> Reducer1 -> Mapper2(+Combiner) -> Reducer2 -> Mapper3 -> Reducer3   
 */
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
		  	
		try {
			String outFolder = args[1];
			deleteOutputFolders(conf, outFolder);
			Job job = Job.getInstance(conf, "StreamingSongs_1");
			job.setJarByClass(SongsDriver.class);
				
			job.setMapperClass(SongsMapper1.class);
			job.setCombinerClass(SongsCombiner.class);
			job.setReducerClass(SongsReducer1.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Song.class);
				
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Song.class);
			
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(outFolder + "Temp1"));
			
			conf.set("BeginEndInDays", "7:1"); //Default values for Begin/End in Days
			job.waitForCompletion(true); // this is for the first job to complete
			
			job = initializeSecondJob(args, conf);
			job.waitForCompletion(true); // this is for the second job to complete
			
			job = initializeThirdJob(args, conf);
			return job.waitForCompletion(true)? 0:1; // this is for the second job to complete
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
   	
	}

	/*
	 * Removes previously created output folders, if any existing
	 */
	private void deleteOutputFolders(Configuration conf, String outFolder) {
		
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.delete(new Path(outFolder), true);
			fs.delete(new Path(outFolder + "Temp1"), true);
			fs.delete(new Path(outFolder + "Temp2"), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		// true stands for recursively deleting the folder you gave
		
	}

	/*
	 * Initializes 3rd Mapper/Reducer, assigns its input/output paths 
	 */
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

	/*
	 * Initializes 3rd Mapper/Reducer, assigns its input/output paths 
	 */
	private Job initializeSecondJob(String[] args, Configuration conf) throws IOException {
		Job job;
		job = Job.getInstance(conf, "StreamingSongs_2");
		job.setJarByClass(SongsDriver.class);
				
		job.setMapperClass(SongsMapper2.class);
		//job.setCombinerClass(SongsReducer2.class);
		job.setReducerClass(SongsReducer2.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Song.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[1] + "Temp1"));
		FileOutputFormat.setOutputPath(job, new Path(args[1] + "Temp2"));
		return job;
	}

}
