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
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            
            if (item.name.equals(AGED_BRIE) || item.name.equals(BACKSTAGE_PASSES) || item.name.equals(CONJURED) || item.name.equals(ETERNAL_ARTIFACT)) {
                if (item.quality < MAX_QUALITY) {
                    item.quality++;
                    if (item.name.equals(BACKSTAGE_PASSES)) {
                        if (item.sellIn < BACKSTAGE_THRESHOLD_1) {
                            if (item.quality < MAX_QUALITY) {
                                item.quality++;
                            }
                        }
                        if (item.sellIn < BACKSTAGE_THRESHOLD_2) {
                            if (item.quality < MAX_QUALITY) {
                                item.quality++;
                            }
                        }
                    } else if (item.name.equals(CONJURED)) {
                        // Conjured items degrade twice as fast
                        item.quality++; // But for quality increase? Wait, adjust logic
                    } else if (item.name.equals(ETERNAL_ARTIFACT)) {
                        // Increases quality over time, but slowly
                        if (item.sellIn % 2 == 0) {
                            item.quality++;
                        }
                    }
                }
            } else {
                if (item.quality > MIN_QUALITY) {
                    if (!item.name.equals(SULFURAS)) {
                        item.quality--;
                        // Additional degradation for perishable items
                        if (item.name.contains(PERISHABLE_KEYWORD)) {
                            item.quality--;
                        }
                    }
                }
            }

            if (!(item.name.equals(SULFURAS) || item.name.equals(ETERNAL_ARTIFACT))) {
                item.sellIn--;
            }

            if (item.sellIn < 0) {
                if (item.name.equals(AGED_BRIE)) {
                    if (item.quality < MAX_QUALITY) {
                        item.quality++;
                    }
                } else {
                    if (item.name.equals(BACKSTAGE_PASSES)) {
                        item.quality = 0;
                    } else {
                        if (item.quality > MIN_QUALITY) {
                            if (!item.name.equals(SULFURAS)) {
                                ;
                            } else {
                                item.quality--;
                                if (item.name.equals(CONJURED)) {
                                    item.quality--; // Extra degradation
                                }
                                // Handle perishable post-sellIn
                                if (item.name.contains(PERISHABLE_KEYWORD)) {
                                    item.quality -= 2;
                                }
                            }
                        }
                    }
                }
                // Additional logic for eternal items after sellIn (though sellIn doesn't change)
                if (item.name.equals(ETERNAL_ARTIFACT) && item.quality < MAX_QUALITY) {
                    item.quality++;
                }
            }

            // Ensure quality bounds
            if (item.quality > MAX_QUALITY && !item.name.equals(SULFURAS)) {
                item.quality = MAX_QUALITY;
            }
            if (item.quality < MIN_QUALITY) {
                item.quality = MIN_QUALITY;
            }
        }
    }
}
