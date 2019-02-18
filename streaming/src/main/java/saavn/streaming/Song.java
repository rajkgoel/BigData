package saavn.streaming;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.*;

class Song implements WritableComparable<Song> {
	private Text songId;
	private IntWritable count;
	private Text streamingDate;

	public Text getStreamingDate() {
		return streamingDate;
	}

	public Text getSongId() {
		return this.songId;
	}

	public IntWritable getCount() {
		return this.count;
	}

	public Song() {
		this.songId = new Text();
		this.count = new IntWritable(0);
		this.streamingDate = new Text();
	}
	
	public Song(String songId, Integer cnt, String streamDate) {
		this(new Text(songId), new IntWritable(cnt), new Text(streamDate));
	}
	
	public Song(Text songId, IntWritable cnt, Text streamDate) {
		this.songId = songId;
		this.count = cnt;
		this.streamingDate = streamDate;
	}
	
	public void write(DataOutput out) throws IOException {
		songId.write(out);
		streamingDate.write(out);
		count.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		songId.readFields(in);
		streamingDate.readFields(in);
		count.readFields(in);
	}

	public int compareTo(Song song) {
		int compareValue = this.songId.compareTo(song.getSongId());
		if (compareValue == 0) {
			compareValue = this.getCount().compareTo(song.getCount());
			if (compareValue == 0) {
				compareValue = this.getStreamingDate().compareTo(song.getStreamingDate());
			}
		}

       return compareValue;
	}
	
	public void incrementCount() {
		this.count = new IntWritable(this.count.get() + 1);
	}
	
	@Override
	public String toString() {
		return String.format("%s,%s,%s", this.songId, this.count, this.streamingDate.toString()) ;
	}
	
	public Song Clone() {
		return new Song(this.songId, this.count, this.getStreamingDate());
	}

	
}