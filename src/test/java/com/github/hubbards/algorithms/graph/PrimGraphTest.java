package com.github.hubbards.algorithms.graph;

import org.junit.Assert;
import org.junit.Test;

public class PrimGraphTest extends WeightedGraphTest<PrimGraph> {
    @Override
    protected PrimGraph createGraph() {
        return new PrimGraph();
    }

    /*
     * Minimum spanning tree
     *
     * debug output
     * edge:      color: cost:
     * (a  , b  ) BLACK  2.00
     * (a  , c  ) GRAY   4.00
     * (a  , d  ) BLACK  1.00
     * (b  , d  ) GRAY   3.00
     * (b  , e  ) GRAY   10.00
     * (c  , d  ) BLACK  2.00
     * (c  , f  ) GRAY   5.00
     * (d  , e  ) GRAY   7.00
     * (d  , f  ) GRAY   8.00
     * (d  , g  ) BLACK  4.00
     * (e  , g  ) BLACK  6.00
     * (f  , g  ) BLACK  1.00
     * total cost: 16.00
     *
     */

    @Test
    public void testPrim1() {
        Assert.assertEquals(16.0, graph.prim1(), DELTA);
    }
}
