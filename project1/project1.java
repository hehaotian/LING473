import java.io.*;
import java.util.*;

public class project1 {
	public static void main(String[] args) throws FileNotFoundException {
	
      int[] total = new int[5];
      PrintStream output = new PrintStream(new File("output.txt"));
   
	   String corporaName = "";
      for (int i = 1400; i <= 1499; i++) {
	   	corporaName = "wsj_" + i + ".prd";
	   	File corpora = new File("/Users/haotianh/Documents/corpora/project1/14/" + corporaName);
	   	Scanner input = new Scanner(corpora);
         parse(input, total, corporaName);
	   }
      
      print(output, total, corporaName);
      System.out.println("/corpora/LDC/LDC99T42/RAW/parsed/prd/wsj/14/" + "\t" + total[0] + "\t" + 
                           total[1] + "\t" + total[2] + "\t" + total[3] + "\t" + total[4]);
	}
   	
	public static void parse(Scanner input, int[] total, String corporaName) {
      boolean is_vp = false;
      boolean ob_np = false;
      boolean is_open = true;
      int vnp = 0;
      
      while (input.hasNextLine()) {
			String line = input.nextLine();
         for (int j = 3; j < line.length(); j ++) {
            if (line.substring(j - 3, j).matches("[(]S\\s"))
               total[0] ++;
				if (line.substring(j - 3, j + 1).matches("[(]NP\\s")) {
               if (is_vp) {
                  vnp ++; 
                  ob_np = true;
               }
               total[1] ++;
            } 
            if (line.substring(j - 3, j + 1).matches("[(]VP\\s")) {  
               total[2] ++;
               is_vp = true;
				}   
            if (line.substring(j - 3, j - 2).matches("[)]") && is_vp) {
               tranNot(total, is_vp, ob_np, vnp);
            }
			}
		}
	}
   
   public static void print(PrintStream output, int[] total, String corporaName) {
		output.print("/corpora/LDC/LDC99T42/RAW/parsed/prd/wsj/14/" + "\t");
      for (int j = 0; j < 5; j ++)
         output.print(total[j] + "\t");
      output.println();
   }
   
   public static void tranNot(int[] total, boolean is_vp, boolean ob_np, int vnp) {
      if (!ob_np && vnp == 0) {
         total[4] ++;
         is_vp = false;
      } else if (ob_np && vnp == 2) {
         total[3] ++;
         is_vp = false;
      }
      
   }

}