package com.github.rcored.project_auto_pass.security.hashing.implementations;

import com.github.rcored.project_auto_pass.security.hashing.abstractions.AbstractHashing;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.Getter;
import lombok.Setter;

/** Implement Argon2 hashing by Argon2
 * @author Marco Martucci
 * @version 0.1.0
 * */
@Getter
@Setter
public class HashingArgon2 implements AbstractHashing {
    /** Represents the memory (MB) that Argon2 will use for hashing */
    private int memory;
    /** Represents the iteration that Argon2 will perform for hashing */
    private int iteration;
    /** Represents the parallelism that Argon2 will use for hashing */
    private int parallelism;
    /** Represents the minimum default salt length that Argon2 will use for hashing */
    private int saltLength;
    /** Represents the minimum default hash length that Argon2 will use for hashing */
    private int hashLength;
    /** Represents the Argon2 reference used for hashing */
    private Argon2 argon2;

    /** Create Argon2 (constructor) */
    public HashingArgon2() {
        this.memory = memoryCalculation(); //in MB
        this.iteration = 1000;
        this.parallelism = parallelismCalculation();    //half of the core or 1 (for 1,2,3 core)
        this.saltLength = 16;   //minimum
        this.hashLength = 32;   //minimum
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id,this.saltLength,this.hashLength);
    }

    /** Create Argon2 (constructor)
     * @param memory Represents the memory (MB) that Argon2 will use for hashing.
     * @param iteration Represents the parallelism that Argon2 will use for hashing.
     * @param parallelism Represents the parallelism that Argon2 will use for hashing.
     * @param saltLength Represents the minimum default salt length that Argon2 will use for hashing.
     * @param hashLength Represents the minimum default hash length that Argon2 will use for hashing.
     * */
    public HashingArgon2(int memory, int iteration, int parallelism, int saltLength, int hashLength) {
        this.memory = memory; //in MB
        this.iteration = iteration;
        this.parallelism = parallelism;
        this.saltLength = saltLength;
        this.hashLength = hashLength;
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id,this.saltLength,this.hashLength);
    }

    /**
     * Use this method to hash a text
     *
     * @param plainString the text that will be hashed.
     * @return The hashed string of plainString.
     */
    @Override
    public String hash(String plainString) {
        return argon2.hash(iteration,memory,parallelism,plainString.getBytes());
    }

    /**
     * Use this method verify the plain text with the hashed text
     *
     * @param hashedString the hashed text.
     * @param plainString  the plain text that you want to verify.
     * @return TRUE if they are equals, FALSE otherwise.
     */
    @Override
    public boolean verify(String hashedString, String plainString) {
        return argon2.verify(hashedString,plainString.getBytes());
    }

    /** This method is required to calculate the memory that Argon2 will use
     * @return the memory in MB
     * */
    private int memoryCalculation() {
        return (int) (Runtime.getRuntime().maxMemory() / 1000000);  //in MB
    }

    /** This method is required to calculate the parallelism that Argon2 will use
     * @return 1 if there is only 1,2 or 3 core available, or the half of the core available
     * */
    private int parallelismCalculation() {
        return Runtime.getRuntime().availableProcessors() <= 3 ?
                1 :
                Runtime.getRuntime().availableProcessors() / 2;
    }
}
