package calc;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import main.TextPair;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

	public class CalcMapper extends Mapper<Text, Text, TextPair, IntWritable>
	{

		private TextPair pair = new TextPair();
		private IntWritable one = new IntWritable(1);
		private IntWritable two = new IntWritable(0);

		@Override
		protected void map(Text key, Text value, Context context) throws IOException, InterruptedException
		{
			//Prvo se iz value izvlaci lista id-eva svih prijatelja korisnika
			String[] friends = value.toString().split(" ");
			for (int i = 0; i < friends.length; i++)
			{
				// Posto su oni vec prijatlji emituje se 1

				pair.set(key.toString(), friends[i]);
				context.write(pair, one);

				for (int j = i + 1; j < friends.length; j++)
				{
					//Sada se emituje da nisu prijatelji,
					//U reducer fazi ako naidje za ovaj par negde da su prijatelji
					//Zove se break i onda nema veze sto je ovde emitovano da nisu.
					pair.set(friends[i], friends[j]);
					context.write(pair, two);
				}
			}
		}
	}
