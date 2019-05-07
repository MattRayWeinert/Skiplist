import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Random;


public class Hw02 {
	
    public static void complexityIndicator()
    {
    	System.err.println("ma483382;1.75;6");
    }
	
    public static void main(String[] args)
    {
        File file = new File(args[0]);
        long seed;
        Random random;
        
        // Scans through the input file and performs functions
        try
        {
        	
        	List list = new List();
           
            BufferedReader br = new BufferedReader(new FileReader(file));
                      
            Scanner scanner = new Scanner(file);
            
        	if(args[0].equals(new String("R")))
        	{
        		seed = System.currentTimeMillis();
        		System.out.println("For the input file named " + args[0]);
        		System.out.println("With the RNG seeded" + seed + ",");
        	}
        	else
        	{
        		seed = 42;
        		System.out.println("For the input file named " + args[0]);
        		System.out.println("With the RNG unseeded,");
        	}
        	
            random = new Random(seed);
        		
            // Loops through file until EOF
            while(scanner.hasNext())
            {            	
                // Scans the line
                String data = scanner.nextLine();
               
                // Splits the function and the number into two Strings
                String[] line = data.split(" "); 
                
                // Insert number call
                if(line[0].equals("i"))
                {                	
                    int num = Integer.parseInt(line[1]);
                    
                    if(list.search(num) != true)
                    {
                        list.insert(num);
                        
                    	while(random.nextInt() % 2 == 1)
                    	{
                    		list.promote(num);
                    	}
                    }

                	
                    // IF(promote success)
                    // insert
                	
                    list.sort();
                }
               
                // Delete number call
                if(line[0].equals("d"))
                {
                    int delNum = Integer.parseInt(line[1]);
                    
                    if(list.search(delNum) == true)
                    {
                    	list.delete(delNum);
                    	System.out.println(delNum + " deleted");
                    }
                    
                    else
                    {
                    	System.out.println(delNum + " integer not found - delete not successful");
                    }

                }
                
                // Quit function and calls the Tree's info
                if(line[0].equals("q"))
                {                                        
                	// Quit Function
                }
               
                // Print function call
                if(line[0].contentEquals("p"))
                {
                	list.print();
                }
                
                // Search function call
                if(line[0].contentEquals("s"))
                {
                	int searchNum = Integer.parseInt(line[1]);
                	
                	if(list.search(searchNum) == true)
                	{
                		System.out.println(searchNum + " found");
                	}
                	
                	else
                	{
                		System.out.println(searchNum + " NOT FOUND");
                	}
                }
 
            }
            
            // Closes text file
            br.close();
           
            // Closes the scanner
            scanner.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // Prints UCF info to STD.err
        complexityIndicator();
    }
}

class List
{
	Node head;
	Node tail;
    
    public List()
    {
        head = null;
        tail = null;
    }
    
	class Node
	{
		int data;
		int level = 1;
		Node next, back;
		
		public Node(int d)
		{
			this.data = d;
		}
	}
	
	public void promote(int key)
	{
		Node temp = head;

		while(temp.data != key)
		{
			temp = temp.next;
		}
		
		temp.level += 1;
	}
	
	public void insert(int data)
	{
		Node node = new Node(data);
		
		if(head == null)
		{
			head = tail = node;
			
			head.back = null;
			
			tail.next= null;
		}
		
		else
		{
			tail.next = node;
			
			node.back = tail;
			
			tail = node;
			
			tail.next = null;
		}
	}
	
	public void sort()
	{
		Node current = null;
		Node index = null;
		
		int temp;
		int temp2;
		
		if(head == null)
		{
			return;
		}
		
		else
		{
			for(current = head; current.next != null; current = current.next)
			{
				for(index = current.next; index != null; index = index.next)
				{
					if(current.data > index.data)
					{
						temp = current.data;
						temp2 = current.level;
						current.data = index.data;
						current.level = index.level;
						index.data = temp;
						index.level = temp2;
					}
				}
			}
		}
	}
	
	public void delete(int key)
	{
		deleteNode(head, key);
	}
	
	public Node deleteNode(Node head, int key)
	{
		
		Node temp = head;
		Node temp2 = null;
		Node temp3 = null;
		
		while(temp.data != key)
		{
			temp = temp.next;
		}
		
		if(temp.back == null)
		{
			temp3 = temp.next;
			temp3.back = temp2;
		}
		
		else if(temp.next == null)
		{
			temp2 = temp.back;
			temp2.next = temp3;
		}
		
		else
		{
			temp2 = temp.back;
			temp3 = temp.next;
			temp2.next = temp3;
			temp3.back = temp2;
		}

		return head;
	}
	
	public void print()
	{
		Node current = head;
		
		if(head == null)
		{
			System.out.println("Empty list");
			return;
		}
		
		System.out.println("the current Skip List is shown below: "); 
		System.out.println("---infinity");
		
		while(current != null)
		{
			for(int i = 0; i < current.level; i++)
			{
				System.out.print(" " + current.data + "; ");
			}
			
			System.out.println();
			current = current.next;
		}
		
		System.out.println("+++infinity");
		System.out.println("---End of Skip List---");
	}
	
    public boolean search(int key)
    {
    	return searchKey(head, key);
    }
    
	public boolean searchKey(Node head, int key)
	{
		Node here = head;
		
		while(here != null)
		{
			if(here.data == key)
			{
				return true;
			}
			
			here = here.next;
		}
		
		return false;
	}
}

