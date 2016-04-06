# MC-Challenge2016
Question: Implement the "people who bought X item also bought Y item" feature in amazon.com?

#include <vector>

class RelatedProducts {
public:
  
    // Called when a product purchase is made by a customer
    virtual void registerPurchase(long customerID, long productId) = 0;

    // Returns the next `numProducts` products related to this product
    virtual std::vector<long> getRelatedProducts(long customerID, long productId, int numProducts) = 0;

    // Returns the ID of a different customer who has also bought the productID
    virtual long getRelatedCustomer(long customerID, int productID) = 0;
};


Problem: Implement the "notify me when the item is in stock" feature in amazon.com


#include <vector>

struct Customer {
    long id;
    bool isPrime;
    void *data;  // use to maintain your own customer data (if needed)
};

class ProductNotifier {
public:
    // Scheme for ordering the notification list of customers
    const int SCHEME_FIFO = 0;         // First in First out
    const int SCHEME_PRIME_FIRST = 1;  // Prime members in FIFO order followed by nonPrime in FIFO

    // Returns the list of customer IDs to be notified depending on the scheme
    virtual std::vector<long> getCustomersToNotify(long productId, int scheme, int numCustomersToBeNotified) = 0;

    // Record a customer's request to be notified when the product becomes available
    virtual void notifyMe(long productId, Customer &customer) = 0;
};
    
    
