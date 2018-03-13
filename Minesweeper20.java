// The "Minesweeper" class.
import java.io.*;
import java.awt.Font;
import java.awt.*;
import hsa.Console;

public class Minesweeper20
{
    static int ROW;
    static int COL;
    static int MINE;
    static Console c;
    static int num[] []; //store the number indicator and mines' position
    static boolean print[] []; //if the block is open
    static int h[] = { - 1, 0, 1, -1, 1, -1, 0, 1}, v[] = { - 1, -1, -1, 0, 0, 1, 1, 1};
    static boolean flag[] []; //if there is a flag at the position
    static boolean lose = false;
    static int right = 0;
    static boolean wrongflag[] []; //if the falg is marked at the wrong place.
    static int time;
    static int counter;
    static String ans;
    static long endTime;
    static int time2 = 0;
    static int maxE;
    static int maxM;
    static int maxH;
    static String maxEa, maxMe, maxHa;
    static int get, getM, getH;
    static char ch;
    static int j4, i4 = 0;
    static boolean win = false;
    static boolean again = false;
    static char chara;
    static char cha;
    static long totalTime;
    static boolean first = true;
    static int HE[];
    static int HM[];
    static int HH[];
    static String HEn[];
    static String HMn[];
    static String HHn[];
    static int counterE, counterM, counterH;



    // The output console

    public static void main (String[] args) throws IOException
    {
	c = new Console ();


	do //keep the program running if the user wants to play again or return to main menu.
	{
	    c.clear ();
	    again = false;
	    lose = false;
	    win = false;
	    first = true;
	    do //make it possible for users to comeback to main menu if they chose to view high score and insctruction.
	    {
		Mainmenu ();
		//main menu get input
		cha = c.getChar ();
		if (cha == 'q' || cha == 'Q') //quit
		{
		    System.exit (0);
		}
		else if (cha == 'i' || cha == 'I') //instruction of the game
		{
		    Instruction ();
		}
		else if (cha == 'V' || cha == 'v') //view score
		{
		    Score ();
		}
	    }
	    while (cha == 'i' || cha == 'I' || cha == 'V' || cha == 'v');

	    //if the user chose to load game
	    if (cha == 'L' || cha == 'l')
	    {
		c.clear ();
		j4 = 0;
		i4 = 0;
		Load ();
	    }
	    else if (cha == 'q' || cha == 'Q') //choose quit
	    {
		System.exit (0);
	    }
	    else //else, start a new game
	    {
		c.clear ();
		ChooseDifficulties ();
		//to create button to promtpt the user to choose the difficulties.
		while (chara != 'E' && chara != 'e' && chara != 'm' && chara != 'M' && chara != 'h' && chara != 'H' && chara != 'c' && chara != 'C') //prevent invalid input
		{
		    ChooseDifficulties ();
		}
		//initialize the game
		InitializeNewGame ();
		GenerateMine ();
		GenerateNum ();
		time = 0;
		counter = 0;
		j4 = 0;
		i4 = 0;
	    }

	    long startTime = System.currentTimeMillis (); //start the timer

	    do //game will keep going until check() is false. Gameplay section
	    {
		c.clear ();
		Color color2 = new Color (211, 211, 211);
		Color color1 = new Color (169, 169, 169);
		c.setColor (color1);
		c.fillRect (0, 0, 700, 700);
		long endTime = System.currentTimeMillis ();
		long totalTime = endTime - startTime; //stop the timer and count how many time is spent in last cycle.

		/*output the header of the game*/
		if (cha == 'L' || cha == 'l')
		{
		    time2 = (int) (totalTime / 1000);
		    c.setColor (Color.white);
		    c.drawString ("The time you have spent in the game is ", 0, 20);
		    String tempp = Integer.toString (time + time2);
		    c.setFont (new Font ("TimesRoman", Font.PLAIN, 18));
		    c.setColor (Color.red);
		    c.drawString (tempp, 252, 20);
		    c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		    c.setColor (Color.white);
		    c.drawString ("      second", 260, 20);
		}
		else
		{
		    time = (int) (totalTime / 1000);
		    String tempp = Integer.toString (time);
		    c.setColor (Color.white);
		    c.drawString ("The time you have spent in the game is ", 0, 20);
		    c.setColor (Color.red);
		    c.setFont (new Font ("TimesRoman", Font.PLAIN, 18));
		    c.drawString (tempp, 252, 20);
		    c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		    c.setColor (Color.white);
		    c.drawString ("      second", 260, 20);
		}
		c.drawString ("The number of flags marked is ", 0, 40);
		c.setColor (Color.red);
		c.setFont (new Font ("TimesRoman", Font.PLAIN, 18));
		String temppp = Integer.toString (counter);
		c.drawString ("                                       " + temppp, 2, 40);
		c.setColor (Color.white);
		c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		c.drawString ("The total number of mines in this level is ", 0, 60);
		c.setColor (Color.red);
		c.setFont (new Font ("TimesRoman", Font.PLAIN, 18));
		String tempppp = Integer.toString (MINE);
		c.drawString ("                                                   " + tempppp, 1, 60);
		c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		c.setColor (Color.white);
		c.drawString ("Press W, A, S or D to move to choose the block. Press ENTER to open the block under the blue square. Press", 0, 80);
		c.drawString ("SPACE to mark flag on the block under the blue square. Press C to save your game process. Press Q to go", 0, 100);
		c.drawString ("back to main menu.", 0, 120);
		/*output the header of the game such as time, mine numbers, instructions in gameplay*/

		PrintGame (); //output the minefield
		ch = c.getChar (); //move the blue square, mark flag, quit, open block, or save the game process.
		if (ch == 'w' || ch == 'W')
		{
		    if (j4 == 0)
		    {
			j4 = j4 + ROW - 1;
		    }
		    else
		    {
			j4 = j4 - 1;
		    }
		}
		else if (ch == 's' || ch == 'S')
		{
		    if (j4 == ROW - 1)
		    {
			j4 = 0;
		    }
		    else
		    {
			j4 = j4 + 1;
		    }
		}
		else if (ch == 'a' || ch == 'A')
		{
		    if (i4 == 0)
		    {
			i4 = i4 + COL - 1;
		    }
		    else
		    {
			i4 = i4 - 1;
		    }
		}
		else if (ch == 'd' || ch == 'D')
		{
		    if (i4 == COL - 1)
		    {
			i4 = 0;
		    }
		    else
		    {
			i4 = i4 + 1;
		    }
		}
		else if (ch == 10) //10 is the number of ENTER
		{
		    if (first == true) //first click can't be a mine..
		    {
			first = false;
			while (num [j4] [i4] == 9)
			{
			    for (int z1 = 0 ; z1 < ROW ; z1++)
			    {
				for (int z2 = 0 ; z2 < COL ; z2++)
				{
				    num [z1] [z2] = 0;
				    wrongflag [z1] [z2] = false;
				}
			    }
			    GenerateMine ();
			    GenerateNum ();
			    for (int z3 = 0 ; z3 < ROW ; z3++)
			    {
				for (int z4 = 0 ; z4 < COL ; z4++)
				{
				    if (flag [z3] [z4] && num [z3] [z4] != 9)
				    {
					wrongflag [z3] [z4] = true;
				    }
				}
			    }
			}
			Visi (j4, i4);
		    }
		    if (num [j4] [i4] == 9 && flag [j4] [i4] == false) /*if the user click on a mine. output. play again or quit?*/
		    {

			for (int j = 0 ; j < ROW ; j++)
			{
			    for (int k = 0 ; k < COL ; k++)
			    {
				if (num [j] [k] == 9)
				{
				    if (flag [j] [k] == false)
				    {
					print [j] [k] = true;
				    }

				}
			    }
			}
			lose = true;
			c.clear ();
			endTime = System.currentTimeMillis ();
			totalTime = endTime - startTime;
			if (cha == 'L' || cha == 'l')
			{
			    time2 = (int) (totalTime / 1000);
			}
			else
			{
			    time = (int) (totalTime / 1000);
			}
			for (int z = 0 ; z < ROW ; z++)
			{
			    for (int z1 = 0 ; z1 < COL ; z1++)
			    {
				if (flag [z] [z1] == true)
				{
				    if (num [z] [z1] != 9)
				    {
					wrongflag [z] [z1] = true;
				    }

				}
			    }
			}
			c.setColor (color1);
			c.fillRect (0, 0, 700, 700);
			c.setColor (Color.red);
			c.setFont (new Font ("TimesRoman", Font.PLAIN, 36));
			c.drawString ("YOU LOST!", 160, 120);

			c.setColor (Color.white);
			c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
			c.drawString ("The total time you spent in the game is ", 0, 20);
			String tempo = Integer.toString (time + time2);
			c.setColor (Color.red);
			c.setFont (new Font ("TimesRoman", Font.PLAIN, 18));
			c.drawString ("                                                 " + tempo, 1, 20);
			c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
			c.setColor (Color.white);
			c.drawString ("                                                                        seconds", 1, 20);
			c.drawString ("Press Y for playing again. Press Q to quit.", 0, 40);
			PrintGame ();
			char answer = c.getChar ();
			while (answer != 'Y' && answer != 'y' && answer != 'q' && answer != 'Q')
			{
			    answer = c.getChar ();
			}
			if (answer == 'Y' || answer == 'y')
			{
			    again = true;
			}
			else if (answer == 'q' || answer == 'Q')
			{
			    System.exit (0);
			}
		    }
		    /*if the user click on a mine. output. play again or quit?*/

		    /*else, can be a number or a block contains nothing. run Visi, which open a block or several blocks depending on what is underneth the block*/
		    else if (flag [j4] [i4] == false)
		    {
			Visi (j4, i4);
		    }
		}
		else if (ch == 32) //mark the flag
		{

		    if (print [j4] [i4] == false)
		    {
			if (flag [j4] [i4] == true)
			{
			    flag [j4] [i4] = false;
			    if (wrongflag [j4] [i4])
			    {
				wrongflag [j4] [i4] = false;
			    }
			}
			else
			{
			    flag [j4] [i4] = true;
			    if (num [j4] [i4] != 9)
			    {
				wrongflag [j4] [i4] = true;
			    }
			}

		    }
		}
		else if (ch == 'c' || ch == 'C') //save the game process i.e. write to file. almost every variable
		{
		    Save ();
		}
		PrintGame ();
		CalculateFlag ();
	    }
	    while (result () && lose == false); //if the player win, quit the loop.

	    if (right == MINE && flagposition ()) //if win
	    {
		c.clear ();
		long endTime = System.currentTimeMillis ();
		long totalTime = endTime - startTime;
		if (cha == 'l' || cha == 'L')
		{
		    time2 = (int) (totalTime / 1000);
		}
		else
		{
		    time = (int) (totalTime / 1000);
		}

		for (int i2 = 0 ; i2 < ROW ; i2++)
		{
		    for (int i3 = 0 ; i3 < COL ; i3++)
		    {
			print [i2] [i3] = true;
		    }
		}
		win = true;
		Color color1 = new Color (169, 169, 169);
		c.setColor (color1);
		c.fillRect (0, 0, 700, 700);

		PrintGame ();
		c.setColor (Color.red);
		c.setFont (new Font ("TimesRoman", Font.PLAIN, 36));
		c.drawString ("YOU WON!", 160, 120);
		c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		c.setColor (Color.white);
		c.drawString ("The total time you spent in the game is ", 0, 20);
		String tempor = Integer.toString (time + time2);
		c.setColor (Color.red);
		c.setFont (new Font ("TimesRoman", Font.PLAIN, 18));
		c.drawString ("                                                 " + tempor, 0, 20);
		c.setColor (Color.white);
		c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		c.drawString ("                                                                      seconds", 0, 20);
		c.drawString ("Do you want to save your time? If yes, press S for saving your time to the computer.", 0, 40);
		c.drawString ("Press any other key to skip.", 0, 60);

		char ans3 = c.getChar ();
		if (ans3 == 'S' || ans3 == 's') //write time to file
		{
		    c.clear ();
		    SaveResult ();
		}
		c.clear ();
		c.setColor (color1);
		c.fillRect (0, 0, 700, 700);
		c.setColor (Color.white);
		c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
		c.drawString ("Press Y to go back to main menu. Press any other letter to quit.", 0, 20);
		char answer = c.getChar ();
		if (answer == 'Y' || answer == 'y')
		{
		    again = true;
		}
		else
		{
		    System.exit (0);
		}
	    }

	}


	while (again == true)
	    ;




    }



    // Place your program here.  'c' is the output console
    // main method

    //Generate mines if different position in the grid.
    public static void GenerateMine ()
    {
	int x[] = new int [MINE];
	int y[] = new int [MINE];
	x [0] = (int) (Math.random () * ROW);
	y [0] = (int) (Math.random () * COL);
	num [x [0]] [y [0]] = 9;
	for (int i = 1 ; i < MINE ; i++)
	{
	    x [i] = (int) (Math.random () * ROW);
	    y [i] = (int) (Math.random () * COL);
	    num [x [i]] [y [i]] = 9;
	    for (int b = 0 ; b < i ; b++) //No duplicate
	    {
		while (x [i] == x [b] && y [i] == y [b])
		{
		    x [i] = (int) (Math.random () * ROW);
		    y [i] = (int) (Math.random () * COL);
		    num [x [i]] [y [i]] = 9;

		    b = 0;
		}
	    }
	}
    }


    //generate numbers to indicate how many mines are adjacent the uncovered block
    public static void GenerateNum ()
    {
	for (int a = 0 ; a < ROW ; a++)
	{
	    for (int b = 0 ; b < COL ; b++)
	    {
		if (num [a] [b] == 9)
		{
		    if (a - 1 >= 0 && (num [a - 1] [b] != 9))
		    {
			num [a - 1] [b]++;
		    }
		    if (a + 1 <= ROW - 1 && (num [a + 1] [b] != 9))
		    {
			num [a + 1] [b]++;
		    }
		    if (b - 1 >= 0 && (num [a] [b - 1] != 9))
		    {
			num [a] [b - 1]++;
		    }
		    if (b + 1 <= COL - 1 && (num [a] [b + 1] != 9))
		    {
			num [a] [b + 1]++;
		    }
		    if (a - 1 >= 0 && b + 1 <= COL - 1 && (num [a - 1] [b + 1] != 9))
		    {
			num [a - 1] [b + 1]++;
		    }
		    if (a + 1 <= ROW - 1 && b + 1 <= COL - 1 && (num [a + 1] [b + 1] != 9))
		    {
			num [a + 1] [b + 1]++;
		    }
		    if (a - 1 >= 0 && b - 1 >= 0 && (num [a - 1] [b - 1] != 9))
		    {
			num [a - 1] [b - 1]++;
		    }
		    if (a + 1 <= ROW - 1 && b - 1 >= 0 && (num [a + 1] [b - 1] != 9))
		    {
			num [a + 1] [b - 1]++;
		    }

		}
	    }

	}
    }


    //interface of gameplay. Graphics. Different output for win, lost, and game process.
    public static void PrintGame ()
    {

	if (lose == false)
	{

	    for (int a = 0 ; a < COL ; a++)
	    {
		String line4 = Integer.toString (a);
		c.setColor (Color.white);
		c.drawString (line4, a * 20 + 25, 160);
	    }
	    for (int b = 0 ; b < ROW ; b++)
	    {
		String line5 = Integer.toString (b);
		c.setColor (Color.white);
		c.drawString (line5, 5, b * 20 + 180);
	    }
	    for (int i = 0 ; i < ROW ; i++)
	    {

		for (int j = 0 ; j < COL ; j++)
		{
		    if (flag [i] [j] == true)
		    {
			c.setColor (Color.lightGray);
			c.fillRect (j * 20 + 20, i * 20 + 165, 20, 20);
			c.setColor (Color.red);
			int x[] = {i * 20 + 167, i * 20 + 170, i * 20 + 175};
			int y[] = {j * 20 + 22, j * 20 + 38, j * 20 + 22};
			c.fillPolygon (y, x, 3);
			c.setColor (Color.black);
			c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);
			c.drawLine (j * 20 + 22, i * 20 + 167, j * 20 + 22, i * 20 + 183);
		    }
		    else if (print [i] [j] == true)
		    {

			if (num [i] [j] != 0)
			{
			    c.setColor (Color.black);
			    c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);
			    String line3 = Integer.toString (num [i] [j]);
			    c.setColor (Color.white);
			    c.drawString (line3, j * 20 + 22, i * 20 + 180);
			}
			else if (num [i] [j] == 0)
			{
			    c.setColor (Color.black);
			    c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);
			}

		    }
		    else
		    {
			c.setColor (Color.gray);
			c.fillRect (j * 20 + 20, i * 20 + 165, 20, 20);
			c.setColor (Color.black);
			c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);
		    }
		}
	    }
	    if (win == false)
	    {
		c.setColor (Color.blue);
		c.fillRect (i4 * 20 + 20, j4 * 20 + 165, 20, 20);
	    }
	}


	else if (lose == true)
	{
	    for (int i = 0 ; i < ROW ; i++)
	    {
		for (int j = 0 ; j < COL ; j++)
		{
		    if (wrongflag [i] [j] == true)
		    {
			c.setColor (Color.lightGray);
			c.fillRect (j * 20 + 20, i * 20 + 165, 20, 20);
			c.setColor (Color.red);
			int x[] = {i * 20 + 167, i * 20 + 170, i * 20 + 175};
			int y[] = {j * 20 + 22, j * 20 + 38, j * 20 + 22};
			c.fillPolygon (y, x, 3);
			c.setColor (Color.black);
			c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);
			c.drawLine (j * 20 + 22, i * 20 + 167, j * 20 + 22, i * 20 + 183);
			c.setColor (Color.red);
			c.drawLine (j * 20 + 20, i * 20 + 165, j * 20 + 40, i * 20 + 185);
			c.drawLine (j * 20 + 40, i * 20 + 165, j * 20 + 20, i * 20 + 185);
		    }

		    else if (flag [i] [j] == true)
		    {
			c.setColor (Color.lightGray);
			c.fillRect (j * 20 + 20, i * 20 + 165, 20, 20);
			c.setColor (Color.red);
			int x[] = {i * 20 + 167, i * 20 + 170, i * 20 + 175};
			int y[] = {j * 20 + 22, j * 20 + 38, j * 20 + 22};
			c.fillPolygon (y, x, 3);
			c.setColor (Color.black);
			c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);
			c.drawLine (j * 20 + 22, i * 20 + 167, j * 20 + 22, i * 20 + 183);
		    }
		    else if (print [i] [j] == true)
		    {
			if (num [i] [j] == 9)
			{
			    c.setColor (Color.black);
			    c.fillOval (j * 20 + 22, i * 20 + 167, 18, 18);
			    c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);

			}
			else
			{
			    if (num [i] [j] != 0)
			    {
				c.setColor (Color.black);
				c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);
				String line3 = Integer.toString (num [i] [j]);
				c.drawString (line3, j * 20 + 22, i * 20 + 180);
			    }
			    else if (num [i] [j] == 0)
			    {
				c.setColor (Color.black);
				c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);
			    }
			}
		    }

		    else
		    {
			c.setColor (Color.gray);
			c.fillRect (j * 20 + 20, i * 20 + 165, 20, 20);
			c.setColor (Color.black);
			c.drawRect (j * 20 + 20, i * 20 + 165, 20, 20);
		    }
		}
	    }

	}
    }



    //check if the player finish the game
    public static boolean result ()
    {
	if (ch == 'q' || ch == 'Q') //if the user presses q, end the loop of gameplay and go back to main menu.
	{
	    again = true;
	    return false;

	}
	for (int i = 0 ; i < ROW ; i++)
	{
	    for (int j = 0 ; j < COL ; j++)
	    {
		if (print [i] [j] == false)
		{
		    if (flag [i] [j] == false) //print[i][j]==false and flag[i][j]==false means the block is neither marked with flag or opened. return true will make the loop keep running.
		    {
			return true;
		    }
		}
		if (right == MINE) //right is an integer variable that counts how many mines does the user marked correctly
		{
		    if (flag [i] [j] == false) //flag[i][j] equals to false means the block is marked with flag
		    {
			if (print [i] [j] == false) // so that if print[i][j]==false, which means the block is neither marked with flag or opened, return true to keep ruuning the game.
			{
			    return true;
			}
		    }
		    if (wrongflag [i] [j] == true)
		    {
			return true;
		    }
		}
	    }
	}


	return false;
    }


    //is used to open blocks.  if the block 's number is zero, open the eight or less blocks around it by using Recursion
    //if there is no mines adjacent to the uncovered block and the user clicks on it, the eight block around it would open except for blocks contain mines.
    //similar to snowplow
    public static void Visi (int num1, int num2)
    {
	if (num1 < 0 || num1 >= ROW || num2 < 0 || num2 >= COL)
	{
	    return;
	}
	if (print [num1] [num2] == true)
	{
	    return;
	}
	if (num [num1] [num2] == 9)
	{
	    return;
	}
	if (flag [num1] [num2])
	{
	    return;
	}
	print [num1] [num2] = true;
	if (num [num1] [num2] != 0)
	{
	    return;
	}
	for (int i = 0 ; i < 8 ; i++)
	{
	    Visi (num1 + h [i], num2 + v [i]);
	}
    }




    //Instruction of the game drawString
    public static void Instruction ()
    {
	c.clear ();
	c.setColor (Color.gray);
	c.fillRect (0, 0, 700, 700);
	c.setColor (Color.white);
	c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
	c.drawString ("-Minesweeper is to find all the mines in a given grid. If the user clicks wrong place where ", 0, 20);
	c.drawString ("contains a mine, the user will lose. If the user marks all the mines correctly and unmask", 0, 40);
	c.drawString ("all the blocks in the grid, the user will win.", 0, 60);
	c.drawString ("-The user can use W, A, S or D to move the blue square around and press enter or", 0, 80);
	c.drawString ("space to open a block or mark a flag respectively. The number in an open block is to ", 0, 100);
	c.drawString ("indicate how many mines are around the block. This includes blocks diagonal to it as well.", 0, 120);
	c.drawString ("-The quicker the user finish the game, the better.", 0, 140);
	c.drawString ("-Users can save the game at any point of the game by pressing C so that they could", 0, 160);
	c.drawString ("load their game data next time if they did not finish this time.", 0, 180);
	c.drawString ("-Users can press Q at any point of game to return to main menu.", 0, 200);
	c.drawString ("-Level Easy is a 9x9 grid contains 10 mines.", 0, 220);
	c.drawString ("-Level Medium is a 16x16 grid contains 40 mines.", 0, 240);
	c.drawString ("-Level Hard is a 16x30 grid contains 99 mines.", 0, 260);
	c.drawString ("-The user can also customize a grid the user wants.", 0, 280);

	c.drawString ("Enter any key to return to main menu.", 0, 320);
	char cc = c.getChar ();
	return;
    }


    public static void Mainmenu ()
    {
	c.setColor (Color.gray);
	c.fillRect (0, 0, 700, 700);
	Color color1 = new Color (169, 169, 169);
	c.setColor (color1);
	c.fillRect (100, 270, 150, 50);
	c.fillRect (360, 270, 150, 50);
	c.fillRect (100, 340, 150, 50);
	c.fillRect (360, 340, 150, 50);
	c.fillRect (230, 410, 150, 50);
	Color color2 = new Color (211, 211, 211);
	c.setColor (color2);
	c.fillRect (105, 275, 140, 40);
	c.fillRect (365, 275, 140, 40);
	c.fillRect (105, 345, 140, 40);
	c.fillRect (365, 345, 140, 40);
	c.fillRect (235, 415, 140, 40);
	c.setColor (Color.black);
	c.drawString ("NEW GAME", 145, 290);
	c.drawString ("(ENTER)", 155, 307);
	c.drawString ("LOAD GAME", 400, 290);
	c.drawString ("(L)", 423, 307);
	c.drawString ("SCORE", 155, 360);
	c.drawString ("(V)", 165, 377);
	c.drawString ("INSTRUCTION", 395, 360);
	c.drawString ("(I)", 425, 377);
	c.drawString ("QUIT", 287, 430);
	c.drawString ("(Q)", 293, 447);
	c.setFont (new Font ("TimesRoman", Font.PLAIN, 70));
	c.setColor (Color.black);
	c.drawString ("MINESWEEPER", 20, 160);
	c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
	//small animation
	for (int yy = 0 ; yy < 35 ; yy++)
	{

	    if (yy % 2 == 0)
	    {
		c.setColor (Color.red);
		int x[] = {70, 73, 78};
		int y[] = {yy * 20 + 2, yy * 20 + 18, yy * 20 + 2};
		c.fillPolygon (y, x, 3);
		c.setColor (Color.black);
		c.drawLine (yy * 20 + 2, 70, yy * 20 + 2, 85);
		try
		{
		    Thread.sleep (25); //    50 is the delay time in microseconds

		}
		catch (InterruptedException error)
		{
		    c.println ("sorry couldn't delay");
		}
	    }
	    else
	    {

		c.setColor (Color.black);
		c.fillOval (yy * 20 + 2, 70, 18, 18);
	    }
	}
	for (int xx = 0 ; xx < 35 ; xx++)
	{
	    if (xx % 2 == 0)
	    {
		c.setColor (Color.black);
		c.fillOval (xx * 20 + 2, 190, 18, 18);
	    }
	    else
	    {
		c.setColor (Color.red);
		int x[] = {190, 193, 198};
		int y[] = {xx * 20 + 2, xx * 20 + 18, xx * 20 + 2};
		c.fillPolygon (y, x, 3);
		c.setColor (Color.black);
		c.drawLine (xx * 20 + 2, 190, xx * 20 + 2, 205);
		try
		{
		    Thread.sleep (25); //    50 is the delay time in microseconds

		}
		catch (InterruptedException error)
		{
		    c.println ("sorry couldn't delay");
		}
	    }
	}

    }


    public static void SaveResult () throws IOException
    {
	Color color1 = new Color (169, 169, 169);
	c.setColor (color1);
	c.fillRect (0, 0, 700, 700);
	c.setColor (Color.white);
	c.drawString ("Please enter your name.", 0, 60);
	String name = c.readLine ();
	String name2 = ("result.txt");
	PrintWriter FileOut;
	FileOut = new PrintWriter (new FileWriter (name2, true));
	if (ans.equals ("E"))
	{
	    FileOut.println ("Name: " + name);
	    FileOut.println ("The level of game is: ");
	    FileOut.println ("Easy");
	    FileOut.println ("The time you spent in the game in second is "); // in second!!!!
	    FileOut.println (time + time2);
	}
	else if (ans.equals ("M"))
	{
	    FileOut.println ("Name: " + name);
	    FileOut.println ("The level of game is: ");
	    FileOut.println ("Medium");
	    FileOut.println ("The time you spent in the game in second is ");
	    FileOut.println (time + time2);
	}
	else if (ans.equals ("H"))
	{
	    FileOut.println ("Name: " + name);
	    FileOut.println ("The level of game is: ");
	    FileOut.println ("Hard");
	    FileOut.println ("The time you spent in the game in second is ");
	    FileOut.println (time + time2);
	}
	else if (ans.equals ("C"))
	{
	    FileOut.println ("Name: " + name);
	    FileOut.println ("The level of game is: ");
	    FileOut.println ("Custom");
	    FileOut.print ("The number of row is " + ROW + ". The number of column is " + COL + ". ");
	    FileOut.println ("The time you spent in the game in second is ");
	    FileOut.println (time + time2);
	}
	FileOut.close ();
    }





    public static void Score () throws IOException
    {
	int counter2 = 0;
	int counter3 = 0;
	int counter4 = 0;
	int counter5 = 0;
	int counter6 = 0;
	counterE = 0;
	counterM = 0;
	counterH = 0;
	int i = 0;
	int k = 0;
	int j = 0;
	BufferedReader input2;
	input2 = new BufferedReader (new FileReader ("result.txt"));
	String iLine = input2.readLine ();
	while (iLine != null)
	{
	    counter2++;
	    if (iLine.equals ("Easy"))
	    {
		counterE++;
	    }
	    else if (iLine.equals ("Medium"))
	    {
		counterM++;
	    }
	    else if (iLine.equals ("Hard"))
	    {
		counterH++;
	    }
	    iLine = input2.readLine ();
	}
	input2.close ();
	HE = new int [counterE];
	HM = new int [counterM];
	HH = new int [counterH];
	HEn = new String [counterE];
	HMn = new String [counterM];
	HHn = new String [counterH];
	BufferedReader input3;
	input3 = new BufferedReader (new FileReader ("result.txt"));
	String resulttxt[] = new String [counter2];
	String iLine2 = input3.readLine ();
	for (int ii = 0 ; ii < counter2 ; ii++)
	{
	    resulttxt [ii] = iLine2;
	    iLine2 = input3.readLine ();
	}
	input3.close ();
	/*compare result*/
	for (int iii = 0 ; iii < (counter2 / 5) ; iii++)
	{
	    if (resulttxt [iii * 5 + 2].equals ("Easy"))
	    {
		HE [i] = Integer.parseInt (resulttxt [iii * 5 + 4]);
		HEn [i] = resulttxt [iii * 5];
		i++;
	    }
	    if (resulttxt [iii * 5 + 2].equals ("Medium"))
	    {
		HM [j] = Integer.parseInt (resulttxt [iii * 5 + 4]);
		HMn [j] = resulttxt [iii * 5];
		j++;
	    }
	    if (resulttxt [iii * 5 + 2].equals ("Hard"))
	    {
		HH [k] = Integer.parseInt (resulttxt [iii * 5 + 4]);
		HHn [k] = resulttxt [iii * 5];
		k++;
	    }
	}
	Sort ();
	c.clear ();
	c.setColor (Color.gray);
	c.fillRect (0, 0, 700, 700);
	c.setColor (Color.white);
	c.setFont (new Font ("TimesRoman", Font.PLAIN, 18));
	c.drawString ("Level Easy", 0, 20);


	for (int ii = 0 ; ii < counterE && ii < 5 ; ii++)
	{
	    c.drawString (HEn [ii] + " Time: " + HE [ii] + " second.", 0, (ii + 20) * (ii + 2));
	}
	c.drawString ("Level Medium", 0, 180);
	for (int iii = 0 ; iii < 5 && iii < counterM ; iii++)
	{
	    c.drawString (HMn [iii] + " Time: " + HM [iii] + " second.", 0, 160 + (iii + 20) * (iii + 2));
	}
	c.drawString ("Level Hard", 0, 340);
	for (int iiii = 0 ; iiii < 5 && iiii < counterH ; iiii++)
	{
	    c.drawString (HHn [iiii] + " Time: " + HH [iiii] + " second.", 0, 320 + (iiii + 20) * (iiii + 2));
	}
	c.drawString ("Press any key to return to main menu.", 0, 495);
	c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
	char asdf = c.getChar ();
    }


    public static void Sort ()
    {
	for (int i = 1 ; i < HE.length ; i++)
	{
	    int temp = HE [i];
	    String temp2 = HEn [i];
	    int j;
	    j = i - 1;
	    while (j >= 0 && temp < HE [j])
	    {
		HE [j + 1] = HE [j];
		HEn [j + 1] = HEn [j];
		j--;
	    }
	    HE [j + 1] = temp;
	    HEn [j + 1] = temp2;
	}
	for (int ii = 1 ; ii < HM.length ; ii++)
	{
	    int temp = HM [ii];
	    String temp2 = HMn [ii];
	    int j;
	    j = ii - 1;
	    while (j >= 0 && temp < HE [j])
	    {
		HM [j + 1] = HM [j];
		HMn [j + 1] = HMn [j];
		j--;
	    }
	    HM [j + 1] = temp;
	    HMn [j + 1] = temp2;
	}
	for (int iii = 1 ; iii < HH.length ; iii++)
	{
	    int temp = HH [iii];
	    String temp2 = HHn [iii];
	    int j;
	    j = iii - 1;
	    while (j >= 0 && temp < HH [j])
	    {
		HH [j + 1] = HH [j];
		HHn [j + 1] = HHn [j];
		j--;
	    }
	    HH [j + 1] = temp;
	    HHn [j + 1] = temp2;
	}
    }


    public static void Save () throws IOException
    {
	c.println ("Please name your savadata.");
	String Sname = c.readLine ();
	PrintWriter output;
	output = new PrintWriter (new FileWriter (Sname));
	output.println (ROW);
	output.println (COL);
	output.println (MINE);
	output.println (right);
	output.println (counter);
	output.println (ans);
	output.println (first);
	if (cha == 'L' || cha == 'l')
	{
	    output.println ((time + time2));
	}
	else
	{
	    output.println (time);
	}
	for (int i1 = 0 ; i1 < ROW ; i1++)
	{
	    for (int i2 = 0 ; i2 < COL ; i2++)
	    {
		output.println (num [i1] [i2]);
	    }
	}
	for (int i3 = 0 ; i3 < ROW ; i3++)
	{
	    for (int i4 = 0 ; i4 < COL ; i4++)
	    {
		output.println (print [i3] [i4]);
	    }
	}
	for (int i5 = 0 ; i5 < ROW ; i5++)
	{
	    for (int i6 = 0 ; i6 < COL ; i6++)
	    {
		output.println (flag [i5] [i6]);
	    }
	}
	for (int i7 = 0 ; i7 < ROW ; i7++)
	{
	    for (int i8 = 0 ; i8 < COL ; i8++)
	    {
		output.println (wrongflag [i7] [i8]);
	    }
	}
	output.close ();
    }


    public static void Load () throws IOException
    {
	c.setColor (Color.gray);
	c.fillRect (0, 0, 700, 700);
	c.setColor (Color.white);
	c.drawString ("Please enter the name of your savadata.", 0, 40);
	String sName = c.readLine ();
	File f = new File (sName);


	while (f.exists () == false)
	{
	    c.clear ();
	    c.setColor (Color.gray);
	    c.fillRect (0, 0, 700, 700);
	    c.setColor (Color.white);
	    c.drawString ("No file is found. Please enter again.", 0, 40);
	    sName = c.readLine ();
	    f = new File (sName);
	}


	BufferedReader input;
	input = new BufferedReader (new FileReader (sName));
	String sLine = input.readLine ();
	String tempROW = sLine;
	sLine = input.readLine ();
	String tempCOL = sLine;
	sLine = input.readLine ();
	String tempMINE = sLine;
	sLine = input.readLine ();
	String tempright = sLine;
	sLine = input.readLine ();
	String tempcounter = sLine;
	sLine = input.readLine ();
	ans = sLine;
	sLine = input.readLine ();
	String tempfirst = sLine;
	sLine = input.readLine ();
	String temptime = sLine;
	ROW = Integer.parseInt (tempROW);
	COL = Integer.parseInt (tempCOL);
	MINE = Integer.parseInt (tempMINE);
	right = Integer.parseInt (tempright);
	counter = Integer.parseInt (tempcounter);
	first = Boolean.valueOf (tempfirst).booleanValue ();
	time = Integer.parseInt (temptime);
	String tempnum[] [] = new String [ROW] [COL];
	num = new int [ROW] [COL]; //Read from file and convert string to proper variable.
	/*read 2 dimensional array savedata*/
	for (int j1 = 0 ; j1 < ROW ; j1++)
	{
	    for (int j2 = 0 ; j2 < COL ; j2++)
	    {
		sLine = input.readLine ();
		tempnum [j1] [j2] = sLine;
		num [j1] [j2] = Integer.parseInt (tempnum [j1] [j2]);
	    }
	}
	String tempprint[] [] = new String [ROW] [COL];
	print = new boolean [ROW] [COL];
	for (int j5 = 0 ; j5 < ROW ; j5++)
	{
	    for (int j6 = 0 ; j6 < COL ; j6++)
	    {

		sLine = input.readLine ();
		tempprint [j5] [j6] = sLine;
		print [j5] [j6] = Boolean.valueOf (tempprint [j5] [j6]).booleanValue ();

	    }
	}
	String tempflag[] [] = new String [ROW] [COL];
	flag = new boolean [ROW] [COL];
	for (int j7 = 0 ; j7 < ROW ; j7++)
	{
	    for (int j8 = 0 ; j8 < COL ; j8++)
	    {

		sLine = input.readLine ();
		tempflag [j7] [j8] = sLine;
		flag [j7] [j8] = Boolean.valueOf (tempflag [j7] [j8]).booleanValue ();

	    }
	}
	String tempwrongflag[] [] = new String [ROW] [COL];
	wrongflag = new boolean [ROW] [COL];
	for (int j3 = 0 ; j3 < ROW ; j3++)
	{
	    for (int j4 = 0 ; j4 < COL ; j4++)
	    {

		sLine = input.readLine ();
		tempwrongflag [j3] [j4] = sLine;
		wrongflag [j3] [j4] = Boolean.valueOf (tempwrongflag [j3] [j4]).booleanValue ();

	    }
	}
	input.close ();




    }


    public static void ChooseDifficulties ()
    {
	c.setColor (Color.gray);
	c.fillRect (0, 0, 700, 700);
	Color color1 = new Color (169, 169, 169);
	c.setColor (color1);
	c.fillRect (235, 80, 150, 50);
	c.fillRect (235, 180, 150, 50);
	c.fillRect (235, 280, 150, 50);
	c.fillRect (235, 380, 150, 50);
	Color color2 = new Color (211, 211, 211);
	c.setColor (color2);
	c.fillRect (240, 85, 140, 40);
	c.fillRect (240, 185, 140, 40);
	c.fillRect (240, 285, 140, 40);
	c.fillRect (240, 385, 140, 40);
	c.setFont (new Font ("TimesRoman", Font.PLAIN, 17));
	c.setColor (Color.white);
	c.drawString ("Please choose difficulties you would like to play.", 0, 20);
	c.setFont (new Font ("TimesRoman", Font.PLAIN, 14));
	c.setColor (Color.black);
	c.drawString ("EASY", 292, 100);
	c.drawString ("(E)", 300, 117);
	c.drawString ("MEDIUM", 284, 200);
	c.drawString ("(M)", 300, 217);
	c.drawString ("HARD", 292, 300);
	c.drawString ("(H)", 300, 317);
	c.drawString ("CUSTOM", 284, 400);
	c.drawString ("(C)", 300, 417);
	chara = c.getChar ();
    }


    public static void InitializeNewGame ()
    {
	if (chara == 'c' || chara == 'C') //custom
	{
	    ans = "C";
	    c.setColor (Color.gray);
	    c.fillRect (0, 0, 700, 700);
	    c.setColor (Color.white);
	    c.drawString ("Please enter the number of row you would like to play", 0, 60);
	    ROW = c.readInt ();
	    while (ROW <= 0)
	    {
		c.clear ();
		c.setColor (Color.gray);
		c.fillRect (0, 0, 700, 700);
		c.setColor (Color.white);
		c.drawString ("Invalid input. Please enter again.", 0, 60);
		c.drawString ("Please enter the number of row you would like to play", 0, 80);
		ROW = c.readInt ();
	    }
	    c.clear ();
	    c.setColor (Color.gray);
	    c.fillRect (0, 0, 700, 700);
	    c.setColor (Color.white);
	    c.drawString ("Please enter the number of column you would like to play", 0, 60);
	    COL = c.readInt ();
	    while (COL <= 0)
	    {
		c.clear ();
		c.setColor (Color.gray);
		c.fillRect (0, 0, 700, 700);
		c.setColor (Color.white);
		c.drawString ("Invalid input. Please enter again.", 0, 60);
		c.drawString ("Please enter the number of column you would like to play", 0, 80);
		COL = c.readInt ();
	    }
	    c.clear ();
	    c.setColor (Color.gray);
	    c.fillRect (0, 0, 700, 700);
	    c.setColor (Color.white);
	    c.drawString ("Pease enter how many mines you would like to have in the grid", 0, 60);
	    MINE = c.readInt ();
	    while (MINE >= ROW * COL || MINE <= 0)
	    {
		c.clear ();
		c.setColor (Color.gray);
		c.fillRect (0, 0, 700, 700);
		c.setColor (Color.white);
		c.drawString ("Invalid input. Please enter again.", 0, 60);
		c.drawString ("Pease enter how many mines you would like to have in the grid", 0, 80);
		MINE = c.readInt ();
	    }
	    num = new int [ROW] [COL];
	    print = new boolean [ROW] [COL];
	    flag = new boolean [ROW] [COL];
	    wrongflag = new boolean [ROW] [COL];
	}
	else if (chara == 'e' || chara == 'E') //easy
	{
	    ans = "E";
	    ROW = 9;
	    COL = 9;
	    MINE = 10;
	    num = new int [ROW] [COL];
	    print = new boolean [ROW] [COL];
	    flag = new boolean [ROW] [COL];
	    wrongflag = new boolean [ROW] [COL];
	}
	else if (chara == 'm' || chara == 'M') //medium
	{
	    ans = "M";
	    ROW = 16;
	    COL = 16;
	    MINE = 40;
	    num = new int [ROW] [COL];
	    print = new boolean [ROW] [COL];
	    flag = new boolean [ROW] [COL];
	    wrongflag = new boolean [ROW] [COL];
	}
	else if (chara == 'h' || chara == 'H') //hard
	{
	    ans = "H";
	    ROW = 16;
	    COL = 30;
	    MINE = 99;
	    num = new int [ROW] [COL];
	    print = new boolean [ROW] [COL];
	    flag = new boolean [ROW] [COL];
	    wrongflag = new boolean [ROW] [COL];
	}


	for (int i = 0 ; i < ROW ; i++)
	{
	    for (int a = 0 ; a < COL ; a++)
	    {
		print [i] [a] = false;
		num [i] [a] = 0;
		flag [i] [a] = false;
		wrongflag [i] [a] = false;
	    }
	}

    }







    public static void CalculateFlag ()
    {
	counter = 0;
	right = 0;
	for (int x = 0 ; x < ROW ; x++)
	{
	    for (int y = 0 ; y < COL ; y++)
	    {
		if (flag [x] [y] == true)
		{
		    counter++;
		}
		if (flag [x] [y] == true && num [x] [y] == 9)
		{
		    right++;
		}
	    }
	}
    }


    public static boolean flagposition ()
    {
	for (int i = 0 ; i < ROW ; i++)
	{
	    for (int j = 0 ; j < COL ; j++)
	    {
		if (wrongflag [i] [j])
		{
		    return false;
		}
	    }

	}
	return true;
    }





    // Minesweeper class
}


