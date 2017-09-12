package calc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import main.TextPair;

		public class CalcReducer extends Reducer<TextPair, IntWritable, TextPair, IntWritable>
		{
			private IntWritable friendsInCommon = new IntWritable();

			/**
			 * Mi ovde kao ulaz dobijemo:
			 * Kljuc- to je par prijatelja tipa : "Petar Milos"
			 * Value je niz, 1 ako su prijatelji, i 2 ako imaju zajednickog prijatelja
			 * */
			@Override
			protected void reduce(TextPair key, Iterable<IntWritable> values,
					Reducer<TextPair, IntWritable, TextPair, IntWritable>.Context context) throws IOException, InterruptedException
			{
				int commonFriends = 0;
				boolean alreadyFriends=false;
				for(IntWritable hops : values)
				{
					if(hops.get()==1)//Ako su prijatlji vec
					{
						alreadyFriends=true;
						break;
					}
					//Ovoliko puta je receno da imaju zajednickog prijatelja
					commonFriends++;
				}
				if(!alreadyFriends)
				{
					friendsInCommon.set(commonFriends);
					context.write(key, friendsInCommon);
				}
			}
		}

		