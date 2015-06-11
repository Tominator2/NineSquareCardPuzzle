
// This interface provides values for the four characters and which
// half of the character that appear on a side of a card.
interface PictureValues
{
    public static final int WIZARD = 0;    
    public static final int WALLY  = 1;
    public static final int WANDA  = 2;
    public static final int WOOF   = 3;
    public static final int HEAD   = 4;
    public static final int TAIL   = 5;

    public static final String[] NAMES = {
	"WW",
	"WY",
	"WA",
	"WF",
	"H",
	"T"
    };
}


// This class represents the top or bottom half (HEAD or TAIL) of a
// character on one side of a card.
public class Pic implements PictureValues{

    public int character = -1;
    public int part      = -1;


    // default constructor
    public Pic(){

    }


    public Pic(int character, int part) {
	this.character = character;
	this.part      = part;
    }


    public void setCharacter(int character) {
	this.character = character;
    }


    public int getCharacter() {
	return character;
    }


    public void setPart(int part) {
	this.part = part;
    }


    public int getPart() {
	return part;
    }


    public int matchingPart() {
	if (part == HEAD)
	    return TAIL;
	else
	    return HEAD;
    } 


    public String toString() {
	return NAMES[character] + "(" + NAMES[part] + ")";
    }


    // check that both the character and the half (HEAD or TAIL) match
    public boolean equals(Pic pic){
	if (this.character == pic.getCharacter() && 
	    this.part == pic.getPart()) 
	    return true;
	else
	    return false;
    }


    // the test for matching parts should be stronger!
    public boolean matches(Pic pic){
	if (this.character == pic.getCharacter() && 
	    this.part != pic.getPart()) 
	    return true;
	else
	    return false;
    }
    

    public static void main (String args[]) {

	Pic pic1 = new Pic();
	pic1.setCharacter(WALLY);
	pic1.setPart(HEAD);
	System.out.println("Picture 1 = " + pic1);

	Pic pic2 = new Pic(WOOF, TAIL);
	System.out.println("Picture 2 = " + pic2);

	System.out.println("\nTesting 'equals':");
	System.out.println(pic1.equals(pic2));
	System.out.println(pic1.equals(new Pic(WALLY,HEAD)));
	System.out.println(pic1.equals(new Pic(WALLY,TAIL)));
	System.out.println(pic1.equals(new Pic(WOOF,HEAD)));

	System.out.println("\nTesting 'matches':");
	System.out.println(pic1.matches(new Pic(WALLY,HEAD)));
	System.out.println(pic1.matches(new Pic(WALLY,TAIL)));
	System.out.println(pic1.matches(new Pic(WOOF,HEAD)));
	System.out.println(pic1.matches(new Pic(WOOF,TAIL)));

    }

}
