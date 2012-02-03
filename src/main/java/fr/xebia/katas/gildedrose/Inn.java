package fr.xebia.katas.gildedrose;

import java.util.List;

public class Inn {
	public static final String NORMAL_ITEM_NAME = "+5 Dexterity Vest";
	public static final String NORMAL_MONGOOSE_ITEM_NAME = "Elixir of the Mongoose";
	public static final String BRIE_ITEM_NAME = "Aged Brie";
	public static final String LEGENDARY_ITEM_NAME = "Sulfuras, Hand of Ragnaros";
	public static final String BACKSTAGE_ITEM_NAME = "Backstage passes to a TAFKAL80ETC concert";

	public static final String CONJURED_ITEM_NAME = "Conjured Mana Cake";

	public static final Integer MAX_PERMITTED_QUALITY_VALUE = 50;
	public static final int MIN_PERMITTED_QUALITY_VALUE = 0;

	public static final int TEN_DAYS_BEFORE_CONCERT = 10;
	public static final int FIVE_DAYS_BEFORE_CONCERT = 5;

	private List<Item> items;

	public Inn( List<Item> items ) {
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void updateQuality() {
		
		for( Item item : items ) {

			updateItemQuality( item );

			decrementSellinForItem( item );

			updateItemQualityWhenConcertDateIsPassed( item );
		}
	}

	private void updateItemQuality( Item item ) {
		if( isNotBrieOrBackstageItem( item ) ) {
			decrementItemQualityForNormalItem( item );
		} else {
			increaseQualityForBrieOrBackstageItem( item );
		}
	}

	private boolean concertDateIsPassed( Item item ) {
		return item.getSellIn() < 0;
	}

	private Boolean isNotBrieOrBackstageItem( Item item ) {
		return isNotBrieItem( item ) && isNotBackstageItem( item );
	}

	private boolean isNotBrieItem( Item item ) {
		return !item.getName().equals( BRIE_ITEM_NAME );
	}

	private boolean isNotBackstageItem( Item item ) {
		return !item.getName().equals( BACKSTAGE_ITEM_NAME );
	}

	private void decrementSellinForItem( Item item ) {
		if( !item.getName().equals( LEGENDARY_ITEM_NAME ) ) {
			item.setSellIn( item.getSellIn() - 1 );
		}
	}

	private void decrementItemQualityForNormalItem( Item item ) {
		if( item.getQuality() > MIN_PERMITTED_QUALITY_VALUE ) {
			if( !item.getName().equals( LEGENDARY_ITEM_NAME ) ) {
				item.setQuality( item.getQuality() - 1 );
			}

			if( item.getName().equals( CONJURED_ITEM_NAME ) ) {
				item.setQuality( item.getQuality() - 1 );
			}
		}
	}

	private void updateItemQualityWhenConcertDateIsPassed( Item item ) {
		if( concertDateIsPassed( item ) ) {
			if( isNotBrieItem( item ) ) {
				if( isNotBackstageItem( item ) ) {
					decrementItemQualityForNormalItem( item );
				} else {
					item.setQuality( MIN_PERMITTED_QUALITY_VALUE );
				}
			} else {
				incrementItemQuality_byOne( item );
			}
		}
	}

	private void increaseQualityForBrieOrBackstageItem( Item item ) {
		incrementItemQuality_byOne( item );

		incrementBackstageItemQuality( item );
	}

	private void incrementItemQuality_byOne( Item item ) {
		if( isMinusThanMaxPermittedQualityValue( item ) ) {
			item.setQuality( item.getQuality() + 1 );
		}
	}

	private void incrementBackstageItemQuality( Item item ) {
		if( isMinusThanMaxPermittedQualityValue( item ) ) {
			if( item.getName().equals( BACKSTAGE_ITEM_NAME ) ) {

				incrementBecauseTenDaysBeforeConcert( item );
				incrementBecauseOfFiveDaysBeforeConcert( item );
			}
		}
	}

	private boolean isMinusThanMaxPermittedQualityValue( Item item ) {
		return item.getQuality() < MAX_PERMITTED_QUALITY_VALUE;
	}

	private void incrementBecauseTenDaysBeforeConcert( Item item ) {
		if( item.getSellIn() <= TEN_DAYS_BEFORE_CONCERT ) {
			incrementItemQuality_byOne( item );
		}
	}

	private void incrementBecauseOfFiveDaysBeforeConcert( Item item ) {
		if( item.getSellIn() <= FIVE_DAYS_BEFORE_CONCERT ) {
			incrementItemQuality_byOne( item );
		}
	}
}