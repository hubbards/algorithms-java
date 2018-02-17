import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This program is a simple test suit for the sieve of Eratosthenes algorithm.
 *
 * @author Spencer Hubbard
 */
public class SieveTest {
    // First six primes
    private static int[] smallPrimes = {2, 3, 5, 7, 11, 13};

    // One hundredth prime
    private static int bigPrime = 541;

    @BeforeClass
    public static void setupForTests() {
        // TODO: fill out
    }

    // Helper method for testing small primes.
    private static void testSmallPrimes(List<Integer> numbers) {
        assertTrue("Too many numbers to test.", numbers.size() <= smallPrimes.length);
        for (int i = 0; i < numbers.size(); i++) {
            assertEquals("Next number is not equal to next prime.", smallPrimes[i], (long) numbers.get(i));
        }
    }

    @Test
    public void testNegativeOne() {
        List<Integer> numbers = Sieve.sieve(-1);
        assertTrue(numbers.isEmpty());
    }

    @Test
    public void testOne() {
        List<Integer> numbers = Sieve.sieve(1);
        assertTrue(numbers.isEmpty());
    }

    @Test
    public void testTwo() {
        List<Integer> numbers = Sieve.sieve(2);
        assertEquals(1, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testThree() {
        List<Integer> numbers = Sieve.sieve(3);
        assertEquals(2, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testFour() {
        List<Integer> numbers = Sieve.sieve(4);
        assertEquals(2, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testFive() {
        List<Integer> numbers = Sieve.sieve(5);
        assertEquals(3, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testSix() {
        List<Integer> numbers = Sieve.sieve(6);
        assertEquals(3, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testSeven() {
        List<Integer> numbers = Sieve.sieve(7);
        assertEquals(4, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testEight() {
        List<Integer> numbers = Sieve.sieve(8);
        assertEquals(4, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testNine() {
        List<Integer> numbers = Sieve.sieve(9);
        assertEquals(4, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testTen() {
        List<Integer> numbers = Sieve.sieve(10);
        assertEquals(4, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testEleven() {
        List<Integer> numbers = Sieve.sieve(11);
        assertEquals(5, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testTwelve() {
        List<Integer> numbers = Sieve.sieve(12);
        assertEquals(5, numbers.size());
        testSmallPrimes(numbers);
    }

    @Test
    public void testThirteen() {
        List<Integer> numbers = Sieve.sieve(13);
        assertEquals(6, numbers.size());
        testSmallPrimes(numbers);
    }
    
    @Test
    public void testBigPrime() {
        List<Integer> numbers = Sieve.sieve(bigPrime);
        assertEquals(bigPrime, (long) numbers.get(numbers.size() - 1));
    }
}
