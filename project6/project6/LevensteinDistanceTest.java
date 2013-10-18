package edu.uw.iprovalov.ling473.project6;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class LevensteinDistanceTest {

  private static final Object GAGA_TEST = "                                                                                           Lady GaGa\t0.00\tLady GaGa\n"
      + "                                                    Joanne Stefani Germanotta (born March 20, 1986),\t0.32\tStefani Joanne Angelina Germanotta (born March\n"
      + "                                                             best known by her stage name Lady GaGa,\t0.19\t28, 1986), known by her stage name Lady Gaga,\n"
      + "                                                        is an Italian-American singer-songwriter and\t0.40\tis an American recording artist. She began\n"
      + "                                                                                                    \t1.00\tperforming in the rock music scene of New York\n"
      + "                                                                                                    \t1.00\tCity's Lower East Side in 2003 and enrolled at\n"
      + "                                                                                                    \t1.00\tNew York University's Tisch School of the\n"
      + "                                                            dance-pop/electronica musician signed to\t0.67\tArts. She soon signed with Streamline Records,\n"
      + "                                                                                 Interscope Records.\t0.36\tan imprint of Interscope Records. During\n"
      + "                                                                                                    \t1.00\ther early time at Interscope, she worked as a\n"
      + "                                                                                                    \t1.00\tsongwriter for fellow label artists and\n"
      + "                                                                                                    \t1.00\tcaptured the attention of Akon, who recognized\n"
      + "                                                                                                    \t1.00\ther vocal abilities, and signed her to\n"
      + "                                                                                                    \t1.00\this own label, Kon Live Distribution.\n"
      + "                                                                                                    \t0.00\t \n"
      + "                                                      Lady Gaga at Sommarkrysset, Stockholm, Sweden.\t0.70\tGaga performing at The Monster Ball Tour in April 2010\n"
      + "                                                                              Background information\t0.00\tBackground information\n"
      + "                                                                 Birth name Stefani Germanotta (lol)\t0.28\tBirth name Stefani Joanne Angelina Germanotta\n"
      + "                                                           Born March 20, 1986 (1986-03-20) (age 24)\t0.05\tBorn March 28, 1986 (1986-03-28) (age 24)\n"
      + "                                                                                                    \t1.00\tNew York City, New York,\n"
      + "                                                                                                    \t1.00\tUnited States[1]\n"
      + "                                                             Origin Yonkers, New York, United States\t0.65\tGenres Pop, electronic, dance\n"
      + "                                                                      Occupations Singer, songwriter\t0.05\tOccupations Singer-songwriter\n"
      + "                                                                                                    \t1.00\tInstruments Vocals, piano, synthesizer, keytar\n"
      + "                                                                         Years active 2007 - present\t0.08\tYears active 2006-present\n"
      + "                                                             Labels Streamline, Kon Live, Interscope\t0.21\tLabels Def Jam, Streamline, Kon Live, Cherrytree, Interscope\n"
      + "                                                                            Website www.ladygaga.com\t0.00\tWebsite www.ladygaga.com\n"
      + "                                                                                                    \t0.00\t \n"
      + "                                                                                     Contents [hide]\t0.00\tContents [hide]\n"
      + "                                                                                         1 Biography\t0.71\t1 Life and career\n"
      + "                                                                                      1.1 Early life\t0.28\t1.1 1986-2004: Early life\n"
      + "                                                                                                    \t1.00\t1.2 2005-07: Career beginnings\n"
      + "                                                                                    1.2 Music career\t0.71\t1.3 2008-present: The Fame and The Fame Monster\n"
      + "                                                                                2 Critical reception\t0.60\t2 Musical style and influences\n"
      + "                                                                   3 TV appearances and performances\t0.76\t2.1 Public image\n"
      + "                                                                                       4 Discography\t0.08\t3 Discography\n"
      + "                                                                                   4.1 Studio albums\t0.58\t4 Tours\n"
      + "                                                                                      5 Music Videos\t0.63\t5 Awards and nominations\n"
      + "                                                                           6 Notable writing credits\t0.57\t6 References\n"
      + "                                                                                        7 References\t0.59\t7 Further reading\n"
      + "                                                                                    8 External links\t0.00\t8 External links\n";

  private LevensteinDistance ld;

  @Before
  public void set() {
    ld = new LevensteinDistance();
  }

  @Test
  public void test1() {
    assertEquals((double) 8, ld.levensteinDistance("intention", "execution"), 0.01);
  }

  @Test
  public void test2() {
    assertEquals((double) 3, ld.levensteinDistance("abcde", "bd"), 0.01);
  }

  @Test
  public void testCornerCases() {
    assertEquals((double) 5, ld.levensteinDistance("abcde", ""), 0.01);
    assertEquals((double) 0, ld.levensteinDistance("", ""), 0.01);
    assertEquals((double) 0, ld.levensteinDistance("abcde", "abcde"), 0.01);
    assertEquals((double) 5, ld.levensteinDistance("", "abcde"), 0.01);
  }

  @Test
  public void test3() {
    assertEquals((double) 0.4286, ld.levensteinDistanceNormalized("abcde", "bd"), 0.01);
  }

  @Test
  public void test4() {
    assertEquals((double) 1.0, ld.levensteinDistanceNormalized("abcde", ""), 0.01);
  }

  @Test
  public void test5() {
    assertEquals((double) 0.0, ld.levensteinDistanceNormalized("", ""), 0.01);
  }

  @Test
  public void test6() {
    assertEquals((double) 0.0, ld.levensteinDistanceNormalized("abc", "abc"), 0.01);
  }

  @Test
  public void test8() {
    assertEquals((double) 0.32, ld.levensteinDistanceNormalized("Joanne Stefani Germanotta (born March 20, 1986),",
        "Stefani Joanne Angelina Germanotta (born March"), 0.01);
  }

  @Test
  public void test9() {
    assertEquals((double) 0.19, ld.levensteinDistanceNormalized("best known by her stage name Lady GaGa,",
        "28, 1986), known by her stage name Lady Gaga,"), 0.01);
  }

  @Test
  public void test10() {
    assertEquals((double) 0.40, ld.levensteinDistanceNormalized("is an Italian-American singer-songwriter and",
        "is an American recording artist. She began"), 0.01);
  }

  @Test
  public void test11() throws Exception {
    assertEquals(((double) 0.5 + (double) 0.4286) / ((double) 2 + (double) 2),
        ld.levensteinDistanceStringsNormalized(new String[] { "abcde", "" }, new String[] { "bd", "" }), 0.01);
  }

  @Test
  public void test12() throws Exception {
    assertEquals(((double) 0.5 + (double) 0.4286 + (double) 0.5 + (double) 0.4286) / ((double) 2 + (double) 2),
        ld.levensteinDistanceStringsNormalized(new String[] { "abcde", "bd" }, new String[] { "bd", "abcde" }), 0.01);
  }

  @Test
  public void test13() {
    assertEquals((double) 0.2380, ld.levensteinDistanceNormalized("mismatch", "some mismatch"), 0.01);
  }

  @Test
  public void testIntegration() throws Exception {
    assertEquals((double) 0.45760480772406,
        ld.levensteinDistanceFromFiles("data/levenstein/gaga0.txt", "data/levenstein/gaga1.txt"), 0.01);
    assertEquals(GAGA_TEST,
        ld.align(ld.list1.toArray(new String[ld.list1.size()]), ld.list2.toArray(new String[ld.list2.size()]), 100));
  }

  @Test
  public void testAlign() throws Exception {
    String[] s = new String[] { "abcde", "bd", "", "match" };
    String[] t = new String[] { "bd", "abcde", "complete mismatch", "match" };
    ld.levensteinDistanceStrings(s, t);
    assertEquals("abcde\t0.43\tbd\nbd\t0.43\tabcde\n\t1.00\tcomplete mismatch\nmatch\t0.00\tmatch\n", ld.align(s, t, 0));
  }

  @Test
  public void testAlignWhenSourceGoesSideways() throws Exception {
    String[] s = new String[] { "abcde", "bd", "mismatch", "match" };
    String[] t = new String[] { "bd", "abcde", "some mismatch", "not match", "match" };
    ld.levensteinDistanceStrings(s, t);
    assertEquals("abcde\t0.43\tbd\n" + "bd\t0.43\tabcde\n" + "mismatch\t0.24\tsome mismatch\n" + "\t1.00\tnot match\n"
        + "match\t0.00\tmatch\n", ld.align(s, t, 0));
  }

  @Test
  public void testAlignWhenTargetGoesUp() throws Exception {
    String[] s = new String[] { "bd", "abcde", "some mismatch", "not match", "match" };
    String[] t = new String[] { "abcde", "bd", "mismatch", "match" };
    ld.levensteinDistanceStrings(s, t);
    assertEquals("bd\t0.43\tabcde\n" + "abcde\t0.43\tbd\n" + "some mismatch\t0.24\tmismatch\n" + "not match\t1.00\t \n"
        + "match\t0.00\tmatch\n", ld.align(s, t, 0));
  }

  @Test
  public void testLastElementNotMatching() {
    String[] s = new String[] { "bd", "abcde", "some mismatch", "not match", "not matching" };
    String[] t = new String[] { "abcde", "bd", "mismatch", "last not matching" };
    ld.levensteinDistanceStrings(s, t);
    assertEquals("bd\t0.43\tabcde\n" + "abcde\t0.43\tbd\n" + "some mismatch\t0.24\tmismatch\n" + "not match\t1.00\t \n"
        + "not matching\t0.17\tlast not matching\n", ld.align(s, t, 0));

  }
  
}
