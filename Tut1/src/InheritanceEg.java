import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InheritanceEg {

	public static void main2(String[] args) throws IOException {
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] strNums1, strNums2;
    	strNums1 = br.readLine().split("\\s");
    	strNums2 = br.readLine().split("\\s");
    	
    	Report[] reports = new Report[2];
    	Bus[] buses = new Bus[2];
    	
    	for(int i=0;i<reports.length;i++)     	{	
    		reports[i] = new Report();
    	}
    	
    	for(int j=0;j<buses.length;j++) {
    		buses[j] = new Bus();
    	}
    	
    	reports[0].Init(strNums1);
    	reports[1].Init(strNums2);
    	
    	buses[0].Init(strNums1);
    	buses[1].Init(strNums2);
		
    	int index = Integer.parseInt(br.readLine());
    	int studIndex = FindStudent(reports, index);
    	if(studIndex >=0) {
    		System.out.println(reports[studIndex].Name + " " + reports[studIndex].Percentage + " " + buses[studIndex].RouteNumber);
    	}
    	else {
    		System.out.println("No entry found");
    	}
	}
	
	private static int FindStudent(Report[] reports, int admNum) {
		
		for(int i=0;i<reports.length;i++) {
			if(reports[i].AdmissionNumber == admNum)
				return i;
		}
		return -1;
	}
}

class Student {
	public String Name;
	public int AdmissionNumber;
	public int Class;
	
	public void Init(String details) {
		String[] detail = details.split(" ");
		this.Name = detail[0];
		this.AdmissionNumber = Integer.parseInt(detail[1]);
		this.Class = Integer.parseInt(detail[2]);
	}
}

class Report extends Student {
	public int Percentage;
	public String PassResult;
	
	public void Init(String[] detail) {
		this.Name = detail[0];
		this.AdmissionNumber = Integer.parseInt(detail[1]);
		this.Class = Integer.parseInt(detail[2]);
		this.Percentage = Integer.parseInt(detail[3]);
		this.PassResult = detail[4];
	}
}

class Bus extends Student {
	public int RouteNumber;
	public String BloodGroup;
	
	public void Init(String[] detail) {
		this.Name = detail[0];
		this.AdmissionNumber = Integer.parseInt(detail[1]);
		this.Class = Integer.parseInt(detail[2]);
		this.RouteNumber = Integer.parseInt(detail[5]);
		this.BloodGroup = detail[6];
	}
}