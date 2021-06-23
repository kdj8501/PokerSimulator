import java.util.ArrayList;

public class Contested implements Comparable<Contested>{
	public static final int HIGH_CARD = 0;
	public static final int PAIR = 1;
	public static final int TWO_PAIR = 2;
	public static final int TRIPLE = 3;
	public static final int STRAIGHT = 4;
	public static final int FLUSH = 5;
	public static final int FULL_HOUSE = 6;
	public static final int FOUR_CARD = 7;
	public static final int STRAIGHT_FLUSH = 8;
	public static final int ROYAL_STRAIGHT_FLUSH = 9;
	
	private int rank;
	private int high1;
	private int high2;
	private ArrayList<Card> valid;
	public Contested(int rank, int high1, int high2, ArrayList<Card> valid)
	{
		this.valid = valid;
		setRank(rank);
		setHigh1(high1);
		setHigh2(high2);
	}
	
	public void setRank(int rank) { this.rank = rank; }
	public int getRank() { return rank; }
	public void setHigh1(int high1) { this.high1 = high1; }
	public int getHigh1() { return high1; }
	public void setHigh2(int high2) { this.high2 = high2; }
	public int getHigh2() { return high2; }
	
	public ArrayList<Card> getValid() { return valid; }
	
	public int compareTo(Contested other)
	{
		if (getRank() > other.getRank())
			return 1;
		else if (getRank() < other.getRank())
			return - 1;
		else
		{
			if (getHigh1() > other.getHigh1())
				return 1;
			else if (getHigh1() < other.getHigh1())
				return -1;
			else
			{
				if (getHigh2() > other.getHigh2())
					return 1;
				else if (getHigh2() < other.getHigh2())
					return -1;
				else
				{
					if (getRank() > 3) return 0;
					else
					{
						if (getRank() == 3)
						{
							int val1 = getValid().get(3).getNumber() == 1 ? 14 : getValid().get(3).getNumber(), val2 = other.getValid().get(3).getNumber() == 1 ? 14 : other.getValid().get(3).getNumber();
							if (val1 > val2)
								return 1;
							else if (val1 < val2)
								return -1;
							else
							{
								val1 = getValid().get(4).getNumber() == 1 ? 14 : getValid().get(4).getNumber(); val2 = other.getValid().get(4).getNumber() == 1 ? 14 : other.getValid().get(4).getNumber();
								if (val1 > val2)
									return 1;
								else if (val1 < val2)
									return -1;
								else
									return 0;
							}
						}
						else if (getRank() == 2)
						{
							int val1 = getValid().get(4).getNumber() == 1 ? 14 : getValid().get(4).getNumber(), val2 = other.getValid().get(4).getNumber() == 1 ? 14 : other.getValid().get(4).getNumber();
							if (val1 > val2)
								return 1;
							else if (val1 < val2)
								return -1;
							else
							{
								return 0;
							}
						}
						else if (getRank() == 1)
						{
							int val1 = getValid().get(2).getNumber() == 1 ? 14 : getValid().get(2).getNumber(), val2 = other.getValid().get(2).getNumber() == 1 ? 14 : other.getValid().get(2).getNumber();
							if (val1 > val2)
								return 1;
							else if (val1 < val2)
								return -1;
							else
							{
								val1 = getValid().get(3).getNumber() == 1 ? 14 : getValid().get(3).getNumber(); val2 = other.getValid().get(3).getNumber() == 1 ? 14 : other.getValid().get(3).getNumber();
								if (val1 > val2)
									return 1;
								else if (val1 < val2)
									return -1;
								else
								{
									val1 = getValid().get(4).getNumber() == 1 ? 14 : getValid().get(4).getNumber(); val2 = other.getValid().get(4).getNumber() == 1 ? 14 : other.getValid().get(4).getNumber();
									if (val1 > val2)
										return 1;
									else if (val1 < val2)
										return -1;
									else
										return 0;
								}
							}
						}
						else
						{
							int val1 = getValid().get(1).getNumber() == 1 ? 14 : getValid().get(1).getNumber(), val2 = other.getValid().get(1).getNumber() == 1 ? 14 : other.getValid().get(1).getNumber();
							if (val1 > val2)
								return 1;
							else if (val1 < val2)
								return -1;
							else
							{
								val1 = getValid().get(2).getNumber() == 1 ? 14 : getValid().get(2).getNumber(); val2 = other.getValid().get(2).getNumber() == 1 ? 14 : other.getValid().get(2).getNumber();
								if (val1 > val2)
									return 1;
								else if (val1 < val2)
									return -1;
								else
								{
									val1 = getValid().get(3).getNumber() == 1 ? 14 : getValid().get(3).getNumber(); val2 = other.getValid().get(3).getNumber() == 1 ? 14 : other.getValid().get(3).getNumber();
									if (val1 > val2)
										return 1;
									else if (val1 < val2)
										return -1;
									else
									{
										val1 = getValid().get(4).getNumber() == 1 ? 14 : getValid().get(4).getNumber(); val2 = other.getValid().get(4).getNumber() == 1 ? 14 : other.getValid().get(4).getNumber();
										if (val1 > val2)
											return 1;
										else if (val1 < val2)
											return -1;
										else
											return 0;
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
