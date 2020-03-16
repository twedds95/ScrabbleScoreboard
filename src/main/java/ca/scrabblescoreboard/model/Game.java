/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.scrabblescoreboard.model;
import java.util.*;

// line 3 "../../../scrabbleScoreboard.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private int numberOfPlayers;

  //Game Associations
  private List<Player> players;
  private Player currentPlayer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(int aNumberOfPlayers)
  {
    numberOfPlayers = aNumberOfPlayers;
    players = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumberOfPlayers(int aNumberOfPlayers)
  {
    boolean wasSet = false;
    numberOfPlayers = aNumberOfPlayers;
    wasSet = true;
    return wasSet;
  }

  public int getNumberOfPlayers()
  {
    return numberOfPlayers;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public Player getCurrentPlayer()
  {
    return currentPlayer;
  }

  public boolean hasCurrentPlayer()
  {
    boolean has = currentPlayer != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Player addPlayer(String aName, int aPoints, int aCurrentRanking)
  {
    return new Player(aName, aPoints, aCurrentRanking, this);
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    Game existingGame = aPlayer.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aPlayer.setGame(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a game
    if (!this.equals(aPlayer.getGame()))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentPlayer(Player aNewCurrentPlayer)
  {
    boolean wasSet = false;
    currentPlayer = aNewCurrentPlayer;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=players.size(); i > 0; i--)
    {
      Player aPlayer = players.get(i - 1);
      aPlayer.delete();
    }
    currentPlayer = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "numberOfPlayers" + ":" + getNumberOfPlayers()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentPlayer = "+(getCurrentPlayer()!=null?Integer.toHexString(System.identityHashCode(getCurrentPlayer())):"null");
  }
}