package examples;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author august
 */
public class RandomBuster {
    //used internally by Random to generate the next numbers                                                                                                                                                
    private static final long MULTIPLIER = 0x5DEECE66DL;
    private static final long ADDEND = 0xBL;
    private static final long MASK = (1L << 48) - 1;

    //the seed. If we find the seed, we can predict the 'random' numbers                                                                                                                                    
    private static Long seed;

    //the random number we will be busting                                                                                                                                                                  
    private static final Random random = new Random();

    //how many iterations did we need to find the seed?                                                                                                                                                                      
    private static int iterations = 1;
    
    //delay execution so we can see what's happening" 
    private static boolean delay = false; 

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        
        if (args.length > 0) {
            delay = Boolean.valueOf(args[0]);   
        }
        
        //find the seed
        findSeed(random.nextInt());
        
        long endTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("Found seed in " + (endTime - startTime) + " milliseconds");
        System.out.println();
        
        //now that we know the seed, we can find the next integer                                                                                                                                           
        predictNext(32); // calculated seed is for v1. This gets us to v2                                                                                                                                   
        System.out.println("Predicted nextInt: " + predictNext(32));  //predicted v3                                                                                                                        

        System.out.println("   Random.nextInt: " + random.nextInt()); //actual v3                                                                                                                           
    }
    
    protected static void findSeed(long v1) {
        System.out.println();
        System.out.println("Finding seed. First value: " + v1);
        
        long v2 = random.nextInt();
        System.out.println("               Next value: " + v2);
                
        for (int i = 0; i < 65536; i++) {
            seed = v1 * 65536 + i;
            System.out.print("\rTesting possible seed: " + seed);
            if (delay) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RandomBuster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if ((((seed * MULTIPLIER + ADDEND) & MASK) >>> 16) == v2) {
                System.out.println();
                System.out.println("Seed found: " + seed + " in " + iterations + " iterations");
                break;
            }
            seed = null;
        }

        //if we haven't found it yet, loop through again                                                                                                                                                    
        iterations++;
        if (seed == null) findSeed(v2);
    }

    protected static synchronized long predictNext(int bits) {
        seed = (seed * MULTIPLIER + ADDEND) & MASK;
        return (long) (seed >>> (48 - bits));
    }
    
}