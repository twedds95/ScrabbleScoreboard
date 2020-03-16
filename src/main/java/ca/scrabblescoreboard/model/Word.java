/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.scrabblescoreboard.model;
import java.util.*;

// line 22 "../../../scrabbleScoreboard.ump"
public class Word
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Word Attributes
  private String spelling;
  private int points;

  //Word Associations
  private Player player;
  private List<Bonus> bonuses;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Word(String aSpelling, int aPoints, Player aPlayer)
  {
    spelling = aSpelling;
    points = aPoints;
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create word due to player");
    }
    bonuses = new ArrayList<Bonus>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSpelling(String aSpelling)
  {
    boolean wasSet = false;
    spelling = aSpelling;
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

  public String getSpelling()
  {
    return spelling;
  }

  public int getPoints()
  {
    return points;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetMany */
  public Bonus getBonus(int index)
  {
    Bonus aBonus = bonuses.get(index);
    return aBonus;
  }

  public List<Bonus> getBonuses()
  {
    List<Bonus> newBonuses = Collections.unmodifiableList(bonuses);
    return newBonuses;
  }

  public int numberOfBonuses()
  {
    int number = bonuses.size();
    return number;
  }

  public boolean hasBonuses()
  {
    boolean has = bonuses.size() > 0;
    return has;
  }

  public int indexOfBonus(Bonus aBonus)
  {
    int index = bonuses.indexOf(aBonus);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    if (aPlayer == null)
    {
      return wasSet;
    }

    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removeWord(this);
    }
    player.addWord(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBonuses()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addBonus(Bonus aBonus)
  {
    boolean wasAdded = false;
    if (bonuses.contains(aBonus)) { return false; }
    bonuses.add(aBonus);
    if (aBonus.indexOfWord(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBonus.addWord(this);
      if (!wasAdded)
      {
        bonuses.remove(aBonus);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeBonus(Bonus aBonus)
  {
    boolean wasRemoved = false;
    if (!bonuses.contains(aBonus))
    {
      return wasRemoved;
    }

    int oldIndex = bonuses.indexOf(aBonus);
    bonuses.remove(oldIndex);
    if (aBonus.indexOfWord(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBonus.removeWord(this);
      if (!wasRemoved)
      {
        bonuses.add(oldIndex,aBonus);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBonusAt(Bonus aBonus, int index)
  {  
    boolean wasAdded = false;
    if(addBonus(aBonus))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBonuses()) { index = numberOfBonuses() - 1; }
      bonuses.remove(aBonus);
      bonuses.add(index, aBonus);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBonusAt(Bonus aBonus, int index)
  {
    boolean wasAdded = false;
    if(bonuses.contains(aBonus))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBonuses()) { index = numberOfBonuses() - 1; }
      bonuses.remove(aBonus);
      bonuses.add(index, aBonus);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBonusAt(aBonus, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removeWord(this);
    }
    ArrayList<Bonus> copyOfBonuses = new ArrayList<Bonus>(bonuses);
    bonuses.clear();
    for(Bonus aBonus : copyOfBonuses)
    {
      aBonus.removeWord(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "spelling" + ":" + getSpelling()+ "," +
            "points" + ":" + getPoints()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}