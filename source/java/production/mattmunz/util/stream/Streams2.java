package mattmunz.util.stream;

import static java.lang.Math.min; 
import static java.util.Spliterator.DISTINCT;
import static java.util.Spliterator.SORTED;
import static java.util.Spliterator.SIZED;
import static java.util.Spliterators.iterator;
import static java.util.Spliterators.spliterator;
import static java.util.stream.StreamSupport.stream;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Utility for working with streams. Includes some code derived from code on stack overflow.
 * 
 * TODO Come up with a better class name.
 */
public class Streams2
{
  public static <L, R, O> Stream<O> 
    zip(Stream<? extends L> left, Stream<? extends R> right,
        BiFunction<? super L, ? super R, ? extends O> combiner)
  {
    @SuppressWarnings("unchecked")
    Spliterator<L> leftSpliterator = (Spliterator<L>) left.spliterator();

    @SuppressWarnings("unchecked")
    Spliterator<R> rightSpliterator = (Spliterator<R>) right.spliterator();
    
    boolean isParallel = left.isParallel() || right.isParallel();

    int outputCharacteristics 
      = leftSpliterator.characteristics() & rightSpliterator.characteristics()
        & ~(DISTINCT | SORTED);

    long outputSize 
      = (outputCharacteristics & SIZED) != 0 
        ? min(leftSpliterator.getExactSizeIfKnown(), rightSpliterator.getExactSizeIfKnown())
        : -1;

    return zip(leftSpliterator, rightSpliterator, combiner, isParallel, 
               outputCharacteristics, outputSize); 
  }

  private static <O, L, R> Stream<O> 
    zip(Spliterator<L> left, Spliterator<R> right,
        final BiFunction<? super L, ? super R, ? extends O> combiner,
        boolean isParallel, int characteristics, long size)
  {
    final Iterator<L> leftIterator = iterator(left);
    final Iterator<R> rightIterator = iterator(right);
    
    Iterator<O> outputIterator = new Iterator<O>()
    {
      @Override
      public boolean hasNext() { return leftIterator.hasNext() && rightIterator.hasNext(); }

      @Override
      public O next() { return combiner.apply(leftIterator.next(), rightIterator.next()); }
    };

    return stream(spliterator(outputIterator, size, characteristics), isParallel);
  }
}
