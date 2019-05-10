package com.github.hubbards.algorithms.graph;

import org.junit.Assert;
import org.junit.Test;

public class KruskalGraphTest extends WeightedGraphTest<KruskalGraph> {
    @Override
    protected KruskalGraph createGraph() {
        return new KruskalGraph();
    }

    /*
     * Minimum spanning tree
     *
     * debug output
     * edge:      color: cost:
     * (a  , d  ) BLACK  1.00
     * (f  , g  ) BLACK  1.00
     * (a  , b  ) BLACK  2.00
     * (c  , d  ) BLACK  2.00
     * (b  , d  ) WHITE  3.00
     * (a  , c  ) WHITE  4.00
     * (d  , g  ) BLACK  4.00
     * (c  , f  ) WHITE  5.00
     * (e  , g  ) BLACK  6.00
     * (d  , e  ) WHITE  7.00
     * (d  , f  ) WHITE  8.00
     * (b  , e  ) WHITE  10.00
     * total cost: 16.00
     */

    @Test
    public void testMinimumSpanningTreeCost() {
        Assert.assertEquals(16, graph.minimumSpanningTreeCost(), DELTA);
    }
}
