package saavn.streaming;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

class Song implements WritableComparable<Song> {
	private Text songId;
	private IntWritable count;

	public Text getSongId() {
		return this.songId;
	}

	public IntWritable getCount() {
		return this.count;
	}

	public Song() {
		this.songId = new Text();
		this.count = new IntWritable(0);
	}
	
	public Song(String songId, Integer cnt) {
		this.songId = new Text(songId);
		this.count = new IntWritable(cnt);
	}
	
	public void write(DataOutput out) throws IOException {
		songId.write(out);
		count.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		songId.readFields(in);
		count.readFields(in);
	}

	public int compareTo(Song song) {
		int compareValue = this.songId.compareTo(song.getSongId());
		if (compareValue == 0) {
			compareValue = this.getCount().compareTo(song.getCount());
		}
	       return compareValue;
	}
	
	public void incrementCount() {
		this.count = new IntWritable(this.count.get() + 1);
	}
	
	@Override
	public String toString() {
		return this.songId + ", " + this.count;
	}
	
	public Song Clone() {
		return new Song(this.songId.toString(), Integer.parseInt(this.count.toString()));
	}

	
}