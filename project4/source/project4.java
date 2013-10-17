// Haotian He collaborated with Jonggun Park
// 1261169
// LING 473 Project 4
// 08/31/2013

import java.io.*;
import java.util.*;

public class project4 {

   public static void main(String[] args) throws IOException, FileNotFoundException {
      
      File target = new File("/dropbox/12-13/473/project4/targets");
      Scanner input = new Scanner(target);
      
      File outputText = new File("extra-credit.txt");
      PrintStream output = new PrintStream(outputText);

      File pathName = new File("/dropbox/12-13/473/project4/hg19-GRCh37/");
      trie prefix = new trie('\0');
      String line = "";
      char[] pattern;
      
      Map<String, Set<String>> report = new HashMap<String, Set<String>>();
      
      for (File f : pathName.listFiles()) {
      
         if (!f.isDirectory()) {
      
            if (f.getAbsolutePath().contains("chr")) {
               System.out.println(f.getAbsolutePath());
               InputStream inputGenome = new BufferedInputStream(new FileInputStream(f.getAbsolutePath()));
      
               while (input.hasNextLine()) {
                  line = input.nextLine();
                  pattern = line.toCharArray();
                  add(prefix, pattern, line);
               }
      
               report = search(prefix, inputGenome, report, f.getName());  
      
            }   
      
         }
      }
      
      for (String key : report.keySet()) {
         output.println(key);
        
         for (String s : report.get(key)) {
            output.println("\t" + s);
         }
      
      }
   }
   
   public static void add(trie root, char[] pattern, String line) {
     
      trie current = root;
     
      for (int n = 0; n < line.length(); n++) {
     
         if (current.nodes[0] == null) {
            current.nodes[0] = new trie(pattern[n]);
            current = current.nodes[0];
         } else if (current.nodes[1] == null && current.nodes[0].letter != pattern[n] ) {
            current.nodes[1] = new trie(pattern[n]);
            current = current.nodes[1];
         } else if (current.nodes[2] == null && current.nodes[0].letter != pattern[n] &&
         current.nodes[1].letter != pattern[n]) {
            current.nodes[2] = new trie(pattern[n]);
            current = current.nodes[2];
         } else if (current.nodes[3] == null && current.nodes[0].letter != pattern[n] &&
         current.nodes[1].letter != pattern[n] && current.nodes[2].letter != pattern[n]) {
            current.nodes[3] = new trie(pattern[n]);
            current = current.nodes[3];
         }  else {
     
            if (current.nodes[0].letter == pattern[n]) {
               current = current.nodes[0];
            } else if (current.nodes[0] != null && current.nodes[1].letter == pattern[n]) {
               current = current.nodes[1];
            } else if (current.nodes[0] != null && current.nodes[1] != null && current.nodes[2] != null &&
            current.nodes[2].letter == pattern[n]) {
               current = current.nodes[2];
            } else if (current.nodes[0] != null && current.nodes[1] != null && current.nodes[2] != null &&
            current.nodes[3] != null && current.nodes[3].letter == pattern[n]) {
               current = current.nodes[3];
       
            }
       
         }
      }
   }
   
   public static Map<String, Set<String>> search(trie root, InputStream inputGenome, Map<String, Set<String>> extraReport, String dna) throws IOException {
      
      trie overallRoot = root;
      return searchHelper(root, overallRoot, "", inputGenome, extraReport, dna);
   
   }

   public static Map<String, Set<String>> searchHelper(trie root, trie overallRoot, String result, InputStream inputGenome, Map<String, Set<String>> extraReport, String dna) throws IOException {
      
      trie current = root;
      int b;
      String checking = "";
      int index = -1;
      int targetLength;
      
      while ((b = inputGenome.read()) != -1) {
         checking = ((char) b) + "";
         index++;
         checking = checking.toUpperCase();
         
         if (current.nodes[0] != null && current.nodes[0].letter == checking.charAt(0)) {
            result += checking.charAt(0);
            current = current.nodes[0];
         } else if(current.nodes[1] != null && current.nodes[1].letter == checking.charAt(0)) {
            result += checking.charAt(0);
            current = current.nodes[1];
         } else if(current.nodes[2] != null && current.nodes[2].letter == checking.charAt(0)) {
            result += checking.charAt(0);
            current = current.nodes[2];
         } else if(current.nodes[3] != null && current.nodes[3].letter == checking.charAt(0)) {
            result += checking.charAt(0);
            current = current.nodes[3];
         } else if (current.nodes[0].letter != checking.charAt(0)) {
            current = overallRoot;
            result = "";
         }
         
         if (current.nodes[0] == null && current.nodes[1] == null && current.nodes[2] == null
         && current.nodes[3] == null) {
         
            if (!(extraReport.containsKey(result))) {
               extraReport.put(result, new HashSet<String>());
               extraReport.get(result).add(index + "\t" + dna);
            } else {
               extraReport.get(result).add(index + "\t" + dna);
            }
         
            System.out.println("\t" + index + "\t" + result);
            targetLength = result.length();
            current = overallRoot;
            result = "";
         }
      }
      
      inputGenome.close();
      return extraReport;
   
   }

}