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

	private List<Item> items;

	public Inn( List<Item> items ) {
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void updateQuality() {
		for( int item = 0; item < items.size(); item++ ) {

			if( isNotBrieOrBackstageItem( item ) ) {
				decrementItemQualityForNormalItem( item );
			} else {
				increaseQualityForBrieOrBackstageItem( item );
			}

			decrementSellinForItem( item );

			if( concertDateIsPassed( item ) ) {
				if( isNotBrieItem( item ) ) {
					if( isNotBackstageItem( item ) ) {
				    	decrementItemQualityForNormalItem( item );
					} else {
						items.get( item ).setQuality( MIN_PERMITTED_QUALITY_VALUE );
					}
				} else {
					incrementItemQuality( item );
				}
			}
		}
	}

	private boolean concertDateIsPassed( int item ) {
		return items.get( item ).getSellIn() < 0;
	}

	private Boolean isNotBrieOrBackstageItem( int item ) {
		return isNotBrieItem( item ) && isNotBackstageItem( item );
	}

	private boolean isNotBrieItem( int item ) {
		return !items.get( item ).getName().equals( BRIE_ITEM_NAME );
	}

	private boolean isNotBackstageItem( int item ) {
		return !items.get( item ).getName().equals( BACKSTAGE_ITEM_NAME );
	}

	private void decrementSellinForItem( int item ) {
		if( !items.get( item ).getName().equals( LEGENDARY_ITEM_NAME ) ) {
			items.get( item ).setSellIn( items.get( item ).getSellIn() - 1 );
		}
	}

	private void decrementItemQualityForNormalItem( int item ) {
		if( items.get( item ).getQuality() > MIN_PERMITTED_QUALITY_VALUE ) {
			if( !items.get( item ).getName().equals( LEGENDARY_ITEM_NAME ) ) {
				items.get( item ).setQuality( items.get( item ).getQuality() - 1 );
			}

			/*if( items.get( i ).getName().equals( CONJURED_ITEM_NAME ) ) {
				decrementItemQualityForNormalItem( i );
			}*/
		}
	}

	private void increaseQualityForBrieOrBackstageItem( int item ) {
		incrementItemQuality( item );

		if( isMinusThan50( item ) ) {
			if( items.get( item ).getName().equals( BACKSTAGE_ITEM_NAME ) ) {
				if( items.get( item ).getSellIn() < 11 ) {
					incrementItemQuality( item );
				}

				if( items.get( item ).getSellIn() < 6 ) {
					incrementItemQuality( item );
				}
			}
		}
	}

	private boolean isMinusThan50( int item ) {
		return items.get( item ).getQuality() < MAX_PERMITTED_QUALITY_VALUE;
	}

	private void incrementItemQuality( int item ) {
		if( isMinusThan50( item ) ) {
			items.get( item ).setQuality( items.get( item ).getQuality() + 1 );
		}
	}
}