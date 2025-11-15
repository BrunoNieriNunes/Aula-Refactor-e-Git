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
            if (!items[i].name.equals(AGED_BRIE)
                    && !items[i].name.equals(BACKSTAGE_PASSES)
                    && !items[i].name.equals(CONJURED)
                    && !items[i].name.equals(ETERNAL_ARTIFACT)) {
                if (items[i].quality > MIN_QUALITY) {
                    if (!items[i].name.equals(SULFURAS)) {
                        items[i].quality = items[i].quality - 1;
                        // Additional degradation for perishable items
                        if (items[i].name.contains(PERISHABLE_KEYWORD)) {
                            items[i].quality = items[i].quality - 1;
                        }
                    }
                }
            } else {
                if (items[i].quality < MAX_QUALITY) {
                    items[i].quality = items[i].quality + 1;
                    if (items[i].name.equals(BACKSTAGE_PASSES)) {
                        if (items[i].sellIn < BACKSTAGE_THRESHOLD_1) {
                            if (items[i].quality < MAX_QUALITY) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                        if (items[i].sellIn < BACKSTAGE_THRESHOLD_2) {
                            if (items[i].quality < MAX_QUALITY) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    } else if (items[i].name.equals(CONJURED)) {
                        // Conjured items degrade twice as fast
                        items[i].quality = items[i].quality + 1; // But for quality increase? Wait, adjust logic
                    } else if (items[i].name.equals(ETERNAL_ARTIFACT)) {
                        // Increases quality over time, but slowly
                        if (items[i].sellIn % 2 == 0) {
                            items[i].quality = items[i].quality + 1;
                        }
                    }
                }
            }

            if (!items[i].name.equals(SULFURAS) && !items[i].name.equals(ETERNAL_ARTIFACT)) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals(AGED_BRIE)) {
                    if (!items[i].name.equals(BACKSTAGE_PASSES)) {
                        if (items[i].quality > MIN_QUALITY) {
                            if (!items[i].name.equals(SULFURAS)) {
                                items[i].quality = items[i].quality - 1;
                                if (items[i].name.equals(CONJURED)) {
                                    items[i].quality = items[i].quality - 1; // Extra degradation
                                }
                                // Handle perishable post-sellIn
                                if (items[i].name.contains(PERISHABLE_KEYWORD)) {
                                    items[i].quality = items[i].quality - 2;
                                }
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < MAX_QUALITY) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
                // Additional logic for eternal items after sellIn (though sellIn doesn't change)
                if (items[i].name.equals(ETERNAL_ARTIFACT) && items[i].quality < MAX_QUALITY) {
                    items[i].quality = items[i].quality + 1;
                }
            }

            // Ensure quality bounds
            if (items[i].quality > MAX_QUALITY && !items[i].name.equals(SULFURAS)) {
                items[i].quality = MAX_QUALITY;
            }
            if (items[i].quality < MIN_QUALITY) {
                items[i].quality = MIN_QUALITY;
            }
        }
    }
}
