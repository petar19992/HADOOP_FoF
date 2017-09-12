package util;

import org.apache.hadoop.mapreduce.Partitioner;

public class UserIdPartitioner extends
Partitioner<User, User> {

@Override
public int getPartition(User key, User value,
		int numPartitions) {
	return Math.abs(String.valueOf(key.getId()).hashCode() * 127) % numPartitions;
}
}