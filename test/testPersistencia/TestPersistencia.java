package testPersistencia;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPersistencia {

	@Before
	public void setUp() throws Exception {
		File archivo = new File("PruebaPersistencia.xml");
		if (archivo.exists())
			archivo.delete();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
	}

}
