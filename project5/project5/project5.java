// Haotian He collaborated with Jonggun Park
// 1261169
// LING 473 Project 5
// 09/03/2013

import java.util.*;
import java.io.*;
import com.google.common.collect.*;
import java.lang.*;

public class project5 {
   
   public static void main(String[] args) throws IOException {
   
      String oneLanguage = "";
      Table<String, String, Integer> table = HashBasedTable.create(); 
      Map<String, Integer> allLanguages = new HashMap<String, Integer>(); 
      Map<String, Double> languageResults = new TreeMap<String, Double>(); 
      Table<String, Double, Double> muSd = TreeBasedTable.create(); 
      Map<String, Double> finalOutput = new TreeMap<String, Double>(); 
      
      File pathName = new File("language-models/");
      int totalCount = 0;
      for (File language : pathName.listFiles()) {
         if (!language.isDirectory()) {
            if (language.getAbsolutePath().contains("unigram")){
               oneLanguage = language.getName().substring(0, 3);
               
               Scanner inputLanguage = new Scanner(language); 
               while (inputLanguage.hasNextLine()) {
                  if (inputLanguage.hasNextLine()) {
                     String oneLine = inputLanguage.nextLine();
                     Scanner token = new Scanner(oneLine);
                     while (token.hasNext()) {
                        String words = token.next();
                        int tally = Integer.parseInt(token.next());
                        table.put(oneLanguage, words, tally); 
                        totalCount += tally;
                     }
                  }
               }
               allLanguages.put(oneLanguage, totalCount); 
            }
         }
      }
      
      File test = new File("train.txt");
      Scanner input = new Scanner(test);
      String testing = "";
      while (input.hasNextLine()) {
         if (input.hasNextLine()) {
            testing = input.nextLine(); 
            testing = testing.replaceAll("[.,!Á´$£?À;:()/ÑÐ'123ÇÈ\"]","");
            Scanner languageTrainSentence = new Scanner(testing);
            int lang = 0;
            String tokenLang = "";
            String tokenTrain = "";
            double trainCalculation = 0;
            double notFound = 0;
            int v = 0;
            while (languageTrainSentence.hasNext()) {
               v++;  
               if (lang == 0) { 
                  tokenLang = languageTrainSentence.next();
                  lang++;
               } else if (languageTrainSentence.hasNext() && lang > 0) {
                  tokenTrain = languageTrainSentence.next();
               }
               if (table.contains(tokenLang, tokenTrain)) { // the probability sum of the train-text.
                  trainCalculation += Math.log((((double) table.get(tokenLang, tokenTrain)) + 1 / ((double) allLanguages.get(tokenLang)) + v));
                  languageResults.put(tokenLang ,trainCalculation);
               } else { // smoothing goes here.
                  notFound = Math.log((1 / ((double) allLanguages.get(tokenLang)) + v));
                  if (languageResults.get(tokenLang) != null) {
                     notFound += languageResults.get(tokenLang); // add the smoothing to the previous probability value.
                  }
                  languageResults.put(tokenLang, notFound);
               }
            }
         }
         muSd = wordCount(testing, muSd); // take each line and the muSd map.
      }
      testing(muSd, finalOutput, allLanguages, table);
   }
   // get the "mu" and the "standard deviation".
   public static double normalDistribution(double x, double mu, double sg) {
      return Math.log(Math.pow(1/(sg * Math.sqrt(2 * Math.PI)), -(Math.pow(x - mu, 2)) / (2 * Math.pow(sg, 2))));
   }
   public static double sigma(double word, double mu) {
      return Math.sqrt(mu * Math.pow(word - mu, 2));
   }
   // table - word(in the train set) , sigma, mu.
   // Total count of each word in the line.
   public static Table<String, Double, Double> wordCount(String line, Table<String, Double, Double> table) {
      Scanner wordCounts = new Scanner(line);
      Map<String, Integer> wordTally = new HashMap<String, Integer>();
      String temp = "";
      String lang = "";
      int first = 0;
      int v = 0;
      while (wordCounts.hasNext()) {
         if (wordCounts.hasNext()) {
            if (first == 0) {
               lang = wordCounts.next();
               first++;
            } else {
               v++;
               temp = wordCounts.next();
               if (wordTally.containsKey(temp)) {
                  wordTally.put(lang, wordTally.get(lang) + 1);
               } else {
                  wordTally.put(lang, 1);
               }
            }
         }
      }
      // sigma then mu
      double muTemp = 0;
      muTemp = (double) wordTally.get(lang) / v;
      table.put(lang, sigma(wordTally.get(lang), muTemp), muTemp);
      return table;
   }
   
   public static void testing(Table<String, Double, Double> muSd, Map<String, Double> finalOutput,
   Map<String, Integer> allLanguages, Table<String, String, Integer> table) throws IOException {
      File testData = new File("test.txt");
      Scanner testing = new Scanner(testData);
      Map<Double, Double> quick = new TreeMap<Double, Double>();
      String temp = "";
      String highLang = "";
      int identifier = 0;
      while (testing.hasNextLine()) { // next line in the Test.txt.
         if (testing.hasNextLine()) {
            temp = testing.nextLine();
            temp = temp.replaceAll("[.,!Á´$£?À;:()/ÑÐ'ÇÈ\"]","");
            Scanner token = new Scanner(temp);
            int start = 0;
            int v = 0;
            while (token.hasNext()) {
               if (token.hasNext()) {
                  if (start == 0) { // first token is always a number.
                     start++;
                     identifier = token.nextInt();
                  } else {
                     v++;
                     String testingWord = token.next(); // get the next token.
                     double normal = 0;
                     for (String language : allLanguages.keySet()) { // for every language
                        if (table.contains(language, testingWord)) { // if the word in the test.txt is in the language
                           quick = muSd.row(language); // get the mu and the standard deviation of that language..
                           for (Double key : quick.keySet()) { // get the mu and the standard deviation of that language.
                              normal = normalDistribution(table.get(language, testingWord) / allLanguages.get(language),
                              quick.get(key), key); // get the normal distribution.
                              if (finalOutput.get(language) != null) {
                                 finalOutput.put(language, (finalOutput.get(language) + normal));
                              } else {
                                 finalOutput.put(language, normal);
                              }
                           }
                        }  else { //smoothing......
                           quick = muSd.row(language); // get the mu and the standard deviation of that language..
                           for (Double key : quick.keySet()) { // get the mu and the standard deviation of that language.
                              normal = normalDistribution((double)1 / allLanguages.get(language),
                              quick.get(key), key); // get the normal distribution.
                              if (finalOutput.get(language) != null) {
                                 finalOutput.put(language, (finalOutput.get(language) + ((double)1/allLanguages.get(language)) ));
                              } else {
                                 finalOutput.put(language, ((double)1/allLanguages.get(language)));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
         Double high = -.0000009;
         System.out.println(temp); // print the line we are testing.
         for (String p : finalOutput.keySet()) { // for every langauge in the finalOuput..
            System.out.println(p + "\t" + finalOutput.get(p));    // print the language. and the calculation
            if (finalOutput.get(p) < high) { // get the highest language
               high = finalOutput.get(p);
               highLang = p;
            } else if (finalOutput.get(p) == high) {
               highLang = "unknown";
            }
         }
         System.out.println("result" + "\t" + highLang);
         finalOutput.clear(); // reset
      }
   }
   
}