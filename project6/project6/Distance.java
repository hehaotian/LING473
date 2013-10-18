public class Distance {
   
   private int Minimum(int a, int b, int c) {
      int min;
      min = a;
      if (b < min) min = b;
      if (c < min) min = c;
      return min;
   }
   
   public double levenshteinDistance(String s, String t) {
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
   
   
   private boolean readLine(String s, String t) {
      boolean flag = false;
      
   
   
   }
   
}