import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
public class FFT{
	public static void main(String args[]) throws Exception {
		/* Enter your code here. Read input from STDIN. Print output to STDOUT */
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new
                 FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);
        ArrayList<Double[]>input = new ArrayList<Double[]>();
        double real, imagine, real2, imagine2, exp=0, temp=0, exp2=0;
        DecimalFormat df=new DecimalFormat("0.000");
        Scanner scanner=new Scanner(System.in);
        while(scanner.hasNextLine()){
            String s =scanner.nextLine();
            String[] arr = s.split(" ");
            input.add(new Double[]{Double.parseDouble(arr[0]),Double.parseDouble(arr[1])});
        }
        
        ArrayList<Double[]>output=fft(input);
        
        for(int i=0;i<output.size();i++){
            out.write(df.format(output.get(i)[0])+" "+df.format(output.get(i)[1])+"\n");
        }
        out.flush();
        /*String[][]output=new String[input.size()][2];
        String result1="";//first half
        String result2="";//last half
        for(int i=0;i<input.size()/2;i++){//goes through the kth frequency
            real=0.0;
            imagine=0.0;
            real2=0.0;
            imagine2=0.0;
            for(int j=0;j<input.size()/2;j++){//adding loop
                int even=2*j;
                int odd=2*j+1;
                exp = (2 * Math.PI * j * i) / (input.size()/2);                            
                real+=input.get(even)[0]*Math.cos(exp)+input.get(even)[1]*Math.sin(exp);//evens
                imagine+=(-input.get(even)[0])*Math.sin(exp)+input.get(even)[1]*Math.cos(exp);   
                
                real2+=input.get(odd)[0]*Math.cos(exp)+input.get(odd)[1]*Math.sin(exp);//odds
                imagine2+=(-input.get(odd)[0])*Math.sin(exp)+input.get(odd)[1]*Math.cos(exp);               
            }
            double multiplier=((2*Math.PI*i)/input.size());//-e^(2PIk/N)  multiplies by that weird term       
            temp=real2;
            real2=real2*Math.cos(multiplier)+imagine2*Math.sin(multiplier);
            imagine2=(-temp)*Math.sin(multiplier)+imagine2*Math.cos(multiplier);

            result1=result1+df.format((real+real2))+" "+df.format((imagine+imagine2))+"\n";
            result2=result2+df.format((real-real2))+" "+df.format((imagine-imagine2))+"\n";
            /*output[i][0]=df.format((real+real2));//assignments
            output[i][1]=df.format((imagine+imagine2));
            output[i+(input.size()/2)][0]=df.format((real-real2));
            output[i+(input.size()/2)][1]=df.format((imagine-imagine2));
        }*/
        
        
        /*for(int k=0;k<output.length;k++){
                out.write(output[k][0]+" "+output[k][1]+"\n");          
        }*/
        //out.write(result1);
        //out.write(result2);
        //out.flush();
    }
    
    public static ArrayList<Double[]> fft(ArrayList<Double[]> input){
    	int length=input.size();
        double real, imagine, real2, imagine2, exp=0, temp=0, exp2=0;
        if(length==1){
            ArrayList<Double[]> returner = new ArrayList<Double[]>();
            returner.add(input.get(0));
            return returner;
        }
        
        ArrayList<Double[]> even = new ArrayList<Double[]>();
        ArrayList<Double[]> odd = new ArrayList<Double[]>();
        for(int i=0;i<length/2;i++){
            even.add(new Double[]{input.get(2*i)[0],input.get(2*i)[1]});//even half
            odd.add(new Double[]{input.get(2*i+1)[0],input.get(2*i+1)[1]});//odd half
        }
         ArrayList<Double[]> m=fft(even);//splits up this even more
         ArrayList<Double[]> n=fft(odd);
        
        ArrayList<Double[]> output=new ArrayList<Double[]>();
        for(int i=0;i<length/2;i++){
            double multiplier=((2*Math.PI*i)/length);
            real=m.get(i)[0];
            imagine=m.get(i)[1];
            real2=n.get(i)[0];
            imagine2=n.get(i)[1];
            
            temp=real2;
            real2=real2*Math.cos(multiplier)+imagine2*Math.sin(multiplier);
            imagine2=(-temp)*Math.sin(multiplier)+imagine2*Math.cos(multiplier);
 
            output.add(i,new Double[]{real+real2,imagine+imagine2});
            output.add(i*2+1,new Double[]{real-real2,imagine-imagine2});
        }
        return output;
	}
}