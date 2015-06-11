import java.util.Vector;
import java.util.Enumeration;
import java.util.Iterator;

//
// This class solves a Where's Wally (a.k.a. Where's Waldo) 9 Card
// puzzle.
//
public class Puzzle {


    // define the 9 cards
    public Card cards[] = {new Card(new Pic(Pic.WALLY,Pic.TAIL),
				    new Pic(Pic.WANDA,Pic.TAIL),
				    new Pic(Pic.WALLY,Pic.HEAD),
				    new Pic(Pic.WIZARD,Pic.HEAD)),
			   new Card(new Pic(Pic.WALLY,Pic.TAIL),
				    new Pic(Pic.WANDA,Pic.HEAD),
				    new Pic(Pic.WIZARD,Pic.HEAD),
				    new Pic(Pic.WOOF,Pic.TAIL)),
			   new Card(new Pic(Pic.WALLY,Pic.HEAD),
				    new Pic(Pic.WANDA,Pic.HEAD),
				    new Pic(Pic.WOOF,Pic.HEAD),
				    new Pic(Pic.WIZARD,Pic.TAIL)),
			   new Card(new Pic(Pic.WALLY,Pic.TAIL),
				    new Pic(Pic.WANDA,Pic.TAIL),
				    new Pic(Pic.WOOF,Pic.HEAD),
				    new Pic(Pic.WIZARD,Pic.HEAD)),
			   new Card(new Pic(Pic.WIZARD,Pic.TAIL),
				    new Pic(Pic.WALLY,Pic.HEAD),
				    new Pic(Pic.WANDA,Pic.HEAD),
				    new Pic(Pic.WOOF,Pic.TAIL)),
			   new Card(new Pic(Pic.WALLY,Pic.TAIL),
				    new Pic(Pic.WIZARD,Pic.HEAD),
				    new Pic(Pic.WALLY,Pic.HEAD),
				    new Pic(Pic.WOOF,Pic.TAIL)),
			   new Card(new Pic(Pic.WALLY,Pic.TAIL), // this card!
				    new Pic(Pic.WOOF,Pic.HEAD),
				    new Pic(Pic.WIZARD,Pic.HEAD),
				    new Pic(Pic.WALLY,Pic.HEAD)),
			   new Card(new Pic(Pic.WIZARD,Pic.TAIL),
				    new Pic(Pic.WOOF,Pic.HEAD),
				    new Pic(Pic.WANDA,Pic.HEAD),
				    new Pic(Pic.WOOF,Pic.TAIL)),
			   new Card(new Pic(Pic.WALLY,Pic.TAIL),
				    new Pic(Pic.WIZARD,Pic.TAIL),
				    new Pic(Pic.WOOF,Pic.TAIL),
				    new Pic(Pic.WANDA,Pic.TAIL))};

    private Card layout[] = new Card[9]; 

    private int tries = 0;
    private int found = 0;
    
    private static boolean debug = false;
    

    // default constructor
    public Puzzle(){

    }


    public void debug(String message) {
	if (debug)
	    System.out.println(message);
    }


    public Vector allCards(){

	Vector v = new Vector();
	for (int i = 0; i < cards.length; i = i + 1) {
	    v.add(cards[i]);
	}
	debug(v.size() + " cards.");
	return v;

    }


    // Remove cards that don't contain this picture
    public void keepCardsWithPicture(Vector v, Pic pic){

	debug("Keeping cards with " + pic);
	Iterator e = v.iterator();
	while(e.hasNext()){
	    Card thisCard = (Card) e.next();
	    if (!thisCard.containsPicture(pic)){
		e.remove();
		debug("Removing:\n" + thisCard);
	    }
	}
	debug(v.size() + " cards found.");

    }


    // convenience method to print the list of possible cards
    public void printCards(Vector v){
	
	Enumeration e = v.elements();
	while(e.hasMoreElements())
	    System.out.println(e.nextElement());
    }
    

    public void removalTest() {
	Vector v = allCards();
	Pic matchPic = new Pic(Pic.WALLY,Pic.TAIL);
	System.out.println("Matching for " + matchPic);
	matchPic.setPart(matchPic.matchingPart());
	keepCardsWithPicture(v, matchPic);
	System.out.println("Possible cards:");
	printCards(v);
    }


    public void editCards(Vector possCards, int position, int side) {

	Pic matchPic = layout[position - 1].getPicture(side);
	debug("Matching for " + matchPic);
	Pic findPic = new Pic(matchPic.getCharacter(),
			      matchPic.matchingPart()); 
	keepCardsWithPicture(possCards, findPic);
	debug("Possible cards:");
	if (debug)
	    printCards(possCards);

    }


    public void addCard(Vector availCards, int position) {

	tries = tries + 1;

	debug("Available cards = " + availCards.size());

	// test for success?
	if (position == 9) {
	    found = found + 1;
	    System.out.println("\nSolution " + found + ":");
	    printLayout();
	    return;
	}

	// edit the set of possible cards
	Vector possCards = (Vector) availCards.clone();
	Pic findPic      = null;
	int side         = -1;

	// keep cards that match with card to the left
	if (position != 3 && position != 6) {  
	    side = Card.RIGHT; // picture is on right side of the card to the left
	    findPic = new Pic((layout[position - 1].getPicture(side)).getCharacter(),
			      (layout[position - 1].getPicture(side)).matchingPart()); 
	    debug("Looking for cards with " + findPic + 
		  " to match left.");
	    keepCardsWithPicture(possCards, findPic);
	}

	// keep cards that match with card above
	if (position != 1 && position != 2) { 
	    side = Card.BOTTOM;	// picture is on bottom side of the card to the top    
	    findPic = new Pic((layout[position - 3].getPicture(side)).getCharacter(),
			      (layout[position - 3].getPicture(side)).matchingPart()); 
	    debug("Looking for cards with " + findPic + 
		  " to match above.");
	    keepCardsWithPicture(possCards, findPic);
	}

	debug("Possible cards:");
	if (debug)
	    printCards(possCards);
	
	//try a matching card
	Iterator e = possCards.iterator();
	while(e.hasNext()){
	    Card thisCard = (Card) e.next();              // get next possible card
	    thisCard.rotatePictureTo(findPic, Card.opposite(side)); // rotate card
	    layout[position] = thisCard;
	    if (debug) 
		printLayout();
	    
	    // check that both sides match for cards in positions 4, 5, 7 & 8
	    if (position == 4 || position == 5 || 
		position == 7 || position == 8) {  
		boolean leftOK = false; 
		boolean aboveOK = false;
		
		debug("Checking surrounding cards...");
		debug(layout[position - 1].getPicture(Card.RIGHT) + 
		      "< >" + layout[position].getPicture(Card.LEFT));
		debug(layout[position - 3].getPicture(Card.BOTTOM) + 
		      "^ v" + layout[position].getPicture(Card.TOP));


		if (layout[position].getPicture(Card.LEFT)
		    .matches(layout[position - 1].getPicture(Card.RIGHT))){
		    debug("to left OK");
		    leftOK = true;
		}

		if (layout[position].getPicture(Card.TOP)
		    .matches(layout[position - 3].getPicture(Card.BOTTOM))){
		    debug("above OK");
		    aboveOK = true;
		}

		if (!(leftOK && aboveOK)) {
		    debug("SIDES DON'T MATCH!!!");
		    layout[position] = null;
		    return;
		}
	    }

	    // remove this card from avail cards
	    Vector newCards = (Vector) availCards.clone();
	    newCards.remove(thisCard);

	    // try to add another card
	    addCard(newCards, position + 1);
	    // try again

	}

    }
    

    public void solve(){
	// add first card
	for (int i = 0; i < cards.length; i = i + 1) {

	    // clear layout
	    for (int j = 0; j < layout.length; j = j + 1) {
		layout[j] = null;
	    }

	    debug("Trying card " + i + " in first position");
	    layout[0] = cards[i];
	    if (debug) 
		printLayout();

	    for (int rot = 0; rot < 4; rot = rot + 1) {
		// try adding more cards

		// first add all cards
		Vector availCards = allCards();
		availCards.remove(cards[i]); //remove this card!
		
		addCard(availCards, 1);
		
		// Only the first card needs to be rotated and tried with a 
		// different orientation.  All other cards rotations are then 
		// determined by matching with already laid cards
		debug("Rotating first card");
		layout[0].rotate();
		// clear rest of layout
		for (int j = 1; j < layout.length; j = j + 1) {
		    layout[j] = null;
		}
		
	    }	    
	}
    }
    

    public void printLayout(){
	printRow(0);
	printRow(3);
	printRow(6);
	System.out.print("+-----------+-----------+-----------+\n");
    }


    // print a single row of cards
    public void printRow(int offset){

	System.out.print("+-----------+-----------+-----------+\n");
	for (int i = offset; i < (offset + 3); i = i + 1) {
	    if (layout[i] != null) 
		System.out.print("|   " + layout[i].getPicture(Card.TOP) + "   ");
	    else
		System.out.print("|           ");
	}
	System.out.print("|\n");
	System.out.print("|           |           |           |\n");
	System.out.print("|           |           |           |\n");
	for (int i = offset; i < (offset + 3); i = i + 1) {
	    if (layout[i] != null) 
		System.out.print("|" + layout[i].getPicture(Card.LEFT) + " " + 
				 layout[i].getPicture(Card.RIGHT));
	    else
		System.out.print("|           ");
	}
	System.out.print("|\n");
	System.out.print("|           |           |           |\n");
	System.out.print("|           |           |           |\n");
	for (int i = offset; i < (offset + 3); i = i + 1) {
	    if (layout[i] != null) 
		System.out.print("|   " + layout[i].getPicture(Card.BOTTOM) + "   ");
	    else
		System.out.print("|           ");
	    
	}
	System.out.print("|\n");
    }


    public static void main(String args[]){

	if (args.length == 1) {
	    if (args[0].equals("-d")){
		debug = true;
	    }
	}
		    
	Puzzle puzzle = new Puzzle();
	//puzzle.removalTest();
	puzzle.solve();
	System.out.println("\n" + puzzle.tries + " tries!");
	System.out.println("There are 4^9 * 9! = 95,126,814,720 possible layouts!");

    }

}
