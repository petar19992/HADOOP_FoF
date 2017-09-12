package sort;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.User;

		public class SortReducer extends Reducer<User, User, Text, Text> {

			private Text id = new Text();
			private Text potentialFriends = new Text();

			@Override
			public void reduce(User key, Iterable<User> values, Context context)
				throws IOException, InterruptedException {

			  StringBuilder sb = new StringBuilder();

			  // Emitujemo samo prvih deset predloga na osnovu broja zajednickih prijatelja
			  int count = 0;
			  for (User potentialFriend : values) {
				if(sb.length() > 0) {
				  sb.append(",");
				}
				//pristigle vrednosti su sortirane, samo ih treba prikazati na citljiv nacin
				sb.append(potentialFriend.getId())
					.append(":")
					.append(potentialFriend.getCommonFriends());

				if (++count == 10) {
				  break;
				}
			  }

			  id.set(String.valueOf(key.getId()));
			  potentialFriends.set(sb.toString());
			  context.write(id, potentialFriends);
			}
		  }