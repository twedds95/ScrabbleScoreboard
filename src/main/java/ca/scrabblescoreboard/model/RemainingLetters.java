/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.scrabblescoreboard.model;

// line 28 "../../../scrabbleScoreboard.ump"
public class RemainingLetters
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RemainingLetters Attributes
  private String letters;
  private String points;

  //RemainingLetters Associations
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RemainingLetters(String aLetters, String aPoints)
  {
    letters = aLetters;
    points = aPoints;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLetters(String aLetters)
  {
    boolean wasSet = false;
    letters = aLetters;
    wasSet = true;
    return wasSet;
  }

  public boolean setPoints(String aPoints)
  {
    boolean wasSet = false;
    points = aPoints;
    wasSet = true;
    return wasSet;
  }

  public String getLetters()
  {
    return letters;
  }

  public String getPoints()
  {
    return points;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removeRemainingLetter(this);
    }
    if (aPlayer != null)
    {
      aPlayer.addRemainingLetter(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (player != null)
    {
      Player placeholderPlayer = player;
      this.player = null;
      placeholderPlayer.removeRemainingLetter(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "letters" + ":" + getLetters()+ "," +
            "points" + ":" + getPoints()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}