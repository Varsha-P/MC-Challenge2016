import java.util.*;

public class RelatedProducts {
	public Map<Long,LinkedHashSet<Long>> customers = new HashMap<Long,LinkedHashSet<Long>>();
	public Map<Long,LinkedHashSet<Long>> products = new HashMap<Long,LinkedHashSet<Long>>();
	// Method 1
	void registerPurchase(long customerID,long productID){ 
		if(customers.containsKey(customerID)){
			if(customers.get(customerID).contains(productID)){
				
			}
			else{
				customers.get(customerID).add(productID);
			}		
		}
		else{
			LinkedHashSet<Long> newSet = new LinkedHashSet<Long>();
			newSet.add(productID);
			customers.put(customerID,newSet);
		}
		if(products.containsKey(productID)){
			LinkedHashSet<Long> set = products.get(productID);
			if(set.contains(customerID)){
				
			}
			else{
				set.add(customerID);
			}		
		}
		else{
			LinkedHashSet<Long> newSet = new LinkedHashSet<Long>();
			newSet.add(customerID);
			products.put(productID,newSet);
		}
		
	}
	
	// Method 2
	ArrayList<Long> getRelatedProducts(long customerID, long productID, int numProducts){
		ArrayList<Long> relatedProducts = new ArrayList<Long>();
		long countOfRelatedProducts = 0;
		//products
		LinkedHashSet<Long> customersList = products.get(productID);
		for(long cid:customersList){
			if(cid!=customerID)
			{
				LinkedHashSet<Long> productsList = customers.get(cid);
				for(long pid:productsList){
					if(pid!=productID)
					{
						relatedProducts.add(pid);
						countOfRelatedProducts ++;	
					}
					if(numProducts==countOfRelatedProducts)
						return relatedProducts;
				}
			}
		}
		return relatedProducts;
	}
	
	// Method 3
	long relatedCustomer(long customerID, long pID){
		LinkedHashSet<Long> relatedCust= products.get(pID); // retrieve customers who bought same product ie., related customers
		for(long cid:relatedCust){ // iterate through related customers
			if(cid!=customerID) // to make sure we do not return the same customer
				return cid; // return the first related customer
		}
		return 0; // return 0 if there is no related customer
	}
	
	public static void main(String args[]){
		RelatedProducts rl = new RelatedProducts();
		rl.registerPurchase(111, 111);
		rl.registerPurchase(111, 222);
		//rl.registerPurchase(111, 222);
		rl.registerPurchase(111, 444);
		rl.registerPurchase(222, 333);
		rl.registerPurchase(222, 444);
		rl.registerPurchase(222, 777);
		rl.registerPurchase(333, 444);
		rl.registerPurchase(333, 777);
		System.out.println(rl.customers);
		System.out.println(rl.products);
		//long relatedCustomer = rl.relatedCustomer(111, 111); //(111,444)
		long relatedCustomer = rl.relatedCustomer(111,444);
		if(relatedCustomer==0)
			System.out.println("There's no related customer");
		else
			System.out.println(relatedCustomer);
		System.out.println(rl.getRelatedProducts(333, 777, 2));
		
	}

}

