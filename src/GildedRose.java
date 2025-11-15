public class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED = "Conjured Mana Cake";
    private static final String ETERNAL_ARTIFACT = "Eternal Artifact";
    private static final String PERISHABLE_KEYWORD = "Perishable";

    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;
    private static final int BACKSTAGE_THRESHOLD_1 = 11;
    private static final int BACKSTAGE_THRESHOLD_2 = 6;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItem(item);
        }
    }

    private void updateItem(Item item) {
        if (item.name.equals(SULFURAS)) {
            return; 
        }

        updateStandardQuality(item);
        
        if (!item.name.equals(ETERNAL_ARTIFACT)) {
            item.sellIn--;
        }

        if (item.sellIn < 0) {
            handleExpiration(item);
        }
    }

    private void updateStandardQuality(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            increaseQuality(item);
        } else if (item.name.equals(BACKSTAGE_PASSES)) {
            increaseQuality(item);
            if (item.sellIn < BACKSTAGE_THRESHOLD_1) {
                increaseQuality(item);
            }
            if (item.sellIn < BACKSTAGE_THRESHOLD_2) {
                increaseQuality(item);
            }
        } else if (item.name.equals(ETERNAL_ARTIFACT)) {
             if (item.sellIn % 2 == 0) {
                increaseQuality(item);
            }
        } else if (item.name.equals(CONJURED)) {
             increaseQuality(item); 
        } else {
            decreaseQuality(item);
            if (item.name.contains(PERISHABLE_KEYWORD)) {
                decreaseQuality(item);
            }
        }
    }

    private void handleExpiration(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            increaseQuality(item);
        } else if (item.name.equals(BACKSTAGE_PASSES)) {
            item.quality = 0;
        } else if (item.name.equals(ETERNAL_ARTIFACT)) {
            increaseQuality(item);
        } else if (item.name.equals(CONJURED)) {
            decreaseQuality(item);
        } else {
            decreaseQuality(item);
            if (item.name.contains(PERISHABLE_KEYWORD)) {
                decreaseQuality(item);
                decreaseQuality(item);
            }
        }
    }
    
    private void increaseQuality(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality++;
        }
    }

    private void decreaseQuality(Item item) {
        if (item.quality > MIN_QUALITY) {
            item.quality--;
        }
    }
}
