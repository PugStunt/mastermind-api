package com.pugstunt.mastermind.service;

import static com.google.common.collect.Lists.newArrayList;
import static com.pugstunt.mastermind.api.TestUtils.convertListColorsIntoGuess;
import static com.pugstunt.mastermind.core.domain.enums.Color.BLUE;
import static com.pugstunt.mastermind.core.domain.enums.Color.CYAN;
import static com.pugstunt.mastermind.core.domain.enums.Color.GREEN;
import static com.pugstunt.mastermind.core.domain.enums.Color.MAGENTA;
import static com.pugstunt.mastermind.core.domain.enums.Color.ORANGE;
import static com.pugstunt.mastermind.core.domain.enums.Color.PURPLE;
import static com.pugstunt.mastermind.core.domain.enums.Color.RED;
import static com.pugstunt.mastermind.core.domain.enums.Color.YELLOW;
import static com.pugstunt.mastermind.service.GameService.CODE_LENGTH;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.exception.MastermindException;
import com.pugstunt.mastermind.store.GameStore;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

	@Mock
	private GameStore store;
	
	@InjectMocks
	private GameService service;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private static List<Color> differentColorsAnswer;
	
	private static List<Color> containsRepeatedColorsAnswer;
	
	@BeforeClass
	public static void setUp() {
		
		differentColorsAnswer = Lists.newArrayList(Color.values());
		containsRepeatedColorsAnswer = 
			Lists.newArrayList(
					Color.BLUE, 
					Color.BLUE,
					Color.GREEN,
					Color.GREEN,
					Color.ORANGE,
					Color.ORANGE,
					Color.PURPLE,
					Color.PURPLE
				);
	}
	
	@Test
	public void newGame() {
		
		GameEntry entry = service.newGame("John Snow");
		assertThat(entry.getPlayer(), equalTo("John Snow"));
		assertThat(entry.getGameKey(), notNullValue());
		assertThat(entry.getGuesses(), equalTo(0));
		assertThat(entry.isSolved(), is(false));
		assertThat(entry.isActive(), is(true));
		assertThat(entry.getAnswer(), is(both(not(emptyCollectionOf(Color.class))).and(hasSize(CODE_LENGTH))));
	}
	
	@Test
	public void newGameWithGameKey() {
		
		GameEntry entry = service.newGame("John Snow", "gameKey#1");
		assertThat(entry.getPlayer(), equalTo("John Snow"));
		assertThat(entry.getGameKey(), equalTo("gameKey#1"));
		assertThat(entry.getGuesses(), equalTo(0));
		assertThat(entry.isSolved(), is(false));
		assertThat(entry.isActive(), is(true));
		assertThat(entry.getAnswer(), is(both(not(emptyCollectionOf(Color.class))).and(hasSize(CODE_LENGTH))));
	}
	
	@Test
	public void checkGuessNearResults() {
		
		GameEntry entry = createGameEntry(differentColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));

		entry = service.checkGuess("gameKey", newArrayList(MAGENTA, CYAN, PURPLE, ORANGE, YELLOW, BLUE, GREEN, RED));
		
		verify(store).findByKey("gameKey");
		
		assertThat(entry.getGameKey(), equalTo("gameKey"));
		assertThat(entry.getPlayer(), equalTo("Marty McFly"));
		assertThat(entry.getAnswer(), equalTo(differentColorsAnswer));
		assertThat(entry.getGuesses(), equalTo(1));
		assertThat(entry.getPastResults(), hasSize(1));
		assertThat(entry.getPastResults().stream().findFirst().get().getNear(), equalTo(8));
		assertThat(entry.getPastResults().stream().findFirst().get().getExact(), equalTo(0));
		assertThat(entry.isActive(), is(true));
		assertThat(entry.isSolved(), is(false));
		assertThat(entry.getPastResults().stream().findFirst().get().getGuess(), 
			equalTo(convertListColorsIntoGuess(
				newArrayList(MAGENTA, CYAN, PURPLE, ORANGE, YELLOW, BLUE, GREEN, RED))));
	}
	
	@Test
	public void checkGuessNearAndExactResults() {
		
		GameEntry entry = createGameEntry(differentColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));
		
		entry = service.checkGuess("gameKey", newArrayList(RED, CYAN, BLUE, ORANGE, YELLOW, PURPLE, GREEN, MAGENTA));
		
		verify(store).findByKey("gameKey");
		
		assertThat(entry.getGameKey(), equalTo("gameKey"));
		assertThat(entry.getPlayer(), equalTo("Marty McFly"));
		assertThat(entry.getAnswer(), equalTo(differentColorsAnswer));
		assertThat(entry.getGuesses(), equalTo(1));
		assertThat(entry.getPastResults(), hasSize(1));
		assertThat(entry.getPastResults().stream().findFirst().get().getNear(), equalTo(4));
		assertThat(entry.getPastResults().stream().findFirst().get().getExact(), equalTo(4));
		assertThat(entry.isActive(), is(true));
		assertThat(entry.isSolved(), is(false));
		assertThat(entry.getPastResults().stream().findFirst().get().getGuess(), 
				equalTo(convertListColorsIntoGuess(
						newArrayList(RED, CYAN, BLUE, ORANGE, YELLOW, PURPLE, GREEN, MAGENTA))));
	}
	
	@Test
	public void checkGuessExactResults() {
		
		GameEntry entry = createGameEntry(differentColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));
		
		entry = service.checkGuess("gameKey", newArrayList(RED, GREEN, BLUE, YELLOW, ORANGE, PURPLE, CYAN, MAGENTA));
		
		verify(store).findByKey("gameKey");
		
		assertThat(entry.getGameKey(), equalTo("gameKey"));
		assertThat(entry.getPlayer(), equalTo("Marty McFly"));
		assertThat(entry.getAnswer(), equalTo(differentColorsAnswer));
		assertThat(entry.getGuesses(), equalTo(1));
		assertThat(entry.getPastResults(), hasSize(1));
		assertThat(entry.getPastResults().stream().findFirst().get().getNear(), equalTo(0));
		assertThat(entry.getPastResults().stream().findFirst().get().getExact(), equalTo(8));
		assertThat(entry.isActive(), is(true));
		assertThat(entry.isSolved(), is(true));
		assertThat(entry.getPastResults().stream().findFirst().get().getGuess(), 
				equalTo(convertListColorsIntoGuess(
						newArrayList(RED, GREEN, BLUE, YELLOW, ORANGE, PURPLE, CYAN, MAGENTA))));
	}
	
	@Test
	public void checkGuessNearResultsWithRepeatedColorsAnswer() {
		
		GameEntry entry = createGameEntry(containsRepeatedColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));

		entry = service.checkGuess("gameKey", newArrayList(MAGENTA, CYAN, PURPLE, ORANGE, YELLOW, BLUE, GREEN, RED));
		
		verify(store).findByKey("gameKey");
		
		assertThat(entry.getGameKey(), equalTo("gameKey"));
		assertThat(entry.getPlayer(), equalTo("Marty McFly"));
		assertThat(entry.getAnswer(), equalTo(containsRepeatedColorsAnswer));
		assertThat(entry.getGuesses(), equalTo(1));
		assertThat(entry.getPastResults(), hasSize(1));
		assertThat(entry.getPastResults().stream().findFirst().get().getNear(), equalTo(4));
		assertThat(entry.getPastResults().stream().findFirst().get().getExact(), equalTo(0));
		assertThat(entry.getPastResults().stream().findFirst().get().getGuess(), 
			equalTo(convertListColorsIntoGuess(
				newArrayList(MAGENTA, CYAN, PURPLE, ORANGE, YELLOW, BLUE, GREEN, RED))));
	}
	
	@Test
	public void checkGuessNearAndExactResultsWithRepeatedColorsAnswer() {
		
		GameEntry entry = createGameEntry(containsRepeatedColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));
		
		entry = service.checkGuess("gameKey", newArrayList(RED, CYAN, GREEN, ORANGE, BLUE, CYAN, YELLOW, MAGENTA));
		
		verify(store).findByKey("gameKey");
		
		assertThat(entry.getGameKey(), equalTo("gameKey"));
		assertThat(entry.getPlayer(), equalTo("Marty McFly"));
		assertThat(entry.getAnswer(), equalTo(containsRepeatedColorsAnswer));
		assertThat(entry.getGuesses(), equalTo(1));
		assertThat(entry.getPastResults(), hasSize(1));
		assertThat(entry.getPastResults().stream().findFirst().get().getNear(), equalTo(2));
		assertThat(entry.getPastResults().stream().findFirst().get().getExact(), equalTo(1));
		assertThat(entry.getPastResults().stream().findFirst().get().getGuess(), 
				equalTo(convertListColorsIntoGuess(
						newArrayList(RED, CYAN, GREEN, ORANGE, BLUE, CYAN, YELLOW, MAGENTA))));
	}
	
	@Test
	public void checkGuessExactResultsWithRepeatedColorsAnswer() {
		
		GameEntry entry = createGameEntry(containsRepeatedColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));
		
		entry = service.checkGuess("gameKey", newArrayList(BLUE, BLUE, GREEN, GREEN, ORANGE, ORANGE, PURPLE, PURPLE));
		
		verify(store).findByKey("gameKey");
		
		assertThat(entry.getGameKey(), equalTo("gameKey"));
		assertThat(entry.getPlayer(), equalTo("Marty McFly"));
		assertThat(entry.getAnswer(), equalTo(containsRepeatedColorsAnswer));
		assertThat(entry.getGuesses(), equalTo(1));
		assertThat(entry.getPastResults(), hasSize(1));
		assertThat(entry.getPastResults().stream().findFirst().get().getNear(), equalTo(0));
		assertThat(entry.getPastResults().stream().findFirst().get().getExact(), equalTo(8));
		assertThat(entry.getPastResults().stream().findFirst().get().getGuess(), 
				equalTo(convertListColorsIntoGuess(
						newArrayList(BLUE, BLUE, GREEN, GREEN, ORANGE, ORANGE, PURPLE, PURPLE))));
	}
	
	@Test
	public void checkGuessRepeatedColorsNearResults() {
		
		GameEntry entry = createGameEntry(differentColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));
		
		entry = service.checkGuess("gameKey", newArrayList(MAGENTA, MAGENTA, CYAN, CYAN, GREEN, GREEN, RED, RED));
		
		verify(store).findByKey("gameKey");
		
		assertThat(entry.getGameKey(), equalTo("gameKey"));
		assertThat(entry.getPlayer(), equalTo("Marty McFly"));
		assertThat(entry.getAnswer(), equalTo(differentColorsAnswer));
		assertThat(entry.getGuesses(), equalTo(1));
		assertThat(entry.getPastResults(), hasSize(1));
		assertThat(entry.getPastResults().stream().findFirst().get().getNear(), equalTo(4));
		assertThat(entry.getPastResults().stream().findFirst().get().getExact(), equalTo(0));
		assertThat(entry.getPastResults().stream().findFirst().get().getGuess(), 
				equalTo(convertListColorsIntoGuess(
						newArrayList(MAGENTA, MAGENTA, CYAN, CYAN, GREEN, GREEN, RED, RED))));
	}
	
	@Test
	public void checkGuessRepeatedColorsNearAndExactResults() {
		
		GameEntry entry = createGameEntry(differentColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));
		
		entry = service.checkGuess("gameKey", newArrayList(RED, RED, BLUE, BLUE, ORANGE, ORANGE, CYAN, CYAN));
		
		verify(store).findByKey("gameKey");
		
		assertThat(entry.getGameKey(), equalTo("gameKey"));
		assertThat(entry.getPlayer(), equalTo("Marty McFly"));
		assertThat(entry.getAnswer(), equalTo(differentColorsAnswer));
		assertThat(entry.getGuesses(), equalTo(1));
		assertThat(entry.getPastResults(), hasSize(1));
		assertThat(entry.getPastResults().stream().findFirst().get().getNear(), equalTo(0));
		assertThat(entry.getPastResults().stream().findFirst().get().getExact(), equalTo(4));
		assertThat(entry.getPastResults().stream().findFirst().get().getGuess(), 
				equalTo(convertListColorsIntoGuess(
						newArrayList(RED, RED, BLUE, BLUE, ORANGE, ORANGE, CYAN, CYAN))));
	}
	
	@Test
	public void checkNullGuess() {
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Invalid guess");
		
		GameEntry entry = createGameEntry(differentColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));
		
		entry = service.checkGuess("gameKey", null);
	}
	
	@Test
	public void checkEmptyGuess() {
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Invalid guess");
		
		GameEntry entry = createGameEntry(differentColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));
		
		entry = service.checkGuess("gameKey", newArrayList());
	}
	
	@Test
	public void checkInvalidGuessSize() {
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Invalid size for guess. Expected: 8; Actual: 2");
		
		GameEntry entry = createGameEntry(differentColorsAnswer);
		
		when(store.findByKey("gameKey")).thenReturn(Optional.of(entry));
		
		entry = service.checkGuess("gameKey", newArrayList(RED, RED));
	}
	
	@Test
	public void gameKeyNotFound() {
		
		expectedException.expect(MastermindException.class);
		expectedException.expectMessage(Matchers.equalTo("No active game"));
	
		when(store.findByKey("gameKey")).thenReturn(Optional.empty());
		service.checkGuess("gameKey", newArrayList(RED, RED, BLUE, BLUE, ORANGE, ORANGE, CYAN, CYAN));
	}
	
	private GameEntry createGameEntry(List<Color> answer) {
		
		return GameEntry.builder("gameKey")
				.active(true)
				.startTime(new Date().getTime())
				.solved(false)
				.playerName("Marty McFly")
				.guessesNumber(0)
				.answer(answer)
				.build();
	}
}
