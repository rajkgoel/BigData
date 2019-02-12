package vouchers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;
import vouchers.Employee;

public class VoucherMapper extends Mapper<LongWritable, Text, IntWritable, Employee> {
	enum InfoCounter{ MARRIED, MALE, FEMALE };
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		if (key.get() == 0 && value.toString().contains("Employee Name") /*Some condition satisfying it is header*/)
            return;
		System.out.println(key + ": " + value);
		//Make month-wise files containing 
		//employee names, employee IDs and birthday 
		//without the year and the month. 
		String cols [] = value.toString().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		
		String firstName = cols[0];
		//String lastName = cols[1];
		Integer empId =Integer.parseInt(cols[1]);
		Integer isMarried = Integer.parseInt(cols[2]);
		Integer isMale = Integer.parseInt(cols[4]);
		
		if (isMarried ==1)
			context.getCounter(InfoCounter.MARRIED).increment(1);
		if (isMale == 1)
			context.getCounter(InfoCounter.MALE).increment(1);
		else
			context.getCounter(InfoCounter.FEMALE).increment(1);
		    
		
		String[] dob = cols[12].split("/");
		int birthMonth = Integer.parseInt(dob[0]);
		int birthDay = Integer.parseInt(dob[1]);
		Employee emp = new Employee(firstName, empId, birthDay);
		context.write(new IntWritable(birthMonth), emp); 
	}
}


class Employee implements WritableComparable<Employee> {
	
	private Text empName;
	private IntWritable empId;
	private IntWritable empBirthDay;
	
	public Employee() {
		this.empName = new Text();
		this.empId = new IntWritable(0);
		this.empBirthDay = new IntWritable(0);
	}
	
	public Employee(String empName, Integer empId, Integer empBirthDay) {
		this.empName = new Text(empName);
		this.empId = new IntWritable(empId);
		this.empBirthDay = new IntWritable(empBirthDay);
	}
	
	public void write(DataOutput out) throws IOException {
		empName.write(out);
		empId.write(out);
		empBirthDay.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		empName.readFields(in);
		empId.readFields(in);
		empBirthDay.readFields(in);
	}

	public int compareTo(Employee emp) {
		int cmp = empName.compareTo(emp.empName);
        if (cmp != 0) return cmp;
        
        cmp = empId.compareTo(emp.empId);
        if (cmp != 0) return cmp;
        
        return empBirthDay.compareTo(emp.empBirthDay);
	}
	
	@Override
	public String toString() {
		return this.empName + ", " + this.empId + " " + this.empBirthDay;
	}
	
}
