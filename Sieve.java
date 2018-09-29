import java.util.Scanner;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * This class contains an implementation of the sieve of Eratosthenes algorithm
 * for finding prime numbers.
 *
 * @author Spencer Hubbard
 */
public class Sieve {
  public static void main(String[] args) {
    System.out.println("This program will tell you all prime numbers up to a given maximum.");
    System.out.println();

    Scanner console = new Scanner(System.in);
    System.out.print("Maximum number? ");
    int max = console.nextInt();

    List<Integer> primes = sieve(max);
    System.out.println("Prime numbers up to " + max + ":");
    System.out.println(primes);
  }

  /**
   * Finds all prime numbers up to a given max using the sieve of
   * Eratosthenes algorithm.
   *
   * @param max
   * the given max
   *
   * @return
   * a list of all prime numbers up to the given max
   */
  public static List<Integer> sieve(int max) {
    List<Integer> primes = new LinkedList<Integer>();
        Queue<Integer> numbers = new LinkedList<Integer>();

        if (max >= 2) {
            primes.add(2);
        }

    // add all odd numbers from 3 to max (inclusive) to queue
    for (int i = 3; i <= max; i += 2) {
      numbers.add(i);
    }

    while (!numbers.isEmpty()) {
      // remove a prime number from the front of the list
      int front = numbers.remove();
      primes.add(front);

      // if front squared is greater than max, then add rest
      if (max < front * front) {
        primes.addAll(numbers);
        numbers.clear();
      } else {
        // remove all multiples of this prime number
        Iterator<Integer> itr = numbers.iterator();
        while (itr.hasNext()) {
          int current = itr.next();
          if (current % front == 0) {
            itr.remove();
          }
        }
      }
    }

    return primes;
  }
}
