package nl.duckstudios.pintandpillage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
class PintandpillageApplicationTests {

	@Test
	void contextLoads() {
		String isDanielHetzelfdeAlsTeelBalKanker = "Teelbal kanker";
		assertThat("Daniel", is(isDanielHetzelfdeAlsTeelBalKanker));


	}

}
