package main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import calc.CalcMapper;
import calc.CalcReducer;
import sort.SortMap;
import sort.SortReducer;
import util.User;
import util.UserComparator;
import util.UserIdComparator;
import util.UserIdPartitioner;

public class Main
{

	public static void main(String[] args)
	{
		String inputFile = args[0];
	    String calcOutputDir = args[1];
	    String sortOutputDir = args[2];
	    try
		{
	    	if (runCalcJob(inputFile, calcOutputDir)) {
	    	      runSortJob(calcOutputDir, sortOutputDir);
	    	    }
//			runCalcJob(inputFile, calcOutputDir);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
	public static boolean runCalcJob(String input, String output)
		      throws Exception {
		    Configuration conf = new Configuration();
		    BasicConfigurator.configure();
		    Job job = new Job(conf);
		    job.setJarByClass(Main.class);
		    job.setMapperClass(CalcMapper.class);
		    job.setReducerClass(CalcReducer.class);

		    job.setInputFormatClass(KeyValueTextInputFormat.class);

		    job.setMapOutputKeyClass(TextPair.class);
		    job.setMapOutputValueClass(IntWritable.class);

		    Path outputPath = new Path(output);

		    FileInputFormat.setInputPaths(job, input);
		    FileOutputFormat.setOutputPath(job, outputPath);

		    outputPath.getFileSystem(conf).delete(outputPath, true);

//		    System.exit(job.waitForCompletion(true) ? 0 : 1);
		    return job.waitForCompletion(true);
		  }
	
	public static void runSortJob(String input, String output)
		      throws Exception {
		    Configuration conf = new Configuration();

		    Job job = new Job(conf);
		    job.setJarByClass(Main.class);
		    job.setMapperClass(SortMap.class);
		    job.setReducerClass(SortReducer.class);

		    job.setInputFormatClass(KeyValueTextInputFormat.class);

		    job.setMapOutputKeyClass(User.class);
		    job.setMapOutputValueClass(User.class);

		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(Text.class);

		    job.setPartitionerClass(UserIdPartitioner.class);
		    job.setSortComparatorClass(UserComparator.class);
		    //Sluzi za redjanje u tekstualnom file-u (output)
		    job.setGroupingComparatorClass(UserIdComparator.class);

		    Path outputPath = new Path(output);

		    FileInputFormat.setInputPaths(job, input);
		    FileOutputFormat.setOutputPath(job, outputPath);

		    outputPath.getFileSystem(conf).delete(outputPath, true);

		    job.waitForCompletion(true);
		  }
}
