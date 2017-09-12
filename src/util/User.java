package util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

		public class User implements WritableComparable<User>
		{

			private String name = "";
			private int id;
			private long commonFriends = 0;

	public User()
	{
	}

	public User(int id, long commonFriends)
	{
		this.id=id;
		this.commonFriends = commonFriends;
	}
	
	public User(String name, int id, long commonFriends)
	{
		this.name = name;
		this.id=id;
		this.commonFriends = commonFriends;
	}

	public void set(int id, long commonFriends)
	{
		this.id=id;
		this.commonFriends = commonFriends;
	}
	
	public void set(String name, int id, long commonFriends)
	{
		this.name = name;
		this.id=id;
		this.commonFriends = commonFriends;
	}

	public String getName()
	{
		return this.name;
	}

	public int getId()
	{
		return id;
	}

	public long getCommonFriends()
	{
		return this.commonFriends;
	}

	@Override
	public void readFields(DataInput in) throws IOException
	{
		this.name = in.readUTF();
		this.id = in.readInt();
		this.commonFriends = in.readLong();
	}

	@Override
	public void write(DataOutput out) throws IOException
	{
		out.writeUTF(name);
		out.writeInt(id);
		out.writeLong(this.commonFriends);
	}

	@Override
	public int compareTo(User other)
	{
		if (this.id != other.id)
		{
			Integer x=this.id;
			return x.compareTo(other.id);
		} else if (this.commonFriends != other.commonFriends)
		{
			return commonFriends < other.commonFriends ? -1 : 1;
		} else
		{
			return 0;
		}
	}

	public static class PersonKeyComparator extends WritableComparator
	{
		public PersonKeyComparator()
		{
			super(User.class);
		}

		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2)
		{
			return compareBytes(b1, s1, l1, b2, s2, l2);
		}
	}

	static
	{ // register this comparator
		WritableComparator.define(User.class, new PersonKeyComparator());
	}
}