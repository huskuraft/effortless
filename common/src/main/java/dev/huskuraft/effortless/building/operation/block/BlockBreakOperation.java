package dev.huskuraft.effortless.building.operation.block;

import java.util.Collections;

import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.building.Context;
import dev.huskuraft.effortless.building.Storage;
import dev.huskuraft.effortless.building.pattern.MirrorContext;
import dev.huskuraft.effortless.building.pattern.MoveContext;
import dev.huskuraft.effortless.building.pattern.RefactorContext;
import dev.huskuraft.effortless.building.pattern.RotateContext;

public class BlockBreakOperation extends BlockOperation {

    public BlockBreakOperation(
            World world,
            Player player,
            Context context,
            Storage storage, // for preview
            BlockInteraction interaction
    ) {
        super(world, player, context, storage, interaction, world.getBlockState(interaction.getBlockPosition()));
    }


    @Override
    public BlockBreakOperationResult commit() {
        var inputs = Collections.<ItemStack>emptyList();
        var outputs = Collections.singletonList(getItemStack());
        var result = destroyBlock();

        if (getWorld().isClient() && getContext().isPreviewOnceType() && getBlockPosition().toVector3d().distance(player.getEyePosition()) <= 32) {
            if (result.success()) {
                getPlayer().getClient().getParticleEngine().destroy(getBlockPosition(), getBlockState());
            } else {
                getPlayer().getClient().getParticleEngine().crack(getBlockPosition(), getInteraction().getDirection());

            }
        }

        return new BlockBreakOperationResult(this, result, inputs, outputs);
    }

    @Override
    public BlockBreakOperation move(MoveContext moveContext) {
        return new BlockBreakOperation(world, player, context, storage, moveContext.move(interaction));
    }

    @Override
    public BlockBreakOperation mirror(MirrorContext mirrorContext) {
        return new BlockBreakOperation(world, player, context, storage, mirrorContext.mirror(interaction));
    }

    @Override
    public BlockBreakOperation rotate(RotateContext rotateContext) {
        return new BlockBreakOperation(world, player, context, storage, rotateContext.rotate(interaction));
    }

    @Override
    public BlockBreakOperation refactor(RefactorContext source) {
        return this;
    }

    @Override
    public Type getType() {
        return Type.BREAK;
    }

    private ItemStack getItemStack() {
        return world.getBlockState(interaction.getBlockPosition()).getItem().getDefaultStack();
    }
}
