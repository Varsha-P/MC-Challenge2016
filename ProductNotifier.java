import java.util.*;

class Customer{ // Customer class
	long id;
	boolean isPrime;
	Customer(long id){
		this.id = id;
		this.isPrime = prime(id); // calling 'prime' method
		System.out.println(this.isPrime);
		
	}
	
	 /* I used 'prime' method as a way to check for prime numbers and set the isPrime
 	member variable without having to manually set the variable each time.
 	The method returns true if number is prime and false otherwise.
 	I consider 1 as non-prime so that it fits in one of the HashSets without being left out for being 'COMPOSITE'
	  */
	boolean prime(long number){
		
		int count = 0;

		for(int i=2;i<=number/2;i++){
			if(number%i==0)
				count++;
		}
		if(count>=1)
			return false;
		else
			return true;
	}
}
public class ProductNotifier { 
	
	/* I chose LinkedHashSet to store customers' requests so as to retain the FIFO order
	 *  and also checking for containment in a LinkedHashSet has better time complexity.
	 */
	HashMap<Long,LinkedHashSet<Customer>> fifo = new HashMap<Long,LinkedHashSet<Customer>>();
	HashMap<Long,LinkedHashSet<Customer>> primeFIFO = new HashMap<Long,LinkedHashSet<Customer>>();
	HashMap<Long,LinkedHashSet<Customer>> nonprimeFIFO = new HashMap<Long,LinkedHashSet<Customer>>();
	
	// helper method
	void notifyAdd(boolean prime,long productID, Customer obj){
		HashMap<Long,LinkedHashSet<Customer>> pnpFIFO;
		if(prime) // adds Customer to HashSet based on the boolean parameter 'prime'
			pnpFIFO = primeFIFO;
		else
			pnpFIFO = nonprimeFIFO;
		if(pnpFIFO.containsKey(productID)){
			pnpFIFO.get(productID).add(obj);
		}
		else{
			LinkedHashSet<Customer> lhs = new LinkedHashSet<Customer>();
			lhs.add(obj);
			pnpFIFO.put(productID, lhs);
		}
		
	}
	
	// method 1 --notifyMe, this method adds 
	void notifyMe(long productID, Customer obj){
		//add to fifo
		if(fifo.containsKey(productID)){
			fifo.get(productID).add(obj);
		}
		else{
			LinkedHashSet<Customer> lhs = new LinkedHashSet<Customer>();
			lhs.add(obj);
			fifo.put(productID, lhs);
		}
		notifyAdd(obj.isPrime,productID,obj); //add to primeFIFO or nonprimeFIFO
		
		
	}
	
	// method 2 --getCustomersToNotify
	ArrayList<Long> getCustomersToNotify(long productID, int scheme, int numCustomersToBeNotified){
		ArrayList<Long> customersToNotify = new ArrayList<Long>();
		int numOfCustomersSoFar = 0;
		if(scheme==0){ // FIFO ordering
			Iterator<Customer> itr1 = fifo.get(productID).iterator();
			while(itr1.hasNext()){
				Customer c1 = itr1.next();
				customersToNotify.add(c1.id);
				itr1.remove(); // remove customer once notified
				if(c1.isPrime) // to remove customer from other HashSet as well 
					primeFIFO.get(productID).remove(c1); 
				else
					nonprimeFIFO.get(productID).remove(c1);
				numOfCustomersSoFar++;
				
				if(numOfCustomersSoFar==numCustomersToBeNotified) // return when number of customers to be notified has been met
					return customersToNotify;
			}
		}
		else { //prime FIFO followed by non-prime FIFO ordering
			Iterator<Customer> itr2 = primeFIFO.get(productID).iterator();
			while(itr2.hasNext()){
				Customer c2 = itr2.next();
				customersToNotify.add(c2.id);
				itr2.remove(); // remove customer once notified
				fifo.get(productID).remove(c2);  // to remove customer from other HashSet as well
				numOfCustomersSoFar++;
				if(numOfCustomersSoFar==numCustomersToBeNotified) // return when number of customers to be notified has been met
					return customersToNotify; 
			}
			Iterator<Customer> itr3 = nonprimeFIFO.get(productID).iterator();
			while(itr3.hasNext()){
				Customer c3 = itr3.next();
				customersToNotify.add(c3.id);
				itr3.remove(); // remove customer once notified
				fifo.get(productID).remove(c3);  // to remove customer from other HashSet as well
				numOfCustomersSoFar++;
				if(numOfCustomersSoFar==numCustomersToBeNotified) // return when number of customers to be notified has been met
					return customersToNotify;
			}
		}
		return customersToNotify; // returns even if number of customers to be notified is greater than the number of number of customer requests for this product
		
	}

	public static void main(String[] args) {
		/* This is an example I created to check if my logic works
		I am creating customers with customerIDs and the constructor itself calls 'prime' method on customerID
		I then create an object for ProductNotifier and include a few customer requests.
		*/
		Customer obj1 = new Customer(1);
		Customer obj2 = new Customer(2);
		Customer obj3 = new Customer(3);
		Customer obj4 = new Customer(4);
		Customer obj5 = new Customer(5);
		Customer obj6 = new Customer(6);
		ProductNotifier pn = new ProductNotifier();
		pn.notifyMe(1, obj1);
		pn.notifyMe(2, obj1);
		pn.notifyMe(2, obj2);
		pn.notifyMe(3, obj1);
		pn.notifyMe(3, obj2);
		pn.notifyMe(3, obj3);
		pn.notifyMe(3, obj5);
		pn.notifyMe(3, obj6);
		pn.notifyMe(3, obj4);
		System.out.println(pn.getCustomersToNotify(3, 0, 5)); // output is [1,2,3,5,6] 
		System.out.println(pn.getCustomersToNotify(3, 1, 4)); // output is [4] and if above line is commented, output is [1,2,3,5]
		
	}

}
