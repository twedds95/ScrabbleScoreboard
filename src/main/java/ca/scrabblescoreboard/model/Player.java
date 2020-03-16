/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.scrabblescoreboard.model;
import java.util.*;

// line 10 "../../../scrabbleScoreboard.ump"
public class Player
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { RED, CYAN, GREEN, YELLOW }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;
  private int points;
  private Color color;
  private int currentRanking;

  //Player Associations
  private List<Word> words;
  private List<RemainingLetters> remainingLetters;
  private Word currentWord;
  private Game game;

  //Helper Variables
  private boolean canSetColor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, int aPoints, int aCurrentRanking, Game aGame)
  {
    name = aName;
    points = aPoints;
    canSetColor = true;
    currentRanking = aCurrentRanking;
    words = new ArrayList<Word>();
    remainingLetters = new ArrayList<RemainingLetters>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPoints(int aPoints)
  {
    boolean wasSet = false;
    points = aPoints;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetImmutable */
  public boolean setColor(Color aColor)
  {
    boolean wasSet = false;
    if (!canSetColor) { return false; }
    canSetColor = false;
    color = aColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentRanking(int aCurrentRanking)
  {
    boolean wasSet = false;
    currentRanking = aCurrentRanking;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getPoints()
  {
    return points;
  }

  public Color getColor()
  {
    return color;
  }

  public int getCurrentRanking()
  {
    return currentRanking;
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
  /* Code from template association_GetMany */
  public RemainingLetters getRemainingLetter(int index)
  {
    RemainingLetters aRemainingLetter = remainingLetters.get(index);
    return aRemainingLetter;
  }

  public List<RemainingLetters> getRemainingLetters()
  {
    List<RemainingLetters> newRemainingLetters = Collections.unmodifiableList(remainingLetters);
    return newRemainingLetters;
  }

  public int numberOfRemainingLetters()
  {
    int number = remainingLetters.size();
    return number;
  }

  public boolean hasRemainingLetters()
  {
    boolean has = remainingLetters.size() > 0;
    return has;
  }

  public int indexOfRemainingLetter(RemainingLetters aRemainingLetter)
  {
    int index = remainingLetters.indexOf(aRemainingLetter);
    return index;
  }
  /* Code from template association_GetOne */
  public Word getCurrentWord()
  {
    return currentWord;
  }

  public boolean hasCurrentWord()
  {
    boolean has = currentWord != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWords()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Word addWord(String aSpelling, int aPoints)
  {
    return new Word(aSpelling, aPoints, this);
  }

  public boolean addWord(Word aWord)
  {
    boolean wasAdded = false;
    if (words.contains(aWord)) { return false; }
    Player existingPlayer = aWord.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
    if (isNewPlayer)
    {
      aWord.setPlayer(this);
    }
    else
    {
      words.add(aWord);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWord(Word aWord)
  {
    boolean wasRemoved = false;
    //Unable to remove aWord, as it must always have a player
    if (!this.equals(aWord.getPlayer()))
    {
      words.remove(aWord);
      wasRemoved = true;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRemainingLetters()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addRemainingLetter(RemainingLetters aRemainingLetter)
  {
    boolean wasAdded = false;
    if (remainingLetters.contains(aRemainingLetter)) { return false; }
    Player existingPlayer = aRemainingLetter.getPlayer();
    if (existingPlayer == null)
    {
      aRemainingLetter.setPlayer(this);
    }
    else if (!this.equals(existingPlayer))
    {
      existingPlayer.removeRemainingLetter(aRemainingLetter);
      addRemainingLetter(aRemainingLetter);
    }
    else
    {
      remainingLetters.add(aRemainingLetter);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRemainingLetter(RemainingLetters aRemainingLetter)
  {
    boolean wasRemoved = false;
    if (remainingLetters.contains(aRemainingLetter))
    {
      remainingLetters.remove(aRemainingLetter);
      aRemainingLetter.setPlayer(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRemainingLetterAt(RemainingLetters aRemainingLetter, int index)
  {  
    boolean wasAdded = false;
    if(addRemainingLetter(aRemainingLetter))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRemainingLetters()) { index = numberOfRemainingLetters() - 1; }
      remainingLetters.remove(aRemainingLetter);
      remainingLetters.add(index, aRemainingLetter);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRemainingLetterAt(RemainingLetters aRemainingLetter, int index)
  {
    boolean wasAdded = false;
    if(remainingLetters.contains(aRemainingLetter))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRemainingLetters()) { index = numberOfRemainingLetters() - 1; }
      remainingLetters.remove(aRemainingLetter);
      remainingLetters.add(index, aRemainingLetter);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRemainingLetterAt(aRemainingLetter, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentWord(Word aNewCurrentWord)
  {
    boolean wasSet = false;
    currentWord = aNewCurrentWord;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removePlayer(this);
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=words.size(); i > 0; i--)
    {
      Word aWord = words.get(i - 1);
      aWord.delete();
    }
    while( !remainingLetters.isEmpty() )
    {
      remainingLetters.get(0).setPlayer(null);
    }
    currentWord = null;
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "points" + ":" + getPoints()+ "," +
            "currentRanking" + ":" + getCurrentRanking()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentWord = "+(getCurrentWord()!=null?Integer.toHexString(System.identityHashCode(getCurrentWord())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}