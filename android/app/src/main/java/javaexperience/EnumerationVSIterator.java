public class EnumerationDemo {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
 
        Vector<Integer> v = new Vector<Integer>();
         
        v.add(new Integer(3));
        v.add(new Integer(5));
        v.add(new Integer(7));
        v.add(new Integer(2));
        v.add(new Integer(9));
         
        Enumeration<Integer> e = v.elements();
        while(e.hasMoreElements()) {
            Integer obj = e.nextElement();
            System.out.println("Element is: " + obj.intValue());
        }
    }
}


The output of the above code is:

Element is: 3
Element is: 5
Element is: 7
Element is: 2
Element is: 9



Read more: http://www.javaexperience.com/difference-between-enumeration-and-iterator-in-java/#ixzz3pJrO98pG

public class IteratorDemo {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
 
        List<Integer> list = new ArrayList();
         
        list.add(new Integer(3));
        list.add(new Integer(5));
        list.add(new Integer(7));
        list.add(new Integer(2));
        list.add(new Integer(9));
         
        Iterator<Integer> it = list.iterator();
         
        while(it.hasNext()) {
            Integer obj = it.next();
            it.remove();
            System.out.println("Element is: " + obj.intValue());
        }
        System.out.println("List size is: " + list.size());
    }
}


Read more: http://www.javaexperience.com/difference-between-enumeration-and-iterator-in-java/#ixzz3pJrRpVfF

The output of the above code is:

Element is: 3
Element is: 5
Element is: 7
Element is: 2
Element is: 9
List size is: 0



Read more: http://www.javaexperience.com/difference-between-enumeration-and-iterator-in-java/#ixzz3pJrVSZWQ


Enumeration and Iterator are the interfaces used to basically traverse through the object collection. Enumeration is there from JDK 1.0 and Iterator is just the improved form of Enumeration we can say.

The major difference between Enumeration and Iterator is that iterator also supports removal of the element while traversing through the collection. Whereas, enumeration only provides the methods to check whether there are any other elements to be traversed and to get the next element from the collection. Iterator method names are also improved as compared to what we have in Enumeration.

Following are the methods available in Enumeration:
1. boolean hasMoreElements()
2. E nextElement()

Whereas the corresponding methods in Iterator are as follows including the extra ‘remove’ method to remove the element from the collection:
1. boolean hasNext()
2. E next()
3. remove()

The remove() method removes the last element returned by the iterator.
Let us see how we can use both of these interfaces while traversing through the collection:
This program uses the Enumeration object to traverse through the vector of Integers.



Read more: http://www.javaexperience.com/difference-between-enumeration-and-iterator-in-java/#ixzz3pJrYAdyC