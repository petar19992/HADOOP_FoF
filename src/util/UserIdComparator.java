package util;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class UserIdComparator extends WritableComparator {

	protected UserIdComparator() {
		super(User.class, true);
	}

	@Override
	public int compare(WritableComparable o1, WritableComparable o2) {

		User p1 = (User) o1;
		User p2 = (User) o2;

		Integer id=p1.getId();
		return id.compareTo(p2.getId());

	}
}