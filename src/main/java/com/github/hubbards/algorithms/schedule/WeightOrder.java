package com.github.hubbards.algorithms.schedule;

import java.util.Comparator;

/**
 * WeightOrder is an ordering on {@link Weighted} by increasing weight. Note
 * that this ordering is not consistent with equals.
 *
 * @author Spencer Hubbard
 */
public class WeightOrder implements Comparator<Weighted> {
    @Override
    public int compare(Weighted w1, Weighted w2) {
        return w1.getWeight() - w2.getWeight();
    }
}
