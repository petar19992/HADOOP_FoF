package util;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class UserComparator extends WritableComparator {
  	protected UserComparator() {
		super(User.class, true);
	}

	@Override
	public int compare(WritableComparable w1, WritableComparable w2) {

		User p1 = (User) w1;
		User p2 = (User) w2;

		String id1=p1.getId();
		int cmp = id1.compareTo(p2.getId());
		if (cmp != 0) {
			return cmp;
		}

		return p1.getCommonFriends() == p2.getCommonFriends() ? 0 : (p1.getCommonFriends() > p2.getCommonFriends() ? -1 : 1);
	}
}