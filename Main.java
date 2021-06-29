import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends JFrame implements ActionListener{
	
	private static final int WIDTH = 300;
	private static final int HEIGHT = 250;
	
	private Deck deck;
	private Player me;
	private Player other;
	private Community community;
	private JLabel myHand;
	private JLabel otherHand;
	private JLabel communityHand;
	
	public Main()
	{
		setTitle("Poker");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		deck = new Deck();
		community = new Community();
		me = new Player(deck.drawCard(), deck.drawCard());
		other = new Player(deck.drawCard(), deck.drawCard());
		
		JLabel myHandT = new JLabel("Your hand");
		myHand = new JLabel(String.valueOf(me));
		myHandT.setBounds(0, 5, 60, 10);
		myHand.setBounds(0, 16, 200, 20);
		
		JLabel communityHandT = new JLabel("Community Card");
		communityHand = new JLabel(String.valueOf(community));
		communityHandT.setBounds(0, 45, 100, 10);
		communityHand.setBounds(0, 56, 300, 20);
		
		JLabel otherHandT = new JLabel("Other hand");
		otherHand = new JLabel(String.valueOf(other));
		otherHandT.setBounds(0, 85, 100, 10);
		otherHand.setBounds(0, 96, 200, 20);
		
		JButton drawBtn = new JButton("Draw");
		drawBtn.setBounds(0, 125, 284, 30);
		drawBtn.addActionListener(this);
		
		JButton resetBtn = new JButton("Reset");
		resetBtn.setBounds(0, 165, 284, 30);
		resetBtn.addActionListener(this);
		
		add(myHandT);
		add(myHand);
		add(communityHandT);
		add(communityHand);
		add(otherHandT);
		add(otherHand);
		add(drawBtn);
		add(resetBtn);
		
		JButton testBtn = new JButton("Test");
		testBtn.setBounds(150, 80, 80, 40);
		testBtn.addActionListener(this);
		add(testBtn);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		String actionCmd = e.getActionCommand();
		if (actionCmd.equals("Draw"))
		{
			if (community.getSize() != 5)
			{
				community.addCard(deck.drawCard());
				communityHand.setText(String.valueOf(community));
				if (community.getSize() == 5)
				{
					Contested myContested = Contested.getContested(community, me);
					Contested otherContested = Contested.getContested(community, other);
					if (myContested.compareTo(otherContested) == 1)
						System.out.println("You Win!");
					else if (myContested.compareTo(otherContested) == -1)
						System.out.println("You Lose!");
					else
						System.out.println("Draw");
				}
			}
		}
		else if (actionCmd.equals("Reset"))
		{	
			deck.resetDeck();
			community.reset();
			me = new Player(deck.drawCard(), deck.drawCard());
			other = new Player(deck.drawCard(), deck.drawCard());
			
			myHand.setText(String.valueOf(me));
			otherHand.setText(String.valueOf(other));
			communityHand.setText(String.valueOf(community));
		}
		else if (actionCmd.equals("Test"))
		{
			long win = 0, lose = 0;
			double winrate;
			Community tmpCom = new Community();
			for (int m = 0; m < 44; m++)
				for (int n = m + 1; n < 45; n++)
					for (int a = n + 1; a < 46; a++)
						for (int b = a + 1; b < 47; b++)
							for (int c = b + 1; c < 48; c++)
							{
								tmpCom.addCard(deck.get(m));
								tmpCom.addCard(deck.get(n));
								tmpCom.addCard(deck.get(a));
								tmpCom.addCard(deck.get(b));
								tmpCom.addCard(deck.get(c));
								if (testContested(tmpCom, me).compareTo(testContested(tmpCom, other)) >= 0)
									win++;
								else
									lose++;
								tmpCom.reset();
							}
			winrate = (double)win / (win + lose);
			System.out.println(winrate * 100 + "%");
		}
	}
	
	public Contested testContested(Community com, Player pl)
	{
		int high = 0;
		int tHigh = 0;
		int p1High = 0;
		int p2High = 0;
		boolean pair = false;
		boolean twoPair = false;
		boolean triple = false;
		boolean fullHouse = false;
		boolean fourCard = false;
		boolean flush = false;
		boolean straight = false;
		boolean straightFlush = false;
		boolean royalStraightFlush = false;
		
		ArrayList<Card> hands = new ArrayList<Card>();
		ArrayList<Card> straightValid = new ArrayList<Card>();
		ArrayList<Card> valid = new ArrayList<Card>();
		hands.add(pl.getHand1());
		hands.add(pl.getHand2());
		for (Card x : com.getCards())
			hands.add(x);
		
		Collections.sort(hands);
		
		// Check Four Card
		for (int i = 0; i < hands.size() - 3; i++)
		{
			if ((hands.get(i).getNumber() == hands.get(i + 1).getNumber()) && (hands.get(i).getNumber() == hands.get(i + 2).getNumber()) && (hands.get(i).getNumber() == hands.get(i + 3).getNumber()))
			{
				fourCard = true;
				high = hands.get(i).getNumber();
				if (high == 1) high = 14;
			}
		}
		// Check Flush
		int sCount = 0, dCount = 0, hCount = 0, cCount = 0;
		int sHigh = 0, dHigh = 0, hHigh = 0, cHigh = 0;
		int stHigh = 0;
		int fShape = -1;
		for (Card x : hands)
		{
			if (x.getShape() == Card.CARD_SHAPE_SPADE)
			{
				sCount++;
				int val = x.getNumber();
				if (val == 1) val = 14;
				sHigh = val > sHigh ? val : sHigh;
				if (sCount > 4)
				{
					flush = true;
					if (!fourCard)
						high = sHigh;
					fShape = Card.CARD_SHAPE_SPADE;
				}
			}
			else if (x.getShape() == Card.CARD_SHAPE_DIAMOND)
			{
				dCount++;
				int val = x.getNumber();
				if (val == 1) val = 14;
				dHigh = val > dHigh ? val : dHigh;
				if (dCount > 4)
				{
					flush = true;
					if (!fourCard)
						high = dHigh;
					fShape = Card.CARD_SHAPE_DIAMOND;
				}
			}
			else if (x.getShape() == Card.CARD_SHAPE_HEART)
			{
				hCount++;
				int val = x.getNumber();
				if (val == 1) val = 14;
				hHigh = val > hHigh ? val : hHigh;
				if (hCount > 4)
				{
					flush = true;
					if (!fourCard)
						high = hHigh;
					fShape = Card.CARD_SHAPE_HEART;
				}
			}
			else
			{
				cCount++;
				int val = x.getNumber();
				if (val == 1) val = 14;
				cHigh = val > cHigh ? val : cHigh;
				if (cCount > 4)
				{
					flush = true;
					if (!fourCard)
						high = cHigh;
					fShape = Card.CARD_SHAPE_CLOVER;
				}
			}
		}
		
		// Check Straight
		boolean thereAce = false;
		for (int i = 0; i < hands.size(); i++)
		{
			if (hands.get(i).getNumber() == 1) thereAce = true;
			if (straightValid.size() == 0)
				straightValid.add(hands.get(i));
			else if (hands.get(i).getNumber() - straightValid.get(straightValid.size() - 1).getNumber() < 2)
				straightValid.add(hands.get(i));
			else if (straightValid.get(straightValid.size() - 1).getNumber() - straightValid.get(0).getNumber() < 3)
				straightValid.clear();
		}

		if (straightValid.size() != 0 && straightValid.get(straightValid.size() - 1).getNumber() - straightValid.get(0).getNumber() >= 4)
		{
			straight = true;
			stHigh = straightValid.get(straightValid.size() - 1).getNumber();
		}
		else if (straightValid.size() != 0 && straightValid.get(straightValid.size() - 1).getNumber() - straightValid.get(0).getNumber() == 3 && straightValid.get(straightValid.size() - 1).getNumber() == 13 && thereAce)
		{
			straight = true;
			stHigh = 14;
			for (Card x : hands)
				if (x.getNumber() == 1)
					straightValid.add(x);
		}
		
		if (straight && stHigh == 13 && thereAce)
		{
			stHigh = 14;
			for (Card x : hands)
				if (x.getNumber() == 1)
					straightValid.add(x);
		}
		
		// Check Straight Flush & Royal Straight Flush
		if (straight && flush)
		{
			for (int i = 0; i < straightValid.size(); i++)
				if (straightValid.get(i).getShape() != fShape)
					straightValid.remove(i);
			while (straightValid.size() > 5)
				straightValid.remove(0);
			if (straightValid.size() == 5)
			{
				high = straightValid.get(straightValid.size() - 1).getNumber();
				if (high == 1) high = 14;
				straightFlush = true;
			}
			if (high == 14 && straightFlush)
				royalStraightFlush = true;
		}
		
		// Check Triple & Full House
		if (!fourCard)
		{
			for (int i = 0; i < hands.size() - 2; i++)
			{
				if ((hands.get(i).getNumber() == hands.get(i + 1).getNumber()) && (hands.get(i).getNumber() == hands.get(i + 2).getNumber()))
				{
					if (triple)
					{
						if (tHigh != 14)
						{
							p1High = tHigh;
							tHigh = hands.get(i).getNumber();
						}
						else
							p1High = hands.get(i).getNumber();
						if (tHigh == 1) tHigh = 14;
						fullHouse = true;
					}
					else
					{
						triple = true;
						tHigh = hands.get(i).getNumber();
						if (tHigh == 1) tHigh = 14;
					}
				}
			}
		}
		if (triple && !fullHouse)
			for (int i = 0; i < hands.size() - 1; i++)
				if (hands.get(i).getNumber() == hands.get(i + 1).getNumber())
					if (tHigh != hands.get(i).getNumber() && !(tHigh == 14 && hands.get(i).getNumber() == 1))
					{
						p1High = hands.get(i).getNumber();
						if (p1High == 1) p1High = 14;
						fullHouse = true;
					}
		
		// Check Pair & Two Pair
		if (!triple && !fourCard)
			for (int i = 0; i < hands.size() - 1; i++)
				if (hands.get(i).getNumber() == hands.get(i + 1).getNumber())
				{
					if (pair)
					{
						if (p1High == 14)
							p2High = hands.get(i).getNumber();
						else
						{
							p2High = p1High;
							p1High = hands.get(i).getNumber();
						}
						twoPair = true;
					}
					else
					{
						p1High = hands.get(i).getNumber();
						if (p1High == 1) p1High = 14;
						pair = true;
					}
				}
		
		if (!flush && !straight && !pair && !twoPair && !triple)
		{
			if (hands.get(0).getNumber() == 1)
				high = 14;
			else
				high = hands.get(hands.size() - 1).getNumber();
		}
		
		if (straightFlush)
		{
			for (Card x : straightValid)
				valid.add(x);
		}
		else if (fourCard)
		{
			for (Card x : hands)
				if (x.getNumber() == high || (high == 14 && x.getNumber() == 1))
					valid.add(x);
			if (high != 14)
				for (Card x : hands)
					if (x.getNumber() == 1)
					{
						valid.add(x);
						break;
					}
			int i = hands.size() - 1;
			while (valid.size() != 5)
			{
				Card x = hands.get(i--);
				if (!(x.getNumber() == high || (high == 14 && x.getNumber() == 1)))
					valid.add(x);
			}
		}
		else if (fullHouse)
		{
			for (Card x : hands)
				if (x.getNumber() == tHigh || x.getNumber() == p1High || (x.getNumber() == 1 && tHigh == 14) || (x.getNumber() == 1 && p1High == 14))
					valid.add(x);
			int i = 0;
			while (valid.size() != 5)
			{
				Card x = hands.get(i++);
				if (x.getNumber() == p1High)
					valid.remove(x);
			}
		}
		else if (flush)
		{
			for (Card x : hands)
				if (x.getShape() == fShape)
					valid.add(x);
			while (valid.size() != 5)
			{
				if (valid.get(0).getNumber() == 1)
					valid.remove(1);
				else
					valid.remove(0);
			}
		}
		else if (straight)
		{
			for (Card x : straightValid)
				valid.add(x);
			for (int i = 0; i < valid.size() - 1; i++)
				if (valid.get(i).getNumber() == valid.get(i + 1).getNumber())
					valid.remove(i + 1);
			while (valid.size() > 5)
					valid.remove(0);
		}
		else if (triple)
		{
			for (Card x : hands)
				if (x.getNumber() == tHigh || (tHigh == 14 && x.getNumber() == 1))
					valid.add(x);
			if (tHigh != 14)
				for (Card x : hands)
					if (x.getNumber() == 1)
						valid.add(x);
			int i = hands.size() - 1;
			while (valid.size() != 5)
			{
				Card x = hands.get(i--);
				if (!(x.getNumber() == tHigh || (tHigh == 14 && x.getNumber() == 1)))
					valid.add(x);
			}
		}
		else if (twoPair)
		{
			for (Card x : hands)
				if ((x.getNumber() == p1High || (p1High == 14 && x.getNumber() == 1)) || (x.getNumber() == p2High || (p2High == 14 && x.getNumber() == 1)))
					valid.add(x);
			if (p1High != 14 && p2High != 14)
				for (Card x : hands)
					if (x.getNumber() == 1)
					{
						valid.add(x);
						break;
					}
			int i = hands.size() - 1;
			while (valid.size() != 5)
			{
				Card x = hands.get(i--);
				if (!((x.getNumber() == p1High || (p1High == 14 && x.getNumber() == 1)) || (x.getNumber() == p2High || (p2High == 14 && x.getNumber() == 1))))
					valid.add(x);
			}
		}
		else if (pair)
		{
			for (Card x : hands)
				if (x.getNumber() == p1High || (p1High == 14 && x.getNumber() == 1))
					valid.add(x);
			if (p1High != 14)
				for (Card x : hands)
					if (x.getNumber() == 1)
					{
						valid.add(x);
						break;
					}
			int i = hands.size() - 1;
			while (valid.size() != 5)
			{
				Card x = hands.get(i--);
				if (!((x.getNumber() == p1High || (p1High == 14 && x.getNumber() == 1)) || (x.getNumber() == p2High || (p2High == 14 && x.getNumber() == 1))))
					valid.add(x);
			}
		}
		else
		{
			if (high == 14)
				for (Card x : hands)
					if (x.getNumber() == 1)
						valid.add(x);
			int i = hands.size() - 1;
			while (valid.size() != 5)
			{
				Card x = hands.get(i--);
				valid.add(x);
			}
		}
		
		int rank = Contested.HIGH_CARD;
		if (royalStraightFlush)
		{
			p1High = high;
			p2High = 0;
			rank = Contested.ROYAL_STRAIGHT_FLUSH;
		}
		else if (straightFlush)
		{
			p1High = high;
			p2High = 0;
			rank = Contested.STRAIGHT_FLUSH;
		}
		else if (fourCard)
		{
			p1High = high;
			p2High = valid.get(valid.size() - 1).getNumber();
			rank = Contested.FOUR_CARD;
		}
		else if (fullHouse)
		{
			p1High = tHigh;
			p2High = p1High;
			rank = Contested.FULL_HOUSE;
		}
		else if (flush)
		{
			p1High = high;
			p2High = 0;
			rank = Contested.FLUSH;
		}
		else if (straight)
		{
			p1High = stHigh;
			p2High = 0;
			rank = Contested.STRAIGHT;
		}
		else if (triple)
		{
			p1High = tHigh;
			p2High = 0;
			rank = Contested.TRIPLE;
		}
		else if (twoPair)
		{
			rank = Contested.TWO_PAIR;
		}
		else if (pair)
		{
			p2High = 0;
			rank = Contested.PAIR;
		}
		else
		{
			p1High = high;
			p2High = 0;
			rank = Contested.HIGH_CARD;
		}
		
		return new Contested(rank, p1High, p2High, valid);
	}
	
	public static void main(String args[])
	{
		new Main();
	}
}