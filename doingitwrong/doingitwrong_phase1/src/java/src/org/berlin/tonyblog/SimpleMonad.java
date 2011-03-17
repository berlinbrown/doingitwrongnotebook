/*
 * Java Monads
 * http://projects.tmorris.net/public/what-does-monad-mean/artifacts/1.1/chunk-html/ar01s04s05.html
 */
package org.berlin.tonyblog;

public class SimpleMonad<X,Y> {

    interface Transformer<X, Y> {
        Y transform(X x);
      }

      interface Monad<M> { // M :: * -> *
        <A> M<A> pure(A a);
        <A, B> M<B> bind(Transformer<A, M<B>> t, M<A> a);
      }
    
      new Monad<List>() { // List :: * -> * (kind checks)
          public <A> List<A> pure(A a) {
            return List.single(a);
          }

          public <A, B> List<B> bind(Transformer<A, List<B>> t, List<A> a) {
            List<B> r = List.empty();
            for(e : a) r.addAll(t.transform(e));        
            return r;
          }          
        };
      
}
