import java.io.*;
import java.util.*;

public class project6 {
   
   public static void main(String[] args) throws IOException {
      File sourceFile = new File("/Users/haotianh/Dropbox/Coursework/LING473/Projects/project6/gaga0.txt");
      File targetFile = new File("/Users/haotianh/Dropbox/Coursework/LING473/Projects/project6/project6/data/gaga1.txt");
      Scanner source = new Scanner(sourceFile);
      Scanner target = new Scanner(targetFile);
      compareLines(source, target);
   }
   
   public static void compareLines(Scanner source, Scanner target) throws IOException {
      String sourceLine = "";
      String targetLine = "";
      double overallDis = 0;
      boolean match = false;
      PrintStream out = new PrintStream(new File("output.txt"));
      while (source.hasNextLine() && target.hasNextLine()) {
         if (source.hasNextLine()) sourceLine = source.nextLine();
         if (target.hasNextLine()) targetLine = target.nextLine();
         match = matchLine(sourceLine, targetLine);
         if (match) {
            double lineDis = levenshteinDistance(sourceLine, targetLine);
            overallDis += lineDis;
            out.printf("%s\t%.2f\t%s\n", sourceLine, lineDis, targetLine);
         } else {
            out.printf("%.2f\t%s\n", 1.00, targetLine);
            while (target.hasNextLine()) {
               targetLine = target.nextLine();
               match = matchLine(sourceLine, targetLine);
            }
         }
      }
      out.println();
      out.println("Edit distance is " + overallDis);
   }
   
   public static double levenshteinDistance(String s, String t) {
      int d[][];
      int n;
      int m;
      int i;
      int j;
      char s_i;
      char t_j;
      int sub;
      int countMatch = 0;
      
      n = s.length();
      m = t.length();   
      
      if (n == 0 && m == 0) {
         return 0.0;
      } else if (n == 0 || m == 0) return 1.0;
      d = new int[n + 1][m + 1];
      
      for (i = 0; i <= n; i++)
         d[i][0] = i;
      
      for (j = 0; j <= m; j++)
         d[0][j] = j;
      
      for (i = 1; i <= n; i++) {
         s_i = s.charAt(i - 1);
         for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            
            if (s_i == t_j)
               sub = 0;
            else
               sub = 2;     
            d[i][j] = Minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + sub);
         }
      }
      return d[n][m] / (n * 1.0 + m * 1.0);
   }
   
   public static int Minimum(int a, int b, int c) {
      int min;
      min = a;
      if (b < min) min = b;
      if (c < min) min = c;
      return min;
   }
   
   public static boolean matchLine(String s, String t) throws IOException {
      if (!s.isEmpty() || !t.isEmpty()) {
         String sourceToken = "";
         String targetToken = "";
         Scanner sourceWords = new Scanner(s);
         Scanner targetWords = new Scanner(t);
         while (sourceWords.hasNext()) {
            if (sourceWords.hasNext()) {
               sourceToken = sourceWords.next();
            }
            while (targetWords.hasNext()) {
               if (targetWords.hasNext()) {
                  targetToken = targetWords.next();
               }
               if (sourceToken.equals(targetToken)) return true;
            }
         }
      }
      return false;
   }
   
}