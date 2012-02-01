package fr.xebia.katas.gildedrose;

import com.google.common.base.Function;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Collections2.transform;
import static fr.xebia.katas.gildedrose.Inn.*;
import static org.fest.assertions.Assertions.assertThat;

public class InnTest {

	public static final int DECREASE_NORMAL_ITEM_AFTER_ONE_DAY = -1;
	public static final int INCREASE_AGED_ITEM_AFTER_ONE_DAY = 1;
	private static final int NOT_DECREASE_LEGEND_ITEM_AFTER_ONE_DAY = 0;
	public static final int INCREASE_BACKSTAGE_ITEM_AFTER_ONE_DAY = 1;
	private static final int DECREASE_CONJURED_ITEM_AFTER_ONE_DAY = -1;

	@Test
	public void shouldUpdateQuality_AfterOneDay() {
		Inn inn = new Inn( buildPreExistantItems() );

		List<Item> items = inn.getItems();

		List<Integer> qualities = new ArrayList<Integer>( transform( items, new Function<Item, Integer>() {
			public Integer apply( Item item ) {
				return item.getQuality();
			}
		} ) );
		inn.updateQuality();

		assertThat( inn.getItems() ).hasSize( 6 );

		assertThat( inn.getItems().get( 0 ).getQuality() )
				.isEqualTo( qualities.get( 0 ) + DECREASE_NORMAL_ITEM_AFTER_ONE_DAY );
		assertThat( inn.getItems().get( 1 ).getQuality() )
				.isEqualTo( qualities.get( 1 ) + INCREASE_AGED_ITEM_AFTER_ONE_DAY );
		assertThat( inn.getItems().get( 2 ).getQuality() )
				.isEqualTo( qualities.get( 2 ) + DECREASE_NORMAL_ITEM_AFTER_ONE_DAY );
		assertThat( inn.getItems().get( 3 ).getQuality() )
				.isEqualTo( qualities.get( 3 ) + NOT_DECREASE_LEGEND_ITEM_AFTER_ONE_DAY );
		assertThat( inn.getItems().get( 4 ).getQuality() )
				.isEqualTo( qualities.get( 4 ) + INCREASE_BACKSTAGE_ITEM_AFTER_ONE_DAY );
		/*assertThat( inn.getItems().get( 5 ).getQuality() )
				.isEqualTo( qualities.get( 5 ) + DECREASE_CONJURED_ITEM_AFTER_ONE_DAY );*/

	}

	@Test
	public void testQuality_whenSellIn_Overflow_ForNormalItem() {
		Inn inn = new Inn( Arrays.asList( new Item( NORMAL_ITEM_NAME, -1, 2 ) ) );
		inn.updateQuality();

		assertThat( inn.getItems().get( 0 ).getQuality() ).isEqualTo( 0 );
	}

	@Test
	public void testQuality_whenSellIn_Overflow_ForNormalItem_WithZeroQuality() {
		Inn inn = new Inn( Arrays.asList( new Item( NORMAL_ITEM_NAME, -1, 0 ) ) );
		inn.updateQuality();

		assertThat( inn.getItems().get( 0 ).getQuality() ).isEqualTo( 0 );
	}

	@Test
	public void qualityShouldNotExceed50_withAgeBrieItem() {
		Inn inn = new Inn( Arrays.asList( new Item( BRIE_ITEM_NAME, 0, 50 ) ) );
		inn.updateQuality();

		assertThat( inn.getItems().get( 0 ).getQuality() ).isEqualTo( 50 );
	}

	@Test
	public void qualityShouldBeZero_forBackstageItemsAfterConcert() {
		Inn inn = new Inn( Arrays.asList( new Item( BACKSTAGE_ITEM_NAME, 0, 1 ) ) );
		inn.updateQuality();

		assertThat( inn.getItems().get( 0 ).getQuality() ).isEqualTo( 0 );
	}

	@Test
	public void qualityShouldIncreaseBy2_forBackstageItemsAt10DaysToSellin() {
		Inn inn = new Inn( Arrays.asList( new Item( BACKSTAGE_ITEM_NAME, 9, 1 ) ) );
		inn.updateQuality();

		assertThat( inn.getItems().get( 0 ).getQuality() ).isEqualTo( 3 );
	}

	@Test
	public void qualityShouldIncreaseBy3_forBackstageItemsAt5DaysToSellin() {
		Inn inn = new Inn( Arrays.asList( new Item( BACKSTAGE_ITEM_NAME, 3, 1 ) ) );
		inn.updateQuality();

		assertThat( inn.getItems().get( 0 ).getQuality() ).isEqualTo( 4 );
	}

	@Test
	public void sellInShouldDecrease_afterOneDay() {
		Inn inn = new Inn( Arrays.asList( new Item( BACKSTAGE_ITEM_NAME, 1, 0 ) ) );
		inn.updateQuality();

		assertThat( inn.getItems().get( 0 ).getSellIn() ).isEqualTo( 0 );
	}

	@Test
	public void qualityShouldDecreaseTwiceFasterThanNormalItem_forConjuredItem() {
		Inn inn = new Inn( Arrays.asList( new Item( CONJURED_ITEM_NAME, 1, 2 ) ) );
		inn.updateQuality();

		assertThat( inn.getItems().get( 0 ).getQuality() ).isEqualTo( 0 );
	}

	private List<Item> buildPreExistantItems() {
		List<Item> items = new ArrayList<Item>();
		items.add( new Item( NORMAL_ITEM_NAME, 10, 20 ) );
		items.add( new Item( BRIE_ITEM_NAME, 2, 0 ) );
		items.add( new Item( NORMAL_MONGOOSE_ITEM_NAME, 5, 7 ) );
		items.add( new Item( LEGENDARY_ITEM_NAME, 0, 80 ) );
		items.add( new Item( BACKSTAGE_ITEM_NAME, 15, 20 ) );
		items.add( new Item( CONJURED_ITEM_NAME, 3, 6 ) );

		return items;
	}

}