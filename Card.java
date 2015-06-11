// This class implements a single card containing half a picture on
// each side for a 9 card puzzle matching game like Where's Wally or
// Das Hundespiele

// This interface provides values to represent the four sides of the card
interface CardSides {

    public static final int TOP    = 0; 
    public static final int LEFT   = 1;
    public static final int BOTTOM = 2; 
    public static final int RIGHT  = 3;

    public static final String[] SIDE = {
	"BOTTOM",
	"LEFT",
	"TOP",
	"RIGHT"
    };
}


class Card implements CardSides{

    // the offset is used so a default card with a default orientation can 
    // be defined.  Rotations are performed by incrementing the offset
    public int offset = 0; 

    // the pictures for each side
    public Pic[] pictures = new Pic[4];


    // default constructor
    public Card () {
     for (int i = 0; i < pictures.length; i = i + 1) {
	pictures[i] = new Pic();
     }	
    }


    // create a card with the corresponding pictures on each side
    public Card(Pic top,
		Pic right,
		Pic bottom,
		Pic left) {

	pictures[TOP]    = top;
	pictures[RIGHT]  = right;
	pictures[BOTTOM] = bottom;
	pictures[LEFT]   = left;

    }


    // rotate the card by 90 degrees.  Note that this assumes a
    // clockwise rotation
    public void rotate() {
	offset = offset + 1;
    }
    

    // reset the card to its original orientation
    public void resetOrientation(){
	offset = 0;
    }


    // set the picture on this side of the card
    public void setPicture(Pic pic, int side){
	pictures[(side + offset)%4] = pic;
    }


    // return the side containing this picture
    public int getSide(Pic pic) {
	for (int i = 0; i < pictures.length; i = i + 1) {
	    if (pictures[i].equals(pic)) {
		//System.out.println("getSide: i = " + i + ", offset = " + offset);
		return (i + offset)%4;
	    }
	}	
	return -1; // not found
    }


    // rotate the card so that the desired picture is on the requested
    // side
    public void rotatePictureTo(Pic pic, int side){
	resetOrientation();
	int currentSide = getSide(pic);
	if (currentSide == side) {
	    return;
	}
	else {
	    // rotate by the required amount
	    int noTurns = currentSide - side;
	    if (noTurns < 0)
		noTurns = 4 + noTurns;

	    //System.out.println("original = " + currentSide + 
	    //		       ", required = " + side + ", noTurns = " + noTurns);
	    for (int i = 0; i < noTurns; i = i + 1) {
		rotate();
	    }
	}
    }


    // return true if the card contains this picture
    public boolean containsPicture(Pic pic){
	//System.out.println("Testing for removal: \n" + this);
	if (getSide(pic) == -1)
	    return false;
	else
	    return true;
    }

    
    // return the value of the side opposite this one
    public static int opposite(int side){
	return (side + 2)%4;
    }


    // return the picture on this side
    public Pic getPicture(int side) {
	return pictures[(side + offset)%4]; // stub
    }


    public String toString(){
	String str = "";

	str = str + "+-----------+\n";
	str = str + "|   " + pictures[(TOP + offset)%4] + "   |\n";   	   
	str = str + "|           |\n";
	str = str + "|           |\n";
	str = str + "|" + pictures[(LEFT + offset)%4] + " " + 
	    pictures[(RIGHT + offset)%4] + "|\n";	 
	str = str + "|           |\n";
	str = str + "|           |\n";
	str = str + "|   " + pictures[(BOTTOM + offset)%4] + "   |\n";	       
	str = str + "+-----------+\n";

	return str;
    }


    public static void main(String args[]) {

	Card card1 = new Card();
	card1.setPicture(new Pic(Pic.WALLY,Pic.HEAD),TOP);
	card1.setPicture(new Pic(Pic.WANDA,Pic.TAIL),RIGHT);
	card1.setPicture(new Pic(Pic.WOOF,Pic.HEAD),LEFT);
	card1.setPicture(new Pic(Pic.WIZARD,Pic.HEAD),BOTTOM);
	System.out.println(card1);

	System.out.println("Left   = " + card1.getPicture(LEFT));
	System.out.println("Right  = " + card1.getPicture(RIGHT));
	System.out.println("Top    = " + card1.getPicture(TOP));
	System.out.println("Bottom = " + card1.getPicture(BOTTOM));
	card1.rotate();
	System.out.println(card1);
	card1.rotate();
	System.out.println(card1);
	card1.rotate();
	System.out.println(card1);
	card1.rotate();
	System.out.println(card1);

	System.out.println("Wanda(TAIL) @ " + 
			   SIDE[card1.getSide(new Pic(Pic.WANDA,Pic.TAIL))]);
	card1.rotate();
	System.out.println(card1);
	System.out.println("Wanda(TAIL) @ " +
			   SIDE[card1.getSide(new Pic(Pic.WANDA,Pic.TAIL))]);
	System.out.println("Woof(TAIL) = " +card1.getSide(new Pic(Pic.WOOF,Pic.TAIL)));


	System.out.println("Reset to original rotation:");
	card1.resetOrientation();
	System.out.println(card1);

	// System.out.println("" + 0%4 + " " + 1%4+ " " + 2%4+ " " + 3%4+ " " + 4%4); 

	System.out.println("Card 2:");
	Card card2 = new Card(new Pic(Pic.WOOF, Pic.TAIL),
			      new Pic(Pic.WALLY,Pic.HEAD),
			      new Pic(Pic.WANDA,Pic.HEAD),
			      new Pic(Pic.WIZARD,Pic.TAIL));

	System.out.println(card2);
	card2.rotatePictureTo(new Pic(Pic.WANDA,Pic.HEAD),TOP);
	System.out.println(card2);
	card2.rotatePictureTo(new Pic(Pic.WANDA,Pic.HEAD),LEFT);
	System.out.println(card2);
	card2.rotatePictureTo(new Pic(Pic.WANDA,Pic.HEAD),RIGHT);
	System.out.println(card2);
	card2.rotatePictureTo(new Pic(Pic.WANDA,Pic.HEAD),BOTTOM);
	System.out.println(card2);

	System.out.println("Opposite of " + SIDE[LEFT] + " = " + 
			   SIDE[opposite(LEFT)]);
	System.out.println("Opposite of " + SIDE[BOTTOM] + " = " + 
			   SIDE[opposite(BOTTOM)]);

    }

}
