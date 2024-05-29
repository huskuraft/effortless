package dev.huskuraft.effortless.building.operation.block;

import java.util.List;

import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.building.operation.BlockSummary;
import dev.huskuraft.effortless.building.operation.Operation;
import dev.huskuraft.effortless.building.operation.empty.EmptyOperation;

public class BlockInteractOperationResult extends BlockOperationResult {

    public BlockInteractOperationResult(
            BlockInteractOperation operation,
            Type result,
            BlockState oldBlockState,
            BlockState newBlockState
    ) {
        super(operation, result, oldBlockState, newBlockState);
    }

    @Override
    public Operation getReverseOperation() {
        return new EmptyOperation();
    }

    @Override
    public List<BlockState> getBlockSummary(BlockSummary blockSummary) {
        switch (blockSummary) {
            case BLOCKS_INTERACTED -> {
                switch (result) {
                    case SUCCESS, SUCCESS_PARTIAL, CONSUME -> {
                        return List.of(getOriginalBlockState());
                    }
                }
            }
            case BLOCKS_NOT_INTERACTABLE -> {
                switch (result) {
                    case FAIL_BREAK_REPLACE_RULE, FAIL_WORLD_BORDER, FAIL_WORLD_HEIGHT -> {
                        return List.of(getOriginalBlockState());
                    }
                }
            }
            case BLOCKS_BLACKLISTED -> {
                switch (result) {
                    case FAIL_INTERACT_BLACKLISTED -> {
                        return List.of(getOriginalBlockState());
                    }
                }
            }
            case BLOCKS_NO_PERMISSION -> {
                switch (result) {
                    case FAIL_INTERACT_NO_PERMISSION -> {
                        return List.of(getOriginalBlockState());
                    }
                }
            }
            case BLOCKS_TOOLS_INSUFFICIENT -> {
                switch (result) {
                    case FAIL_BREAK_TOOL_INSUFFICIENT -> {
                        return List.of(getOriginalBlockState());
                    }
                }
            }

        }
        return List.of();
    }


}
