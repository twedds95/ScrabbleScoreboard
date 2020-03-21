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
  private boolean invalidChallenge;
  private Color color;
  private int currentRanking;

  //Player Associations
  private List<Word> words;
  private RemainingLetters remainingLetters;
  private Word currentWord;
  private Game game;

  //Helper Variables
  private boolean canSetColor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, Game aGame)
  {
    name = aName;
    points = 0;
    invalidChallenge = false;
    canSetColor = true;
    currentRanking = 1;
    words = new ArrayList<Word>();
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

  public boolean setInvalidChallenge(boolean aInvalidChallenge)
  {
    boolean wasSet = false;
    invalidChallenge = aInvalidChallenge;
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

  public boolean getInvalidChallenge()
  {
    return invalidChallenge;
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
  /* Code from template association_GetOne */
  public RemainingLetters getRemainingLetters()
  {
    return remainingLetters;
  }

  public boolean hasRemainingLetters()
  {
    boolean has = remainingLetters != null;
    return has;
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
  /* Code from template association_AddManyToOptionalOne */
  public boolean addWord(Word aWord)
  {
    boolean wasAdded = false;
    if (words.contains(aWord)) { return false; }
    Player existingPlayer = aWord.getPlayer();
    if (existingPlayer == null)
    {
      aWord.setPlayer(this);
    }
    else if (!this.equals(existingPlayer))
    {
      existingPlayer.removeWord(aWord);
      addWord(aWord);
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
    if (words.contains(aWord))
    {
      words.remove(aWord);
      aWord.setPlayer(null);
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
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setRemainingLetters(RemainingLetters aNewRemainingLetters)
  {
    boolean wasSet = false;
    if (aNewRemainingLetters == null)
    {
      RemainingLetters existingRemainingLetters = remainingLetters;
      remainingLetters = null;
      
      if (existingRemainingLetters != null && existingRemainingLetters.getPlayer() != null)
      {
        existingRemainingLetters.setPlayer(null);
      }
      wasSet = true;
      return wasSet;
    }

    RemainingLetters currentRemainingLetters = getRemainingLetters();
    if (currentRemainingLetters != null && !currentRemainingLetters.equals(aNewRemainingLetters))
    {
      currentRemainingLetters.setPlayer(null);
    }

    remainingLetters = aNewRemainingLetters;
    Player existingPlayer = aNewRemainingLetters.getPlayer();

    if (!equals(existingPlayer))
    {
      aNewRemainingLetters.setPlayer(this);
    }
    wasSet = true;
    return wasSet;
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
    while( !words.isEmpty() )
    {
      words.get(0).setPlayer(null);
    }
    if (remainingLetters != null)
    {
      remainingLetters.setPlayer(null);
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
            "invalidChallenge" + ":" + getInvalidChallenge()+ "," +
            "currentRanking" + ":" + getCurrentRanking()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "remainingLetters = "+(getRemainingLetters()!=null?Integer.toHexString(System.identityHashCode(getRemainingLetters())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentWord = "+(getCurrentWord()!=null?Integer.toHexString(System.identityHashCode(getCurrentWord())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}