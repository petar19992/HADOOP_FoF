package sort;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import util.User;

		public class SortMap extends Mapper<Text, Text, User, User>
		{
			User outputKey = new User();
			User outputValue = new User();

			@Override
			protected void map(Text key, Text value, Mapper<Text, Text, User, User>.Context context)
					throws IOException, InterruptedException
			{

				// key je prvi id
				String[] parts = StringUtils.split(value.toString());
				int id2 = Integer.valueOf(parts[0]); // id2 je drugi id koji se cita iz file-a
				
				int commonFriends = Integer.valueOf(parts[1]); //broj zajednickih prijatelja ova dva korisnika
				outputKey.set(id2, commonFriends);
				outputValue.set(Integer.valueOf(key.toString()), commonFriends);
				//emitujemo drugog korisnika kao key, a kao value prvog korisnika i broj zajednickih prijatelja
				context.write(outputKey, outputValue);

				outputValue.set(id2, commonFriends);
				outputKey.set(Integer.valueOf(key.toString()), commonFriends);
				//emitujemo prvog korisnika kao key, a kao value drugog korisnika i broj zajednickih prijatelja
				context.write(outputKey, outputValue);
			}
		}

		