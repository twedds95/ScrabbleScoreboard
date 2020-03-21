/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.scrabblescoreboard.model;

// line 29 "../../../scrabbleScoreboard.ump"
public class RemainingLetters
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RemainingLetters Attributes
  private String letters;
  private int points;

  //RemainingLetters Associations
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RemainingLetters(String aLetters, int aPoints)
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

  public boolean setPoints(int aPoints)
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

  public int getPoints()
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
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    if (aNewPlayer == null)
    {
      Player existingPlayer = player;
      player = null;
      
      if (existingPlayer != null && existingPlayer.getRemainingLetters() != null)
      {
        existingPlayer.setRemainingLetters(null);
      }
      wasSet = true;
      return wasSet;
    }

    Player currentPlayer = getPlayer();
    if (currentPlayer != null && !currentPlayer.equals(aNewPlayer))
    {
      currentPlayer.setRemainingLetters(null);
    }

    player = aNewPlayer;
    RemainingLetters existingRemainingLetters = aNewPlayer.getRemainingLetters();

    if (!equals(existingRemainingLetters))
    {
      aNewPlayer.setRemainingLetters(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (player != null)
    {
      player.setRemainingLetters(null);
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