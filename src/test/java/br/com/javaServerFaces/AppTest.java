package br.com.javaServerFaces;

import static org.junit.Assert.assertTrue;

import javax.persistence.Persistence;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("javaServerFaces");
	}
	
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
