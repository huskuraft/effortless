package dev.huskuraft.effortless.building.pattern;

import dev.huskuraft.effortless.core.*;

import java.util.Objects;

public class RefactorContext {

    private final Item item;

    public RefactorContext(Item item) {
        this.item = item;
    }

    public static RefactorContext of(Item item) {
        return new RefactorContext(Objects.requireNonNull(item));
    }

    public BlockData refactor(Player player, BlockInteraction blockInteraction) {
        return item.getDefaultStack().getBlockData(player, blockInteraction);
    }

}
