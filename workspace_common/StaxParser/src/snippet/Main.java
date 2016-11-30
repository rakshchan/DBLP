package snippet;

import java.awt.EventQueue;
import java.util.*;

import javax.swing.JFrame;

/*! Main class */
public class Main {
	
	private EntityResolution er;
	/*!
     *  Constructor of Main Class
     *  A new instance of the EntityResolution Class is created.
     *  WWWParsing and AuthorParsing is called.
     */
	public Main()
	{
		er = EntityResolution.getInstance();
		er.parseWWW();
		er.parseAllAuthors();
	}
	
	/*!  
     *  MoreThanK function creates a set of authors.
     *  Iterates the Map<String,Author> and if the value 
     *  of the Number of publications is greater than K ,
     *   appends it to the set and returns it.
     */
	public Set<Author> getMoreThanK(int k)
	{
		Set<Author> KAuthors = new TreeSet<Author>();
		for(Map.Entry<String, Author> entry : er.ourMainMap.entrySet())
		{
			if(entry.getValue().getNumOfPublications() > k)
			{
				KAuthors.add(entry.getValue());
			}
		}
		return KAuthors;
	}
	
	/*!  
     *  Takes the AuthorName as the argument and returns the Publications
     *  by that author after Parsing.
     */
	public List<Publication> getPublicationByAuthor(String authorName)
	{
		return er.parsePublicationByAuthor(authorName);
	}
	
	/*!  
     * 	This Sorts the List of Publications By Year
     */
	public void sortPublicationByDate(List<Publication> publ)
	{
		Collections.sort(publ, new Comparator<Publication>()
				{
					public int compare(Publication a, Publication b)
					{
						return Integer.valueOf(b.getYear()).compareTo(a.getYear());
					}
				});
	}
	/*!  
     *  A function for Binary Search.
     */
	public int binarySearch(List<Publication> publ, int searchYear)
	{
		int l = 0;
		int h = publ.size();
		while(l < h )
		{
			int mid = (l + h)/2;
			if(publ.get(mid).getYear() < searchYear)
				h = mid;
			else
				l = mid + 1;
		}
		return l;
	}
	/*!  
     *  This first creates a list of string of titleQueries entered by the user.
     *  A list iterator iterates the List and removes the articles.
     *  Next, the parsePublicationByTitle method is called which is stored in a map.
     */
	public Map<Integer, List<Publication> > getPublicatonByTitle(String title)
	{
		String[] splitTitle = title.split(" ");
		List<String> listTitle = Arrays.asList(splitTitle);
		Iterator<String> listIterator = listTitle.iterator();
		while(listIterator.hasNext())
		{
			String word = listIterator.next();
			if(word.equalsIgnoreCase("is") | word.equalsIgnoreCase("a") | word.equalsIgnoreCase("an") | word.equalsIgnoreCase("the") | word.equalsIgnoreCase("on") | word.equalsIgnoreCase("for") | word.equalsIgnoreCase("of"))
			{
				listIterator.remove();
			}
		}
		Map<Integer, List<Publication> > publ = er.parsePublicationByTitle(listTitle);
		return publ;
	}
	
	public Integer prediction(Map<Integer, Integer> predMap, int year)
	{
		int n=predMap.size();
		System.out.println(n);
		double sumx, sumxsq, sumy, sumxy,a,b,denom;
		sumx = 0;
		sumxsq = 0;
		sumy = 0;
		sumxy = 0;

		for(Map.Entry<Integer, Integer> entry : predMap.entrySet())
		{ 
			
			int x = entry.getKey();
		    sumx += x;
		    sumxsq += (x)*(x);
		    int y = entry.getValue();
		    System.out.println(x + " : " + y);
		    sumy += y;	
		    sumxy += y*(x);
		}
		double avg = sumy/n;
		System.out.println(sumx);
		System.out.println(sumy);
		System.out.println(sumxsq);
		System.out.println(sumxy);
		denom = n*sumxsq-(sumx*sumx);
		a = ((sumy*sumxsq)-(sumx*sumxy))/denom;
		b = (n*sumxy-sumx*sumy)/denom;
		Double ans  = a+b*year;
		ans = (ans+(1.5*avg))/2.5;
		if(ans - ans.intValue() < 0.5)
			return ans.intValue();
		else
			return ans.intValue()+1;
	}
	/*!  
     *  This is the MAIN method where the GUI is invoked.
     */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
              DBLP_GUI frame = new DBLP_GUI();
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setVisible(true);
           }
        });
	}
}
