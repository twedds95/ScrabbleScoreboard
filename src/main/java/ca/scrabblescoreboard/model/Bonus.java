/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.scrabblescoreboard.model;
import java.util.*;

// line 34 "../../../scrabbleScoreboard.ump"
public class Bonus
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Bonus Attributes
  private String optionName;
  private int multiplier;

  //Bonus Associations
  private List<Word> words;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Bonus(String aOptionName, int aMultiplier)
  {
    optionName = aOptionName;
    multiplier = aMultiplier;
    words = new ArrayList<Word>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMultiplier(int aMultiplier)
  {
    boolean wasSet = false;
    multiplier = aMultiplier;
    wasSet = true;
    return wasSet;
  }

  public String getOptionName()
  {
    return optionName;
  }

  public int getMultiplier()
  {
    return multiplier;
  }
  /* Code from template association_GetMany */
  public Word getWord(int index)
  {
    Word aWord = words.get(index);
    return aWord;
  }

  public List<Word> getWords()
  {
    List<Word> newWords = Collections.unmodifiableList(words);
    return newWords;
  }

  public int numberOfWords()
  {
    int number = words.size();
    return number;
  }

  public boolean hasWords()
  {
    boolean has = words.size() > 0;
    return has;
  }

  public int indexOfWord(Word aWord)
  {
    int index = words.indexOf(aWord);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWords()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addWord(Word aWord)
  {
    boolean wasAdded = false;
    if (words.contains(aWord)) { return false; }
    words.add(aWord);
    if (aWord.indexOfBonus(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aWord.addBonus(this);
      if (!wasAdded)
      {
        words.remove(aWord);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeWord(Word aWord)
  {
    boolean wasRemoved = false;
    if (!words.contains(aWord))
    {
      return wasRemoved;
    }

    int oldIndex = words.indexOf(aWord);
    words.remove(oldIndex);
    if (aWord.indexOfBonus(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aWord.removeBonus(this);
      if (!wasRemoved)
      {
        words.add(oldIndex,aWord);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWordAt(Word aWord, int index)
  {  
    boolean wasAdded = false;
    if(addWord(aWord))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWords()) { index = numberOfWords() - 1; }
      words.remove(aWord);
      words.add(index, aWord);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWordAt(Word aWord, int index)
  {
    boolean wasAdded = false;
    if(words.contains(aWord))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWords()) { index = numberOfWords() - 1; }
      words.remove(aWord);
      words.add(index, aWord);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWordAt(aWord, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Word> copyOfWords = new ArrayList<Word>(words);
    words.clear();
    for(Word aWord : copyOfWords)
    {
      aWord.removeBonus(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "optionName" + ":" + getOptionName()+ "," +
            "multiplier" + ":" + getMultiplier()+ "]";
  }
}