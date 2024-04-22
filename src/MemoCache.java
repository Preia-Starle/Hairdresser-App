import java.util.*;

public class MemoCache {

    //cache to store the results for each service combination
    private HashMap<String, Result> memoCache;

    //constructor
    public MemoCache() {
        this.memoCache = new HashMap<>();
    }

    //check if cache contains key
    public boolean containsKey(String key) {
        return memoCache.containsKey(key);
    }

    //retrieve cache value
    public Result getValue(String serviceCombinationKey) {
        return memoCache.get(serviceCombinationKey);
    }

    //add to cache
    public void addToCache(String serviceCombinationKey, Result result) {
        memoCache.put(serviceCombinationKey, result);
    }

    //generate unique key for serviceCombination regardless of order of items
    public String generateKey(ArrayList<Service> serviceCombination) {
        //sort the service combination list based on a serviceName
        serviceCombination.sort(Comparator.comparing(Service::getServiceName));
        //generate a hash code for the sorted service combination
        int hashCode = Objects.hash(serviceCombination);
        //convert the hash code to a string
        return Integer.toString(hashCode);
    }


}
