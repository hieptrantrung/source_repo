Singleton pattern is used to create a single instance of a class in the entire application, The use of singleton itself is debatable and has been discouraged by google. Still new applications keep on using it and make use of it. Though the definition may look very simple but the implementation has some tricks present in it.

There are multiple approaches to implement the singleton pattern and these approaches vary with the JDK version being used. As we shall see shortly, JDK 1.5 offers the best approach. Let us see the various implementations of Singleton:

Lazy Creation Non-Thread safe

The very basic approach is to use a instance member variable and check if it has already been initialized or not. If initialized, return the instance else initialize an return the reference. The following is the sample code:

public class SingleInstance {
 
    private SingleInstance inst = null;
     
    private SingleInstance(){}
     
    public static SingleInstance getInstance() {
        if(inst==null) {
            inst = new SingleInstance();
        }
        return inst;
    }
}
If you try calling the above method getInstance from two different java threads, then there could be a scenario where two different threads could get hold of two separate instances due to thread interleaving. But if your application is single threaded then this program will work fine.

Lazy Creation Thread safe

We can make the above non-thread safe implementation thread safe by using synchronization. Again we can place synchronized method or block to avoid thread interleaving but still the best approach is shown below:

public class SingleInstance {
    private SingleInstance inst = null;
     
    private SingleInstance(){}
     
    public static SingleInstance getInstance() {
        synchronized(SingleInstance.class) {
            if(inst == null) {
                inst = new SingleInstance();
            }
        }
        return inst;
    }
}
As shown above, a class level lock has been used which can only be acquired by a single thread in an application

Early Creation Thread safe

There is another approach to make the singleton thread safe by initializing he instance during loading of class. This is achieved by making the member variable as static and initializing it at declaration time. The sample code is shown below:

public class SingleInstance {
    private static SingleInstance inst = new SingleInstance();
     
    private SingleInstance(){}
     
    public static SingleInstance getInstance() {
        return inst;
    }
}
The above code has the disadvantage that irrespective of whether the instance is really required, it is getting created during class loading. Hence we could be wasting resources if the single instance is never required but the singleton class was referred to at some point of time.

Using enum

Enums were introduced in JDK 1.5. These are best available and easy to use approach for implementing Singleton pattern.

public enum SingleInstance {
    INSTANCE;
 
    private SingleInstance() {}
 
    public void dostuff() {
     ...
    }
}
The above enum has private constructor and has one instance only. The clients will have to INSTANCE to access the methods of this enum. For example : SingleInstance.INSTANCE.doStuff();

Reference

1) http://en.wikipedia.org/wiki/Singleton_pattern
2) http://www.oodesign.com/singleton-pattern.html
3) http://www.javaworld.com/community/node/8425
4) http://www.codeproject.com/Articles/37897/Singleton-Design-Pattern-in-Asp-net-using-C



Read more: http://www.javaexperience.com/design-patterns-singleton-design-pattern/#ixzz3pJuoeBud