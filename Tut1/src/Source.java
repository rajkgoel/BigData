import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Source {

    public static void main(String[] args) throws IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       String input = br.readLine();
       if(input == null || input =="") return;
        int numOfTests = Integer.parseInt(input);
        String[] borders = new String[numOfTests];
        String[] jumpers = new String[numOfTests];
        for(int i=0;i<numOfTests;i++){
            borders[i] = br.readLine();
            jumpers[i] = br.readLine();
        }
        
        for(int i=0;i<numOfTests;i++){
            String[] borderVals = borders[i].split(" ");
            String[] jumperVals = jumpers[i].split(" ");
            
            int start = Integer.parseInt(borderVals[0]);
            int end = Integer.parseInt(borderVals[1]);
            int jumpBy = Integer.parseInt(jumperVals[0]);
            int jumpCnt = Integer.parseInt(jumperVals[1]);
            StartJumping(start, end, jumpBy, jumpCnt);
        }
    }
    
    private static void StartJumping(int start, int end, int jumpBy, int jumpCnt){
        int sum=start;
        for(int i=1;i<=jumpCnt;i++){
            sum += start + (i * jumpBy);
        }
        System.out.println(sum);
    }


}