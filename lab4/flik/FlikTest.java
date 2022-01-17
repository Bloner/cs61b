package flik;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FlikTest {
    @Test
    public void FlikTest1(){
        for(int i = 0, j = 0; i < 500; i++, j++){
            assertTrue("It should be True", Flik.isSameNumber(i, j));
        }
    }

    @Test
    public void FlikTest123(){
        assertTrue("It should be True", Flik.isSameNumber(123, 123));
    }

    @Test
    public void FlikTest223(){
        assertTrue("It should be True", Flik.isSameNumber(223, 223));
    }

    @Test
    public void FlikTest323(){
        assertTrue("It should be True", Flik.isSameNumber(323, 323));
    }

    @Test
    public void FlikTest23(){
        assertTrue("It should be True", Flik.isSameNumber(23, 23));
    }

    @Test
    public void FlikTest423(){
        assertTrue("It should be True", Flik.isSameNumber(423, 423));
    }

    @Test
    public void FlikTest200(){
        assertTrue("It should be True", Flik.isSameNumber(200, 200));
    }

    @Test
    public void FlikTest35(){
        assertTrue("It should be True", Flik.isSameNumber(35, 35));
    }

}
