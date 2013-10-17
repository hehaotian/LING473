public class trie {
   char letter;
   trie[] nodes;
   String result;
   
   public trie(char letter) {
      this.letter = letter;
      nodes = new trie[4];
   }
}