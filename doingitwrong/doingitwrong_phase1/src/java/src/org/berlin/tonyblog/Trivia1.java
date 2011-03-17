/**
 * From:
 * http://blog.tmorris.net/java-trivia/
 * 
 * Very verbose test of the Java trivia problem.
 */
package org.berlin.tonyblog;

import java.util.Date;

/**
 * Java Trivia

 Implement the missing function body. These rules must be followed:

    * No using null
    * No throwing an exception/error
    * Function must terminate
    * No side-effecting
    * No type-casing (instanceof)
    * No type-casting

 How many different ways of achieving the objective?
 
 * @author bbrown
 *
 */
public class Trivia1 {
    
    public interface Function<A, B> {
        B apply(A a);
    }    
    public static <A, B, C> Function<A, C> c(final Function<A, B> f, 
                                             final Function<B, C> g) {
        
        // With generics, return A, C parameters        
        return new Function<A, C>() {
            private A lastInput;
            public C apply(A a) {
                lastInput = a;
                return g.apply(f.apply(a));
            }
            public String toString() {               
                return (lastInput == null) ? "{invalid}" : lastInput.toString();
            }
        }; // End of anon        
    }       

    public static void test1() {
        System.out.println("Trivia 1 - return Function<A, C>");
        
        // Unrelated random classes: C = String, B = Long, A = Date Field
        final Function<Date, Long> f_ab = new Function<Date, Long>() {            
            private Date lastInput;                                    
            @Override
            public Long apply(Date a) {
                lastInput = a;
                return a.getTime();
            }            
            public String toString() {
                return lastInput.toString();
            }
        }; // End of Anon a-b
        
        final Function<Long, String> g_bc = new Function<Long, String>() {
            private Long lastInput;
            @Override
            public String apply(Long b) {
                lastInput = b;
                return b.toString();
            }
            public String toString() {
                return lastInput.toString();
            }
        }; // End of Anon a-b  
        
        System.out.println("Result: " + c(f_ab, g_bc).apply(new Date()));   
    }

    public static void test2() {
        System.out.println("Trivia 2 - return Function<A, C>");
        
        final Function<Integer, Integer> f_ab = new Function<Integer, Integer>() {            
                                               
            @Override
            public Integer apply(Integer a) {                
                return a * 2;
            }                        
        }; // End of Anon a-b
        
        final Function<Integer, Integer> g_bc = new Function<Integer, Integer>() {            
            @Override
            public Integer apply(Integer b) {                
                return b * 3;
            }            
        }; // End of Anon a-b  
        
        final Integer rescomplex = c(c(f_ab, g_bc), c(f_ab, g_bc)).apply(10);
        System.out.println("Result: " + c(f_ab, g_bc).apply(10));   
        System.out.println("Result-2: " + rescomplex);
    }    
    
    /**
     * Main Entry Point
     * @param args
     */
    public static void main(String [] args) {
        
        test2();
    }
    
} // End of Class //
